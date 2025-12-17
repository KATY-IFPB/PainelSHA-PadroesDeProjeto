package hidrometro;



public class TarifaResidencial implements CalculoTarifaStrategy {
    @Override
    public double calcular(double consumo) {
        return consumo * 2.5;
    }
}
