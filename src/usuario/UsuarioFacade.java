package usuario;

import java.io.IOException;
import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * UsuarioFacade
 *
 * Fachada responsável por centralizar todas as operações
 * relacionadas a usuários do sistema.
 *
 * Aplica os padrões:
 * - Facade → simplifica o acesso ao subsistema de usuários
 * - Singleton → garante uma única instância global
 *
 * A camada de apresentação (Painel) nunca acessa o DAO diretamente,
 * apenas esta fachada.
 */
public class UsuarioFacade {

    // ===================== ATRIBUTOS =====================

    /** Instância única da fachada (Singleton) */
    private static UsuarioFacade instancia;

    /** DAO responsável pela persistência dos usuários */
    private UsuarioDAO usuarioDao;

    /** Usuário atualmente logado no sistema */
    private Usuario usuarioLogado;

    /** Indica se existe um usuário logado */
    private boolean temUsuarioLogado;

    // ===================== CONSTRUTOR =====================

    /**
     * Construtor privado.
     * Impede criação externa e força o uso do getInstance().
     */
    private UsuarioFacade() {
        usuarioDao = UsuarioDAO.getInstance();
        usuarioDao.inicializarSistema(); // carrega usuários do arquivo
        usuarioLogado = null;
        temUsuarioLogado = false;
    }

    // ===================== SINGLETON =====================

    /**
     * Retorna a instância única da fachada.
     *
     * @return UsuarioFacade
     */
    public static synchronized UsuarioFacade getInstance() {
        if (instancia == null) {
            instancia = new UsuarioFacade();
        }
        return instancia;
    }

    // ===================== CASOS DE USO =====================

    /**
     * Cadastra um novo usuário no sistema.
     *
     * A fachada cria o objeto Usuario, valida os dados
     * e delega a persistência ao DAO.
     *
     * @param login CPF do usuário
     * @param nome  Nome do usuário
     * @param senha Senha do usuário
     */
    public void cadastrarUsuario(String login, String nome, String senha)
            throws UsuarioException, IOException {

        Usuario usuario = new Usuario(login, nome, senha);
        usuarioDao.salvar(usuario);
    }

    /**
     * Remove um usuário do sistema.
     *
     * Regra de negócio:
     * - Um usuário não pode remover a si próprio enquanto estiver logado.
     *
     * @param id CPF do usuário a ser removido
     */
    public void removerUsuario(String id)
            throws UsuarioException, IOException {

        if (!id.equals(usuarioLogado.getLogin())) {
            usuarioDao.remover(id);
        } else {
            throw new UsuarioException(Messages.getString("UsuarioFacade.0")); //$NON-NLS-1$
        }
    }

    /**
     * Atualiza os dados de um usuário.
     *
     * @param id    CPF do usuário
     * @param nome  Novo nome
     * @param senha Nova senha
     */
    public void updateUsuario(int id, String nome, String senha)
            throws UsuarioException, IOException {

        usuarioDao.update(id + "", nome, senha); //$NON-NLS-1$
    }

    /**
     * Lista todos os usuários cadastrados no sistema.
     *
     * @return lista de usuários
     */
    public List<Usuario> listarUsuarios() throws IOException {
        return usuarioDao.listarUsuarios();
    }

    /**
     * Busca um usuário pelo CPF (login).
     *
     * @param login CPF do usuário
     * @return usuário encontrado ou null
     */
    public Usuario buscarPorLogin(String login) throws IOException {
        return usuarioDao.buscarPorLogin(login);
    }

    /**
     * Autentica um usuário no sistema.
     *
     * Regras:
     * - Login deve existir
     * - Senha deve corresponder
     *
     * @param login CPF
     * @param senha senha
     * @return usuário autenticado
     * @throws LoginException se senha estiver incorreta
     */
    public Usuario autenticarUsuario(String login, String senha) throws LoginException {

        if (usuarioDao.containsKey(login)) {
            Usuario usuario = usuarioDao.buscarPorLogin(login);

            if (usuario.getSenha().equals(senha)) {
                usuarioLogado = usuario;
                temUsuarioLogado = true;
                return usuario;
            } else {
                throw new LoginException(Messages.getString("UsuarioFacade.2")); //$NON-NLS-1$
            }
        }

        return null;
    }

    /**
     * Realiza logout do usuário atual.
     *
     * Também salva o estado dos usuários no arquivo.
     */
    public void logout() {
        usuarioLogado = null;
        temUsuarioLogado = false;
        usuarioDao.salvarEstadoUsuarios();
    }

    // ===================== MÉTODOS AUXILIARES =====================

    /**
     * Retorna o DAO de usuários.
     * Usado internamente por outras fachadas (Observer).
     */
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDao;
    }
}
