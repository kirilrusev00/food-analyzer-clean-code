package bg.sofia.uni.fmi.mjt.food.server.commands;

import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.GET_FOOD_USAGE;

public class GetFoodCommand extends Command {

    GetFoodCommand() {

    }

    @Override
    public String execute(String argumentLine) {
        if (argumentLine == null) {
            return GET_FOOD_USAGE;
        }
        String[] arguments = splitCommand(argumentLine);

        return getFoodData(getSearchInput(arguments));
    }

    private String getSearchInput(String[] commandArguments) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < commandArguments.length - 1; i++) {
            stringBuilder.append(commandArguments[i]).append("%20");
        }
        stringBuilder.append(commandArguments[commandArguments.length - 1]);
        return stringBuilder.toString();
    }
}
