package estadosDoPainel;

import src.FachadaSHA;
import src.OperacaoAdicionarUsuario;
import src.OperacaoAtualizarUsuario;
import src.OperacaoListarUsuarios;
import src.OperacaoPainel;
import src.OperacaoRemoverUsuario;

public class EstadoLogado implements EstadoPainelIF {

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENU (Usuário LOGADO) ---");
        System.out.println("1 - Adicionar novo Usuario");
        System.out.println("2 - Listar Usuarios");
        System.out.println("3 - Remover Usuario");
        System.out.println("4 - Atualizar Usuario");
        System.out.println("5 - Adicionar Hidrometros");
        System.out.println("6 - Remover Hidrometros");
        System.out.println("0 - Logout");
    }

    @Override
    public OperacaoPainel interpretarOpcao(String opcao, FachadaSHA fachada) {

        return switch (opcao) {
            
            case "1" -> new OperacaoAdicionarUsuario(fachada);
            case "2" -> new OperacaoListarUsuarios(fachada);
            case "3" -> new OperacaoRemoverUsuario(fachada);
            case "4" -> new OperacaoAtualizarUsuario(fachada);
            case "5" -> new OperacaoAdicionarHidrometro(fachada);
            case "6" -> new OperacaoRemoverHidrometro(fachada);
            
            case "0" -> new OperacaoLogout(fachada);
            
            default -> {
                System.out.println("Opção inválida.");
                yield null;
            }
        };
    }
}