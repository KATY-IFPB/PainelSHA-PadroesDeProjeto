package src;

import usuario.Usuario;

public class OperacaoListarUsuarios extends OperacaoPainel{

	private FachadaSHA fachada;
	
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
	}

	@Override
	protected void exibirResultado() {
		// Resultado já exibido no método processar
	}

}
