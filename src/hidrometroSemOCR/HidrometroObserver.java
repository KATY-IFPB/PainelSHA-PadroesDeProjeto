package hidrometroSemOCR;

/**
 * Interface que define o contrato do padrão Observer
 * específico para o monitoramento de hidrômetros.
 *
 * Classes que implementam esta interface serão notificadas
 * sempre que um hidrômetro ultrapassar o limite máximo de leitura.
 *
 * Este Observer é utilizado pela classe Hidrometro,
 * que atua como o Subject (sujeito observado).
 */
public interface HidrometroObserver {

    /**
     * Método chamado automaticamente quando o hidrômetro
     * ultrapassa o valor máximo permitido de leitura.
     *
     * @param h                hidrômetro que disparou o evento
     * @param leituraAnterior  valor da leitura antes do estouro
     */
    void limiteUltrapassado(Hidrometro h, double leituraAnterior);
}
