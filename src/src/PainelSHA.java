package src;

import java.util.Scanner;

import hidrometro.OperacaoConsultarHidrometro;
import hidrometro.OperacaoRegistrarConsumo;

public class PainelSHA {
    private final FachadaSHA fachada = FachadaSHA.getInstance();
    private final String OPCAO_LIGAR_PAINEL = "1";
    private final String OPCAO_FAZER_LOGIN = "2";

    public void iniciar() {
        while (true) {
        	System.out.println(Messages.getString("Painel.0"));
        /*  System.out.println("\n--- MENU SHA ---");
            System.out.println("1 - Fazer Login");
            System.out.println("9 - Consultar hidrômetro");
            System.out.println("9 - Registrar consumo");
            System.out.println("0 - Sair");*/

            String opcao = new Scanner(System.in).next();

            OperacaoPainel op = switch (opcao) {
            	case OPCAO_LIGAR_PAINEL -> new OperacaoIniciarSistema(fachada);
            	case OPCAO_FAZER_LOGIN -> new OperacaoLogin(fachada);
                case "88" -> new OperacaoConsultarHidrometro(fachada);
                case "99" -> new OperacaoRegistrarConsumo(fachada);
                case "0" -> null;
                default -> {
                    System.out.println("Opção inválida.");
                    yield null;
                }
            };

            if (op == null) {
                if ("0".equals(opcao)) break;
                continue;
            }

            try {
                op.executar();   // <── TEMPLATE METHOD sendo executado
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        PainelSHA painel = new PainelSHA();
        painel.iniciar();
    }
}

