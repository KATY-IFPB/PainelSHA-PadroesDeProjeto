package src;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Logger — Singleton
 *
 * Classe responsável por registrar mensagens de log do sistema
 * em um arquivo de texto.
 *
 * Implementa o padrão Singleton para garantir que exista
 * apenas uma instância de logger em toda a aplicação.
 *
 * Características:
 * - Escrita em arquivo
 * - Thread-safe
 * - Suporte a mensagens INFO e ERRO
 */
public class Logger {

    /** Instância única do Logger (Singleton) */
    private static Logger instance;

    /** Caminho do arquivo de log (definido em configurações) */
    private static final String ARQUIVO =
            Configuracoes.getString("ArquivoDeLog");

    /**
     * Construtor privado.
     * Impede que outras classes criem instâncias do Logger.
     */
    private Logger() {}

    /**
     * Retorna a instância única do Logger.
     *
     * Implementação lazy e thread-safe:
     * - A instância só é criada na primeira chamada
     * - synchronized evita múltiplas instâncias em ambientes concorrentes
     *
     * @return instância única do Logger
     */
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    // ===================== API PÚBLICA =====================

    /**
     * Registra uma mensagem de nível INFO.
     *
     * @param msg mensagem informativa
     */
    public void info(String msg) {
        escrever("INFO", msg, null);
    }

    /**
     * Registra uma mensagem de nível ERRO.
     *
     * @param msg mensagem de erro
     * @param e exceção associada ao erro (opcional)
     */
    public void error(String msg, Exception e) {
        escrever("ERRO", msg, e);
    }

    // ===================== IMPLEMENTAÇÃO INTERNA =====================

    /**
     * Método responsável por escrever efetivamente no arquivo de log.
     *
     * - synchronized garante que múltiplas threads não escrevam
     *   simultaneamente no arquivo
     * - O arquivo é aberto em modo append (true)
     *
     * @param nivel nível do log (INFO, ERRO, etc.)
     * @param msg mensagem a ser registrada
     * @param e exceção associada (pode ser null)
     */
    private synchronized void escrever(String nivel, String msg, Exception e) {

        try (FileWriter fw = new FileWriter(ARQUIVO, true)) {

            // Escreve data/hora, nível e mensagem
            fw.write(LocalDateTime.now()
                    + " [" + nivel + "] "
                    + msg + "\n");

            // Se houver exceção, registra a causa
            if (e != null) {
                fw.write("   Causa: " + e.getMessage() + "\n");
            }

        } catch (IOException ex) {
            // Falha silenciosa para não quebrar o sistema
            System.out.println("Falha ao escrever no log");
        }
    }
}
