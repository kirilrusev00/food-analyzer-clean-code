package bg.sofia.uni.fmi.mjt.food.server.commands;

import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.DISCONNECTED_MESSAGE;

public class Disconnect extends Command {

    Disconnect() {

    }

    @Override
    public String execute(String argumentsLine) {
        return DISCONNECTED_MESSAGE;
    }
}
