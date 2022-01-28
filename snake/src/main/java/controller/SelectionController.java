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
import java.io.IOException;
import java.net.URL;

/**
 * controls gameover screen
 */
public class SelectionController {

    @FXML
    private Button exitBTN;
    @FXML
    private Button highscoreBTN;
    @FXML
    private Button restartBTN;

    CenterWindowScreen centerWindowScreen = new CenterWindowScreen();

    /**
     * if exit button clicked method will be executed
     *
     * @param event
     */
    @FXML
    void onExitBTNClick(ActionEvent event) {
        try {
            System.exit(0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * if highscore button clicked method will be executed
     *
     * @param event click on highscore button
     */
    @FXML
    void onHighscoreBTNClick(ActionEvent event) {
        try {
            URL url = new File("snake/src/main/resources/com/example/snake/highscoreView.fxml").toURI().toURL();
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

    /**
     * if restart button clicked method will be executed
     *
     * @param event click on restart button
     */
    @FXML
    void onRestartGameBTNClick(ActionEvent event) {
        try {
            URL url = new File("snake/src/main/resources/com/example/snake/loginView.fxml").toURI().toURL();
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
}