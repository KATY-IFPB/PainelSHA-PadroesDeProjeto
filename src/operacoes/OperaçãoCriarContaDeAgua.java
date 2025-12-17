package operacoes;

import java.util.Scanner;

import src.FachadaSHA;
import src.Logger;

public class OperaçãoCriarContaDeAgua extends OperacaoPainel {
	private FachadaSHA fachada;
	private String idUsuario;
	private String idHidrometro;
	private Logger log = Logger.getInstance();

	public OperaçãoCriarContaDeAgua(FachadaSHA facade) {
		this.fachada = facade;
	}

	@Override
	protected void lerDados() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o CPF do usuário e o ID do hidrômetro separados por \" \":");

		String[] linha = sc.nextLine().split(" ");
	
		this.idUsuario = linha[0].trim();
		this.idHidrometro = linha[1].trim();
		
		
	}

	@Override
	protected void validar() {
		if(idUsuario.isEmpty() || idHidrometro.isEmpty()) {
			log.error("Erro ao criar conta de água: ID do usuário ou ID do hidrômetro vazio.",null);
			throw new IllegalArgumentException("ID do usuário e ID do hidrômetro não podem ser vazios.");
		}if(!fachada.existeUsuario(idUsuario)) {
			log.error("Erro ao criar conta de água: Usuário com o ID fornecido não existe.",null);
			throw new IllegalArgumentException("Usuário com o ID fornecido não existe.");
		}if(!fachada.existeHidrometro(idHidrometro)) {
			log.error("Erro ao criar conta de água: Hidrômetro com o ID fornecido não existe.",null);
			throw new IllegalArgumentException("Hidrômetro com o ID fornecido não existe.");
		}
		fachada.criarContaDeAgua(idUsuario, idHidrometro);
		log.info("Conta de água criada com sucesso para o usuário " + idUsuario + " e hidrômetro " + idHidrometro + ".");
		System.out.println("Conta de água criada com sucesso para o usuário " + idUsuario + " e hidrômetro " + idHidrometro + ".");
		
	}

	@Override
	protected void processar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void exibirResultado() {
		// TODO Auto-generated method stub
		
	}

}
