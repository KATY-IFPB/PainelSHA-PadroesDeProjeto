package hidrometroSemOCR;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import usuario.UsuarioDAO;

public class HidrometroFacade {

	private static HidrometroFacade instance;
	private HidrometroDAO hidrometroDao;

	/**
	 * Construtor privado para impedir criação externa.
	 */

	private HidrometroFacade() {
		// Inicializa o DAO usando um arquivo padrão
		hidrometroDao = HidrometroDAO.getInstance();
		hidrometroDao.inicializarSistema();
	}

	/**
	 * Obtém a instância única do HidrometroFacade (Singleton)
	 * 
	 * @return instância única do HidrometroFacade
	 */
	public static HidrometroFacade getInstance() {
		if (instance == null) {
			instance = new HidrometroFacade();
		}
		return instance;
	}

	/**
	 * Reseta a instância (útil para testes)
	 */
	public static void resetInstance() {
		instance = null;
	}

	
//====================================================================================== metodos usados no projeto

	/**
	 * Adiciona um hidrômetro ao mapa
	 * 
	 * @param id         identificador único do hidrômetro
	 * @param hidrometro objeto Hidrometro
	 * @return true se adicionado com sucesso, false se o ID já existe
	 */
	public void adicionarHidrometro(Double leituraInicial) {
		
		hidrometroDao.adicionarHidrometro(leituraInicial);
	}

	/**
	 * Lista todos os hidrômetros cadastrados
	 * 
	 * @return mapa com todos os hidrômetros
	 */
	public List<Hidrometro> listarHidrometros() {
		return hidrometroDao.getHidrometros();
	}

	public boolean existeHidrometro(String id) {
		// TODO Auto-generated method stub
		return hidrometroDao.existeHidrometro(id);
	}

	public void removerHidrometro(String id) {
		hidrometroDao.removerHidrometro(id);
		
	}
}