package com.example.multiplayer_snake.model;

import controller.LoginController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.SubmissionPublisher;

public class InputMessageHandler implements Runnable {

    BufferedReader reader;
    SubmissionPublisher source = new SubmissionPublisher<String>(); // Observer Pattern

    public InputMessageHandler(BufferedReader reader) {
        this.reader = reader;
        source.subscribe(new LoginController());
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
        source.submit(message);
        System.out.println(">>> Received: '" + message + "'");
    }
}