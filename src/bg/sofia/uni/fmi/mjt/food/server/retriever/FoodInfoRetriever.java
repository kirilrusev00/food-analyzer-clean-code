package bg.sofia.uni.fmi.mjt.food.server.retriever;

import java.io.IOException;

public interface FoodInfoRetriever {
    //String buildAnalyzerURIString(String searchInput);

    //String retrieveInformationFromFDC(String searchInput);

    String getRequiredInformationAsString(String allInformation);
}
