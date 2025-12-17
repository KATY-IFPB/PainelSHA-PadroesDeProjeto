package hidrometroOpenCV;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.w3c.dom.css.Rect;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class LeitorHidrometroOtimizado {

    public static void main(String[] args) {
        // --- Substitua pelo caminho da sua imagem no Linux ---
        String caminhoImagem = "./0.jpeg"; // Exemplo
        
        try {
            String leitura = processarEler(caminhoImagem);
            System.out.println("Leitura Otimizada (m³): " + leitura);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Falha ao processar a imagem. Verifique se o Tesseract está instalado e se as dependências do JavaCV estão no CLASSPATH.");
        }
    }

    public static String processarEler(String caminhoImagem) throws TesseractException {
        // O Tesseract deve estar instalado no seu sistema Linux para que o Tess4J funcione.
        // Verifique com 'tesseract -v' no seu terminal.

        // --- 1. CARREGAR E CORTAR A IMAGEM (com JavaCV/OpenCV) ---
        Mat imagemOriginal = imread(caminhoImagem);
        // ... (código de carregamento e verificação omitido por brevidade, é o mesmo da resposta anterior)
        
        // --- AJUSTE DE COORDENADAS: Essencial para suas imagens (800x600 aprox.) ---
        // Estas coordenadas tentam isolar: [1] [8] [0] [0] 
        int x = 270;
        int y = 175;
        int largura = 300; 
        int altura = 100;

        Rect roi = new Rect(x, y, largura, altura);
        Mat areaDesejada = new Mat(imagemOriginal, roi);

        // --- 2. PRÉ-PROCESSAMENTO: Binarização e Contraste (OpenCV) ---
        Mat cinza = new Mat();
        opencv_imgproc.cvtColor(areaDesejada, cinza, opencv_imgproc.COLOR_BGR2GRAY);
        
        Mat binarizada = new Mat();
        // Thresholding Adaptativo: lida melhor com variações de luz
        opencv_imgproc.adaptiveThreshold(cinza, binarizada, 255, 
            opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, 
            opencv_imgproc.THRESH_BINARY_INV, 11, 2);

        // --- 3. EXECUTAR OCR COM TESSERACT ---
        ITesseract tesseract = new Tesseract();
        tesseract.setPageSegMode(7);
        tesseract.setTessVariable("tessedit_char_whitelist", "0123456789");

        // Salvar a Mat temporariamente em um arquivo para o Tesseract ler
        File tempFileOCR = null;
        try {
            Path tempPath = Files.createTempFile("ocr_input_", ".png");
            tempFileOCR = tempPath.toFile();
            imwrite(tempFileOCR.getAbsolutePath(), binarizada);

            String resultado = tesseract.doOCR(tempFileOCR);
            return resultado.replaceAll("[^0-9]", "");
            
        } catch (Exception e) {
             throw new RuntimeException("Erro ao salvar/ler arquivo temporário para OCR.", e);
        } finally {
            if (tempFileOCR != null && tempFileOCR.exists()) {
                tempFileOCR.delete();
            }
            // Libera a memória alocada pelo OpenCV
            imagemOriginal.release();
            areaDesejada.release();
            cinza.release();
            binarizada.release();
        }
    }
}