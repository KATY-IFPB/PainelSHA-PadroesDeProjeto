package hidrometroSemOCR;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.Configuracoes;
import src.Messages;


public class HidrometroDAO {

    private static final String ARQUIVO = "hidrometros.txt";

    // ===== Singleton =====
    private static HidrometroDAO instance;
    private final File arquivo;
	private Map<String, Hidrometro> mapaHidrometros;

    private HidrometroDAO(String caminhoArquivo) {
    	this.arquivo = new File(caminhoArquivo);
    	mapaHidrometros = new HashMap<>();
    }

    public static synchronized HidrometroDAO getInstance() {
        if (instance == null) {
        	instance = new HidrometroDAO(Configuracoes.getString("NomeArquivoDeHidrometros")); //$NON-NLS-1$ );
        }
        return instance;
    }

    public static synchronized void resetInstance() {
		instance = null;
	}
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
			// TODO Auto-generated catch block1
			
			e.printStackTrace();
		}
    }
    /**
     * Salva ou atualiza a leitura de um hidrômetro no arquivo.
     * Se o ID já existir, a leitura é atualizada.
     */
    private synchronized void salvar(String id, double leitura) throws IOException {
        Map<String, Double> dados = lerTodos();
        dados.put(id, leitura);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Map.Entry<String, Double> entry : dados.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }
        }
    }

    /**
     * Lê a leitura de um hidrômetro específico pelo ID.
     */
    public synchronized Double lerPorId(String id) throws IOException {
        return lerTodos().get(id);
    }

    /**
     * Lê todos os hidrômetros do arquivo.
     */
   private synchronized Map<String, Double> lerTodos() throws IOException {
        Map<String, Double> dados = new HashMap<>();
        File file = new File(ARQUIVO);

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
     */
    public synchronized void remover(String id) throws IOException {
        Map<String, Double> dados = lerTodos();
        dados.remove(id);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Map.Entry<String, Double> entry : dados.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }
        }
    }
    public Map<String, Hidrometro> getMapaHidrometros() {
    			return mapaHidrometros;
    }

	public synchronized void adicionarHidrometro(Double leituraInicial) {
		try {
			Hidrometro hidrometro = new Hidrometro(leituraInicial);
			new Thread(hidrometro).start();
			mapaHidrometros.put(hidrometro.getIdentificador(), hidrometro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public synchronized List<Hidrometro> getHidrometros() {
		
		return mapaHidrometros.values().stream().toList();
	}

	public boolean existeHidrometro(String id) {
		// TODO Auto-generated method stub
		return mapaHidrometros.containsKey(id);
	}

	public void removerHidrometro(String id) {
		Hidrometro hidrometro = mapaHidrometros.get(id);
		hidrometro.pararLeitura();
		mapaHidrometros.remove(id);
		
	}
}
