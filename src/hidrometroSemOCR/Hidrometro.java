package hidrometroSemOCR;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hidrometro extends Thread {

	private static final double LIMITE_MAX = 9_999_999.0;

	private String id;
    private double leituraAtual;
    private volatile boolean rodando;
    private long intervalo = 5000;	
    private Random random;
    private DateTimeFormatter formatter;

    // -------- LISTA DE OBSERVADORES --------
    private List<HidrometroObserver> observers = new ArrayList<>();
    // ---------------------------------------

    public Hidrometro() {
        this(0.0, 5000);
        this.id = String.format("%07d", random.nextInt(10000000));
    }

    public Hidrometro(double leituraInicial) {
		this.leituraAtual = leituraInicial;
		//this.id = id;
		this.intervalo = 5000;
		this.rodando = false;
		this.random = new Random();
		this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		this.id = String.format("%07d", random.nextInt(10000000));
	}
    public Hidrometro(String id, double leituraInicial) {
        this.leituraAtual = leituraInicial;
        this.id = id;
        this.rodando = false;
        this.random = new Random();
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
      //  this.id = String.format("%07d", random.nextInt(10000000));
    }
    public Hidrometro(double leituraInicial, long intervalo) {
        this.leituraAtual = leituraInicial;
        this.intervalo = intervalo;
        this.rodando = false;
        this.random = new Random();
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.id = String.format("%07d", random.nextInt(10000000));
    }

    // -------- MÉTODOS DO OBSERVER ----------
    public void adicionarObserver(HidrometroObserver o) {
        observers.add(o);
    }

    public void removerObserver(HidrometroObserver o) {
        observers.remove(o);
    }

    private void notificarLimite(double leituraAnterior) {
        for (HidrometroObserver o : observers) {
            o.limiteUltrapassado(this, leituraAnterior);
        }
    }
    // ----------------------------------------

    @Override
    public void run() {
        rodando = true;
        System.out.println("Hidrômetro iniciado ID: " + id +
                           " Leitura inicial: " + String.format("%.3f", leituraAtual));

        while (rodando) {
            try {
                Thread.sleep(intervalo);

                if (rodando) {
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
                System.out.println("Hidrômetro interrompido.");
                rodando = false;
            }
        }

        System.out.println("Hidrômetro parado. Leitura final: " + String.format("%.3f", leituraAtual));
    }

    public void pararLeitura() {
        rodando = false;
        System.out.println("Solicitação de parada enviada...");
    }

    public boolean estaRodando() {
        return rodando;
    }

    public double getLeituraAtual() {
        return leituraAtual;
    }

    public void setLeituraAtual(double novaLeitura) {
        this.leituraAtual = novaLeitura;
        System.out.println("Leitura ajustada para: " + String.format("%.3f", novaLeitura));
    }

    public void setIntervalo(long intervalo) {
        this.intervalo = intervalo;
    }

    public String getIdentificador() {
        return this.id;
    }
    
    public String toString() {
    	String resposta="";
		resposta+=("Hidrômetro ID: " + id+" ");
		resposta+=("Leitura Atual: " + String.format("%.3f", leituraAtual)+" ");
		resposta+=("Última Atualização: " + LocalDateTime.now().format(formatter)+"\n");
		return resposta;
	}
}
