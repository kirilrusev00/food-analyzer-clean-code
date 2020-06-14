package bg.sofia.uni.fmi.mjt.food.client;

class CommandUtilities {
    private static final int MIN_WORDS_IN_COMMANDLINE = 1;

    private static String[] splitCommand(String command) {
        return command.trim().split(" ");
    }

    static boolean checkIfCorrectCommand(String command) {
        if (command.isEmpty() || splitCommand(command).length < MIN_WORDS_IN_COMMANDLINE) {
            System.out.println("Not full command");
            return false;
        }
        return true;
    }
}
