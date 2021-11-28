package com.example.multiplayer_snake.model;

import java.io.BufferedReader;
import java.io.IOException;

public class InputMessageHandler implements Runnable {

    BufferedReader reader;

    public InputMessageHandler(BufferedReader reader) {
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