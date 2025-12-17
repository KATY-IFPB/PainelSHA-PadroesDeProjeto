package hidrometroSemOCR;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.Messages;

/**
 * Classe que representa um Hidrômetro.
 *
 * Esta classe:
 * - Estende Thread para simular leituras periódicas
 * - Gera consumo automaticamente em intervalos fixos
 * - Implementa o padrão Observer (customizado) para notificar
 *   quando o limite máximo de leitura é ultrapassado
 */
public class Hidrometro extends Thread {

    /** Limite máximo permitido para a leitura do hidrômetro */
	private static final double LIMITE_MAX = 9_999_999.0;

    /** Identificador único do hidrômetro */
	private String id;

    /** Leitura atual do hidrômetro */
    private double leituraAtual;

    /** Flag de controle da execução da Thread */
    private volatile boolean rodando;

    /** Intervalo de tempo entre leituras (em milissegundos) */
    private long intervalo = 5000;	

    /** Gerador de números aleatórios para simular consumo */
    private Random random;

    /** Formatador de data/hora para exibição */
    private DateTimeFormatter formatter;

    // -------- LISTA DE OBSERVADORES --------

    /**
     * Lista de observadores interessados em eventos do hidrômetro,
     * como o estouro do limite máximo de leitura.
     */
    private List<HidrometroObserver> observers = new ArrayList<>();

    // ---------------------------------------

    /**
     * Construtor padrão.
     * Inicializa leitura com 0 e intervalo padrão de 5 segundos.
     */
    public Hidrometro() {
        this(0.0, 5000);
        this.id = String.format("%07d", random.nextInt(10000000)); //$NON-NLS-1$
    }

    /**
     * Construtor que define a leitura inicial.
     *
     * @param leituraInicial valor inicial da leitura
     */
    public Hidrometro(double leituraInicial) {
		this.leituraAtual = leituraInicial;
		this.intervalo = 5000;
		this.rodando = false;
		this.random = new Random();
		this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); //$NON-NLS-1$
		this.id = String.format("%07d", random.nextInt(10000000)); //$NON-NLS-1$
	}

    /**
     * Construtor utilizado quando o ID já existe
     * (por exemplo, ao carregar dados do arquivo).
     *
     * @param id identificador do hidrômetro
     * @param leituraInicial leitura inicial
     */
    public Hidrometro(String id, double leituraInicial) {
        this.leituraAtual = leituraInicial;
        this.id = id;
        this.rodando = false;
        this.random = new Random();
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); //$NON-NLS-1$
    }

    /**
     * Construtor que define leitura inicial e intervalo personalizado.
     *
     * @param leituraInicial leitura inicial
     * @param intervalo intervalo entre leituras (ms)
     */
    public Hidrometro(double leituraInicial, long intervalo) {
        this.leituraAtual = leituraInicial;
        this.intervalo = intervalo;
        this.rodando = false;
        this.random = new Random();
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); //$NON-NLS-1$
        this.id = String.format("%07d", random.nextInt(10000000)); //$NON-NLS-1$
    }

    // -------- MÉTODOS DO OBSERVER ----------

    /**
     * Adiciona um observador ao hidrômetro.
     *
     * @param o observador interessado nos eventos
     */
    public void adicionarObserver(HidrometroObserver o) {
        observers.add(o);
    }

    /**
     * Remove um observador previamente registrado.
     *
     * @param o observador a ser removido
     */
    public void removerObserver(HidrometroObserver o) {
        observers.remove(o);
    }

    /**
     * Notifica todos os observadores quando o limite máximo
     * de leitura é ultrapassado.
     *
     * @param leituraAnterior valor antes do estouro
     */
    private void notificarLimite(double leituraAnterior) {
        for (HidrometroObserver o : observers) {
            o.limiteUltrapassado(this, leituraAnterior);
        }
    }

    // ----------------------------------------

    /**
     * Método executado pela Thread.
     *
     * Simula o funcionamento contínuo do hidrômetro,
     * gerando consumo periódico e verificando o limite máximo.
     */
    @Override
    public void run() {
        rodando = true;

        while (rodando) {
            try {
                Thread.sleep(intervalo);

                if (rodando) {
                    // Gera consumo aleatório
                    double consumo = 0.001 + (random.nextDouble() * 0.009);
                    double leituraAnterior = leituraAtual;

                    leituraAtual += consumo;

                    // ------- CHECAGEM DO LIMITE -------
                    if (leituraAtual > LIMITE_MAX) {
                        leituraAtual = 0.0;
                        notificarLimite(leituraAnterior); // dispara evento!
                    }
                    // ----------------------------------
                }

            } catch (InterruptedException e) {
                System.out.println(Messages.getString("Hidrometro.1")); //$NON-NLS-1$
                rodando = false;
            }
        }

        System.out.println(
            Messages.getString("Hidrometro.2") +
            String.format("%.3f", leituraAtual)); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Interrompe a leitura do hidrômetro,
     * encerrando a execução da Thread.
     */
    public void pararLeitura() {
        rodando = false;
        System.out.println(Messages.getString("Hidrometro.3")); //$NON-NLS-1$
    }

    /**
     * Verifica se o hidrômetro está em execução.
     *
     * @return true se estiver rodando
     */
    public boolean estaRodando() {
        return rodando;
    }

    /**
     * Retorna a leitura atual.
     *
     * @return leitura atual
     */
    public double getLeituraAtual() {
        return leituraAtual;
    }

    /**
     * Atualiza manualmente a leitura do hidrômetro.
     *
     * @param novaLeitura novo valor da leitura
     */
    public void setLeituraAtual(double novaLeitura) {
        this.leituraAtual = novaLeitura;
        System.out.println(
            Messages.getString("Hidrometro.4") +
            String.format("%.3f", novaLeitura)); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Define o intervalo entre leituras.
     *
     * @param intervalo intervalo em milissegundos
     */
    public void setIntervalo(long intervalo) {
        this.intervalo = intervalo;
    }

    /**
     * Retorna o identificador do hidrômetro.
     *
     * @return ID do hidrômetro
     */
    public String getIdentificador() {
        return this.id;
    }

    /**
     * Representação textual do estado do hidrômetro.
     *
     * @return string formatada com ID, leitura e data/hora
     */
    public String toString() {
    	String resposta = ""; //$NON-NLS-1$
		resposta += (Messages.getString("Hidrometro.5") + id + " "); //$NON-NLS-1$ //$NON-NLS-2$
		resposta += (Messages.getString("Hidrometro.6") +
		             String.format("%.3f", leituraAtual) + " "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		resposta += (Messages.getString("Hidrometro.7") +
		             LocalDateTime.now().format(formatter) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		return resposta;
	}
}
