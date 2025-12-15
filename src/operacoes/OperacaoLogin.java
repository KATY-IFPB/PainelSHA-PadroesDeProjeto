package operacoes;

import java.util.Scanner;

import src.FachadaSHA;
import src.LoginException;
import src.Messages;

public class OperacaoLogin extends OperacaoPainel{
	private FachadaSHA fachada;
	private String login;
	private String senha;
	private boolean jaTemUsuarioLogado;
	
	public OperacaoLogin(FachadaSHA fachada) {
		// TODO Auto-generated constructor stub
		this.fachada = fachada;
	}

	@Override
	protected void lerDados() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println(Messages.getString("OperacaoLogin.0"));
		String[] linha = sc.nextLine().split(" ");
		this.login = linha[0];
		this.senha = linha[1];
		
		
	}

	@Override
	protected void validar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processar() {
		if(fachada.temUsuarioLogado()) {
			jaTemUsuarioLogado = true;
			System.out.println("Erro: Já existe um usuário logado no sistema.");
			
		}
		try {
			fachada.fazerLogin(login, senha);
			
		} catch (LoginException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
	}

	@Override
	protected void exibirResultado() {
		/*
		 * if(jaTemUsuarioLogado) { System.out.println("ja tem usuario logado"); }
		 */
		
	}

}
