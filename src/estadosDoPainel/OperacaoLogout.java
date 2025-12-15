package estadosDoPainel;

import operacoes.OperacaoPainel;
import src.FachadaSHA;

public class OperacaoLogout extends OperacaoPainel{
	private FachadaSHA fachada;
	
	public OperacaoLogout(FachadaSHA fachada) {
		// TODO Auto-generated constructor stub
		this.fachada = fachada;
	}

	@Override
	protected void lerDados() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processar() {
		fachada.fazerLogout();
		System.out.println("Logout realizado com sucesso.");
		
	}

	@Override
	protected void exibirResultado() {
		// TODO Auto-generated method stub
		
	}

}
