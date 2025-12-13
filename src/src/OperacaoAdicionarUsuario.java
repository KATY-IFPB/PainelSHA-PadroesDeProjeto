package src;

import java.util.Scanner;

public class OperacaoAdicionarUsuario extends OperacaoPainel {
	private FachadaSHA fachada;
	private String nome;
	private String loginCPF;
	private String senha;

	public OperacaoAdicionarUsuario(FachadaSHA fachada) {
		this.fachada = fachada;
	}

	@Override
	protected void lerDados() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o cpf, o nome e a senha do novo usuário separados por $:");
		String[] linha = sc.nextLine().split("\\$");
	
		this.loginCPF = linha[0].trim();
		this.nome = linha[1].trim();
		this.senha = linha[2].trim();
	}

	@Override
	protected void validar() {
		// Validação simples para garantir que os campos não estejam vazios
		if (nome.isEmpty() || loginCPF.isEmpty() || senha.isEmpty()) {
			throw new IllegalArgumentException("Nome, login e senha não podem ser vazios.");
		}
	}

	@Override
	protected void processar() throws UsuarioExistenteException {
		fachada.adicionarUsuario(nome, loginCPF, senha);
		System.out.println("Usuário adicionado com sucesso.");
	}

	@Override
	protected void exibirResultado() {
		// Resultado já exibido no método processar
	}

}
