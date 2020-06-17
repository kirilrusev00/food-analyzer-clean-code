package bg.sofia.uni.fmi.mjt.food.server.commands;

import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetriever;
import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetrieverFactory;
import bg.sofia.uni.fmi.mjt.food.server.retriever.InformationType;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.*;

public class CommandFactory {
    private static final String apiKey = "LMm1mjww0SJZFfTe5ie1Dw48cS9jtdxEuI6HhOmf";
    private static final int INDEX_OF_COMMAND_NAME = 0;
    private static final int INDEX_OF_ARGUMENTS_LINE = 1;

    private Map<String, Command> commands;
    static FoodInfoRetriever foodDataRetriever;
    static FoodInfoRetriever foodReportRetriever;

    public CommandFactory(HttpClient httpClient) {
        this.commands = new HashMap<>();
        setCommands();
        foodDataRetriever =
                FoodInfoRetrieverFactory.getInstance(InformationType.DATA, httpClient, apiKey);
        foodReportRetriever =
                FoodInfoRetrieverFactory.getInstance(InformationType.REPORT, httpClient, apiKey);
    }

    public String processLine(String line) {
        String[] splittedLine = splitCommand(line);

        Command command = getCommand(splittedLine[INDEX_OF_COMMAND_NAME]);
        if (command == null) {
            return UNKNOWN_COMMAND_MESSAGE;
        }

        String argumentsLine;
        if (splittedLine.length == INDEX_OF_ARGUMENTS_LINE) {
            argumentsLine = null;
        } else {
            argumentsLine = splittedLine[INDEX_OF_ARGUMENTS_LINE];
        }

        return command.execute(argumentsLine);
    }

    private void setCommands() {
        commands.put(DISCONNECT_COMMAND_NAME, new Disconnect());
        commands.put(GET_FOOD_COMMAND_NAME, new GetFood());
        commands.put(GET_FOOD_REPORT_COMMAND_NAME, new GetFoodReport());
        commands.put(GET_FOOD_BY_BARCODE_COMMAND_NAME, new GetFoodByBarcode());
    }

    private Command getCommand(String line) {
        return commands.get(line);
    }

    private String[] splitCommand(String command) {
        return command.trim().split(" ", 2);
    }
}
