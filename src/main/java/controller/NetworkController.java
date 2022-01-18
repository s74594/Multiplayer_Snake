package controller;

import com.example.multiplayer_snake.model.SocketClient;
import org.json.JSONObject;

import java.io.IOException;

/**
 * handles communication with server
 */
public class NetworkController {

    /**
     * starts the socket connection between client and server
     */
    public static void connect() {
        SocketClient.connect();
    }

    /**
     * requests password for given user from server and
     * compare it with user entered password
     *
     * @param name
     * @param password
     * @return if user entered password is same as in server database saved
     */
    public static boolean login(String name, String password) {
        // send message with user and password to server
        JSONObject messageOut = new JSONObject();
        messageOut.put("sql_login_user", name);
        SocketClient.writer.println(messageOut);
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

    /**
     * registers an user in server database
     *
     * @param name
     * @param password
     * @return successfully insert into database
     */
    public static boolean register(String name, String password) {
        // send message
        JSONObject messageOutJSON = new JSONObject();
        messageOutJSON.put("sql_register_user", name);
        messageOutJSON.put("sql_register_pass", password);
        SocketClient.writer.println(messageOutJSON);
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

    /**
     * sets highscore in server database
     *
     * @param p
     * @param points
     * @return successfully insert in database
     */
    public static boolean setHighscore(String p, int points) {
        //send message
        JSONObject messageOutJSON = new JSONObject();
        messageOutJSON.put("sql_highscore_player", p);
        messageOutJSON.put("sql_highscore_points", points);
        SocketClient.writer.println(messageOutJSON);
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
}
