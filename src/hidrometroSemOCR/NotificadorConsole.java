package hidrometroSemOCR;

public class NotificadorConsole implements HidrometroObserver {

    @Override
    public void limiteUltrapassado(Hidrometro h, double leituraAnterior) {
        System.out.println("⚠ ALERTA NO HIDRÔMETRO " + h.getIdentificador());
        System.out.println("Leitura ultrapassou 9.999.999!");
        System.out.println("Valor anterior: " + leituraAnterior);
        System.out.println("Leitura reiniciada para 0.");
    }
}

