package bg.sofia.uni.fmi.mjt.food.server;

import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetriever;
import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetrieverFactory;
import bg.sofia.uni.fmi.mjt.food.server.retriever.InformationType;
import bg.sofia.uni.fmi.mjt.food.server.retriever.barcode.BarcodeRetriever;

import java.io.IOException;
import java.net.http.HttpClient;

public class CommandExecutor {
    private static final int INDEX_OF_COMMAND = 0;
    private static final int INDEX_OF_GTIN_UPC = 1;

    private FoodInfoRetriever foodDataRetriever;
    private FoodInfoRetriever foodReportRetriever;
    private static final String apiKey = "LMm1mjww0SJZFfTe5ie1Dw48cS9jtdxEuI6HhOmf";

    public CommandExecutor(HttpClient httpClient) {
        foodDataRetriever = FoodInfoRetrieverFactory.getInstance(InformationType.DATA, httpClient, apiKey);
        foodReportRetriever = FoodInfoRetrieverFactory.getInstance(InformationType.REPORT, httpClient, apiKey);
    }

    private String[] splitCommand(String command) {
        return command.trim().split(" ");
    }

    public String executeCommand(String commandLine) {
        if (commandLine == null) {
            return "Empty command";
        }
        String[] commandParts = splitCommand(commandLine);
        String command = commandParts[INDEX_OF_COMMAND];

        switch (command.toLowerCase()) {
            case "get-food":
                return foodDataRetriever.getRequiredInformationAsString(getSearchInput(commandParts));
            case "get-food-report":
                return foodReportRetriever.getRequiredInformationAsString(commandParts[INDEX_OF_GTIN_UPC]);
            case "get-food-by-barcode":
                return getFoodByBarcode(commandParts);
            case "disconnect":
                return "Disconnected from server.";
            default:
                return "Unknown command";
        }
    }

    private String getSearchInput(String[] commandParts) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i < commandParts.length - 1; i++) {
            stringBuilder.append(commandParts[i]).append("%20");
        }
        stringBuilder.append(commandParts[commandParts.length - 1]);
        return stringBuilder.toString();
    }

    private String getFoodByBarcode(String[] commandParts) {
        String reply = BarcodeRetriever.getRequiredInformation(commandParts);
        if (reply == null) {
            return "Item not found in cache";
        }
        return reply;
    }
}
