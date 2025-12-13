package hidrometroSemOCR;

public class MaindeHidrometro {
	public static void main(String[] args) {

	    Hidrometro h1 = new Hidrometro();
	    Hidrometro h2 = new Hidrometro();

	    // Registrar o observer
	    NotificadorConsole not = new NotificadorConsole();
	    h1.adicionarObserver(not);
	    h2.adicionarObserver(not);

	    h1.start();
	    h2.start();
	}
}
