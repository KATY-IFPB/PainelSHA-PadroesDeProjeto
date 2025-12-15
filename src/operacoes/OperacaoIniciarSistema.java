package operacoes;

import src.FachadaSHA;
import src.Messages;

public class OperacaoIniciarSistema extends OperacaoPainel {
	FachadaSHA fachada;
	 
	public OperacaoIniciarSistema(FachadaSHA fachada) {
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
		fachada.iniciar();
		
	}

	@Override
	protected void exibirResultado() {
		System.out.println(Messages.getString("Painel.1"));
		
	}

}
