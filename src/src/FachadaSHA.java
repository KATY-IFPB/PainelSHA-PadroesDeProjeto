package src;


import java.util.HashMap;
import java.util.Map;

import hidrometro.CalculoTarifaStrategy;
import hidrometro.EstadoHidrometro;
import hidrometro.Hidrometro;
import hidrometro.TarifaResidencial;
import usuario.Usuario;
import usuario.UsuarioFacade;

/**
 * FachadaSHA — Singleton
 * 
 * Ponto central de comunicação entre o Painel e o subsistema.
 */
public class FachadaSHA {

    // Instância única 
    private static FachadaSHA instancia;
    private boolean inicializado;
    private Usuario usuarioLogado;
    private UsuarioFacade usuarioFacade;

    // Mapa de hidrômetros
    private final Map<String, Hidrometro> hidrômetros = new HashMap<>();

    // Estratégia de cálculo de tarifa (Strategy)
    private CalculoTarifaStrategy estrategiaTarifa;

    /**
     * Construtor privado → impede que outras classes criem instâncias.
     */
    private FachadaSHA() {
        this.estrategiaTarifa = new TarifaResidencial();
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

    // =====================================================
    //  CONFIGURAÇÕES
    // =====================================================

    public void setEstrategiaTarifa(CalculoTarifaStrategy e) {
        this.estrategiaTarifa = e;
    }

    // =====================================================
    //  OPERAÇÕES PRINCIPAIS
    // =====================================================

    public void cadastrarHidrometro(String id) {
        if (hidrômetros.containsKey(id)) {
            throw new IllegalArgumentException("Hidrômetro já existe!");
        }
        hidrômetros.put(id, new Hidrometro(id));
    }

    public Hidrometro consultarHidrometro(String id) {
        return hidrômetros.get(id);
    }

    public void registrarConsumo(String id, double litros) {
        Hidrometro h = consultarHidrometro(id);
        if (h == null) {
            throw new IllegalArgumentException("Hidrômetro não encontrado.");
        }

        h.registrarConsumo(litros);
    }

    public double calcularConta(String id) {
        Hidrometro h = consultarHidrometro(id);
        if (h == null) {
            throw new IllegalArgumentException("Hidrômetro não encontrado.");
        }

        double consumo = h.getConsumoTotal();
        return estrategiaTarifa.calcular(consumo);
    }

    public void mudarEstado(String id, EstadoHidrometro novoEstado) {
        Hidrometro h = consultarHidrometro(id);
        if (h == null) {
            throw new IllegalArgumentException("Hidrômetro não encontrado.");
        }

        h.setEstado(novoEstado);
    }

	public void iniciar() {
		if(!inicializado) {
			inicializado=true;
			usuarioFacade = UsuarioFacade.getInstance();
		}
		
	}

	public void fazerLogin(String login, String senha) throws LoginException {
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
			
		}
		
	}
	public boolean temUsuarioLogado() {
		return usuarioLogado != null;
	}
}


