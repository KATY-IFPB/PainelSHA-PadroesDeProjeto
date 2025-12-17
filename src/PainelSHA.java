package src;

import java.util.Scanner;

import estadosDoPainel.EstadoDeslogado;
import estadosDoPainel.EstadoLogado;
import estadosDoPainel.EstadoPainelIF;
import estadosDoPainel.EstadoPreInicializado;
import operacoes.OperacaoPainel;

/**
 * Classe PainelSHA
 *
 * Representa a interface principal (CLI) do sistema.
 * É responsável por:
 * - exibir menus
 * - ler opções do usuário
 * - delegar ações para as operações
 * - alternar automaticamente os estados do painel
 *
 * Padrões de projeto utilizados:
 * - State → controla o menu conforme o estado do sistema
 * - Command → encapsula cada operação do menu
 * - Facade → centraliza acesso ao subsistema (FachadaSHA)
 */
public class PainelSHA {

    /** Fachada central do sistema */
    private final FachadaSHA fachada = FachadaSHA.getInstance();

    /** Estado atual do painel (State Pattern) */
    private EstadoPainelIF estadoAtual = new EstadoPreInicializado();

    /**
     * Método principal que controla o loop do painel.
     * Executa até que o usuário decida encerrar o sistema.
     */
    public void iniciar() {

        Scanner sc = new Scanner(System.in);

        while (true) {

            // Atualiza automaticamente o estado do painel
            // conforme a situação do sistema (inicializado, logado, etc.)
            atualizarEstado();

            // Exibe o menu correspondente ao estado atual
            estadoAtual.mostrarMenu();

            // Lê a opção digitada pelo usuário
            String opcao = sc.next();

            // O estado interpreta a opção e retorna uma operação (Command)
            OperacaoPainel operacao = estadoAtual.interpretarOpcao(opcao, fachada);

            // Se não existir operação associada
            if (operacao == null) {

                // Opção "0" tem comportamento especial
                if ("0".equals(opcao)) {

                    // Se estiver logado → faz logout
                    if (fachada.temUsuarioLogado()) {
                        System.out.println("Logout realizado.");
                        fachada.fazerLogout();
                    }
                    // Se não estiver logado → encerra o sistema
                    else {
                        break;
                    }
                }
                // Continua o loop sem executar nenhuma operação
                continue;
            }

            // Executa a operação escolhida
            try {
                operacao.executar();
            } catch (Exception e) {
                // Trata exceções de forma genérica para não quebrar o painel
                System.out.println("Erro: " + e.getMessage());
            }
        }

        // Fecha o scanner antes de encerrar o programa
        sc.close();
    }

    /**
     * Atualiza o estado do painel automaticamente.
     *
     * Regras:
     * - Se o sistema não foi inicializado → EstadoPreInicializado
     * - Se existe usuário logado → EstadoLogado
     * - Caso contrário → EstadoDeslogado
     */
    private void atualizarEstado() {

        if (!fachada.isSistemaInicializado()) {
            estadoAtual = new EstadoPreInicializado();

        } else if (fachada.temUsuarioLogado()) {
            estadoAtual = new EstadoLogado();

        } else {
            estadoAtual = new EstadoDeslogado();
        }
    }

    /**
     * Método main — ponto de entrada do programa.
     */
    public static void main(String[] args) {
        new PainelSHA().iniciar();
    }
}
