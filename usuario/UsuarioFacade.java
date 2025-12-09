package usuario;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

/**
 * Fachada para operações relacionadas aos usuários.
 * Implementada como Singleton para garantir apenas uma instância global.
 */
public class UsuarioFacade {

    private static UsuarioFacade instancia;    // única instância
    private UsuarioDAO usuarioDao;             // acesso ao DAO
    private Usuario usuarioLogado;
    private boolean temUsuarioLogado;
    //private Map<String,Usuario> usuariosDoSistema;
    

    /**
     * Construtor privado para impedir criação externa.
     */
    private UsuarioFacade() {
        // Inicializa o DAO usando um arquivo padrão
        usuarioDao = UsuarioDAO.getInstance();
        usuarioDao.inicializarSistema();
        usuarioLogado=null;
        
        temUsuarioLogado=false;
        
        
    }

    /**
     * Método estático que retorna a instância única da fachada.
     */
    public static synchronized UsuarioFacade getInstance() {
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
     * 
     */
    /*
     * <CPF>, Usuario
     */
    public List<Usuario> listarUsuarios() throws IOException {
        return  usuarioDao.listarUsuarios();
    }

    /**
     * Busca um usuário pelo ID.
     */
    public Usuario buscarPorLogin(String login) throws IOException {
        
        return usuarioDao.buscarPorLogin(login); 
    }

	public Usuario autenticarUsuario(String login, String senha) throws LoginException {
		
		
			if (usuarioDao.containsKey(login) ){
				Usuario usuario = usuarioDao.buscarPorLogin(login);
				if (usuario.getSenha().equals(senha)) {
					usuarioLogado = usuario;
					temUsuarioLogado=true;
					return usuario;
				}else {
					throw new LoginException("Senha incorreta.");
				}
			}
		
		
		return null;
		
	
	}
}
