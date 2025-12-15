package operacoes;

import java.util.Scanner;

import src.FachadaSHA;
import src.UsuarioExistenteException;

public class OperacaoRemoverHidrometro extends OperacaoPainel {
	private FachadaSHA fachada;
	private String id;

	public OperacaoRemoverHidrometro(FachadaSHA fachada) {
		this.fachada = fachada;
	}

	@Override
	protected void lerDados() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o ID do hidrometro que deseja remover");
		this.id = sc.nextLine();
	
	}

	@Override
	protected void validar() {
		
		// Validação simples para garantir que os campos não estejam vazios
				if (id.isEmpty()) {
					throw new IllegalArgumentException("ID não pode ser vazio.");
				}
		
	}

	@Override
	protected void processar() throws UsuarioExistenteException {
		try {
			if(fachada.existeHidrometro(id)) {
				fachada.removerHidrometro(id);
				System.out.println("Hidrometro removido com sucesso.");
			}else {
				System.out.println("O hidrometro com o ID informado não existe.");
			}
		} catch (Exception e) {
			System.out.println("Erro ao remover hidrometro: " + e.getMessage());
		}
		
	}

	@Override
	protected void exibirResultado() {
		// Resultado já exibido no método processar
		
	}

	

}

