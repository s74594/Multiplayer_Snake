package client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * InputMessageHandler planed to use with future multiplayer feature, not used yet
 */
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
        System.out.println("InputMessageHandler - Message from server received: '" + message + "'");
        JSONObject messageJSON = new JSONObject(message);
        // login answer from server
        if ((messageJSON.has("sql_login_user")) && (messageJSON.has("sql_login_user_pass"))) {
            //LoginController.setSqlLoginUser(messageJSON.getString("sql_login_user"));
            //LoginController.setSqlLoginUserPass(messageJSON.getString("sql_login_user_pass"));
        }
        // register answer from server
        if (messageJSON.has("sql_register_user_answer")) {
            //LoginController.setSqlRegisterUserAnswer(messageJSON.getString("sql_register_user_answer"));
        }
        // highscore answer from server
        if (messageJSON.has("sql_highscore_answer")) {
            System.out.println(messageJSON.getString("sql_highscore_answer"));
        }
        // highscore table answer from server
        if (messageJSON.has("sql_highscore_table_answer")) {
            //HighscoreController.setHighscoreList(messageJSON.getJSONArray("sql_highscore_table_answer"));
        }
    }

}