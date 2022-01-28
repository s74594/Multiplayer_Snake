package com.example.snake;

import controller.CenterWindowScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Snake extends Application {

    CenterWindowScreen centerWindowScreen = new CenterWindowScreen();

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * JavaFX start method
     *
     * @param stage loginView
     */
    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) {
        try {
            URL url = new File("snake/src/main/resources/com/example/snake/loginView.fxml").toURI().toURL();
            Parent rootParent = FXMLLoader.load(url);
            Scene scene = new Scene(rootParent);
            stage.setTitle("Snake");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            centerWindowScreen.CenterScreen(stage);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}