package hidrometroSemOCR;

public interface HidrometroObserver {
    void limiteUltrapassado(Hidrometro h, double leituraAnterior);
}
