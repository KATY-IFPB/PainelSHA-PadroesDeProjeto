package src;

import java.io.IOException;
import java.util.List;

import conta.Conta;
import conta.ContaDAO;
import conta.ContaFacade;
import hidrometroSemOCR.Hidrometro;
import hidrometroSemOCR.HidrometroDAO;
import hidrometroSemOCR.HidrometroFacade;
import usuario.Usuario;
import usuario.UsuarioDAO;
import usuario.UsuarioException;
import usuario.UsuarioFacade;

/**
 * FachadaSHA — Singleton
 *
 * Classe que implementa o padrão FACADE.
 * 
 * Ela centraliza toda a comunicação entre a interface do sistema
 * (Painel / CLI / UI) e os subsistemas:
 * - Usuários
 * - Hidrômetros
 * - Contas de água
 *
 * Também é responsável por:
 * - Inicialização do sistema
 * - Controle de login
 * - Configuração do padrão Observer
 */
public class FachadaSHA {

	/** Instância única da fachada (Singleton) */
	private static FachadaSHA instancia;

	/** Indica se o sistema já foi inicializado */
	private boolean inicializado;

	/** Usuário atualmente logado no sistema */
	private Usuario usuarioLogado;

	/** Fachadas dos subsistemas */
	private UsuarioFacade usuarioFacade;
	private HidrometroFacade hidrometroFacade;
	private ContaFacade contaDeAguaFacade;

	/**
	 * Construtor privado.
	 * Impede criação direta da classe e garante o Singleton.
	 */
	private FachadaSHA() {
		this.inicializado = false;
		this.usuarioLogado = null;
	}

	/**
	 * Retorna a instância única da FachadaSHA.
	 *
	 * @return instância única
	 */
	public static synchronized FachadaSHA getInstance() {
		if (instancia == null) {
			instancia = new FachadaSHA();
		}
		return instancia;
	}

	/**
	 * Inicializa o sistema.
	 * 
	 * - Cria as fachadas dos subsistemas
	 * - Configura os observers
	 * - Garante que a inicialização ocorra apenas uma vez
	 */
	public void iniciar() {
		if (!inicializado) {
			inicializado = true;

			usuarioFacade = UsuarioFacade.getInstance();
			hidrometroFacade = HidrometroFacade.getInstance();
			contaDeAguaFacade = ContaFacade.getInstance();

			configurarObservers();
		}
	}

	/**
	 * Configura o padrão Observer entre os DAOs.
	 *
	 * - ContaDAO observa UsuarioDAO
	 * - ContaDAO observa HidrometroDAO
	 *
	 * Assim, quando um usuário ou hidrômetro for removido,
	 * as contas associadas são automaticamente removidas.
	 */
	private void configurarObservers() {

		// Obtém os DAOs através das facades
		UsuarioDAO usuarioDAO = usuarioFacade.getUsuarioDAO();
		HidrometroDAO hidrometroDAO = hidrometroFacade.getHidrometroDAO();
		ContaDAO contaDAO = contaDeAguaFacade.getContaDAO();

		// Registro dos observers
		usuarioDAO.adicionarObserver(contaDAO);
		hidrometroDAO.adicionarObserver(contaDAO);
	}

	/**
	 * Realiza o login de um usuário no sistema.
	 *
	 * @param login CPF/login do usuário
	 * @param senha senha do usuário
	 * @throws LoginException se o sistema não estiver inicializado
	 *                        ou se já houver usuário logado
	 */
	public void fazerLogin(String login, String senha) throws LoginException {

		if (!inicializado) {
			throw new LoginException(
					Messages.getString("Inicialize o sistema antes de fazer login."));
		}

		if (usuarioLogado != null) {
			throw new LoginException(Messages.getString("FachadaSHA.0"));
		}

		try {
			Usuario usuario = usuarioFacade.autenticarUsuario(login, senha);

			if (usuario == null) {
				throw new LoginException(Messages.getString("FachadaSHA.1"));
			}

			usuarioLogado = usuario;
			System.out.println("Login realizado com sucesso. Bem-vindo, "
					+ usuario.getNome() + "!");

		} catch (javax.security.auth.login.LoginException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Indica se existe um usuário logado no sistema.
	 */
	public boolean temUsuarioLogado() {
		return usuarioLogado != null;
	}

	/**
	 * Realiza logout do sistema.
	 * 
	 * Salva o estado de todos os subsistemas antes de encerrar.
	 */
	public void fazerLogout() {
		usuarioLogado = null;

		usuarioFacade.logout();
		hidrometroFacade.logout();
		contaDeAguaFacade.logout();
	}

	/**
	 * Verifica se o sistema já foi inicializado.
	 */
	public boolean isSistemaInicializado() {
		return inicializado;
	}

	/**
	 * Cadastra um novo usuário.
	 */
	public void adicionarUsuario(String nome, String loginCPF, String senha) {
		try {
			usuarioFacade.cadastrarUsuario(loginCPF, nome, senha);
		} catch (UsuarioException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lista todos os usuários cadastrados.
	 */
	public List<Usuario> listarUsuarios() {
		try {
			return usuarioFacade.listarUsuarios();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retorna o usuário atualmente logado.
	 */
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	/**
	 * Remove um usuário do sistema.
	 *
	 * Não permite remover o usuário que está logado.
	 */
	public void removerUsuario(String cpf) {

		if (usuarioLogado != null && cpf.equals(usuarioLogado.getLogin())) {
			throw new IllegalArgumentException("Usuário logado não pode ser removido.");
		}

		try {
			usuarioFacade.removerUsuario(cpf);
		} catch (UsuarioException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifica se um usuário existe.
	 */
	public boolean existeUsuario(String cpf) {
		try {
			return usuarioFacade.buscarPorLogin(cpf) != null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Adiciona um novo hidrômetro ao sistema.
	 */
	public void adicionarHidrometro(double leitura) {
		hidrometroFacade.adicionarHidrometro(leitura);
	}

	/**
	 * Lista todos os hidrômetros cadastrados.
	 */
	public List<Hidrometro> listarHidrometros() {
		return hidrometroFacade.listarHidrometros();
	}

	/**
	 * Verifica se um hidrômetro existe.
	 */
	public boolean existeHidrometro(String id) {
		return hidrometroFacade.existeHidrometro(id);
	}

	/**
	 * Remove um hidrômetro do sistema.
	 *
	 * Dispara evento que remove automaticamente as contas associadas.
	 */
	public void removerHidrometro(String id) {
		hidrometroFacade.removerHidrometro(id);
	}

	/**
	 * Cria uma conta de água vinculada a um usuário e a um hidrômetro.
	 */
	public void criarContaDeAgua(String idUsuario, String idHidrometro) {

		try {
			if (usuarioFacade.buscarPorLogin(idUsuario) == null
					|| !hidrometroFacade.existeHidrometro(idHidrometro)) {
				throw new IllegalArgumentException("Usuário ou hidrômetro não existem.");
			}

			if (contaDeAguaFacade.existeContaParaHidrometro(idHidrometro)) {
				throw new IllegalArgumentException("Já existe uma conta para esse hidrômetro.");
			}

			contaDeAguaFacade.criarContaDeAgua(
					idUsuario,
					idHidrometro,
					hidrometroFacade.buscarPorId(idHidrometro).getLeituraAtual());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lista todas as contas de água.
	 */
	public List<Conta> listarContasDeAgua() {
		return contaDeAguaFacade.listarContasDeAgua();
	}

	/**
	 * Remove uma conta de água.
	 */
	public void removerConta(String id) {
		contaDeAguaFacade.removerConta(id);
	}

	/**
	 * Verifica se uma conta existe.
	 */
	public boolean existeConta(String id) {
		return contaDeAguaFacade.existeConta(id);
	}
}
