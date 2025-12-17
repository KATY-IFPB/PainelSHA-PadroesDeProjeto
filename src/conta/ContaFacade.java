package conta;

import java.util.Collection;
import java.util.List;

import src.Messages;

/**
 * Classe Facade responsável por centralizar os casos de uso
 * relacionados às contas de água.
 *
 * Esta classe:
 * - Implementa o padrão Facade
 * - Implementa o padrão Singleton
 * - Encapsula regras de negócio
 * - Atua como intermediária entre a camada de interface
 *   e o ContaDAO
 */
public class ContaFacade {

    /** Instância única da Facade (Singleton) */
    private static ContaFacade instance;

    /** DAO responsável pela persistência das contas */
    private ContaDAO contaDAO;

    /**
     * Construtor privado.
     * Inicializa o DAO associado.
     */
    private ContaFacade() {
        this.contaDAO = ContaDAO.getInstance();
    }

    /**
     * Retorna a instância única da Facade.
     *
     * @return instância única de ContaFacade
     */
    public static synchronized ContaFacade getInstance() {
        if (instance == null) {
            instance = new ContaFacade();
        }
        return instance;
    }

    /* =========================
       Casos de uso
       ========================= */

    /**
     * Cria uma nova conta de água associada a um usuário e a um hidrômetro.
     *
     * Regra de negócio:
     * - Cada hidrômetro pode estar associado a apenas uma conta.
     *
     * @param idUsuario     identificador do usuário
     * @param idHidrometro  identificador do hidrômetro
     * @param leituraInicial leitura inicial do hidrômetro
     * @return conta criada
     */
    public Conta criarContaDeAgua(
            String idUsuario,
            String idHidrometro,
            double leituraInicial) {

        // Regra de negócio: 1 hidrômetro → 1 conta
        if (existeContaParaHidrometro(idHidrometro)) {
            throw new IllegalStateException(
                    Messages.getString("ContaFacade.0") + idHidrometro); //$NON-NLS-1$
        }

        Conta conta = new Conta(idUsuario, idHidrometro, leituraInicial);
        contaDAO.salvar(conta);

        return conta;
    }

    /**
     * Atualiza a última leitura registrada de uma conta.
     *
     * @param idConta     identificador da conta
     * @param novaLeitura nova leitura do hidrômetro
     */
    public void atualizarLeitura(String idConta, double novaLeitura) {
        Conta conta = contaDAO.buscarPorId(idConta);

        if (conta == null) {
            throw new IllegalArgumentException(
                    Messages.getString("ContaFacade.1") + idConta); //$NON-NLS-1$
        }

        conta.setUltimaLeitura(novaLeitura);

        /*
         * Observação:
         * Este método é um ponto estratégico para:
         * - recalcular consumo
         * - gerar faturas
         * - notificar observers
         * - registrar logs
         * - persistir alterações
         */
    }

    /**
     * Busca uma conta pelo seu identificador.
     *
     * @param idConta identificador da conta
     * @return conta encontrada ou null
     */
    public Conta buscarConta(String idConta) {
        return contaDAO.buscarPorId(idConta);
    }

    /**
     * Lista todas as contas cadastradas no sistema.
     *
     * @return coleção de contas
     */
    public Collection<Conta> listarContas() {
        return contaDAO.listarTodas();
    }

    /* =========================
       Regras de negócio
       ========================= */

    /**
     * Verifica se já existe uma conta associada a um determinado hidrômetro.
     *
     * @param idHidrometro identificador do hidrômetro
     * @return true se existir uma conta, false caso contrário
     */
    public boolean existeContaParaHidrometro(String idHidrometro) {
        return contaDAO.listarTodas().stream()
                .anyMatch(c -> c.getIdHidrometro().equals(idHidrometro));
    }

    /* =========================
       Ciclo de vida do sistema
       ========================= */

    /**
     * Inicializa o módulo de contas,
     * carregando os dados persistidos.
     */
    public void iniciarSistema() {
        contaDAO.iniciarSistema();
    }

    /**
     * Encerra o módulo de contas,
     * salvando os dados no arquivo.
     */
    public void logout() {
        contaDAO.encerrarSistema();
    }

    /* =========================
       Acesso ao DAO
       ========================= */

    /**
     * Retorna o DAO de contas.
     *
     * @return ContaDAO
     */
    public ContaDAO getContaDAO() {
        return contaDAO;
    }

    /**
     * Lista todas as contas de água em formato de lista.
     *
     * @return lista de contas
     */
    public List<Conta> listarContasDeAgua() {
        try {
            return contaDAO.listarContasDeAgua();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Verifica se existe uma conta com o ID informado.
     *
     * @param id identificador da conta
     * @return true se existir, false caso contrário
     */
    public boolean existeConta(String id) {
        return contaDAO.buscarPorId(id) != null;
    }

    /**
     * Remove uma conta pelo seu identificador.
     *
     * @param id identificador da conta
     */
    public void removerConta(String id) {
        contaDAO.removerConta(id);
    }
}
