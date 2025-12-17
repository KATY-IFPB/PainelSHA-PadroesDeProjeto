package src;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Classe utilitária responsável por fornecer mensagens
 * internacionalizadas (i18n) a partir de um arquivo .properties.
 *
 * O arquivo base esperado é:
 *   messages.properties
 *
 * Essa classe evita strings "hardcoded" no código,
 * facilitando manutenção e futura internacionalização.
 */
public class Messages {

    /** Nome do arquivo de mensagens (sem extensão .properties) */
    private static final String BUNDLE_NAME = "messages"; //$NON-NLS-1$

    /**
     * ResourceBundle carregado uma única vez (Singleton implícito).
     * Contém todas as mensagens definidas no arquivo properties.
     */
    private static final ResourceBundle RESOURCE_BUNDLE =
            ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Construtor privado.
     * Impede a criação de instâncias da classe utilitária.
     */
    private Messages() {
    }

    /**
     * Retorna a mensagem associada à chave informada.
     *
     * @param key chave da mensagem no arquivo messages.properties
     * @return texto da mensagem correspondente à chave
     *         ou !chave! caso ela não exista
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            // Caso a chave não exista no arquivo, retorna no formato !chave!
            return '!' + key + '!';
        }
    }
}
