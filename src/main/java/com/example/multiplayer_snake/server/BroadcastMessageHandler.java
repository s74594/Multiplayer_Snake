package com.example.multiplayer_snake.server;

import controller.DatabaseController;
import org.json.JSONObject;

import java.io.BufferedReader;

public class BroadcastMessageHandler extends MessageHandler {

    public BroadcastMessageHandler(BufferedReader reader) {
        super(reader);
    }

    @Override
    public void onMessage(String message) {
        super.onMessage(message);
        JSONObject messageJSON = new JSONObject(message);
        if (messageJSON.get("PlayerLogin") != null) {
            String expectedPW = DatabaseController.Select_Player_PW((String) messageJSON.get("PlayerLogin"));

        }

        // broadcast
        SnakeServer.broadcast(message);
    }
}