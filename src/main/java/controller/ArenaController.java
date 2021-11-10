package controller;

import com.example.multiplayer_snake.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class ArenaController {

    private Player player; // Controller <-> Model connection

    @FXML
    public Text namePlayer1;

    @FXML
    public Text scorePlayer1;

    @FXML
    public Text namePlayer2;

    @FXML
    public Text scorePlayer2;

    @FXML
    public Pane playGround;

    @FXML
    public Circle snake;

    @FXML
    private MenuItem exitBTNMenu;

    @FXML
    private MenuItem gameInfoBTNMenu;

    @FXML
    void initialize() {
        player = new Player();

        namePlayer1.setText("Max");
        namePlayer2.setText("Maxi");
        scorePlayer1.setText("9014");
        scorePlayer2.setText("9541");
    }

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
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void centerWindowOnScreen(Stage stage) {
        /* Center Snake Window on Screen */
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    @FXML
    void snakeSteering(KeyEvent keyEvent) {
        final double snakeSpeed = 2; // speed adjust
        double snakeX = snake.getLayoutX();
        double snakeY = snake.getLayoutY();

        System.out.println("Key: " + keyEvent.getCode() + "  SnakeX: " + snakeX + "  SnakeY: " + snakeY);  // Debug

        switch (keyEvent.getCode()) {
            case UP -> snake.setLayoutY(snakeY - snakeSpeed);
            case DOWN -> snake.setLayoutY(snakeY + snakeSpeed);
            case LEFT -> snake.setLayoutX(snakeX - snakeSpeed);
            case RIGHT -> snake.setLayoutX(snakeX + snakeSpeed);
        }
    }
}
