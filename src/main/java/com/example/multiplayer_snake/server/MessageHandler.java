package com.example.multiplayer_snake.server;

import controller.DatabaseController;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

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
        JSONObject messageJSON = new JSONObject(message);
        if (messageJSON.get("PlayerLogin") != null) {
            String expectedPW = DatabaseController.Select_Player_PW((String) messageJSON.get("PlayerLogin"));
            JSONObject answerMessage = new JSONObject();
            answerMessage.put("Player", messageJSON.get("PlayerLogin"));
            answerMessage.put("Password", expectedPW);
            SnakeServer.broadcast(String.valueOf(answerMessage));
        }
    }
}