package hidrometro;

public class EstadoVazamento implements EstadoHidrometro {

    @Override
    public void registrarConsumo(Hidrometro h, double litros) {
        h.addConsumo(litros);
        System.out.println("ALERTA: possível vazamento no hidrômetro " + h.getId());
    }

    @Override
    public void enviarAlerta(Hidrometro h) {
        System.out.println("Enviando alerta de vazamento...");
    }

    @Override
    public String getNome() {
        return "Vazamento";
    }
}
