package conta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.Configuracoes;
import src.Evento;
import src.Messages;
import src.Observer;

/**
 * Classe DAO (Data Access Object) responsável pelo gerenciamento
 * das contas de água do sistema.
 * 
 * Esta classe:
 * - Implementa o padrão Singleton
 * - Mantém um cache das contas em memória
 * - Realiza a persistência em arquivo texto
 * - Atua como Observer, reagindo a eventos de remoção
 *   de usuários e hidrômetros
 */
public class ContaDAO implements Observer {

    /** Instância única do DAO (Singleton) */
    private static ContaDAO instance;

    /** Caminho do arquivo de persistência das contas */
    private static final String ARQUIVO =
            Configuracoes.getString("NomeArquivoDeContas"); //$NON-NLS-1$

    /** Mapa em memória contendo todas as contas (idConta → Conta) */
    private Map<String, Conta> contas;

    /**
     * Construtor privado.
     * Inicializa o mapa em memória e carrega os dados do arquivo.
     */
    private ContaDAO() {
        contas = new HashMap<>();
        carregarDoArquivo();
    }

    /**
     * Retorna a instância única do ContaDAO.
     *
     * @return instância única do DAO
     */
    public static synchronized ContaDAO getInstance() {
        if (instance == null) {
            instance = new ContaDAO();
        }
        return instance;
    }

    /* =========================
       Inicialização / Persistência
       ========================= */

    /**
     * Carrega todas as contas armazenadas no arquivo para a memória.
     * 
     * Caso o arquivo não exista, o método simplesmente retorna,
     * mantendo o mapa vazio.
     */
    private synchronized void carregarDoArquivo() {
        File file = new File(ARQUIVO);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                Conta conta = parseLinha(linha);
                contas.put(conta.getId(), conta);
            }

        } catch (IOException e) {
            throw new RuntimeException(Messages.getString("ContaDAO.1"), e); //$NON-NLS-1$
        }
    }

    /* =========================
       Leitura
       ========================= */

    /**
     * Busca uma conta pelo seu identificador.
     *
     * @param idConta identificador da conta
     * @return conta encontrada ou null
     */
    public Conta buscarPorId(String idConta) {
        return contas.get(idConta);
    }

    /**
     * Retorna todas as contas cadastradas.
     *
     * @return coleção de contas
     */
    public Collection<Conta> listarTodas() {
        return contas.values();
    }

    /**
     * Retorna o mapa interno de contas.
     *
     * @return mapa id → conta
     */
    public Map<String, Conta> getMapaContas() {
        return contas;
    }

    /* =========================
       Parser
       ========================= */

    /**
     * Converte uma linha do arquivo texto em um objeto Conta.
     * 
     * Formato esperado da linha:
     * idConta-idUsuario-idHidrometro-ultimaLeitura
     *
     * @param linha linha lida do arquivo
     * @return objeto Conta
     */
    private Conta parseLinha(String linha) {
        try {
            String[] partes = linha.split("-"); //$NON-NLS-1$

            String idConta = partes[0];
            String idUsuario = partes[1];
            String idHidrometro = partes[2];
            double ultimaLeitura = Double.parseDouble(partes[3]);

            return new Conta(idConta, idUsuario, idHidrometro, ultimaLeitura);

        } catch (Exception e) {
            throw new RuntimeException(
                    Messages.getString("ContaDAO.2") + linha, e); //$NON-NLS-1$
        }
    }

    /* =========================
       Escrita
       ========================= */

    /**
     * Salva uma conta no mapa em memória.
     *
     * @param conta conta a ser salva
     */
    public synchronized void salvar(Conta conta) {
        contas.put(conta.getId(), conta);
    }

    /**
     * Inicializa o sistema, carregando os dados do arquivo.
     */
    public void iniciarSistema() {
        carregarDoArquivo();
    }

    /**
     * Encerra o sistema, salvando todas as contas no arquivo.
     */
    public void encerrarSistema() {
        regravarArquivo();
    }

    /**
     * Regrava completamente o arquivo a partir do mapa em memória.
     * 
     * O arquivo antigo é sobrescrito.
     */
    private synchronized void regravarArquivo() {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(ARQUIVO, false))) {

            for (Conta conta : contas.values()) {

                String linha = conta.getId() + "-" //$NON-NLS-1$
                        + conta.getIdUsuario() + "-" //$NON-NLS-1$
                        + conta.getIdHidrometro() + "-" //$NON-NLS-1$
                        + conta.getUltimaLeitura();

                writer.write(linha);
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(Messages.getString("ContaDAO.3"), e); //$NON-NLS-1$
        }
    }

    /* =========================
       Observer
       ========================= */

    /**
     * Método chamado quando ocorre um evento no sistema.
     * 
     * Remove automaticamente as contas associadas a:
     * - usuários removidos
     * - hidrômetros removidos
     *
     * @param evento evento recebido
     */
    @Override
    public synchronized void atualizar(Evento evento) {

        switch (evento.getTipo()) {

            case USUARIO_REMOVIDO:
                contas.values().removeIf(
                        conta -> conta.getIdUsuario().equals(evento.getId())
                );
                break;

            case HIDROMETRO_REMOVIDO:
                contas.values().removeIf(
                        conta -> conta.getIdHidrometro().equals(evento.getId())
                );
                break;
        }
    }

    /* =========================
       Utilitários
       ========================= */

    /**
     * Retorna todas as contas em formato de lista.
     *
     * @return lista de contas
     */
    public List<Conta> listarContasDeAgua() {
        return contas.values().stream().toList();
    }

    /**
     * Remove uma conta pelo seu identificador.
     *
     * @param id identificador da conta
     */
    public void removerConta(String id) {
        contas.remove(id);
    }
}
