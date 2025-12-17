package usuario;

/**
 * UsuarioExistenteException
 *
 * Exceção específica lançada quando se tenta cadastrar
 * um usuário que já existe no sistema.
 *
 * Por que usar uma exceção específica?
 * - Torna o código mais legível
 * - Permite tratamento diferenciado no fluxo da aplicação
 * - Evita uso genérico de Exception
 *
 * Exemplo de uso:
 * if (usuarioDAO.containsKey(cpf)) {
 *     throw new UsuarioExistenteException();
 * }
 */
public class UsuarioExistenteException extends Exception {

    /**
     * Necessário para serialização.
     * Boa prática em todas as subclasses de Exception.
     */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor padrão.
	 * Pode ser usado quando a mensagem é tratada externamente
	 * (ex: via ResourceBundle).
	 */
	public UsuarioExistenteException() {
		super();
	}

	/**
	 * Construtor com mensagem personalizada.
	 *
	 * @param mensagem descrição do erro
	 */
	public UsuarioExistenteException(String mensagem) {
		super(mensagem);
	}
}
