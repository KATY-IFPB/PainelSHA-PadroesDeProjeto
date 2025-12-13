package estadosDoPainel;

import src.FachadaSHA;
import src.OperacaoLogin;
import src.OperacaoPainel;

public class EstadoDeslogado implements EstadoPainelIF {

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENU (Usuário NÃO logado) ---");
        System.out.println("1 - Fazer Login");
        System.out.println("0 - Sair");
    }

    @Override
    public OperacaoPainel interpretarOpcao(String opcao, FachadaSHA fachada) {

        return switch (opcao) {
            case "1" -> new OperacaoLogin(fachada);
            case "0" -> null;
            default -> {
                System.out.println("Opção inválida.");
                yield null;
            }
        };
    }
}