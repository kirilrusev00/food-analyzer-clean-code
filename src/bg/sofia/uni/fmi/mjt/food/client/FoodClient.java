package bg.sofia.uni.fmi.mjt.food.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import static bg.sofia.uni.fmi.mjt.food.client.CommandUtilities.checkIfCorrectCommand;

public class FoodClient {
    private static final String COMMAND_PROMPT_MESSAGE = "Enter message: ";
    private static final String SENDING_TO_THE_SERVER_MESSAGE = "Sending message <%s> to the server...";
    private static final String REPLY_MESSAGE = "The server replied: %s";
    private static final String DISCONNECT_COMMAND_NAME = "disconnect";

    private String serverHost;
    private int serverPort;

    public FoodClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    private void run() {
        try (SocketChannel socketChannel = SocketChannel.open();
             BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, "UTF-8"));
             PrintWriter printWriter = new PrintWriter(Channels.newWriter(socketChannel, "UTF-8"), true);
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(serverHost, serverPort));

            while (true) {
                System.out.println(COMMAND_PROMPT_MESSAGE);
                String command = scanner.nextLine();
                if (!checkIfCorrectCommand(command)) {
                    continue;
                }

                System.out.println(String.format(SENDING_TO_THE_SERVER_MESSAGE, command));
                printWriter.println(command);
                String reply = reader.readLine();

                System.out.println(String.format(REPLY_MESSAGE, reply));
                if (DISCONNECT_COMMAND_NAME.equalsIgnoreCase(command.split(" ")[0])) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error in getting input stream from client socket");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final String SERVER_HOST = "localhost";
        final int SERVER_PORT = 1111;

        FoodClient foodClient = new FoodClient(SERVER_HOST, SERVER_PORT);
        foodClient.run();
    }
}
