package src;


import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Fachada para operações relacionadas aos usuários.
 * Implementada como Singleton para garantir apenas uma instância global.
 */
public class UsuarioFacade {

    private static UsuarioFacade instancia;    // única instância
    private UsuarioDAO usuarioDao;             // acesso ao DAO
    private Usuario usuarioLogado;
    private boolean temUsuarioLogado;
    

    /**
     * Construtor privado para impedir criação externa.
     */
    private UsuarioFacade() {
        // Inicializa o DAO usando um arquivo padrão
        usuarioDao = UsuarioDAO.getInstance();
        temUsuarioLogado=false;
    }

    /**
     * Método estático que retorna a instância única da fachada.
     */
    public static synchronized UsuarioFacade getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioFacade();
        }
        return instancia;
    }

    /**
     * Registra um novo usuário no sistema.
     * A fachada esconde os detalhes de persistência.
     */
    public void cadastrarUsuario(String login, String nome, String senha)
            throws UsuarioException, IOException {

        Usuario usuario = new Usuario(login, nome, senha);
        usuarioDao.salvar(usuario);
    }
    
    /**
     * Remove um usuário no sistema.
     * 
     */
    public void removerUsuario(int id)
            throws UsuarioException, IOException {

        
        usuarioDao.remover(id);
    }

    /**
     * Atualiza o cadastro do usuario no sistema.
     * .
     */
    public void updateUsuario(int id, String nome, String senha)
            throws UsuarioException, IOException {

        
        usuarioDao.update(id,nome,senha);
    }
    
    /**
     * Lista todos os usuários cadastrados.
     */
    public Map<Integer,Usuario> listarUsuarios() throws IOException {
        return (Map<Integer, Usuario>) usuarioDao.listarTodos();
    }

    /**
     * Busca um usuário pelo ID.
     */
    public Usuario buscarPorLogin(String login) throws IOException {
        
        return usuarioDao.buscarPorLogin(login); 
    }
}
