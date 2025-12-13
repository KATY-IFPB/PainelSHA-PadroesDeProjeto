package estadosDoPainel;

import src.FachadaSHA;
import src.OperacaoPainel;

public interface EstadoPainelIF {
    void mostrarMenu();
    OperacaoPainel interpretarOpcao(String opcao, FachadaSHA fachada);
}
