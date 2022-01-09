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
        // User login
        if (messageJSON.has("sql_login_user") == true) {
            answerJSON.put("sql_login_user", messageJSON.get("sql_login_user"));
            answerJSON.put("sql_login_user_pass", DatabaseController.Select_Player_PW(messageJSON.getString("sql_login_user")));
        }
        // Register new user
        if (messageJSON.has("sql_register_user") == true) {
            DatabaseController.Insert_Player(messageJSON.getString("sql_register_user"), messageJSON.getString("sql_register_pass"), messageJSON.getString("sql_register_pass"));
            answerJSON.put("sql_register_user_answer", "User successfully added to database");
        }
        SnakeServer.broadcast(String.valueOf(answerJSON));
    }
}