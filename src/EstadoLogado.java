package src;

import hidrometro.OperacaoConsultarHidrometro;
import hidrometro.OperacaoRegistrarConsumo;

public class EstadoLogado implements EstadoPainelIF {

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENU (Usuário LOGADO) ---");
        System.out.println("88 - Consultar hidrômetro");
        System.out.println("99 - Registrar consumo");
        System.out.println("0 - Logout");
    }

    @Override
    public OperacaoPainel interpretarOpcao(String opcao, FachadaSHA fachada) {

        return switch (opcao) {
            case "88" -> new OperacaoConsultarHidrometro(fachada);
            case "99" -> new OperacaoRegistrarConsumo(fachada);
            case "0" -> null;
            default -> {
                System.out.println("Opção inválida.");
                yield null;
            }
        };
    }
}