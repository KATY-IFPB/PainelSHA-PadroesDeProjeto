package operacoes;

import hidrometroSemOCR.Hidrometro;
import src.FachadaSHA;
import src.Logger;

public class OperacaoListarHidrometros extends OperacaoPainel{

	private FachadaSHA fachada;
	private Logger log = Logger.getInstance();
	
	public OperacaoListarHidrometros(FachadaSHA fachada) {
		this.fachada = fachada;
	}
	
	@Override
	protected void lerDados() {
		// Nenhum dado a ser lido para listar usuários
	}

	@Override
	protected void validar() {
		// Nenhuma validação necessária para listar usuários
	}

	@Override
	protected void processar() {
		System.out.println("Lista de Hidrometros:");
		for (Hidrometro hidrometro: fachada.listarHidrometros()) {
			System.out.println("- " + hidrometro);
		}
		log.info("Hidrometros listados com sucesso.");
	}

	@Override
	protected void exibirResultado() {
		// Resultado já exibido no método processar
	}

}