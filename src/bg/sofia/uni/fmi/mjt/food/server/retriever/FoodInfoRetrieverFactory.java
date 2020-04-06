package bg.sofia.uni.fmi.mjt.food.server.retriever;

import bg.sofia.uni.fmi.mjt.food.server.retriever.data.FoodDataRetriever;
import bg.sofia.uni.fmi.mjt.food.server.retriever.report.FoodReportRetriever;

import java.net.http.HttpClient;

public class FoodInfoRetrieverFactory {
    public static FoodInfoRetriever getInstance(InformationType policy, HttpClient client, String apiKey) {
        if (policy == InformationType.DATA) {
            return new FoodDataRetriever(client, apiKey);
        } else {
            return new FoodReportRetriever(client, apiKey);
        }
    }
}
