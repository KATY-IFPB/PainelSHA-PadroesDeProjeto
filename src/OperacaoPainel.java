package src;
public abstract class OperacaoPainel {

    // TEMPLATE METHOD
    public final void executar() {
        lerDados();
        validar();
        processar();
        exibirResultado();
    }

    protected abstract void lerDados();
    protected abstract void validar();
    protected abstract void processar();
    protected abstract void exibirResultado();
}
