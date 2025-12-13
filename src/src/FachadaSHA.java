package src;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import usuario.Usuario;
import usuario.UsuarioException;
import usuario.UsuarioFacade;

/**
 * FachadaSHA — Singleton
 * 
 * Ponto central de comunicação entre o Painel e o subsistema.
 */
public class FachadaSHA {

	// Instância única 
	private static FachadaSHA instancia;
	private boolean inicializado; //quer dizer se o sistema foi iniciado
	private Usuario usuarioLogado; //usuario logado no sistema
	private UsuarioFacade usuarioFacade;
	private HidrometroFacade hidrometroFacade;

	// Mapa de hidrômetros
	//private final Map<String, Hidrometro> hidrômetros = new HashMap<>();

	// Estratégia de cálculo de tarifa (Strategy)
	//private CalculoTarifaStrategy estrategiaTarifa;

	/**
	 * Construtor privado → impede que outras classes criem instâncias.
	 */
	private FachadaSHA() {
		//this.estrategiaTarifa = new TarifaResidencial();
		this.inicializado = false;
		this.usuarioLogado = null;
	}

	/**
	 * Método de acesso estático → garante instância única.
	 */
	public static synchronized FachadaSHA getInstance() {
		if (instancia == null) {
			instancia = new FachadaSHA();

		}
		return instancia;
	}

	/*
	 * // ===================================================== // CONFIGURAÇÕES //
	 * =====================================================
	 * 
	 * public void setEstrategiaTarifa(CalculoTarifaStrategy e) {
	 * this.estrategiaTarifa = e; }
	 * 
	 * // ===================================================== // OPERAÇÕES
	 * PRINCIPAIS // =====================================================
	 * 
	 * public void cadastrarHidrometro(String id) { if (hidrômetros.containsKey(id))
	 * { throw new IllegalArgumentException("Hidrômetro já existe!"); }
	 * hidrômetros.put(id, new Hidrometro(id)); }
	 * 
	 * public Hidrometro consultarHidrometro(String id) { return
	 * hidrômetros.get(id); }
	 * 
	 * public void registrarConsumo(String id, double litros) { Hidrometro h =
	 * consultarHidrometro(id); if (h == null) { throw new
	 * IllegalArgumentException("Hidrômetro não encontrado."); }
	 * 
	 * h.registrarConsumo(litros); }
	 * 
	 * public double calcularConta(String id) { Hidrometro h =
	 * consultarHidrometro(id); if (h == null) { throw new
	 * IllegalArgumentException("Hidrômetro não encontrado."); }
	 * 
	 * double consumo = h.getConsumoTotal(); return
	 * estrategiaTarifa.calcular(consumo); }
	 * 
	 * public void mudarEstado(String id, EstadoHidrometro novoEstado) { Hidrometro
	 * h = consultarHidrometro(id); if (h == null) { throw new
	 * IllegalArgumentException("Hidrômetro não encontrado."); }
	 * 
	 * h.setEstado(novoEstado); }
	 */

	public void iniciar() {
		if(!inicializado) {
			inicializado=true;
			usuarioFacade = UsuarioFacade.getInstance();
			hidrometroFacade = HidrometroFacade.getInstance();
		}

	}

	public void fazerLogin(String login, String senha) throws LoginException {
		if(!inicializado) {
			throw new LoginException(Messages.getString("Inicialize o sistema antes de fazer login."));
		}else {
			if(usuarioLogado != null) {
				throw new LoginException(Messages.getString("FachadaSHA.0"));
			}else {
				Usuario usuario;
				try {
					usuario = usuarioFacade.autenticarUsuario(login, senha);
					if(usuario == null) {
						throw new LoginException(Messages.getString("FachadaSHA.1"));
					}else {
						usuarioLogado = usuario;
						System.out.println("Login realizado com sucesso. Bem-vindo, " + usuario.getNome() + "!");
					}
				} catch (javax.security.auth.login.LoginException e) {
					System.out.println(e.getMessage());
				}

			}}

	}
	public boolean temUsuarioLogado() {
		return usuarioLogado != null;
	}
	
	public void fazerLogout() {
	    usuarioLogado = null;
	    usuarioFacade.logout(); // Salva o estado dos usuários ao fazer logout
	}

	public boolean isSistemaInicializado() {
		// TODO Auto-generated method stub
		return inicializado;
	}

	public void adicionarUsuario(String nome, String loginCPF, String senha) {
		try {
			usuarioFacade.cadastrarUsuario( loginCPF, nome, senha);
		} catch (UsuarioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Usuario> listarUsuarios() {
		try {
			return usuarioFacade.listarUsuarios();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Usuario getUsuarioLogado() {
		
		return usuarioLogado;
	}

	public void removerUsuario(String cpf) {
		
		if(cpf.equals(usuarioLogado.getLogin())) {
			System.out.println("Usuário logado não pode ser removido.");
			throw new IllegalArgumentException("Usuário logado não pode ser removido.");
		}else {
		try {
			usuarioFacade.removerUsuario(cpf);
		} catch (NumberFormatException | UsuarioException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	public boolean existeUsuario(String cpf) {
		try {
			Usuario u = usuarioFacade.buscarPorLogin(cpf);
			if(u != null) {
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void adicionarHidrometro(double leitura) {
		Hi
		
	}
}


