package src;

/**
 * Classe que representa um evento do sistema.
 *
 * Um evento é utilizado no padrão Observer para notificar
 * outros componentes sobre mudanças importantes,
 * como a remoção de usuários ou hidrômetros.
 */
public class Evento {

    /**
     * Enumeração que define os tipos possíveis de eventos
     * que podem ocorrer no sistema.
     */
    public enum Tipo {

        /** Evento disparado quando um usuário é removido */
        USUARIO_REMOVIDO,

        /** Evento disparado quando um hidrômetro é removido */
        HIDROMETRO_REMOVIDO
    }

    /** Tipo do evento ocorrido */
    private final Tipo tipo;

    /** Identificador do objeto relacionado ao evento (usuário ou hidrômetro) */
    private final String id;

    /**
     * Construtor do evento.
     *
     * @param tipo tipo do evento ocorrido
     * @param id   identificador do objeto relacionado ao evento
     */
    public Evento(Tipo tipo, String id) {
        this.tipo = tipo;
        this.id = id;
    }

    /**
     * Retorna o tipo do evento.
     *
     * @return tipo do evento
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Retorna o identificador associado ao evento.
     *
     * @return ID do usuário ou do hidrômetro envolvido
     */
    public String getId() {
        return id;
    }
}
