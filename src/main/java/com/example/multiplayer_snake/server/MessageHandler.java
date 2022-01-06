package com.example.multiplayer_snake.server;

import controller.DatabaseController;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

public class MessageHandler implements Runnable {

    BufferedReader reader;

    public MessageHandler(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                onMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMessage(String message) {
        System.out.println(">>> Received: '" + message + "'");
    }
}