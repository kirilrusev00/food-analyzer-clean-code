package bg.sofia.uni.fmi.mjt.food.server.commands;

import bg.sofia.uni.fmi.mjt.food.server.retriever.barcode.FoodByBarcodeRetriever;

public class GetFoodByBarcode extends Command {

    GetFoodByBarcode() {

    }

    @Override
    public String execute(String argumentsLine) {
        if (argumentsLine == null) {
            return "Usage: get-food-by-barcode --code=<gtinUpc_code>|--img=<barcode_image_file>";
        }
        String[] arguments = splitCommand(argumentsLine);

        return getFoodByBarcode(arguments);
    }

    private String getFoodByBarcode(String[] commandArguments) {
        String reply = FoodByBarcodeRetriever.getRequiredInformation(commandArguments);
        if (reply == null) {
            return "Item not found in cache";
        }
        return reply;
    }
}
