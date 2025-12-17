package operacoes;

import src.FachadaSHA;
import src.Logger;

public class OperacaoLogout extends OperacaoPainel{
	private FachadaSHA fachada;
	private Logger log = Logger.getInstance();
	
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
		String usuarioLogado = fachada.getUsuarioLogado().getLogin();
		fachada.fazerLogout();
		System.out.println("Logout realizado com sucesso.");
		log.info("Usuário "+usuarioLogado+" deslogado com sucesso.");
		
	}

	@Override
	protected void exibirResultado() {
		// TODO Auto-generated method stub
		
	}

}
