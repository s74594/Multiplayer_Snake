package com.example.multiplayer_snake.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;


public class SnakeServer {

    static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            while (true) {
                // wait for new client connection
                Socket clientSocket = serverSocket.accept();
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

    static void broadcast(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
            writer.flush();
        }
    }
}