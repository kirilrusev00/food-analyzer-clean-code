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

public class FoodServer implements AutoCloseable {
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
            System.err.println("Error while creating the server");
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
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
                        System.out.println("New message from client!");
                        this.read(key);
                    } else if (key.isAcceptable()) {
                        System.out.println("New client!");
                        this.accept(key);
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            stop();
            System.err.println("Error while running the server");
            e.printStackTrace();
        }
    }

    public void stop() {
        isRunning = false;
    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            System.err.println("Error in accepting a new socket channel");
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try {
            String message = getMessageFromClient(socketChannel);
            if (message == null) {
                return;
            }

            String reply = commandExecutor.executeCommand(message);

            System.out.println("Message from client: " + message);
            System.out.println("Reply to client: " + reply);

            writeInBuffer(reply);

            sendMessageToClient(socketChannel);
        } catch (IOException e) {
            stop();
            System.err.println("Error in reading or writing to buffer");
            e.printStackTrace();
        }
    }

    private String getMessageFromClient(SocketChannel socketChannel) throws IOException {
        commandBuffer.clear();
        int r = socketChannel.read(commandBuffer);
        if (r <= 0) {
            System.out.println("Nothing to read, closing channel");
            socketChannel.close();
            return null;
        }
        commandBuffer.flip();
        return StandardCharsets.UTF_8.decode(commandBuffer).toString();
    }

    private void writeInBuffer(String reply) {
        commandBuffer.clear();
        try {
            commandBuffer.put((reply + System.lineSeparator()).getBytes());
        } catch (BufferOverflowException e) {
            System.err.println(String.format("Message is longer than buffer size [%d bytes]", BUFFER_SIZE));
            e.printStackTrace();
        }
        commandBuffer.flip();
    }

    private void sendMessageToClient(SocketChannel socketChannel) throws IOException {
        socketChannel.write(commandBuffer);
        commandBuffer.clear();
    }

    @Override
    public void close() {
        try {
            selector.close();
            serverSocketChannel.close();
        } catch (IOException e) {
            System.err.println("Error in stopping the server");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FoodServer chatServer = new FoodServer(SERVER_PORT);
        chatServer.start();
        chatServer.run();
    }
}
