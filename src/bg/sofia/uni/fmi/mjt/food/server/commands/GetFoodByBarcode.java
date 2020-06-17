package bg.sofia.uni.fmi.mjt.food.server.commands;

import bg.sofia.uni.fmi.mjt.food.server.retriever.barcode.FoodByBarcodeRetriever;

import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.GET_FOOD_BY_BARCODE_USAGE;
import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.NOT_FOUND_IN_CACHE_MESSAGE;

public class GetFoodByBarcode extends Command {

    GetFoodByBarcode() {

    }

    @Override
    public String execute(String argumentsLine) {
        if (argumentsLine == null) {
            return GET_FOOD_BY_BARCODE_USAGE;
        }
        String[] arguments = splitCommand(argumentsLine);

        return getFoodByBarcode(argumentsLine);
    }

    private String getFoodByBarcode(String argumentsLine) {
        String reply = FoodByBarcodeRetriever.getRequiredInformation(argumentsLine);
        if (reply == null) {
            return NOT_FOUND_IN_CACHE_MESSAGE;
        }
        return reply;
    }
}
