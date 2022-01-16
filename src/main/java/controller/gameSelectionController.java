package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

/**
 * Buttons: Restart game, Show Highscore, Exit Game
 */
public class gameSelectionController {
	
	@FXML
	private Button exitBTN;
	@FXML
	private Button highscoreBTN;
	@FXML
	private Button restartBTN;
	
	CenterWindowScreen centerWindowScreen = new CenterWindowScreen();
	
	@FXML
	void onExitBTNClick(ActionEvent event) {
		try {
			// Close game window
			System.exit(0);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
	}
	
	@FXML
	void onHigshoreBTNClick(ActionEvent event) {
		try {
			URL url = new File("src/main/resources/com/example/multiplayer_snake/highscoreView.fxml").toURI().toURL();
			Parent rootParent = FXMLLoader.load(url);
			Scene scene = new Scene(rootParent);
			Stage stage = new Stage();
			stage.setTitle("Highscore");
			stage.initModality(Modality.APPLICATION_MODAL); // disable minimize, maximize button
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			centerWindowScreen.CenterScreen(stage); // call method: center frame on screen
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@FXML
	void onRestartGameBTNClick(ActionEvent event) {
		// Muss noch implementiert werden!!
	}
}