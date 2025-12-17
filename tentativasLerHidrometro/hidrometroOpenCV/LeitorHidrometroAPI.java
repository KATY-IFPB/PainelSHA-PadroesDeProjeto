package hidrometroOpenCV;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class LeitorHidrometroAPI {

    // --- 1. DEFINA SEUS DADOS DA API AQUI ---
    // Você deve usar um serviço de API de Visão Computacional (ex: Google Cloud Vision, AWS Rekognition)
    private static final String API_KEY = "SUA_CHAVE_SECRETA"; 
    private static final String API_ENDPOINT = "https://api.example.com/seu/servico/ocr"; 

    // O arquivo 0.jpeg que você enviou parece ter a leitura '1800' (ou próximo).
    private static final String CAMINHO_IMAGEM = "./0.jpeg"; 

    public static void main(String[] args) {
        try {
            System.out.println("Enviando imagem para OCR de Nuvem...");
            String resultadoJSON = enviarImagemParaAPI(CAMINHO_IMAGEM);
            
            System.out.println("\n--- Resposta da API ---");
            // A resposta conterá o JSON com o texto detectado.
            System.out.println(resultadoJSON);
            
            // Você precisará de uma biblioteca JSON (ex: Jackson) para extrair o número
            // Se usar o Google Cloud Vision, o texto bruto é fácil de encontrar no JSON.
            
        } catch (Exception e) {
            System.err.println("\nERRO: Falha na comunicação ou no arquivo. Verifique se o caminho da imagem está correto.");
            System.err.println("Detalhe: " + e.getMessage());
        }
    }

    /**
     * Carrega o arquivo, codifica para Base64 e envia para o endpoint da API.
     */
    public static String enviarImagemParaAPI(String imagePath) throws IOException, InterruptedException {
        // 1. Converte a imagem para Base64 (formato que APIs usam para binários)
        Path path = Path.of(imagePath);
        byte[] imageBytes = Files.readAllBytes(path);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // 2. Cria o JSON de requisição (modelo genérico para OCR de imagem)
        String jsonPayload = String.format("""
            {
                "image": { "content": "%s" },
                "features": [ { "type": "TEXT_DETECTION" } ]
            }
            """, base64Image);

        // 3. Configura e envia a requisição HTTP (sem JARs externos)
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY) 
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("API retornou status não-200: " + response.statusCode());
        }

        return response.body();
    }
}