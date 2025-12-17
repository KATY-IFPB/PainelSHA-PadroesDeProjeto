package hidrometro;



public interface EstadoHidrometro {
    void registrarConsumo(Hidrometro h, double litros);
    void enviarAlerta(Hidrometro h);
    String getNome();
}
