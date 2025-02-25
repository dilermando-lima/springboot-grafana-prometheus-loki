package runner1;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class App  {
    public static void main(String[] args) throws InterruptedException {
        while(true){
            TimeUnit.SECONDS.sleep(1);

            System.out.println("sending...");

            try {
                HttpClient.newHttpClient().send(
                    HttpRequest
                        .newBuilder()
                        .uri(URI.create("http://localhost:8083/api1"))
                        .header("X-REQUEST-ID", newRequestId())
                        .GET()
                    .build(),
                    HttpResponse.BodyHandlers.ofString()
                );
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static String newRequestId(){
        return "id-" + 
                Long.toString(new Random().nextInt(100000) + 1000, 36) + 
                Long.toString(new Random().nextInt(100000) + 1000, 36);
    }
}