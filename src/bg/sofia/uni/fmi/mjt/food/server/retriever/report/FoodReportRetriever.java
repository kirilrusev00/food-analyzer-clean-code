package bg.sofia.uni.fmi.mjt.food.server.retriever.report;

import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetrieverImpl;
import bg.sofia.uni.fmi.mjt.food.server.retriever.data.search.Food;
import bg.sofia.uni.fmi.mjt.food.server.retriever.data.search.FoodSearch;
import bg.sofia.uni.fmi.mjt.food.server.retriever.report.search.FoodReport;
import com.google.gson.Gson;

import java.net.http.HttpClient;

public class FoodReportRetriever extends FoodInfoRetrieverImpl {

    public FoodReportRetriever(HttpClient client, String apiKey) {
        super(client, apiKey);
    }

    @Override
    public String buildAnalyzerURIString(String fdcId) {
        return String.format(getAnalyzerUriTemplate(), fdcId, getApiKey());
    }

    @Override
    public String getRequiredInformationAsString(String searchInput) {
        String cacheInfo = checkInCache(searchInput);
        if (cacheInfo != null) {
            System.out.println("Found in cache!");
            return cacheInfo;
        }

        FoodReport foodReport = getRequiredInformation(searchInput);
        if (foodReport != null) {
            addToCache(foodReport.getFdcId(), foodReport.toString());
            addToGtinUpcCache(foodReport.getGtinUpc(), foodReport.toString());
            return foodReport.toString();
        }
        final String notFoundMessage = "No food matching the given input";
        addToCache(searchInput, notFoundMessage);
        return notFoundMessage;
    }

    FoodReport getRequiredInformation(String searchInput) {
        System.out.println("Sending request to FoodData Central!");
        String analyzerJsonResponse = retrieveInformationFromFDC(searchInput);

        Gson gson = new Gson();
        return gson.fromJson(analyzerJsonResponse, FoodReport.class);
    }
}
