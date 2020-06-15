package bg.sofia.uni.fmi.mjt.food.server.commands;

public class GetFood extends Command {

    GetFood() {

    }

    @Override
    public String execute(String argumentLine) {
        if (argumentLine == null) {
            return "Usage: get-food <food_name>";
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
