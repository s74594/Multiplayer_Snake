package snakeserver.handler;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * handles incoming messages
 */
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
            System.out.println(e.getMessage() + " - Client hat Verbindung beendet");
        }
    }

    public void onMessage(String message) {
        System.out.println("IN: " + message);
    }
}