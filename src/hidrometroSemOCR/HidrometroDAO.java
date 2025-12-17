package hidrometroSemOCR;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.Configuracoes;
import src.Evento;
import src.Observer;
import src.Subject;

/**
 * DAO responsável pelo gerenciamento dos hidrômetros do sistema.
 *
 * Esta classe:
 * - Implementa o padrão Singleton (instância única)
 * - Gerencia hidrômetros em memória
 * - Realiza persistência em arquivo texto
 * - Implementa o padrão Observer (Subject),
 *   notificando outros módulos quando um hidrômetro é removido
 */
public class HidrometroDAO implements Subject {

    // ===== Singleton =====

    /** Instância única do HidrometroDAO */
    private static HidrometroDAO instance;

    /** Arquivo onde os hidrômetros são persistidos */
    private final File arquivo;

    /** Mapa em memória contendo os hidrômetros (ID → Hidrometro) */
	private Map<String, Hidrometro> mapaHidrometros;

    /** Lista de observers interessados em eventos do hidrômetro */
	private List<Observer> observers = new ArrayList<>();

    /**
     * Construtor privado.
     * Impede criação externa e força o uso do Singleton.
     *
     * @param caminhoArquivo caminho do arquivo de hidrômetros
     */
    private HidrometroDAO(String caminhoArquivo) {
    	this.arquivo = new File(caminhoArquivo);
    	mapaHidrometros = new HashMap<>();
    }

    /**
     * Retorna a instância única do HidrometroDAO.
     *
     * @return instância singleton
     */
    public static synchronized HidrometroDAO getInstance() {
        if (instance == null) {
        	instance = new HidrometroDAO(
                Configuracoes.getString("NomeArquivoDeHidrometros")
            ); //$NON-NLS-1$
        }
        return instance;
    }

    /**
     * Reinicia a instância Singleton.
     * Útil principalmente para testes.
     */
    public static synchronized void resetInstance() {
		instance = null;
	}

    /**
     * Inicializa o sistema carregando os hidrômetros do arquivo
     * e iniciando cada um em sua própria Thread.
     */
    public void inicializarSistema() {
    	try {
			Map<String, Double> hidrometros = lerTodos();
			for (Map.Entry<String, Double> entry : hidrometros.entrySet()) {
				String id = entry.getKey();
				double leitura = entry.getValue();
				Hidrometro hidrometro = new Hidrometro(id, leitura);
				new Thread(hidrometro).start();
				mapaHidrometros.put(id, hidrometro);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * Salva ou atualiza a leitura de um hidrômetro no arquivo.
     *
     * @param id identificador do hidrômetro
     * @param leitura leitura atual
     * @throws IOException em caso de erro de escrita
     */
    private synchronized void salvar(String id, double leitura) throws IOException {
        Map<String, Double> dados = lerTodos();
        dados.put(id, leitura);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (Map.Entry<String, Double> entry : dados.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }
        }
    }

    /**
     * Retorna a leitura de um hidrômetro específico.
     *
     * @param id identificador do hidrômetro
     * @return leitura atual
     */
    public synchronized Double lerPorId(String id) throws IOException {
        return lerTodos().get(id);
    }

    /**
     * Lê todos os hidrômetros armazenados no arquivo.
     *
     * @return mapa contendo ID → leitura
     */
    private synchronized Map<String, Double> lerTodos() throws IOException {
        Map<String, Double> dados = new HashMap<>();
        File file = new File(arquivo.getName());

        if (!file.exists()) {
            return dados;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("-");
                if (partes.length == 2) {
                    String id = partes[0].trim();
                    double leitura = Double.parseDouble(partes[1].trim());
                    dados.put(id, leitura);
                }
            }
        }

        return dados;
    }

    /**
     * Remove um hidrômetro do arquivo.
     *
     * @param id identificador do hidrômetro
     */
    public synchronized void remover(String id) throws IOException {
        Map<String, Double> dados = lerTodos();
        dados.remove(id);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (Map.Entry<String, Double> entry : dados.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }
        }
    }

    /**
     * Retorna o mapa de hidrômetros em memória.
     */
    public Map<String, Hidrometro> getMapaHidrometros() {
    	return mapaHidrometros;
    }

    /**
     * Cria e adiciona um novo hidrômetro ao sistema.
     * O hidrômetro é iniciado em uma nova Thread.
     *
     * @param leituraInicial leitura inicial do hidrômetro
     */
	public synchronized void adicionarHidrometro(Double leituraInicial) {
		try {
			Hidrometro hidrometro = new Hidrometro(leituraInicial);
			new Thread(hidrometro).start();
			mapaHidrometros.put(hidrometro.getIdentificador(), hidrometro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * Retorna todos os hidrômetros cadastrados.
     */
	public synchronized List<Hidrometro> getHidrometros() {
		return mapaHidrometros.values().stream().toList();
	}

    /**
     * Verifica se um hidrômetro existe pelo ID.
     */
	public boolean existeHidrometro(String id) {
		return mapaHidrometros.containsKey(id);
	}

    /**
     * Remove um hidrômetro do sistema.
     *
     * - Para a Thread do hidrômetro
     * - Remove do mapa em memória
     * - Notifica os observers (ex: ContaDAO)
     *
     * @param id identificador do hidrômetro
     */
	public void removerHidrometro(String id) {
		Hidrometro hidrometro = mapaHidrometros.get(id);
		hidrometro.pararLeitura();
		mapaHidrometros.remove(id);

		// Notifica o sistema que o hidrômetro foi removido
		notificarObservers(
		    new Evento(Evento.Tipo.HIDROMETRO_REMOVIDO, id)
		);
	}

    /**
     * Persiste o estado atual de todos os hidrômetros no arquivo.
     */
	public void salvarEstadoHidrometros() {
        try (FileWriter fw = new FileWriter(arquivo.getName(), false)) {
			List<Hidrometro> lista = getHidrometros();
            for (Hidrometro u : lista) {
                String linha = u.getIdentificador() + "-" + u.getLeituraAtual();
                fw.write(linha + System.lineSeparator());
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * Retorna um hidrômetro pelo ID.
     */
	public Hidrometro getHidrometro(String idHidrometro) {
		return mapaHidrometros.get(idHidrometro);
	}

    // ===== Métodos do padrão Observer (Subject) =====

    /**
     * Registra um novo observer.
     */
	@Override
	public void adicionarObserver(Observer o) {
	    if (o != null && !observers.contains(o)) {
	        observers.add(o);
	    }
	}

    /**
     * Remove um observer registrado.
     */
	@Override
	public void removerObserver(Observer o) {
	    observers.remove(o);
	}

    /**
     * Notifica todos os observers sobre um evento ocorrido.
     *
     * @param evento evento disparado
     */
	@Override
	public void notificarObservers(Evento evento) {
	    for (Observer o : observers) {
	        o.atualizar(evento);
	    }
	}
}
