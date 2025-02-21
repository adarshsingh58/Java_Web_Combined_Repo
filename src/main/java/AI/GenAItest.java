package AI;

import com.google.genai.Client;
import com.google.genai.ResponseStream;
import com.google.genai.types.GenerateContentResponse;
import org.apache.http.HttpException;

import java.io.IOException;

public class GenAItest {

    final static String API_KEY_GEMINI = "AIzaSyBQC2xDCT5pYPzMDaalo5fkSv7Wq196U94";

    public static void main(String[] args) throws HttpException, IOException {
//        genAiGemini();
        chatWithAI();
    }

    private static void genAiGemini() throws HttpException, IOException {
        Client client = Client.builder().apiKey(API_KEY_GEMINI).build();
        ResponseStream<GenerateContentResponse> responseStream = client.models.
                generateContentStream("gemini-2.0-flash-001", "tell me a story in 100 words", null);
        responseStream.forEach(res -> System.out.println(res.text()));
    }

    private static void chatWithAI() throws HttpException, IOException {
        Client client = Client.builder().apiKey(API_KEY_GEMINI).build();
    }

}
