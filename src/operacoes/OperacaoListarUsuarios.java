package operacoes;

import src.FachadaSHA;
import src.Logger;
import usuario.Usuario;

public class OperacaoListarUsuarios extends OperacaoPainel{

	private FachadaSHA fachada;
	private Logger log = Logger.getInstance();
	
	public OperacaoListarUsuarios(FachadaSHA fachada) {
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
		System.out.println("Lista de Usuários:");
		for (Usuario usuario : fachada.listarUsuarios()) {
			System.out.println("- " + usuario);
		}
		log.info("Usuários listados com sucesso.");
	}

	@Override
	protected void exibirResultado() {
		// Resultado já exibido no método processar
	}

}
