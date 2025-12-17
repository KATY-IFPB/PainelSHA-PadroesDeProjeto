package hidrometro;

import java.util.Scanner;

import operacoes.OperacaoPainel;
import src.FachadaSHA;

public class OperacaoRegistrarConsumo extends OperacaoPainel {

    private String id;
    private double litros;
    private FachadaSHA fachada;

    public OperacaoRegistrarConsumo(FachadaSHA fachada) {
        this.fachada = fachada;
    }

    @Override
    protected void lerDados() {
        Scanner sc = new Scanner(System.in);

        System.out.print("ID do hidrômetro: ");
        id = sc.nextLine();

        System.out.print("Litros consumidos: ");
        litros = sc.nextDouble();
    }

    @Override
    protected void validar() {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID inválido.");
        }
        if (litros < 0) {
            throw new IllegalArgumentException("Consumo não pode ser negativo.");
        }
    }

    @Override
    protected void processar() {
        fachada.registrarConsumo(id, litros);
    }

    @Override
    protected void exibirResultado() {
        System.out.println("Consumo registrado com sucesso.");
    }
}
