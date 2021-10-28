package com.example.multiplayer_snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Snake extends Application {
	
	@SuppressWarnings("exports")
	@Override
	public void start(Stage stage) throws IOException {
		try {
			// Open: Login Window
			FXMLLoader fxmlLoader = new FXMLLoader(Snake.class.getResource("loginFrame.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 400);
			stage.setTitle("Snake");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch();
	}
}