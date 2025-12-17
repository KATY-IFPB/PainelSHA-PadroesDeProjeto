package hidrometro;

import operacoes.OperacaoPainel;
import src.FachadaSHA;

public class OperacaoConsultarHidrometro extends OperacaoPainel {

    private String id;
    private Hidrometro resultado;
    private final FachadaSHA fachada;

    public OperacaoConsultarHidrometro(FachadaSHA fachada) {
        this.fachada = fachada;
    }

    @Override
    protected void lerDados() {
        System.out.print("Digite o ID do hidrômetro: ");
        id = new java.util.Scanner(System.in).nextLine();
    }

    @Override
    protected void validar() {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID inválido!");
        }
    }

    @Override
    protected void processar() {
        resultado = fachada.consultarHidrometro(id);
    }

    @Override
    protected void exibirResultado() {
        if (resultado == null) {
            System.out.println("Hidrômetro não encontrado.");
        } else {
            System.out.println("Hidrômetro encontrado: " + resultado);
        }
    }
}

