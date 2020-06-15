package bg.sofia.uni.fmi.mjt.food.server.commands;

import static bg.sofia.uni.fmi.mjt.food.server.commands.CommandFactory.foodDataRetriever;
import static bg.sofia.uni.fmi.mjt.food.server.commands.CommandFactory.foodReportRetriever;

public abstract class Command {

    Command() {

    }

    public abstract String execute(String argumentLine);

    protected String[] splitCommand(String command) {
        return command.trim().split(" ");
    }

    protected String getFoodData(String name) {
        return foodDataRetriever.getRequiredInformationAsString(name);
    }

    protected String getFoodReport(String gtinUpc) {
        return foodReportRetriever.getRequiredInformationAsString(gtinUpc);
    }
}
