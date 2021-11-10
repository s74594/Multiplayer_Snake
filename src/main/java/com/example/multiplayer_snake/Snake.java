package com.example.multiplayer_snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Snake extends Application {

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
        try {
            URL url = new File("src/main/resources/com/example/multiplayer_snake/loginView.fxml").toURI().toURL();
            Parent rootParent = FXMLLoader.load(url);
            Scene scene = new Scene(rootParent);
            stage.setTitle("Snake");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            centerWindowOnScreen(stage);
        } catch (Exception e) {
            // handle exception
            System.err.println(e.getMessage());
        }
    }

    void centerWindowOnScreen(Stage stage) {
        /* Center Snake Window on Screen */
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}