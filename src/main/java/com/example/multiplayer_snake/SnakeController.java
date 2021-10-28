package com.example.multiplayer_snake;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SnakeController {
	
	@FXML
    private Button playButton;
	@FXML
    private Button exitButton;

	@FXML
	protected void onPlayButtonClick(ActionEvent event) {
		try {
			// Play Button: Open a new game window
			FXMLLoader fxmlLoader = new FXMLLoader(Snake.class.getResource("game.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 400);
			Stage stage = new Stage();
			stage.setTitle("Snake");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
	}
	
	@FXML
    protected void onExitButtonClick(ActionEvent event) {
		try {
			// Exit Button: Close login window
			Stage stage = (Stage) exitButton.getScene().getWindow();
		    stage.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
    }
}