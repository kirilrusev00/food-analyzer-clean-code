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
                System.out.println("Enter message: ");
                String command = scanner.nextLine();
                if (!checkIfCorrectCommand(command)) {
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
