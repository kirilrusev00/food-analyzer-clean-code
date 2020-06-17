package bg.sofia.uni.fmi.mjt.food.server.commands;

import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.DISCONNECTED_MESSAGE;

public class DisconnectCommand extends Command {

    DisconnectCommand() {

    }

    @Override
    public String execute(String argumentsLine) {
        return DISCONNECTED_MESSAGE;
    }
}
