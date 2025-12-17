package operacoes;

import java.util.Scanner;

import src.FachadaSHA;
import src.Logger;
import usuario.UsuarioExistenteException;

public class OperaçãoRemoverContaDeAgua extends OperacaoPainel {
	private FachadaSHA fachada;
	private String id;
	private Logger log = Logger.getInstance();

	public OperaçãoRemoverContaDeAgua(FachadaSHA fachada) {
		this.fachada = fachada;
	}

	@Override
	protected void lerDados() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o ID da conta que deseja remover");
		this.id = sc.nextLine();
	
	}

	@Override
	protected void validar() {
		
		// Validação simples para garantir que os campos não estejam vazios
				if (id.isEmpty()) {
					log.error("Tentativa de remoção de Conta com ID vazio.",null);
					throw new IllegalArgumentException("ID não pode ser vazio.");
					
				}
		
	}

	@Override
	protected void processar() throws UsuarioExistenteException {
		try {
			if(fachada.existeConta(id)) {
				fachada.removerConta(id);
				log.info("Conta com ID " + id + " removido com sucesso.");
				System.out.println("Conta removida com sucesso.");
			}else {
				System.out.println("A conta com o ID informado não existe.");
			}
		} catch (Exception e) {
			System.out.println("Erro ao remover conta: " + e.getMessage());
			log.error("Erro ao remover conta com ID " + id, e);
		}
		
	}

	@Override
	protected void exibirResultado() {
		// Resultado já exibido no método processar
		
	}

	

}

