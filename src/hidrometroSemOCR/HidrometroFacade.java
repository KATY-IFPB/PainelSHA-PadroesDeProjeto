package hidrometroSemOCR;

import java.util.HashMap;
import java.util.Map;

public class HidrometroFacade {
    
    private static HidrometroFacade instance;
    private Map<String, Hidrometro> hidrometros;
    
    /**
     * Construtor privado para implementar Singleton
     */
    private HidrometroFacade() {
        this.hidrometros = new HashMap<>();
    }
    
    /**
     * Obtém a instância única do HidrometroFacade (Singleton)
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
    
    /**
     * Adiciona um hidrômetro ao mapa
     * @param id identificador único do hidrômetro
     * @param hidrometro objeto Hidrometro
     * @return true se adicionado com sucesso, false se o ID já existe
     */
    public boolean adicionarHidrometro(String id, Hidrometro hidrometro) {
        if (hidrometros.containsKey(id)) {
            System.out.println("Erro: Hidrômetro com ID " + id + " já existe!");
            return false;
        }
        hidrometros.put(id, hidrometro);
        System.out.println("Hidrômetro " + id + " adicionado com sucesso!");
        return true;
    }
    
    /**
     * Remove um hidrômetro do mapa
     * @param id identificador do hidrômetro a ser removido
     * @return true se removido com sucesso, false se não encontrado
     */
    public boolean removerHidrometro(String id) {
        if (!hidrometros.containsKey(id)) {
            System.out.println("Erro: Hidrômetro com ID " + id + " não encontrado!");
            return false;
        }
        Hidrometro hidrometro = hidrometros.get(id);
        if (hidrometro.estaRodando()) {
            hidrometro.pararLeitura();
        }
        hidrometros.remove(id);
        System.out.println("Hidrômetro " + id + " removido com sucesso!");
        return true;
    }
    
    /**
     * Obtém um hidrômetro específico pelo ID
     * @param id identificador do hidrômetro
     * @return objeto Hidrometro ou null se não encontrado
     */
    public Hidrometro getHidrometro(String id) {
        return hidrometros.get(id);
    }
    
    /**
     * Lista todos os hidrômetros cadastrados
     * @return mapa com todos os hidrômetros
     */
    public Map<String, Hidrometro> listarHidrometros() {
        return new HashMap<>(hidrometros);
    }
    
    /**
     * Retorna a quantidade de hidrômetros cadastrados
     * @return número de hidrômetros
     */
    public int quantidadeHidrometros() {
        return hidrometros.size();
    }
    
    /**
     * Verifica se existe um hidrômetro com o ID informado
     * @param id identificador do hidrômetro
     * @return true se existe, false caso contrário
     */
    public boolean existeHidrometro(String id) {
        return hidrometros.containsKey(id);
    }
    
    /**
     * Inicia a leitura de um hidrômetro específico
     * @param id identificador do hidrômetro
     * @return true se iniciado com sucesso, false se não encontrado
     */
    public boolean iniciarHidrometro(String id) {
        Hidrometro hidrometro = hidrometros.get(id);
        if (hidrometro == null) {
            System.out.println("Erro: Hidrômetro com ID " + id + " não encontrado!");
            return false;
        }
        if (!hidrometro.estaRodando()) {
            hidrometro.start();
            return true;
        }
        System.out.println("Hidrômetro " + id + " já está em execução!");
        return false;
    }
    
    /**
     * Para a leitura de um hidrômetro específico
     * @param id identificador do hidrômetro
     * @return true se parado com sucesso, false se não encontrado
     */
    public boolean pararHidrometro(String id) {
        Hidrometro hidrometro = hidrometros.get(id);
        if (hidrometro == null) {
            System.out.println("Erro: Hidrômetro com ID " + id + " não encontrado!");
            return false;
        }
        if (hidrometro.estaRodando()) {
            hidrometro.pararLeitura();
            return true;
        }
        System.out.println("Hidrômetro " + id + " já está parado!");
        return false;
    }
    
    /**
     * Para todos os hidrômetros em execução
     */
    public void pararTodosHidrometros() {
        for (Map.Entry<String, Hidrometro> entry : hidrometros.entrySet()) {
            if (entry.getValue().estaRodando()) {
                entry.getValue().pararLeitura();
            }
        }
        System.out.println("Todos os hidrômetros foram parados!");
    }
    
    /**
     * Imprime informações de todos os hidrômetros
     */
    public void imprimirStatusHidrometros() {
        System.out.println("\n=== Status dos Hidrômetros ===");
        System.out.println("Total: " + hidrometros.size() + " hidrômetro(s)");
        for (Map.Entry<String, Hidrometro> entry : hidrometros.entrySet()) {
            String id = entry.getKey();
            Hidrometro h = entry.getValue();
            String status = h.estaRodando() ? "RODANDO" : "PARADO";
            System.out.printf("ID: %s | Status: %s | Leitura: %.3f m³%n", 
                            id, status, h.getLeituraAtual());
        }
        System.out.println("==============================\n");
    }
    
}