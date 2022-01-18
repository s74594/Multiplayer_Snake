package com.example.multiplayer_snake.server;

import controller.DatabaseController;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.util.List;

/**
 * handles incoming messages and broadcasts them
 */
public class BroadcastMessageHandler extends MessageHandler {

    public BroadcastMessageHandler(BufferedReader reader) {
        super(reader);
    }

    /**
     * evaluates incoming messages and delivers the requested information as json string
     *
     * @param message
     */
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
            answerJSON.put("sql_register_user_answer", "true");
        }
        // Set highscore
        if (messageJSON.has("sql_highscore_player") == true) {
            DatabaseController.Insert_Highscore(messageJSON.getString("sql_highscore_player"), LocalDateTime.now(), messageJSON.getInt("sql_highscore_points"));
            answerJSON.put("sql_highscore_answer", "true");
        }
        // Get highscore
        if (messageJSON.has("sql_get_highscore_table") == true) {
            JSONArray highscoreTableJSON = new JSONArray();
            List<String[]> highscoreTable = DatabaseController.GetHighscore();
            highscoreTableJSON.putAll(highscoreTable);
            answerJSON.put("sql_highscore_table_answer", highscoreTableJSON);
        }
        SnakeServer.broadcast(String.valueOf(answerJSON));
    }
}