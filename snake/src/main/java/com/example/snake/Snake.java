package com.example.snake;

import connector.NetworkFacade;
import controller.WindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Snake extends Application {

    WindowController centerWindowScreen = new WindowController();

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
            NetworkFacade.connect();
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