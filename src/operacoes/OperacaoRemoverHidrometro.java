package operacoes;

import java.util.Scanner;

import src.FachadaSHA;
import src.Logger;
import usuario.UsuarioExistenteException;

public class OperacaoRemoverHidrometro extends OperacaoPainel {
	private FachadaSHA fachada;
	private String id;
	private Logger log = Logger.getInstance();

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
					log.error("Tentativa de remoção de hidrometro com ID vazio.",null);
					throw new IllegalArgumentException("ID não pode ser vazio.");
					
				}
		
	}

	@Override
	protected void processar() throws UsuarioExistenteException {
		try {
			if(fachada.existeHidrometro(id)) {
				fachada.removerHidrometro(id);
				log.info("Hidrometro com ID " + id + " removido com sucesso.");
				System.out.println("Hidrometro removido com sucesso.");
			}else {
				System.out.println("O hidrometro com o ID informado não existe.");
			}
		} catch (Exception e) {
			System.out.println("Erro ao remover hidrometro: " + e.getMessage());
			log.error("Erro ao remover hidrometro com ID " + id, e);
		}
		
	}

	@Override
	protected void exibirResultado() {
		// Resultado já exibido no método processar
		
	}

	

}

