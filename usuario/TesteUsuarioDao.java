package usuario;

import java.io.IOException;

public class TesteUsuarioDao {

    public static void main(String[] args) {

        UsuarioDAO dao = UsuarioDAO.getInstance();

        try {
            Usuario u = new Usuario("053.351.554-85", "Katia Silva", "abc123");

            dao.salvar(u);

            System.out.println("Usuários cadastrados:");
            for (Usuario x : dao.listarTodos()) {
                System.out.println(x);
            }

        } catch (IOException | UsuarioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
