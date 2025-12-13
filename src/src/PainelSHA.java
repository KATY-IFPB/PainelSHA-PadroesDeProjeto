package src;

import java.util.Scanner;

import estadosDoPainel.EstadoDeslogado;
import estadosDoPainel.EstadoLogado;
import estadosDoPainel.EstadoPainelIF;
import estadosDoPainel.EstadoPreInicializado;

public class PainelSHA {

    private final FachadaSHA fachada = FachadaSHA.getInstance();
    private EstadoPainelIF estadoAtual = new EstadoPreInicializado();

    public void iniciar() {

        Scanner sc = new Scanner(System.in);

        while (true) {

            // troca automaticamente o estado se logou/deslogou
            atualizarEstado();

            estadoAtual.mostrarMenu();
            String opcao = sc.next();

            OperacaoPainel operacao = estadoAtual.interpretarOpcao(opcao, fachada);

            if (operacao == null) {
                // saída ou opção inválida já tratada
                if ("0".equals(opcao)) {
                    // se estiver logado, faz logout. senão sai do sistema
                    if (fachada.temUsuarioLogado()) {
                        System.out.println("Logout realizado.");
                        fachada.fazerLogout();
                    } else {
                        break; // encerrar programa
                    }
                }
                continue;
            }

            try {
                operacao.executar();
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        sc.close();
    }

    private void atualizarEstado() {
    	if (!fachada.isSistemaInicializado()) {
			estadoAtual = new EstadoPreInicializado();
			
		} else if (fachada.temUsuarioLogado()) {
            estadoAtual = new EstadoLogado();
        } else {
            estadoAtual = new EstadoDeslogado();
        }
    }

    public static void main(String[] args) {
        new PainelSHA().iniciar();
    }
}
