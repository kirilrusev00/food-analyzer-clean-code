package bg.sofia.uni.fmi.mjt.food.client;

class CommandUtilities {
    private static final int MIN_WORDS_IN_COMMANDLINE = 1;
    private static final String NOT_FULL_COMMAND_MESSAGE = "Not full command";

    static boolean checkIfCorrectCommand(String command) {
        if (command.isEmpty() || splitCommand(command).length < MIN_WORDS_IN_COMMANDLINE) {
            System.out.println(NOT_FULL_COMMAND_MESSAGE);
            return false;
        }
        return true;
    }

    private static String[] splitCommand(String command) {
        return command.trim().split(" ");
    }
}
