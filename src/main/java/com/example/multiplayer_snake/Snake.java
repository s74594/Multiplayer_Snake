package com.example.multiplayer_snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

public class Snake extends Application {
	
	@SuppressWarnings("exports")
	@Override
	public void start(Stage stage) throws IOException {
		try {
			// Open: Login Window
			Parent rootParent = FXMLLoader.load(getClass().getResource("loginFrame.fxml"));
			Scene scene = new Scene(rootParent);
			stage.setTitle("Snake");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			
			/** Center Snake Window on Screen **/
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
	        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}