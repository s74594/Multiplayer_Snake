package connector;

import client.SocketClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * handles communication with server
 */
public class NetworkFacade {

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
        messageOutJSON.put("sql_set_highscore_player", p);
        messageOutJSON.put("sql_set_highscore_points", points);
        SocketClient.writer.println(messageOutJSON);
        SocketClient.writer.flush();
        // answer message
        String messageIn;
        try {
            while ((messageIn = SocketClient.reader.readLine()) != null) {
                JSONObject messageInJSON = new JSONObject(messageIn);
                if ((messageInJSON.has("sql_set_highscore_answer"))) {
                    if (messageInJSON.getString("sql_set_highscore_answer").equals("true")) {
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

    /**
     * retrives highscore table data from server database
     *
     * @return the highscore table
     */
    public static List<String[]> getHighscoreTable() {
        LinkedList<String[]> highscoreTable = new LinkedList<>();
        //send message
        JSONObject messageOutJSON = new JSONObject();
        messageOutJSON.put("sql_get_highscore_table", "true");
        SocketClient.writer.println(messageOutJSON);
        SocketClient.writer.flush();
        // answer message
        String messageIn;
        try {
            while ((messageIn = SocketClient.reader.readLine()) != null) {
                JSONObject messageInJSON = new JSONObject(messageIn);
                if ((messageInJSON.has("sql_highscore_table_answer"))) {
                    Type listType = new TypeToken<LinkedList<String[]>>() {}.getType();
                    highscoreTable = new Gson().fromJson(messageInJSON.getString("sql_highscore_table_answer"), listType);
                    return highscoreTable;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highscoreTable;
    }

    /**
     * sets game data in server database
     *
     * @param player1id
     * @param player2id
     * @param player1_points
     * @param player2_points
     * @param startTime
     * @param datenow
     * @param duration
     */
    public static boolean setGamedata(String player1id, String player2id, int player1_points, int player2_points, String startTime, String datenow, int duration) {
        //send message
        JSONObject messageOutJSON = new JSONObject();
        messageOutJSON.put("sql_set_gamedata", "true");
        messageOutJSON.put("sql_set_gamedata_player1id", player1id);
        messageOutJSON.put("sql_set_gamedata_player2id", player2id);
        messageOutJSON.put("sql_set_gamedata_player1_points", player1_points);
        messageOutJSON.put("sql_set_gamedata_player2_points", player2_points);
        messageOutJSON.put("sql_set_gamedata_startTime", startTime);
        messageOutJSON.put("sql_set_gamedata_datenow", datenow);
        messageOutJSON.put("sql_set_gamedata_duration", duration);
        SocketClient.writer.println(messageOutJSON);
        SocketClient.writer.flush();
        // answer message
        String messageIn;
        try {
            while ((messageIn = SocketClient.reader.readLine()) != null) {
                JSONObject messageInJSON = new JSONObject(messageIn);
                if ((messageInJSON.has("sql_set_gamedata_answer"))) {
                    if (messageInJSON.getString("sql_set_gamedata_answer").equals("true")) {
                        System.out.println("Game data successfully set");
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

    public static String getPlayerId(String playerName) {
        //send message
        JSONObject messageOutJSON = new JSONObject();
        messageOutJSON.put("sql_get_playerid", playerName);
        SocketClient.writer.println(messageOutJSON);
        SocketClient.writer.flush();
        // answer message
        String messageIn;
        try {
            while ((messageIn = SocketClient.reader.readLine()) != null) {
                JSONObject messageInJSON = new JSONObject(messageIn);
                if ((messageInJSON.has("sql_get_playerid_answer"))) {
                    return messageInJSON.getString("sql_get_playerid_answer");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error getPlayerID";
    }


    public void multiplayerSnakeStatus(String name, String points, String eatFruit, String gameOver, String snakeX, String snakeY) {
        JSONObject snakeStatus = new JSONObject();
        try {
            snakeStatus.put("Player", name);
            snakeStatus.put("Points", points);
            snakeStatus.put("Eatfruit", eatFruit);
            snakeStatus.put("Gameover", gameOver);
            snakeStatus.put("SnakeX", snakeX);
            snakeStatus.put("SnakeY", snakeY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SocketClient.writer.println(snakeStatus);
        SocketClient.writer.flush();
    }
}
