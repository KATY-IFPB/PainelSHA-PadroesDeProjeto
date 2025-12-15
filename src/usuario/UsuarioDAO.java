package usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.Configuracoes;
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
			instancia = new UsuarioDAO(Configuracoes.getString("NomeArquivoDeUsuarios")); //$NON-NLS-1$ );
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

	/*
	 * ]* Método opcional para fechar o sistema e salvar os dados dos usuarios em arquivo.
	 */
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

	public void remover(String id) {
		mapaUsuarios.remove(id);
	}

	public void update(String id, String nome, String senha) {
		Usuario aux;
		try {
			aux = new Usuario(id, nome, senha);
			mapaUsuarios.put(id+"", aux);
		} catch (UsuarioException e) {
			
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub

	}

	public Usuario buscarPorLogin(String login) {
		// TODO Auto-generated method stub
		return mapaUsuarios.get(login);
	}

	
	public boolean containsKey(String login) { 
		return mapaUsuarios.containsKey(login); }

	public List<Usuario> listarUsuarios() {
		// TODO Auto-generated method stub
		return mapaUsuarios.values().stream().toList();
	}

	public void salvarEstadoUsuarios() {
		// 1) Abrir o arquivo SEM append → APAGA o conteúdo antigo
        try (FileWriter fw = new FileWriter(arquivo.getName(), false)) {
			List<Usuario> lista = listarUsuarios();
            // 2) Escrever todos os usuários do zero
            for (Usuario u : lista) {
                String linha = u.getLogin() + "-" + u.getSenha() + "-" + u.getNome();
                fw.write(linha + System.lineSeparator());
            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
