package src;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Classe utilitária responsável por carregar e fornecer
 * configurações do sistema a partir de um arquivo .properties.
 *
 * Exemplo de arquivo esperado:
 *   configuracoesSistema.properties
 *
 * Essa classe segue o padrão Utility Class:
 * - Não pode ser instanciada
 * - Todos os métodos são estáticos
 */
public class Configuracoes {

    /**
     * Nome base do arquivo de configurações (SEM a extensão .properties).
     * O Java irá procurar por:
     *   configuracoesSistema.properties
     * no classpath da aplicação.
     */
    private static final String BUNDLE_NAME = "configuracoesSistema";

    /**
     * ResourceBundle carregado uma única vez quando a classe é inicializada.
     * Ele contém todos os pares chave=valor definidos no arquivo .properties.
     */
    private static final ResourceBundle RESOURCE_BUNDLE =
            ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Construtor privado para impedir a criação de instâncias da classe.
     * Garante que a classe seja usada apenas de forma estática.
     */
    private Configuracoes() {}

    /**
     * Retorna o valor associado a uma chave no arquivo de configurações.
     *
     * @param key chave definida no arquivo configuracoesSistema.properties
     * @return valor correspondente à chave ou, caso não exista,
     *         retorna a própria chave entre exclamações (ex: !minhaChave!)
     */
    public static String getString(String key) {
        try {
            // Tenta obter o valor da chave no arquivo .properties
            return RESOURCE_BUNDLE.getString(key);

        } catch (MissingResourceException e) {
            // Caso a chave não exista, retorna um valor visível
            // para facilitar a identificação de erro de configuração
            return "!" + key + "!";
        }
    }
}
