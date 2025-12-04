package usuario;

/**
 * Exceção lançada quando há erro na criação ou modificação de um usuário.
 */
public class UsuarioException extends Exception {

    public UsuarioException(String mensagem) {
        super(mensagem);
    }
}

