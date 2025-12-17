package hidrometro2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class LeitorHidrometro {

    // Coordenadas dos 5 dígitos na sua imagem (sempre fixas!)
    private static final int[][] POSICOES = {
            {330, 150, 70, 90},  // dígito 1
            {410, 150, 70, 90},  // dígito 2
            {490, 150, 70, 90},  // dígito 3
            {570, 150, 70, 90},  // dígito 4
            {650, 150, 70, 90}   // dígito 5 (vermelho)
    };

    public static String lerDisplay(String arquivo) throws Exception {

        BufferedImage img = ImageIO.read(new File(arquivo));
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < POSICOES.length; i++) {

            int x = POSICOES[i][0];
            int y = POSICOES[i][1];
            int w = POSICOES[i][2];
            int h = POSICOES[i][3];

            BufferedImage digito = img.getSubimage(x, y, w, h);

            int valor = reconhecer(digito);

            resultado.append(valor);
        }

        return resultado.toString();
    }

    // Reconhece 0–9 comparando pixel a pixel com templates
    private static int reconhecer(BufferedImage digito) throws Exception {

        int melhor = -1;
        double menorErro = Double.MAX_VALUE;

        for (int n = 0; n <= 9; n++) {

            BufferedImage template = ImageIO.read(new File("digits/" + n + ".png"));

            double erro = comparar(digito, template);

            if (erro < menorErro) {
                menorErro = erro;
                melhor = n;
            }
        }

        return melhor;
    }

    // comparar pixel por pixel
    private static double comparar(BufferedImage a, BufferedImage b) {

        int w = Math.min(a.getWidth(), b.getWidth());
        int h = Math.min(a.getHeight(), b.getHeight());

        double soma = 0;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                int rgbA = a.getRGB(x, y) & 0xFFFFFF;
                int rgbB = b.getRGB(x, y) & 0xFFFFFF;

                int diff = Math.abs(rgbA - rgbB);

                soma += diff;
            }
        }

        return soma;
    }
    
    public static void main(String[] args) throws Exception {

        String arquivo = "0.jpeg"; // sua imagem

        String leitura = LeitorHidrometro.lerDisplay(arquivo);

        System.out.println("Valor lido: " + leitura);
    }
}
