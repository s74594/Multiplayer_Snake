package controller;

import com.example.multiplayer_snake.model.SocketClient;
import org.json.JSONObject;

import java.io.IOException;

public class NetworkController {

    public static void connect() {
        SocketClient.connect();
    }

    public static boolean login(String name, String password) {
        // send message with user and password to server
        JSONObject messageOut = new JSONObject();
        messageOut.put("sql_login_user", name);
        SocketClient.writer.println(String.valueOf(messageOut));
        SocketClient.writer.flush();
        // answer message
        String messageIn;
        try {
            while ((messageIn = SocketClient.reader.readLine()) != null) {
                JSONObject messageInJSON = new JSONObject(messageIn);
                if ((messageInJSON.has("sql_login_user")) && (messageInJSON.has("sql_login_user_pass"))) {
                    String dbName = messageInJSON.getString("sql_login_user");
                    String dbPass = messageInJSON.getString("sql_login_user_pass");
                    if (dbName.equals(name)) {
                        if(dbPass.equals(password)) {
                            System.out.println("Login credentials correct");
                            return true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean register(String name, String password) {
        // send message
        JSONObject messageOutJSON = new JSONObject();
        messageOutJSON.put("sql_register_user", name);
        messageOutJSON.put("sql_register_pass", password);
        SocketClient.writer.println(String.valueOf(messageOutJSON));
        SocketClient.writer.flush();
        // answer message
        String messageIn;
        try {
            while ((messageIn = SocketClient.reader.readLine()) != null) {
                JSONObject messageInJSON = new JSONObject(messageIn);
                if ((messageInJSON.has("sql_register_user_answer"))) {
                    if (messageInJSON.getString("sql_register_user_answer").equals("true")) {
                            System.out.println("Register successfully");
                            return true;
                        } else {
                        return false;
                    }
                }
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean setHighscore(String p, int points) {
        //send message
        JSONObject messageOutJSON = new JSONObject();
        messageOutJSON.put("sql_highscore_player", p);
        messageOutJSON.put("sql_highscore_points", points);
        SocketClient.writer.println(String.valueOf(messageOutJSON));
        SocketClient.writer.flush();
        // answer message
        String messageIn;
        try {
            while ((messageIn = SocketClient.reader.readLine()) != null) {
                JSONObject messageInJSON = new JSONObject(messageIn);
                if ((messageInJSON.has("sql_highscore_answer"))) {
                    if (messageInJSON.getString("sql_highscore_answer").equals("true")) {
                        System.out.println("Highscore successfully set");
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onMessage(String message) {
        System.out.println("InputMessageHandler - Message from server received: '" + message + "'");
        JSONObject messageJSON = new JSONObject(message);
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
