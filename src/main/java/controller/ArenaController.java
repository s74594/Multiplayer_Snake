package controller;

import java.io.File;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ArenaController {

    @FXML
    private MenuItem exitBTNMenu;

    @FXML
    private MenuItem gameInfoBTNMenu;

    @FXML
	void onExitMenuClick(ActionEvent event) {
		try {
			// <Menubar: Game -> Exit Game>: Close game window
			System.exit(0);
		} catch (Exception e) {
			// handle error exception
			System.err.println(e.getMessage());
		}
	}

    @FXML
	void onGameInfoMenuClick(ActionEvent event) {
		try {
			// <Menubar: Help -> Game Info>: Open a new game window
//			Parent rootParent = FXMLLoader.load(getClass().getResource("frameGameInfo.fxml"));
			URL url = new File("src/main/resources/com/example/multiplayer_snake/frameGameInfo.fxml").toURI().toURL();
			Parent rootParent = FXMLLoader.load(url);

			Scene scene = new Scene(rootParent);
			Stage stage = new Stage();
			stage.setTitle("Snake - Gameplay Info");
			stage.initModality(Modality.APPLICATION_MODAL); // disable minimize, maximize button
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

			centerWindowOnScreen(stage); // call method: center frame on screen
		} catch (Exception e) {
			// handle error exception
			System.err.println(e.getMessage());
		}
	}
    
    void centerWindowOnScreen(Stage stage) {
		/** Center Snake Window on Screen **/
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
	}
}