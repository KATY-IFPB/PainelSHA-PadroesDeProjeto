package src;

/**
 * Interface Observer do padrão de projeto Observer.
 *
 * Classes que implementam esta interface desejam ser notificadas
 * quando ocorrerem eventos em um determinado Subject.
 *
 * No sistema, essa interface é utilizada para:
 * - reagir à remoção de usuários
 * - reagir à remoção de hidrômetros
 * - manter consistência entre entidades (ex: remover contas associadas)
 */
public interface Observer {

    /**
     * Método chamado automaticamente pelo Subject
     * quando ocorre um evento relevante no sistema.
     *
     * @param evento objeto que descreve o tipo de evento ocorrido
     *               e o identificador relacionado ao evento
     */
    void atualizar(Evento evento);
}
