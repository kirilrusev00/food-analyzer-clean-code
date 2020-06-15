package bg.sofia.uni.fmi.mjt.food.server.commands;

public class Disconnect extends Command {

    Disconnect() {

    }

    @Override
    public String execute(String argumentsLine) {
        return "Disconnected from server.";
    }
}
