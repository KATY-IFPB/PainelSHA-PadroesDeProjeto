package operacoes;

import java.util.Scanner;

import src.FachadaSHA;
import src.Logger;
import usuario.UsuarioExistenteException;

public class OperacaoRemoverUsuario extends OperacaoPainel {
	private FachadaSHA fachada;
	private String cpf;
	private Logger log = Logger.getInstance();

	public OperacaoRemoverUsuario(FachadaSHA fachada) {
		this.fachada = fachada;
	}

	@Override
	protected void lerDados() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o cpf do usuario que deseja remover");
		this.cpf = sc.nextLine();
	
	}

	@Override
	protected void validar() {
		
		// Validação simples para garantir que os campos não estejam vazios
				if (cpf.isEmpty()) {
					log.error("Tentativa de remoção de usuário com CPF vazio.",null);
					throw new IllegalArgumentException("CPF não pode ser vazio.");
				}if(cpf.equals(fachada.getUsuarioLogado().getLogin())) {
					log.error("Tentativa de remoção do usuário logado.",null);
					throw new IllegalArgumentException("Não é possível remover o usuário logado.");
				}
		
	}

	@Override
	protected void processar() throws UsuarioExistenteException {
		try {
			if(fachada.existeUsuario(cpf)) {
				fachada.removerUsuario(cpf);
				log.info("Usuário com CPF " + cpf + " removido com sucesso.");
				System.out.println("Usuário removido com sucesso.");
			}else {
				log.info("Tentativa de remoção de usuário inexistente com CPF " + cpf + ".");
				System.out.println("Usuário com o CPF informado não existe.");
			}
		} catch (Exception e) {
			log.error("Erro ao remover usuário com CPF " + cpf, e);
			System.out.println("Erro ao remover usuário: " + e.getMessage());
		}
		
	}

	@Override
	protected void exibirResultado() {
		// Resultado já exibido no método processar
		
	}

	

}
