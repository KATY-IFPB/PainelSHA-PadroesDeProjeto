package hidrometro;



public class EstadoNormal implements EstadoHidrometro {

    @Override
    public void registrarConsumo(Hidrometro h, double litros) {
        h.addConsumo(litros);
    }

    @Override
    public void enviarAlerta(Hidrometro h) {
        // não envia nada
    }

    @Override
    public String getNome() {
        return "Normal";
    }
}

