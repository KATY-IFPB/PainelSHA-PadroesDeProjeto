package conta;

import java.util.Random;

/**
 * Classe que representa uma Conta de Água.
 * 
 * Uma conta está associada a:
 * - um usuário
 * - um hidrômetro
 * - a última leitura registrada
 * 
 * Essa classe é uma entidade de domínio, utilizada pelo sistema
 * para armazenar e manipular os dados da conta em memória.
 */
public class Conta {

    /** Identificador único da conta (gerado automaticamente) */
    private final String id;

    /** Identificador do usuário associado à conta */
    private String idUsuario;

    /** Identificador do hidrômetro associado à conta */
    private String idHidrometro;

    /** Última leitura registrada para esta conta */
    private double ultimaLeitura;

    /** Gerador de números aleatórios usado para criar o ID da conta */
    private Random random = new Random();

    /**
     * Construtor utilizado na criação de uma nova conta.
     * 
     * O ID da conta é gerado automaticamente com 7 dígitos.
     *
     * @param idUsuario     identificador do usuário
     * @param idHidrometro  identificador do hidrômetro
     * @param ultimaLeitura valor inicial da leitura
     */
    public Conta(String idUsuario, String idHidrometro, double ultimaLeitura) {
        this.id = String.format("%07d", random.nextInt(10000000));
        this.idUsuario = idUsuario;
        this.idHidrometro = idHidrometro;
        this.ultimaLeitura = ultimaLeitura;
    }

    /**
     * Construtor utilizado pelo DAO ao carregar dados do arquivo.
     * 
     * Nesse caso, o ID da conta já existe e não deve ser gerado novamente.
     *
     * @param id            identificador da conta
     * @param idUsuario     identificador do usuário
     * @param idHidrometro  identificador do hidrômetro
     * @param ultimaLeitura última leitura registrada
     */
    public Conta(String id, String idUsuario, String idHidrometro, double ultimaLeitura) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idHidrometro = idHidrometro;
        this.ultimaLeitura = ultimaLeitura;
    }

    /**
     * Retorna o identificador único da conta.
     *
     * @return ID da conta
     */
    public String getId() {
        return id;
    }

    /**
     * Retorna o identificador do usuário associado à conta.
     *
     * @return ID do usuário
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Retorna o identificador do hidrômetro associado à conta.
     *
     * @return ID do hidrômetro
     */
    public String getIdHidrometro() {
        return idHidrometro;
    }

    /**
     * Retorna a última leitura registrada.
     *
     * @return valor da última leitura
     */
    public double getUltimaLeitura() {
        return ultimaLeitura;
    }

    /**
     * Define o usuário associado à conta.
     *
     * @param idUsuario novo identificador do usuário
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Define o hidrômetro associado à conta.
     *
     * @param idHidrometro novo identificador do hidrômetro
     */
    public void setIdHidrometro(String idHidrometro) {
        this.idHidrometro = idHidrometro;
    }

    /**
     * Atualiza a última leitura registrada na conta.
     *
     * @param ultimaLeitura novo valor da leitura
     */
    public void setUltimaLeitura(double ultimaLeitura) {
        this.ultimaLeitura = ultimaLeitura;
    }

    /**
     * Retorna uma representação textual da conta.
     * 
     * Utilizado principalmente para depuração e logs.
     */
    @Override
    public String toString() {
        return "Conta{" +
                "id='" + id + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", idHidrometro='" + idHidrometro + '\'' +
                ", ultimaLeitura=" + ultimaLeitura +
                '}';
    }
}
