package src;

public interface EstadoPainelIF {
    void mostrarMenu();
    OperacaoPainel interpretarOpcao(String opcao, FachadaSHA fachada);
}
