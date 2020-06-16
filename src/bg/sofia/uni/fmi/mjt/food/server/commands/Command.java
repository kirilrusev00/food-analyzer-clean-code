package bg.sofia.uni.fmi.mjt.food.server.commands;

import static bg.sofia.uni.fmi.mjt.food.server.commands.CommandFactory.foodDataRetriever;
import static bg.sofia.uni.fmi.mjt.food.server.commands.CommandFactory.foodReportRetriever;

public abstract class Command {

    Command() {

    }

    public abstract String execute(String argumentLine);

    String[] splitCommand(String command) {
        return command.trim().split(" ");
    }

    String getFoodData(String name) {
        return foodDataRetriever.getRequiredInformationAsString(name);
    }

    String getFoodReport(String gtinUpc) {
        return foodReportRetriever.getRequiredInformationAsString(gtinUpc);
    }
}
