package bg.sofia.uni.fmi.mjt.food.server.retriever.report;

import bg.sofia.uni.fmi.mjt.food.server.cache.FoodInfoCache;
import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetriever;
import bg.sofia.uni.fmi.mjt.food.server.retriever.report.search.FoodReport;
import com.google.gson.Gson;

import java.net.http.HttpClient;

import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.*;

public class FoodReportRetriever extends FoodInfoRetriever {

    public FoodReportRetriever(HttpClient client, String apiKey) {
        super(client, apiKey);
    }

    @Override
    public String buildAnalyzerURIString(String fdcId) {
        return String.format(getAnalyzerUriTemplate(), fdcId, getApiKey());
    }

    @Override
    public String getRequiredInformationAsString(String searchInput) {
        String cacheInfo = FoodInfoCache.checkInCache(searchInput);
        if (cacheInfo != null) {
            System.out.println(FOUND_IN_CACHE_MESSAGE);
            return cacheInfo;
        }

        FoodReport foodReport = getRequiredInformation(searchInput);
        if (foodReport != null) {
            FoodInfoCache.addToCache(foodReport.getFdcId(), foodReport.toString());
            FoodInfoCache.addToGtinUpcCache(foodReport.getGtinUpc(), foodReport.toString());
            return foodReport.toString();
        }
        final String notFoundMessage = NOT_FOUND_MESSAGE;
        FoodInfoCache.addToCache(searchInput, notFoundMessage);
        return notFoundMessage;
    }

    FoodReport getRequiredInformation(String searchInput) {
        System.out.println(SENDING_REQUEST_MESSAGE);
        String analyzerJsonResponse = retrieveInformationFromFDC(searchInput);

        Gson gson = new Gson();
        return gson.fromJson(analyzerJsonResponse, FoodReport.class);
    }
}
