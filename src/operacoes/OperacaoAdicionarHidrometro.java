package operacoes;

import java.util.Scanner;

import src.FachadaSHA;
import src.Logger;

public class OperacaoAdicionarHidrometro extends OperacaoPainel{
	private FachadaSHA fachada;
	private double leitura;
	private Logger log = Logger.getInstance();

	public OperacaoAdicionarHidrometro(FachadaSHA fachada) {
		this.fachada = fachada;
	}
	@Override
	protected void lerDados() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite a leitura do novo hidrometro");
		leitura = sc.nextDouble();
	
		
	}

	@Override
	protected void validar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processar() {
	
		fachada.adicionarHidrometro(leitura);
		log.info("Hidrometro adicionado com leitura: " + leitura);
		
		
	}

	@Override
	protected void exibirResultado() {
		System.out.println("Hidrometro adicionado com sucesso!");
		
	}

}
