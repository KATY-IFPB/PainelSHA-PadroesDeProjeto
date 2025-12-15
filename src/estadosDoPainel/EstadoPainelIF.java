package estadosDoPainel;

import operacoes.OperacaoPainel;
import src.FachadaSHA;

public interface EstadoPainelIF {
    void mostrarMenu();
    OperacaoPainel interpretarOpcao(String opcao, FachadaSHA fachada);
}
