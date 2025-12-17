package operacoes;

import conta.Conta;
import hidrometroSemOCR.Hidrometro;
import src.FachadaSHA;
import src.Logger;

public class OperaçãoListarContasDeAgua extends OperacaoPainel {
	
	private FachadaSHA fachada;
	private Logger log = Logger.getInstance();
	
	public OperaçãoListarContasDeAgua(FachadaSHA fachada) {
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
		System.out.println("Lista de Contas:");
		for (Conta conta: fachada.listarContasDeAgua()) {
			System.out.println("- " + conta);
		}
		log.info("Contas de Agua listados com sucesso.");
	}

	@Override
	protected void exibirResultado() {
		// Resultado já exibido no método processar
	}
}
