package hidrometro;

public class TarifaComercial implements CalculoTarifaStrategy {
    @Override
    public double calcular(double consumo) {
        return consumo * 4.2;
    }
}
