package bg.sofia.uni.fmi.mjt.food.client;

import bg.sofia.uni.fmi.mjt.food.client.thread.ClientRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class FoodClient {
    private static final int MIN_WORDS_IN_COMMANDLINE = 1;

    private String serverHost;
    private int serverPort;

    public FoodClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    private String[] splitCommand(String command) {
        return command.trim().split(" ");
    }

    private boolean checkIfFullCommand(String command) {
        if (splitCommand(command).length < FoodClient.MIN_WORDS_IN_COMMANDLINE) {
            System.out.println("Not full command");
            return false;
        }
        return true;
    }

    private void run() {
        try (SocketChannel socketChannel = SocketChannel.open();
             BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, "UTF-8"));
             PrintWriter printWriter = new PrintWriter(Channels.newWriter(socketChannel, "UTF-8"), true);
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(serverHost, serverPort));

            while (true) {
                System.out.println("Enter message: ");
                String command = scanner.nextLine();
                if (command.isEmpty() || !checkIfFullCommand(command)) {
                    continue;
                }
                System.out.println("Sending message <" + command + "> to the server...");
                printWriter.println(command);
                String reply = reader.readLine();

                System.out.println("The server replied: " + reply);
                if ("disconnect".equalsIgnoreCase(command.split(" ")[0])) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println(
                    String.format("Problem in getting input stream from client socket!%n%s", e.getMessage()));
        }
    }

    public static void main(String[] args) {
        final String SERVER_HOST = "localhost";
        final int SERVER_PORT = 1111;

        FoodClient foodClient = new FoodClient(SERVER_HOST, SERVER_PORT);
        foodClient.run();
    }
}
