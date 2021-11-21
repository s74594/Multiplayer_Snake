package controller;

import com.example.multiplayer_snake.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Random;

public class ArenaController {

    private Player model; // Controller -> Model connection

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
    public Circle snake; // snake head

    @FXML
    private MenuItem exitBTNMenu;

    @FXML
    private MenuItem gameInfoBTNMenu;

    @FXML
    public ImageView foodImage;

    @FXML
    private Label gameOver;

    CenterWindowScreen centerWindowScreen = new CenterWindowScreen();
    Random random = new Random();

    @FXML
    void initialize() {
        model = new Player();

        namePlayer1.setText("Max");
        namePlayer2.setText("Maxi");
        scorePlayer1.setText("9014");
        scorePlayer2.setText("9541");

        gameOver.setVisible(false);
        generateFood(); // initialize food
    }

    public void generateFood() {
        model.generateFood();
        foodImage.setLayoutX(model.fruitX);
        foodImage.setLayoutY(model.fruitY);
        foodImage.setVisible(true);
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
            centerWindowScreen.CenterScreen(stage); // call method: center frame on screen
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void snakeSteering(KeyEvent keyEvent) {
        Player move;
        double snakeX = snake.getLayoutX();
        double snakeY = snake.getLayoutY();
        KeyCode direction = keyEvent.getCode();

        // bounds
        model.snakeBounds = snake.getBoundsInParent();
        model.fruitBounds = foodImage.getBoundsInParent();

        // move
        model.movePlayer(snakeX, snakeY, direction);
        System.out.println(model.snakeX + "   " + model.snakeY);
        snake.setLayoutY(model.snakeY);
        snake.setLayoutX(model.snakeX);
        if (model.eatFruit == 1) {
            foodImage.setVisible(false); // set food invisible the snake hits its boundaries
            model.generateFood();
        }
        if(model.gameOver == 1) {
            gameOver.setText("Game Over!");
            gameOver.setTextFill(Color.RED);
        }
    }
}