package hidrometroOpenCV;

public class Test {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        System.out.println("OpenCV carregado: " + Core.VERSION);
    }
}
