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
        JSONObject answerJSON = new JSONObject();
        if (messageJSON.has("sql_login_user") == true) {
            answerJSON.put("sql_login_user", messageJSON.get("sql_login_user"));
            answerJSON.put("sql_login_user_pass", DatabaseController.Select_Player_PW((String) messageJSON.get("sql_login_user")));
        }
        SnakeServer.broadcast(String.valueOf(answerJSON));
    }
}