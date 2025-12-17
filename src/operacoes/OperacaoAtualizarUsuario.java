package operacoes;

import java.util.Scanner;

import src.FachadaSHA;
import src.Logger;

public class OperacaoAtualizarUsuario extends OperacaoPainel {
	private FachadaSHA fachada;
	private String cpfAntigo,cpfNovo,nomeNovo,senhaNova;
	private Logger log = Logger.getInstance();

	public OperacaoAtualizarUsuario(FachadaSHA fachada) {
		this.fachada = fachada;
	}
	
	@Override
	protected void lerDados() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o cpf do usuario que deseja atualizar");
		this.cpfAntigo = sc.nextLine();
		
		System.out.println("Digite o cpf, o nome e a senha do novo usuário separados por $:");
		String[] linha = sc.nextLine().split("\\$");
	
		this.cpfNovo = linha[0].trim();
		this.nomeNovo = linha[1].trim();
		this.senhaNova = linha[2].trim();
	}

	@Override
	protected void validar() {
		// Lógica para validar os dados do usuário
		System.out.println("Validando dados do usuário...");
	}

	@Override
	protected void processar() {
		// Lógica para atualizar o usuário no sistema
		System.out.println("Atualizando usuário no sistema...");
	}

	@Override
	protected void exibirResultado() {
		// Lógica para exibir o resultado da operação de atualização
		System.out.println("Usuário atualizado com sucesso!");
	}
}
