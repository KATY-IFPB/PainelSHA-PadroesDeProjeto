package src;

/**
 * Exceção personalizada para erros relacionados ao processo de login.
 *
 * Esta exceção é lançada quando ocorre alguma falha na autenticação,
 * como:
 * - tentativa de login sem o sistema estar inicializado
 * - usuário já logado tentando fazer novo login
 * - credenciais inválidas
 *
 * Estender a classe Exception torna essa uma exceção verificada,
 * forçando o tratamento explícito em tempo de compilação.
 */
public class LoginException extends Exception {

    /** 
     * Versão da classe para controle de serialização.
     * Boa prática ao estender Exception.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construtor que recebe a mensagem de erro.
     *
     * @param mensagem descrição do erro ocorrido durante o login
     */
    public LoginException(String mensagem) {
        super(mensagem);
    }
}
