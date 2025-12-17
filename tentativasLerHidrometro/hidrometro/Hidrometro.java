package src;

package hidrometro;

public class Hidrometro {

    private final String id;
    private double consumoTotal;
    private EstadoHidrometro estado;

    public Hidrometro(String id) {
        this.id = id;
        this.estado = new EstadoNormal(); // Estado padrão
    }

    public String getId() {
        return id;
    }

    public double getConsumoTotal() {
        return consumoTotal;
    }

    public void addConsumo(double litros) {
        this.consumoTotal += litros;
    }

    // ========= STATE =========

    public EstadoHidrometro getEstado() {
        return estado;
    }

    public void setEstado(EstadoHidrometro estado) {
        this.estado = estado;
    }

    public void registrarConsumo(double litros) {
        estado.registrarConsumo(this, litros);
    }

    public void enviarAlerta() {
        estado.enviarAlerta(this);
    }

    @Override
    public String toString() {
        return "[Hidrômetro " + id + ", consumo=" 
            + consumoTotal + "L, estado=" + estado.getNome() + "]";
    }
}
