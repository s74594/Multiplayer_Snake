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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class ArenaController {

    private Player model; // Controller <-> Model connection

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
        System.out.println("init");
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

    void centerWindowOnScreen(Stage stage) {
        /* Center Snake Window on Screen **/
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

        if(keyEvent.getCode() == KeyCode.UP) {
            snake.setLayoutY(snakeY - snakeSpeed);
        }
        if(keyEvent.getCode() == KeyCode.DOWN) {
            snake.setLayoutY(snakeY + snakeSpeed);
        }
        if(keyEvent.getCode() == KeyCode.LEFT) {
            snake.setLayoutX(snakeX - snakeSpeed);
        }
        if(keyEvent.getCode() == KeyCode.RIGHT) {
            snake.setLayoutX(snakeX + snakeSpeed);
        }

    }

    @FXML
    public void snakeDemo(MouseEvent mouseEvent) {
        snake.setRadius(20);
    }
}
