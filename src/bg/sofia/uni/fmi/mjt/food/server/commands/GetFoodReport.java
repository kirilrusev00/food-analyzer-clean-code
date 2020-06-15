package bg.sofia.uni.fmi.mjt.food.server.commands;

public class GetFoodReport extends Command {
    private static final int INDEX_OF_ARGUMENT_GTIN_UPC = 0;

    GetFoodReport() {

    }

    @Override
    public String execute(String argumentLine) {
        if (argumentLine == null) {
            return "Usage: get-food-report <food_fdcId>";
        }
        String[] arguments = splitCommand(argumentLine);

        return getFoodReport(arguments[INDEX_OF_ARGUMENT_GTIN_UPC]);
    }
}
