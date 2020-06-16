package bg.sofia.uni.fmi.mjt.food.server.commands;

import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.GET_FOOD_REPORT_USAGE;

public class GetFoodReport extends Command {
    private static final int INDEX_OF_ARGUMENT_GTIN_UPC = 0;

    GetFoodReport() {

    }

    @Override
    public String execute(String argumentLine) {
        if (argumentLine == null) {
            return GET_FOOD_REPORT_USAGE;
        }
        String[] arguments = splitCommand(argumentLine);

        return getFoodReport(arguments[INDEX_OF_ARGUMENT_GTIN_UPC]);
    }
}
