package estadosDoPainel;

import operacoes.OperacaoAdicionarHidrometro;
import operacoes.OperacaoAdicionarUsuario;
import operacoes.OperacaoAtualizarUsuario;
import operacoes.OperacaoListarHidrometros;
import operacoes.OperacaoListarUsuarios;
import operacoes.OperacaoLogout;
import operacoes.OperacaoPainel;
import operacoes.OperacaoRemoverHidrometro;
import operacoes.OperacaoRemoverUsuario;
import operacoes.OperaçãoCriarContaDeAgua;
import operacoes.OperaçãoListarContasDeAgua;
import operacoes.OperaçãoRemoverContaDeAgua;
import src.FachadaSHA;

public class EstadoLogado implements EstadoPainelIF {

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENU (Usuário LOGADO) ---");
        System.out.println("1 - Adicionar novo Usuario");
        System.out.println("2 - Listar Usuarios");
        System.out.println("3 - Remover Usuario");
      //  System.out.println("4 - Atualizar Usuario");
        System.out.println("4 - Adicionar Hidrometros");
        System.out.println("5 - Listar Hidrometros");
        System.out.println("6 - Remover Hidrometros");
        System.out.println("7 - Criar Conta de Água");
        System.out.println("8 - Listar Contas de Água");
        System.out.println("9 - Remover Conta de Água");
        System.out.println("0 - Logout");
    }

    @Override
    public OperacaoPainel interpretarOpcao(String opcao, FachadaSHA fachada) {

        return switch (opcao) {
            
            case "1" -> new OperacaoAdicionarUsuario(fachada);
            case "2" -> new OperacaoListarUsuarios(fachada);
            case "3" -> new OperacaoRemoverUsuario(fachada);
         // case "4" -> new OperacaoAtualizarUsuario(fachada);
            case "4" -> new OperacaoAdicionarHidrometro(fachada);
            case "5" -> new OperacaoListarHidrometros(fachada);
            case "6" -> new OperacaoRemoverHidrometro(fachada);
            case "7" -> new OperaçãoCriarContaDeAgua(fachada);
            case "8" -> new OperaçãoListarContasDeAgua(fachada);
            case "9" -> new OperaçãoRemoverContaDeAgua(fachada);
           //
            case "0" -> new OperacaoLogout(fachada);
            
            default -> {
                System.out.println("Opção inválida.");
                yield null;
            }
        };
    }
}