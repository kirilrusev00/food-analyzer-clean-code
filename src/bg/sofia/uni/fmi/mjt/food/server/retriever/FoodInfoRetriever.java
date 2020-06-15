package bg.sofia.uni.fmi.mjt.food.server.retriever;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class FoodInfoRetriever {
    private static final String ANALYZER_URI_TEMPLATE
            = "https://api.nal.usda.gov/fdc/v1/%s?api_key=%s&requireAllWords=true";

    private HttpClient client;
    private String apiKey;

    public FoodInfoRetriever(HttpClient client, String apiKey) {
        this.client = client;
        this.apiKey = apiKey;
    }

    public abstract String getRequiredInformationAsString(String allInformation);

    protected abstract String buildAnalyzerURIString(String searchInput);

    protected String retrieveInformationFromFDC(String searchInput) {
        String analyzerURIAsString = buildAnalyzerURIString(searchInput);
        URI analyzerURI = URI.create(analyzerURIAsString);
        System.out.println(analyzerURI);
        HttpRequest analyzerRequest = HttpRequest.newBuilder().uri(analyzerURI).build();

        try {
            return client.send(analyzerRequest, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException e) {
            System.err.println((String.format("Error in response from DFC %n%s", e.getMessage())));
            return null;
        } catch (InterruptedException e) {
            System.err.println((String.format("Error while getting response from DFC %n%s", e.getMessage())));
            return null;
        }
    }

    protected static String getAnalyzerUriTemplate() {
        return ANALYZER_URI_TEMPLATE;
    }

    protected String getApiKey() {
        return apiKey;
    }

}
