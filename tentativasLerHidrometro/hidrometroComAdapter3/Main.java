package hidrometroComAdapter3;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class Main{

    /**
     * Método principal para ler os dígitos do display.
     */
    public String lerNumeros(File imagemArquivo) throws Exception {

        // ---- 1) Ler imagem original ----
        BufferedImage imagemOriginal = ImageIO.read(imagemArquivo);

        // ---- 2) Recortar apenas a área dos dígitos ----
        BufferedImage recorte = recortarDigitos(imagemOriginal);

        // ---- 3) Pré-processar a imagem ----
        BufferedImage processada = preprocessarImagem(recorte);

        // ---- 4) Configurar Tesseract ----
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("tessdata"); // pasta onde está o eng.traineddata
        tesseract.setLanguage("eng");

        tesseract.setOcrEngineMode(1); // LSTM
        tesseract.setPageSegMode(7);   // uma linha de texto
        tesseract.setTessVariable("tessedit_char_whitelist", "0123456789");

        // ---- 5) Executar OCR ----
        String texto = tesseract.doOCR(processada);

        // ---- 6) Limpar o texto ----
       //_______________________________________________ return texto.replaceAll("\\D", "");
        return texto;
    }



    /**
     * Recorta a área onde ficam os números do visor.
     * As coordenadas funcionam para as imagens enviadas.
     */
    private BufferedImage recortarDigitos(BufferedImage img) {

        // Coordenadas aproximadas para as imagens que você enviou
        int x = 250;
        int y = 140;
        int w = 230;
        int h = 70;

        return img.getSubimage(x, y, w, h);
    }



    /**
     * Pré-processamento ideal para displays digitais.
     */
    private BufferedImage preprocessarImagem(BufferedImage img) {

        // ---- 1) Converter para grayscale ----
        BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = gray.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        // ---- 2) Aumentar contraste ----
        RescaleOp rescale = new RescaleOp(1.8f, -30f, null);
        gray = rescale.filter(gray, null);

        // ---- 3) Binarizar (preto/branco) ----
        BufferedImage bin = new BufferedImage(gray.getWidth(), gray.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2 = bin.createGraphics();
        g2.drawImage(gray, 0, 0, null);
        g2.dispose();

        return bin;
    }


    // ---- Teste rápido ----
    public static void main(String[] args) throws Exception {

        Main leitor = new Main();

        // Teste com suas imagens
        
        String resultado2 = leitor.lerNumeros(new File("0.jpeg"));
        System.out.println("Resultado 2 = " + resultado2);
        
        String resultado1 = leitor.lerNumeros(new File("1.jpeg"));
        System.out.println("Resultado 1 = " + resultado1);
        
        String resultado3 = leitor.lerNumeros(new File("2.jpeg"));
        System.out.println("Resultado 2 = " + resultado3);
        
        String resultado4 = leitor.lerNumeros(new File("3.jpeg"));
        System.out.println("Resultado 1 = " + resultado4);
        
        String resultado5 = leitor.lerNumeros(new File("4.jpeg"));
        System.out.println("Resultado 2 = " + resultado5);
        
        String resultado6 = leitor.lerNumeros(new File("5.jpeg"));
        System.out.println("Resultado 1 = " + resultado6);

    }

}

