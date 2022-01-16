package com.example.multiplayer_snake.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {

    public static Socket socket;
    public static PrintWriter writer;
    public static BufferedReader reader;

    public static void connect() {
        try {
            // connect to server
            socket = new Socket("127.0.0.1", 5000);
            System.out.println(">>> Client connected using port " + socket.getLocalPort());

            // create output writer
            writer = new PrintWriter(socket.getOutputStream());

            // create input reader
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // start concurrent input message handler
            // new Thread(new InputMessageHandler(reader)).start();

        } catch (IOException e) {
            System.out.println(e.getMessage() + " - Kann keine Verbindung zu Server herstellen, bitte Server überprüfen!!!");
        }
    }
}