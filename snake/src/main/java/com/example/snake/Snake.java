package com.example.snake;

import controller.CenterWindowScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Snake extends Application {

	CenterWindowScreen centerWindowScreen = new CenterWindowScreen();

	/**
	 * JavaFX start method
	 *
	 * @param stage
	 * @throws IOException
	 */
	@SuppressWarnings("exports")
	@Override
	public void start(Stage stage) throws IOException {
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
			// handle exception
			//System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}