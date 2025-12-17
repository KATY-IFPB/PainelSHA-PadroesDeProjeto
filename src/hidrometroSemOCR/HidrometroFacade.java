package hidrometroSemOCR;

import java.util.List;

/**
 * Fachada responsável por fornecer uma interface simples
 * para o gerenciamento de hidrômetros no sistema.
 *
 * Esta classe:
 * - Implementa o padrão Facade
 * - Implementa o padrão Singleton
 * - Centraliza o acesso ao HidrometroDAO
 * - Isola a camada de apresentação das regras internas
 */
public class HidrometroFacade {

	/** Instância única da fachada (Singleton) */
	private static HidrometroFacade instance;

	/** DAO responsável pela persistência e controle dos hidrômetros */
	private HidrometroDAO hidrometroDao;

	/**
	 * Construtor privado.
	 * Impede criação externa e inicializa o sistema de hidrômetros.
	 */
	private HidrometroFacade() {
		hidrometroDao = HidrometroDAO.getInstance();
		hidrometroDao.inicializarSistema();
	}

	/**
	 * Retorna a instância única da fachada de hidrômetros.
	 *
	 * @return instância singleton do HidrometroFacade
	 */
	public static HidrometroFacade getInstance() {
		if (instance == null) {
			instance = new HidrometroFacade();
		}
		return instance;
	}

	/**
	 * Reseta a instância Singleton.
	 * Útil principalmente para testes automatizados.
	 */
	public static void resetInstance() {
		instance = null;
	}

	/**
	 * Cria e adiciona um novo hidrômetro ao sistema.
	 *
	 * @param leituraInicial leitura inicial do hidrômetro
	 */
	public void adicionarHidrometro(Double leituraInicial) {
		hidrometroDao.adicionarHidrometro(leituraInicial);
	}

	/**
	 * Retorna a lista de todos os hidrômetros cadastrados.
	 *
	 * @return lista de hidrômetros
	 */
	public List<Hidrometro> listarHidrometros() {
		return hidrometroDao.getHidrometros();
	}

	/**
	 * Verifica se um hidrômetro existe pelo seu identificador.
	 *
	 * @param id identificador do hidrômetro
	 * @return true se existir, false caso contrário
	 */
	public boolean existeHidrometro(String id) {
		return hidrometroDao.existeHidrometro(id);
	}

	/**
	 * Remove um hidrômetro do sistema.
	 *
	 * A remoção:
	 * - Encerra a Thread do hidrômetro
	 * - Remove o hidrômetro da memória
	 * - Dispara notificações para os observers
	 *
	 * @param id identificador do hidrômetro
	 */
	public void removerHidrometro(String id) {
		hidrometroDao.removerHidrometro(id);
	}

	/**
	 * Finaliza a sessão do sistema de hidrômetros,
	 * salvando o estado atual em arquivo.
	 */
	public void logout() {
		hidrometroDao.salvarEstadoHidrometros();
	}

	/**
	 * Busca um hidrômetro pelo identificador.
	 *
	 * @param idHidrometro identificador do hidrômetro
	 * @return objeto Hidrometro ou null se não existir
	 */
	public Hidrometro buscarPorId(String idHidrometro) {
		return hidrometroDao.getHidrometro(idHidrometro);
	}

	/**
	 * Retorna o DAO interno de hidrômetros.
	 *
	 * Método útil para:
	 * - Registro de observers
	 * - Testes
	 * - Integração com outros módulos (ex: ContaDAO)
	 *
	 * @return instância do HidrometroDAO
	 */
	public HidrometroDAO getHidrometroDAO() {
		return hidrometroDao;
	}
}
