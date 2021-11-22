package com.example.multiplayer_snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import controller.CenterWindowScreen;

public class Game extends Application {

	CenterWindowScreen centerWindowScreen = new CenterWindowScreen();

	@SuppressWarnings("exports")
	@Override
	public void start(Stage stage) throws IOException {
		try {
			URL url = new File("src/main/resources/com/example/multiplayer_snake/arenaView.fxml").toURI().toURL();
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

	public static void main(String[] args) {
		Application.launch(args);
	}
}