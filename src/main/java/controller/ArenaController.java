package controller;

import com.example.multiplayer_snake.model.Player;
import com.example.multiplayer_snake.model.SocketClient;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.SubmissionPublisher;

public class ArenaController {

    private Player model; // Controller -> Model connection
    @SuppressWarnings("exports")
    @FXML
    public Text namePlayer1;
    @SuppressWarnings("exports")
    @FXML
    public Text scorePlayer1;
    @SuppressWarnings("exports")
    @FXML
    public Text namePlayer2;
    @SuppressWarnings("exports")
    @FXML
    public Text scorePlayer2;
    @SuppressWarnings("exports")
    @FXML
    public Pane playGround;
    @SuppressWarnings("exports")
    @FXML
    public Circle snakeHead; // snake head
    @SuppressWarnings("exports")
    @FXML
    public Circle snakeBody; // snake body
    @SuppressWarnings("exports")
    @FXML
    public Circle snakeTailCircle; // snake tail
    @FXML
    private MenuItem exitBTNMenu;
    @FXML
    private MenuItem HighscoreBTN;
    @FXML
    private MenuItem gameInfoBTNMenu;
    @SuppressWarnings("exports")
    @FXML
    public ImageView foodImage;
    @FXML
    private Label gameOver;
    private boolean isApplicationRunning = false;
    private Timeline animation = new Timeline();
    public static double millis = 0.3;
    public int point_counter_player1 = 0;

    CenterWindowScreen centerWindowScreen = new CenterWindowScreen();
    @SuppressWarnings("rawtypes")
    SubmissionPublisher source = new SubmissionPublisher<String>(); // Observer Pattern

    KeyEvent animationDirection = null;
    final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (animationDirection != null) {
                snakeSteering(animationDirection);
            }
        }
    };


    @SuppressWarnings("unchecked")
    @FXML
    void initialize() {
        model = new Player();
        timer.start();

        // Observer Pattern
        source.subscribe(new SocketClient());

        namePlayer1.setText("Max");
        namePlayer2.setText("Maxi");
        scorePlayer1.setText("9014");
        scorePlayer2.setText(String.valueOf(point_counter_player1));

        gameOver.setVisible(false);
        generateFood(); // initialize food

        // Read file and set the color of the snake
        try {
            File myObj = new File("color.txt");
            Scanner reader = new Scanner(myObj);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
                snakeHead.setFill(Color.web(data));
            }
            reader.close();
            delete();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /* Löschen des Files, wenn die Farbe der Snake gesetzt wurde */
    public void delete() {
        File myObj = new File("color.txt");
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    // Customize the color of a snake
    @SuppressWarnings("exports")
    public void custom_Snake_Color(Color value) {

        // Format a color in a web-friendly hex format
        String webFormat = String.format("#%02x%02x%02x",
                (int) (255 * value.getRed()),
                (int) (255 * value.getGreen()),
                (int) (255 * value.getBlue()));

        // Wert muss an initialize übergeben werden..
        // Zurzeit Auslagerung in einem File
        // Hexadezimalwert der Variable webFormat schreiben
        try {
            FileWriter writer = new FileWriter("color.txt");
            writer.write(webFormat);
            writer.close();
            System.out.println("Successfully wrote \"" + webFormat + "\" to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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
    void onHighscoreBTNClick(ActionEvent event) {
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

    private void startGame() {
        animation.play();
        isApplicationRunning = true;
    }

    private void stopGame() {
        animation.stop();
        isApplicationRunning = false;
        gameSelection();
    }

    private void gameSelection() {
        try {
            URL url = new File("src/main/resources/com/example/multiplayer_snake/gameSelectionView.fxml").toURI()
                    .toURL();
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

    @SuppressWarnings("unchecked")
    private void multiplayerSnakeStatus() {
        // Observer Pattern
        JSONObject snakeStatus = new JSONObject();
        try {
            snakeStatus.put("Player", String.valueOf(model.name));
            snakeStatus.put("Points", String.valueOf(model.points));
            snakeStatus.put("Eatfruit", String.valueOf(model.eatFruit));
            snakeStatus.put("Gameover", String.valueOf(model.gameOver));
            snakeStatus.put("SnakeX", String.valueOf(model.snakeX));
            snakeStatus.put("SnakeY", String.valueOf(model.snakeY));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        source.submit(String.valueOf(snakeStatus));
    }

    @FXML
    void snakeSteering(KeyEvent keyEvent) {

        double snakeHeadX = snakeHead.getLayoutX();
        double snakeHeadY = snakeHead.getLayoutY();
        KeyCode direction = keyEvent.getCode();
        animationDirection = keyEvent;
        //System.out.println(direction.toString());

        // bounds
        model.snakeBounds = snakeHead.getBoundsInParent();
        model.fruitBounds = foodImage.getBoundsInParent();

        // move
        model.movePlayer(snakeHeadX, snakeHeadY, direction);
        snakeHead.setLayoutX(model.snakeX);
        snakeHead.setLayoutY(model.snakeY);

        // data to server
        multiplayerSnakeStatus();

        if (model.eatFruit == true) {
            foodImage.setVisible(false); // set food invisible the snake hits its boundaries
            model.generateFood();
            foodImage.setLayoutX(model.fruitX);
            foodImage.setLayoutY(model.fruitY);
            foodImage.setVisible(true);
            model.eatFruit = false;
            point_counter_player1++;
            scorePlayer2.setText(String.valueOf(point_counter_player1));
        }

        if (model.gameOver == true) {
            gameOver.setVisible(true);
            gameOver.setText("Game Over!");
            gameOver.setTextFill(Color.RED);
            DatabaseController.Insert_Highscore("7", LocalDateTime.now(), point_counter_player1);
            timer.stop();
            gameSelection();
        }
    }
}