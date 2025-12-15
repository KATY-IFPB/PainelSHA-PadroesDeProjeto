package operacoes;

import java.util.Scanner;

import src.FachadaSHA;

public class OperacaoAdicionarHidrometro extends OperacaoPainel{
	private FachadaSHA fachada;
	private double leitura;

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
		
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void exibirResultado() {
		System.out.println("Hidrometro adicionado com sucesso!");
		
	}

}
