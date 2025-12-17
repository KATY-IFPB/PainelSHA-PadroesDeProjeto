package src;

/**
 * Interface Subject do padrão de projeto Observer.
 *
 * Um Subject é o objeto observado.
 * Ele mantém uma lista de observadores (Observers)
 * e os notifica sempre que ocorre um evento relevante.
 *
 * No sistema, exemplos de Subjects:
 * - UsuarioDAO
 * - HidrometroDAO
 *
 * Esses Subjects notificam os Observers (ex: ContaDAO)
 * quando usuários ou hidrômetros são removidos.
 */
public interface Subject {

    /**
     * Registra um novo Observer para ser notificado.
     *
     * @param o observer que deseja observar o Subject
     */
    void adicionarObserver(Observer o);

    /**
     * Remove um Observer previamente registrado.
     *
     * @param o observer a ser removido
     */
    void removerObserver(Observer o);

    /**
     * Notifica todos os Observers registrados sobre um evento.
     *
     * @param evento objeto que descreve o tipo do evento
     *               e o identificador relacionado
     */
    void notificarObservers(Evento evento);
}
