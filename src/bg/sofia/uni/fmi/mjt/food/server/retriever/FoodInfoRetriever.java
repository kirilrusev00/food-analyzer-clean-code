package bg.sofia.uni.fmi.mjt.food.server.retriever;

import bg.sofia.uni.fmi.mjt.food.server.cache.Cache;
import bg.sofia.uni.fmi.mjt.food.server.cache.CacheFactory;

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

    private Cache<String, String> cache;
    private static Cache<String, String> gtinUpcCache;

    public FoodInfoRetriever(HttpClient client, String apiKey) {
        this.client = client;
        this.apiKey = apiKey;
        this.cache = CacheFactory.getInstance();
        gtinUpcCache = CacheFactory.getInstance();
    }

    public String checkInCache(String searchInput) {
        return cache.get(searchInput);
    }

    protected abstract String buildAnalyzerURIString(String searchInput);

    protected String retrieveInformationFromFDC(String searchInput) {
        String analyzerURIAsString = buildAnalyzerURIString(searchInput);
        URI analyzerURI = URI.create(analyzerURIAsString);
        System.out.println(analyzerURI);
        HttpRequest analyzerRequest = HttpRequest.newBuilder().uri(analyzerURI).build();

        //TO DO: Exceptions and make async
        /*client.sendAsync(analyzerRequest, BodyHandlers.ofString())
                .thenApply(response -> { System.out.println(response.statusCode());
                    return response; } )
                .thenApply(HttpResponse::body);*/
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

    public abstract String getRequiredInformationAsString(String allInformation);

    protected void addToCache(String key, String values) {
        cache.set(key, values);
    }

    protected void addToGtinUpcCache(String key, String values) {
        gtinUpcCache.set(key, values);
    }

    protected static String getAnalyzerUriTemplate() {
        return ANALYZER_URI_TEMPLATE;
    }

    protected String getApiKey() {
        return apiKey;
    }

    public static Cache<String, String> getGtinUpcCache() {
        return gtinUpcCache;
    }
}
