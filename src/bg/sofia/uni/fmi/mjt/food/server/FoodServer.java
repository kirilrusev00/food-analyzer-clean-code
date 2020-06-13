package bg.sofia.uni.fmi.mjt.food.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FoodServer {
    private static final int SERVER_PORT = 1111;
    private static final int BUFFER_SIZE = 8 * 1024;

    private Selector selector;
    private ByteBuffer commandBuffer;
    private ServerSocketChannel serverSocketChannel;
    private boolean isRunning = true;

    private CommandExecutor commandExecutor;

    public FoodServer(int port) {
        try {
            selector = Selector.open();
            commandBuffer = ByteBuffer.allocate(BUFFER_SIZE);
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            commandExecutor = new CommandExecutor(HttpClient.newBuilder().build());
        } catch (IOException e) {
            System.err.println(String.format("Error while creating the server. %n%s", e.getMessage()));
        }
    }

    private void start() {
        try {
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            start();
            while (isRunning) {
                int readyChannels = selector.select();
                if (readyChannels <= 0) {
                    System.out.println("Still waiting for a ready channel...");
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        System.out.println("New message!");
                        this.read(key);
                    } else if (key.isAcceptable()) {
                        System.out.println("New client!");
                        this.accept(key);
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            System.err.println(String.format("Error in starting the server. %n%s", e.getMessage()));
        } finally {
            stop();
        }
    }

    private void stop() {
        try {
            selector.close();
            serverSocketChannel.close();
        } catch (IOException e) {
            System.err.println(String.format("Error in stopping the server. %n%s", e.getMessage()));
        }
        isRunning = false;
    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            System.err.println(String.format("Error in accepting socket channel. %n%s", e.getMessage()));
        }
    }

    private void read(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try {
            commandBuffer.clear();
            int r = socketChannel.read(commandBuffer);
            if (r <= 0) {
                System.out.println("Nothing to read, closing channel");
                socketChannel.close();
                return;
            }
            commandBuffer.flip();
            String message = StandardCharsets.UTF_8.decode(commandBuffer).toString();

            String reply = commandExecutor.executeCommand(message);

            System.out.println("Message: " + message);
            System.out.println("Reply: " + reply);

            commandBuffer.clear();
            try {
                commandBuffer.put((reply + System.lineSeparator()).getBytes());
            } catch (BufferOverflowException e) {
                System.err.println(String.format("Message is longer than buffer size. %n%s", e.getMessage()));
            }
            commandBuffer.flip();
            socketChannel.write(commandBuffer);
            commandBuffer.clear();
        } catch (IOException e) {
            stop();
            System.err.println(String.format("Error in reading or writing to buffer. %n%s", e.getMessage()));
        }
    }

    public static void main(String[] args) {
        FoodServer chatServer = new FoodServer(SERVER_PORT);
        chatServer.run();
    }
}
