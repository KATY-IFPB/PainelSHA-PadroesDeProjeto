package src;

/**
 * Representa um usuário do sistema.
 * A classe inclui validações para garantir que os dados
 * sejam consistentes e seguros.
 */
public class Usuario {

    private String login; // CPF
    private String nome;
    private String senha;

    /**
     * Construtor completo com validações.
     *
     * @param login CPF do usuário (com ou sem máscara).
     * @param nome  Nome completo do usuário (não pode ser vazio).
     * @param senha Senha do usuário (mínimo de 6 caracteres).
     * @throws UsuarioException Caso algum dado seja inválido.
     */
    public Usuario(String login, String nome, String senha) throws UsuarioException {
        setLogin(login);
        setNome(nome);
        setSenha(senha);
    }

    public String getLogin() {
        return login;
    }

    /**
     * Define o CPF do usuário.
     *
     * @param login CPF válido (somente números ou formato XXX.XXX.XXX-XX)
     * @throws UsuarioException caso CPF seja inválido.
     */
    public void setLogin(String login) throws UsuarioException {
        if (login == null) {
            throw new UsuarioException(Messages.getString("Usuario.cpfNulo"));
        }

        // Normaliza: remove máscara
        String cpf = login.replace(".", "").replace("-", "");

        if (!cpf.matches("\\d{11}")) {
            throw new UsuarioException(Messages.getString("Usuario.cpfFormatoInvalido"));
        }

        // Verifica se todos os dígitos iguais
        if (cpf.chars().allMatch(c -> c == cpf.charAt(0))) {
            throw new UsuarioException(Messages.getString("Usuario.cpfInvalido"));
        }

        if (!isCpfValido(cpf)) {
            throw new UsuarioException(Messages.getString("Usuario.cpfInvalido"));
        }

        this.login = cpf; // Armazena sempre sem máscara
    }

    /**
     * Valida CPF usando o algoritmo dos dígitos verificadores.
     */
    private boolean isCpfValido(String cpf) {
        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }
            int dig1 = 11 - (soma % 11);
            if (dig1 >= 10) dig1 = 0;

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }
            int dig2 = 11 - (soma % 11);
            if (dig2 >= 10) dig2 = 0;

            return dig1 == (cpf.charAt(9) - '0') && dig2 == (cpf.charAt(10) - '0');

        } catch (Exception e) {
            return false;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws UsuarioException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new UsuarioException(Messages.getString("Usuario.1"));
        }
        if(!nome.matches("[A-Za-z ]+")) {
            throw new UsuarioException(Messages.getString("Usuario.3"));
        }
        this.nome = nome.trim();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) throws UsuarioException {
        if (senha == null || senha.length() < 6) {
            throw new UsuarioException(Messages.getString("Usuario.4"));
        }
        if (senha.contains("-")) {
            throw new UsuarioException(Messages.getString("Usuario.5"));
        }
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario{cpf=" + login + ", nome='" + nome + "'}";
        // Senha não exibida por segurança.
    }
}
