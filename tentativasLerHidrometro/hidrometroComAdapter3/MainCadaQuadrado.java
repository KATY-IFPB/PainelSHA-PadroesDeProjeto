package hidrometroComAdapter3;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class MainCadaQuadrado{

    /**
     * Lê cada dígito separadamente.
     */
    public String lerNumeros(File imagemArquivo) throws Exception {

        // ---- 1) Ler imagem original ----
        BufferedImage imagemOriginal = ImageIO.read(imagemArquivo);

        // ---- 2) Recortar os dígitos individualmente ----
        BufferedImage[] digitos = recortarCadaDigito(imagemOriginal);

        // ---- 3) Configurar Tesseract ----
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("tessdata");
        tesseract.setLanguage("eng");
        tesseract.setOcrEngineMode(1); // LSTM
        tesseract.setPageSegMode(10);  // PSM_SINGLE_CHAR — ideal para 1 dígito
        tesseract.setTessVariable("tessedit_char_whitelist", "0123456789");

        StringBuilder numeroFinal = new StringBuilder();

        // ---- 4) Processar cada quadradinho ----
        for (BufferedImage dig : digitos) {

            BufferedImage proc = preprocessarImagem(dig);

            String txt = tesseract.doOCR(proc);

            txt = txt.replaceAll("\\D", ""); // limpar

            if (txt.length() == 0)
                numeroFinal.append("?"); // dígito ilegível
            else
                numeroFinal.append(txt.charAt(0));
        }

        return numeroFinal.toString();
    }



    /**
     * Recorta cada dígito individualmente.
     * Ajuste baseado no layout da imagem enviada.
     */
    private BufferedImage[] recortarCadaDigito(BufferedImage img) {

        // POSIÇÃO BASE do primeiro quadrado (esquerda)
        int baseX = 250;
        int baseY = 140;

        // TAMANHO do quadrado individual
        int w = 45;
        int h = 70;

        // DISTÂNCIA horizontal entre quadrados
        int espaçamento = 47;  // ajuste fino conforme sua imagem real

        // Existem 6 quadrados na imagem
        BufferedImage[] digitos = new BufferedImage[6];

        for (int i = 0; i < 6; i++) {
            int x = baseX + (i * espaçamento);
            digitos[i] = img.getSubimage(x, baseY, w, h);
        }

        return digitos;
    }



    /**
     * Pré-processamento ideal para displays digitais.
     */
    private BufferedImage preprocessarImagem(BufferedImage img) {

        // ---- 1) Grayscale ----
        BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = gray.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        // ---- 2) Aumentar contraste ----
        RescaleOp rescale = new RescaleOp(1.8f, -30f, null);
        gray = rescale.filter(gray, null);

        // ---- 3) Binarizar ----
        BufferedImage bin = new BufferedImage(gray.getWidth(), gray.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2 = bin.createGraphics();
        g2.drawImage(gray, 0, 0, null);
        g2.dispose();

        return bin;
    }


    public static void main(String[] args) throws Exception {

        Main leitor = new Main();

        System.out.println("Resultado = " + leitor.lerNumeros(new File("0.jpeg")));
        System.out.println("Resultado = " + leitor.lerNumeros(new File("1.jpeg")));
        System.out.println("Resultado = " + leitor.lerNumeros(new File("2.jpeg")));
        System.out.println("Resultado = " + leitor.lerNumeros(new File("3.jpeg")));
        System.out.println("Resultado = " + leitor.lerNumeros(new File("4.jpeg")));
        System.out.println("Resultado = " + leitor.lerNumeros(new File("5.jpeg")));
    }
}