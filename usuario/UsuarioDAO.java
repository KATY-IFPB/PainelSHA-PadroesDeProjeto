package usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.Messages;

public class UsuarioDAO {

    // Instância única (Singleton)
    private static UsuarioDAO instancia;

    // Caminho fixo do arquivo
    private final File arquivo;
    private Map<String, Usuario> mapaUsuarios;

    /**
     * Construtor privado — impede criação externa.
     */
    private UsuarioDAO(String caminhoArquivo) {
        this.arquivo = new File(caminhoArquivo);
    }

    /**
     * Método público para obter a instância única.
     * Singleton Lazy (criado apenas no primeiro uso).
     */
    public static synchronized UsuarioDAO getInstance() {
        if (instancia == null) {
            instancia = new UsuarioDAO(Messages.getString("NomeArquivoDeUsuarios")); //$NON-NLS-1$);
        }
        return instancia;
    }

    /**
     * Salva um usuário no arquivo (append).
     * Impede IDs duplicados.
     */
    public void salvar(Usuario usuario) throws IOException, UsuarioException {

        List<Usuario> usuarios = listarTodos();

        for (Usuario u : usuarios) {
            if (u.getLogin() == usuario.getLogin()) {
                throw new UsuarioException("ID já existe no arquivo: " + usuario.getLogin());
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
            String linha = usuario.getLogin() + "-" + usuario.getSenha() + "-" + usuario.getNome();
            bw.write(linha);
            bw.newLine();
        }
    }

    /**
     * Lê todos os usuários do arquivo.
     */
    private Map<String,Usuario> listarTodosUsuariosArquivo() throws IOException {
        Map<String,Usuario> usuarios = new HashMap<String, Usuario>();

        if (!arquivo.exists()) {
            return usuarios;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("-");

                if (partes.length != 3) {
                    continue;
                }

                try {
                    String login = partes[0];
                    String senha = partes[1];
                    String nome = partes[2];

                    Usuario usuario = new Usuario(login, nome, senha);
                    usuarios.put(login,usuario);

                } catch (Exception e) {
                    // Ignora usuários corrompidos
                }
            }
        }

        return usuarios;
    }

    /**
     * Método opcional para inicializar o sistema.
     * Pode ser usado para criar o arquivo caso não exista.
     */
    public void inicializarSistema() {
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
                mapaUsuarios = new HashMap<String, Usuario>();
                //@TODO Adicionar usuario default?
            }else {
            	mapaUsuarios = listarTodosUsuariosArquivo();
            }
        } catch (IOException e) {
            System.out.println("Erro ao inicializar arquivo: " + e.getMessage());
        }
    }

	public void remover(int id) {
		// TODO Auto-generated method stub
		
	}

	public void update(int id, String nome, String senha) {
		// TODO Auto-generated method stub
		
	}

	public Usuario buscarPorLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Usuario> listarUsuarios() {
		// TODO Auto-generated method stub
		return mapaUsuarios;
	}
}

