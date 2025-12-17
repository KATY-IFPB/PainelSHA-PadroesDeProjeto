package usuario;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Classe utilitária responsável por fornecer mensagens
 * internacionalizadas (i18n) para o pacote "usuario".
 *
 * As mensagens são carregadas a partir de um arquivo
 * "messages.properties" localizado no mesmo pacote.
 *
 * Exemplo de uso:
 *   Messages.getString("UsuarioDAO.erroLogin");
 */
public class Messages {

    /**
     * Nome base do arquivo de mensagens.
     *
     * Messages.class.getPackageName() garante que o bundle
     * será buscado no mesmo pacote da classe "usuario".
     */
    private static final String BUNDLE_NAME =
            Messages.class.getPackageName() + ".messages"; //$NON-NLS-1$

    /**
     * ResourceBundle que carrega automaticamente o arquivo
     * messages.properties do pacote "usuario".
     */
    private static final ResourceBundle RESOURCE_BUNDLE =
            ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Construtor privado.
     *
     * Impede a criação de instâncias dessa classe,
     * pois ela deve ser utilizada apenas de forma estática.
     */
    private Messages() {
    }

    /**
     * Retorna a mensagem associada à chave informada.
     *
     * @param key chave definida no arquivo messages.properties
     * @return mensagem correspondente à chave ou uma marcação
     *         "!chave!" caso a chave não exista
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            // Retorna a chave destacada para facilitar debug
            return '!' + key + '!';
        }
    }
}
