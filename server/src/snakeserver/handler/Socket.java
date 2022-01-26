package snakeserver.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

/**
 * server for multiplayer snake
 *
 */
public class Socket {

    static Set<PrintWriter> clientWriters = new HashSet<>();

    /**
     * starts server socket, creates reader and writer for receiving and sending data
     *
     * @param args
     */
    public static void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server gestartet");
            while (true) {
                // wait for new client connection
                java.net.Socket clientSocket = serverSocket.accept();
                System.out.println(">>> Client connected from " + clientSocket.getRemoteSocketAddress());

                // create output writer
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientWriters.add(writer);

                // create input reader
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // start concurrent message handler
                new Thread(new BroadcastMessageHandler(reader)).start();
            }
        } catch (SocketException e) {
            System.out.println("Connection lost");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * broadcasts messages to all connected clients
     *
     * @param message
     */
    static void broadcast(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
            writer.flush();
        }
        System.out.println("OUT: " + message);
    }
}