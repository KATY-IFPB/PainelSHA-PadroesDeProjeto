package operacoes;

import src.UsuarioExistenteException;

public abstract class OperacaoPainel {

    // TEMPLATE METHOD
    public final void executar() throws UsuarioExistenteException {
        lerDados();
        validar();
        processar();
        exibirResultado();
    }

    protected abstract void lerDados();
    protected abstract void validar();
    protected abstract void processar() throws UsuarioExistenteException;
    protected abstract void exibirResultado();
}
