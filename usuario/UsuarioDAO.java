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
	 * Método público para obter a instância única. Singleton Lazy (criado apenas no
	 * primeiro uso).
	 */
	public static synchronized UsuarioDAO getInstance() {
		if (instancia == null) {
			instancia = new UsuarioDAO(Messages.getString("NomeArquivoDeUsuarios")); //$NON-NLS-1$ );
		}
		return instancia;
	}

	/**
	 * Salva um usuário no mapa em tempo de execução. Impede IDs duplicados, pois só
	 * adiciona o usuario se o login não existir.
	 */
	public void salvar(Usuario usuario) throws IOException, UsuarioException {

		mapaUsuarios.putIfAbsent(usuario.getLogin(), usuario);

	}

	private Map<String, Usuario> listarTodosUsuariosArquivo() throws IOException {
	    Map<String, Usuario> usuarios = new HashMap<>();

	    if (!arquivo.exists()) {
	        return usuarios;
	    }

	    try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
	        String linha;

	        while ((linha = br.readLine()) != null) {

	            // dividir corretamente usando hífen literal
	            String[] partes = linha.split("\\-");

	            if (partes.length != 3) {
	                continue;
	            }

	            try {
	                String login = partes[0].trim();
	                
	                String senha = partes[1].trim();
	                String nome  = partes[2].trim();

	                Usuario usuario = new Usuario(login, nome, senha);
	                usuarios.put(login, usuario);

	            } catch (Exception e) {
	                // usuário corrompido → ignorar
	            }
	        }
	    }

	    return usuarios;
	}

		 

	/**
	 * Método opcional para inicializar o sistema. Pode ser usado para criar o
	 * arquivo caso não exista.
	 */
	public void inicializarSistema() {
		try {
			if (!arquivo.exists()) {
				arquivo.createNewFile();
				mapaUsuarios = new HashMap<String, Usuario>();
				// @TODO Adicionar usuario default?
			} else {
				mapaUsuarios = listarTodosUsuariosArquivo();
			}
		} catch (IOException e) {
			System.out.println("Erro ao inicializar arquivo: " + e.getMessage());
		}
	}

	public void fecharSistema() {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {

			for (Usuario u : mapaUsuarios.values()) {
				String linha = u.getLogin() + "-" + u.getSenha() + "-" + u.getNome();
				bw.write(linha);
				bw.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
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
		return mapaUsuarios.get(login);
	}
 public boolean containsKey(String login) {
		// TODO Auto-generated method stub
		return mapaUsuarios.containsKey(login);
	}
	public List<Usuario> listarUsuarios() {
		// TODO Auto-generated method stub
		return mapaUsuarios.values().stream().toList();
	}
}
