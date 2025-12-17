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

import src.Configuracoes;
import src.Evento;
import src.Messages;
import src.Observer;
import src.Subject;

/**
 * UsuarioDAO
 *
 * Classe responsável pela persistência e gerenciamento dos usuários.
 *
 * Padrões aplicados:
 * - DAO (Data Access Object): encapsula acesso ao arquivo
 * - Singleton: garante uma única instância
 * - Observer (Subject): notifica outros subsistemas quando um usuário é removido
 */
public class UsuarioDAO implements Subject {

	/** Instância única do DAO (Singleton) */
	private static UsuarioDAO instancia;

	/** Arquivo onde os usuários são persistidos */
	private final File arquivo;

	/** Estrutura em memória para acesso rápido aos usuários */
	private Map<String, Usuario> mapaUsuarios;

	/** Lista de observers interessados em eventos de usuário */
	private List<Observer> observers = new ArrayList<>();

	/**
	 * Construtor privado.
	 * Impede criação direta da classe fora do padrão Singleton.
	 */
	private UsuarioDAO(String caminhoArquivo) {
		this.arquivo = new File(caminhoArquivo);
	}

	/**
	 * Retorna a instância única do UsuarioDAO.
	 *
	 * Singleton Lazy:
	 * - A instância só é criada quando o método é chamado pela primeira vez
	 */
	public static synchronized UsuarioDAO getInstance() {
		if (instancia == null) {
			instancia = new UsuarioDAO(
				Configuracoes.getString("NomeArquivoDeUsuarios")
			);
		}
		return instancia;
	}

	/**
	 * Salva um usuário apenas em memória.
	 *
	 * - Não sobrescreve usuários existentes
	 * - A persistência em arquivo ocorre posteriormente
	 */
	public void salvar(Usuario usuario) throws IOException, UsuarioException {
		mapaUsuarios.putIfAbsent(usuario.getLogin(), usuario);
	}

	/**
	 * Lê todos os usuários do arquivo e os carrega para um mapa.
	 *
	 * Formato esperado do arquivo:
	 * CPF-SENHA-NOME
	 *
	 * Linhas inválidas ou corrompidas são ignoradas.
	 */
	private Map<String, Usuario> listarTodosUsuariosArquivo() throws IOException {

	    Map<String, Usuario> usuarios = new HashMap<>();

	    // Se o arquivo não existir, retorna mapa vazio
	    if (!arquivo.exists()) {
	        return usuarios;
	    }

	    try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
	        String linha;

	        while ((linha = br.readLine()) != null) {

	            // Divide usando hífen literal
	            String[] partes = linha.split("\\-");

	            // Linha inválida
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
	                // Usuário inválido ou corrompido → ignorado
	            }
	        }
	    }

	    return usuarios;
	}

	/**
	 * Inicializa o sistema de usuários.
	 *
	 * - Cria o arquivo caso não exista
	 * - Carrega os usuários existentes para a memória
	 */
	public void inicializarSistema() {
		try {
			if (!arquivo.exists()) {
				arquivo.createNewFile();
				mapaUsuarios = new HashMap<>();
				// @TODO adicionar usuário padrão, se necessário
			} else {
				mapaUsuarios = listarTodosUsuariosArquivo();
			}
		} catch (IOException e) {
			System.out.println(Messages.getString("UsuarioDAO.1") + e.getMessage());
		}
	}

	/**
	 * Persiste todos os usuários da memória para o arquivo.
	 *
	 * - Sobrescreve completamente o arquivo
	 * - Usado ao encerrar o sistema
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

	/**
	 * Remove um usuário do sistema.
	 *
	 * Após a remoção, notifica os observers (ex: ContaDAO),
	 * permitindo limpeza de dados relacionados.
	 */
	public void remover(String id) {
		mapaUsuarios.remove(id);
		notificarObservers(
			new Evento(Evento.Tipo.USUARIO_REMOVIDO, id)
		);
	}

	/**
	 * Atualiza um usuário existente.
	 *
	 * Cria um novo objeto Usuario e substitui o anterior no mapa.
	 */
	public void update(String id, String nome, String senha) {
		try {
			Usuario aux = new Usuario(id, nome, senha);
			mapaUsuarios.put(id, aux);
		} catch (UsuarioException e) {
			e.printStackTrace();
		}
	}

	/** Busca um usuário pelo login (CPF) */
	public Usuario buscarPorLogin(String login) {
		return mapaUsuarios.get(login);
	}

	/** Verifica se existe um usuário com o login informado */
	public boolean containsKey(String login) {
		return mapaUsuarios.containsKey(login);
	}

	/** Retorna todos os usuários cadastrados */
	public List<Usuario> listarUsuarios() {
		return mapaUsuarios.values().stream().toList();
	}

	/**
	 * Salva explicitamente o estado atual dos usuários no arquivo.
	 *
	 * Usado, por exemplo, ao realizar logout.
	 */
	public void salvarEstadoUsuarios() {

        // Abre o arquivo sem append → sobrescreve tudo
        try (FileWriter fw = new FileWriter(arquivo.getName(), false)) {

			List<Usuario> lista = listarUsuarios();

            for (Usuario u : lista) {
                String linha = u.getLogin() + "-" + u.getSenha() + "-" + u.getNome();
                fw.write(linha + System.lineSeparator());
            }

        } catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ===================== OBSERVER =====================

	@Override
	public void adicionarObserver(Observer o) {
	    if (o != null && !observers.contains(o)) {
	        observers.add(o);
	    }
	}

	@Override
	public void removerObserver(Observer o) {
	    observers.remove(o);
	}

	@Override
	public void notificarObservers(Evento evento) {
	    for (Observer o : observers) {
	        o.atualizar(evento);
	    }
	}
}
