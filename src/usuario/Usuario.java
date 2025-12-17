package usuario;

import src.Messages;

/**
 * Representa um usuário do sistema.
 *
 * Esta classe é uma entidade de domínio e contém:
 * - Dados do usuário (CPF/login, nome e senha)
 * - Regras de validação para garantir consistência e segurança
 *
 * Todas as validações lançam UsuarioException em caso de erro,
 * permitindo que camadas superiores tratem adequadamente.
 */
public class Usuario {

    /** CPF do usuário (armazenado sempre sem máscara) */
    private String login;

    /** Nome completo do usuário */
    private String nome;

    /** Senha do usuário */
    private String senha;

    /**
     * Construtor completo.
     *
     * O construtor delega as validações para os métodos setters,
     * garantindo reaproveitamento das regras de negócio.
     *
     * @param login CPF do usuário (com ou sem máscara)
     * @param nome  Nome completo
     * @param senha Senha do usuário
     * @throws UsuarioException caso algum dado seja inválido
     */
    public Usuario(String login, String nome, String senha) throws UsuarioException {
        setLogin(login);
        setNome(nome);
        setSenha(senha);
    }

    /** Retorna o CPF/login do usuário */
    public String getLogin() {
        return login;
    }

    /**
     * Define o CPF do usuário.
     *
     * Regras:
     * - Não pode ser nulo
     * - Deve conter exatamente 11 dígitos numéricos
     * - Não pode ter todos os dígitos iguais
     * - A máscara (pontos e hífen) é removida automaticamente
     *
     * @param login CPF informado pelo usuário
     * @throws UsuarioException caso o CPF seja inválido
     */
    public void setLogin(String login) throws UsuarioException {

        // Verificação de nulo
        if (login == null) {
            throw new UsuarioException(Messages.getString("Usuario.cpfNulo"));
        }

        // Normaliza o CPF: remove máscara
        String cpf = login.replace(".", "").replace("-", "");

        // Verifica se possui exatamente 11 dígitos
        if (!cpf.matches("\\d{11}")) {
            throw new UsuarioException(Messages.getString("Usuario.cpfFormatoInvalido"));
        }

        // Verifica se todos os dígitos são iguais (CPF inválido)
        if (cpf.chars().allMatch(c -> c == cpf.charAt(0))) {
            throw new UsuarioException(Messages.getString("Usuario.cpfInvalido"));
        }

        // Validação completa por dígitos verificadores (opcional)
        // if (!isCpfValido(cpf)) {
        //     throw new UsuarioException(Messages.getString("Usuario.cpfInvalido"));
        // }

        // Armazena sempre o CPF sem máscara
        this.login = cpf;
    }

    /**
     * Valida CPF utilizando o algoritmo oficial
     * dos dígitos verificadores.
     *
     * @param cpf CPF sem máscara
     * @return true se for válido, false caso contrário
     */
    private boolean isCpfValido(String cpf) {
        try {
            int soma = 0;

            // Cálculo do primeiro dígito verificador
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }
            int dig1 = 11 - (soma % 11);
            if (dig1 >= 10) dig1 = 0;

            soma = 0;

            // Cálculo do segundo dígito verificador
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }
            int dig2 = 11 - (soma % 11);
            if (dig2 >= 10) dig2 = 0;

            return dig1 == (cpf.charAt(9) - '0')
                && dig2 == (cpf.charAt(10) - '0');

        } catch (Exception e) {
            return false;
        }
    }

    /** Retorna o nome do usuário */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário.
     *
     * Regras:
     * - Não pode ser nulo ou vazio
     * - Deve conter apenas letras e espaços
     *
     * @param nome Nome completo
     * @throws UsuarioException caso o nome seja inválido
     */
    public void setNome(String nome) throws UsuarioException {

        if (nome == null || nome.trim().isEmpty()) {
            throw new UsuarioException(Messages.getString("Usuario.1"));
        }

        if (!nome.matches("[A-Za-z ]+")) {
            throw new UsuarioException(Messages.getString("Usuario.3"));
        }

        // Remove espaços extras
        this.nome = nome.trim();
    }

    /** Retorna a senha do usuário */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário.
     *
     * Regras:
     * - Não pode ser nula
     * - Deve ter no mínimo 4 caracteres
     * - Não pode conter o caractere '-'
     *
     * @param senha Senha informada
     * @throws UsuarioException caso a senha seja inválida
     */
    public void setSenha(String senha) throws UsuarioException {

        if (senha == null || senha.length() < 4) {
            throw new UsuarioException(Messages.getString("Usuario.4"));
        }

        if (senha.contains("-")) {
            throw new UsuarioException(Messages.getString("Usuario.5"));
        }

        this.senha = senha;
    }

    /**
     * Representação textual do usuário.
     *
     * A senha nunca é exibida por questões de segurança.
     */
    @Override
    public String toString() {
        return "Usuario{cpf/login=" + login + ", nome='" + nome + "'}";
    }
}
