package com.example.multiplayer_snake.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {

    static Socket socket;
    static PrintWriter writer;

    public static void main(String[] args) {
        try {
            // connect to server
            socket = new Socket("127.0.0.1", 5000);
            System.out.println(">>> Client connected using port " + socket.getLocalPort());

            // create output writer
            writer = new PrintWriter(socket.getOutputStream());

            // create input reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // start concurrent message handler
            new Thread(new MessageHandler(reader)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Wait for messages to send to the server
        while (true) {
            System.out.println(">>> Write your message:");
            Scanner scanner = new Scanner(System.in);
            writer.println(scanner.next());
            writer.flush();
        }
    }
}