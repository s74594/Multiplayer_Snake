package controller;

import com.example.snake.model.Player;
import connector.NetworkFacade;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

/**
 * controls the main game window
 */
public class ArenaController {

    private Player model; // Controller -> Model connection
    private Player modelPlayerTwo; // Controller -> Model connection
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
    public Pane playGround2;
    @FXML
    public Circle snakeHead; // snake head player one
    @FXML
    public Circle snakeTailCircle; // snake tail player one
    @FXML
    private Circle snakeHeadPlayerTwo; // snake head player two
    @FXML
    private ImageView avatarPlayerOne; // avatar img player one
    @FXML
    private ImageView avatarPlayerTwo; // avatar img player two
    @FXML
    private MenuItem exitBTNMenu;
    @FXML
    private MenuItem HighscoreBTN;
    @FXML
    private MenuItem gameInfoBTNMenu;
    @FXML
    public ImageView foodImage; // raspberry
    @FXML
    public ImageView foodImage1; // pear
    @FXML
    public String player1_id;
    @FXML
    public String player2_id;
    @FXML
    public LocalDateTime game_start;
    @FXML
    public LocalDateTime gameEnd;
    @FXML
    public int gameDuration;
    Date startDate;
    Date endDate;
    public static double millis = 0.3;
    public int point_counter_player1 = 0;
    public int point_counter_player2 = 0;
    @FXML
    public Circle[] snakeBody = new Circle[1000]; // snake body player one
    @FXML
    public Circle[] bodyPlayerTwoCircles = new Circle[1000]; // snake body player two
    CenterWindowScreen centerWindowScreen = new CenterWindowScreen();

    // Animation timer for player one
    KeyEvent animationDirection = null;
    final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (animationDirection != null) {
                snakeSteering(animationDirection);
            }
        }
    };
    // Animation timer for player two
    KeyEvent animationDirectionPlayerTwo = null;
    final AnimationTimer timerPlayerTwo = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (animationDirectionPlayerTwo != null) {
                snakeSteering(animationDirectionPlayerTwo);
            }
        }
    };

    /**
     * initializes main game window...snake, food, sets playernames, scorefields...
     */
    @FXML
    void initialize() {

        model = new Player();
        model.snakeBodyLocationsX.addFirst(250.); // store initial position in bodyparts array
        model.snakeBodyLocationsY.addFirst(200.); // store initial position in bodyparts array

        /* Player Two */
        modelPlayerTwo = new Player();
        modelPlayerTwo.snakeBodyLocationsXP2.addFirst(950.); // store initial position in bodyparts array
        modelPlayerTwo.snakeBodyLocationsYP2.addFirst(450.); // store initial position in bodyparts array

        timer.start(); // Animation Timer
        timerPlayerTwo.start(); // Start animation timer for player tw
        namePlayer2.setText("Max");
        scorePlayer1.setText(String.valueOf(point_counter_player1));
        scorePlayer2.setText(String.valueOf(point_counter_player2));
        game_start = LocalDateTime.now();
        startDate = new Date();
        player2_id = "3";

		/* Threading - Nebenläufigkeit */
        /**
         * Generierte Frucht wird nach einer bestimmten Zeit an einem neuen Punkt erneut
         * generiert.
         */
        Thread thread = new Thread() {
            public void run() {
                System.out.println("Thread Running");
                try {
                    generateFood();
                    generateFoodPlayerTwo();
                    Thread.sleep(10L * 1000L);
                    foodImage.setVisible(false);
                    foodImage1.setVisible(false);
                    generateFoodPlayerTwo();
                    generateFood();
                    run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

        // Initialize Snakebody
        for (int i = 0; i < snakeBody.length; i++) {
            snakeBody[i] = new Circle(10);
            bodyPlayerTwoCircles[i] = new Circle(10);
            snakeBody[i].setFill(Color.BLACK);
            bodyPlayerTwoCircles[i].setFill(Color.BLACK);
            snakeBody[i].setVisible(false);
            bodyPlayerTwoCircles[i].setVisible(false);
            playGround2.getChildren().addAll(snakeBody[i], bodyPlayerTwoCircles[i]);
        }

        // Read file and set the color of the snake
        try {
            File myObj = new File("color.txt");
            Scanner reader = new Scanner(myObj);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                snakeHead.setFill(Color.web(data)); // Color Player One
                snakeHeadPlayerTwo.setFill(Color.web(data)); // Color Player Two

                for (int i = 0; i < snakeBody.length; i++) {
                    snakeBody[i].setFill(Color.web(data));
                    bodyPlayerTwoCircles[i].setFill(Color.web(data));
                }
            }
            reader.close();
            delete();
        } catch (FileNotFoundException e) {
            System.out.println("color.txt not found");
        }
    }

    /**
     * deletes file, if snake color is set
     */
    public void delete() {
        File myObj = new File("color.txt");
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    /**
     * customizes the color of a snake
     *
     * @param value
     */
    public void custom_Snake_Color(Color value) {

        // Format a color in a web-friendly hex format
        String webFormat = String.format("#%02x%02x%02x", (int) (255 * value.getRed()), (int) (255 * value.getGreen()),
                (int) (255 * value.getBlue()));
        // Wert des Color pickers wird als hexformat in color.txt geschrieben und in
        // initialize() ausgelesen.
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

    /**
     * generates food for player1
     */

    public void generateFood() {
        model.generateFood();
        foodImage.setLayoutX(model.fruitX);
        foodImage.setLayoutY(model.fruitY);
        foodImage.setVisible(true);
    }

    /**
     * generates food for player2
     */
    public void generateFoodPlayerTwo() {
        modelPlayerTwo.generateFood();
        foodImage1.setLayoutX(modelPlayerTwo.fruitX);
        foodImage1.setLayoutY(modelPlayerTwo.fruitY);
        foodImage1.setVisible(true);
    }

    /**
     * if exit button clicked method will be executed
     *
     * @param event
     */
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

    /**
     * if game info button clicked method will be executed
     *
     * @param event
     */
    @FXML
    void onGameInfoMenuClick(ActionEvent event) {
        try {
            URL url = new File("snake/src/main/resources/com/example/snake/frameGameInfo.fxml").toURI().toURL();
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

    /**
     * if highscore button clicked method will be executed
     *
     * @param event
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
     * starts the game selection view (normally after game over)
     */
    private void gameSelection() {
        try {
            URL url = new File("snake/src/main/resources/com/example/snake/gameSelectionView.fxml").toURI().toURL();
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
     * keyboard control of snakes
     *
     * @param keyEvent
     */
    @FXML
    void snakeSteering(KeyEvent keyEvent) {
        KeyCode key = keyEvent.getCode();
        // move
        // key handling player one or two
        if ((key == KeyCode.UP) || (key == KeyCode.RIGHT) || (key == KeyCode.DOWN) || (key == KeyCode.LEFT)) {
            double snakeHeadX = model.snakeBodyLocationsX.getFirst();
            double snakeHeadY = model.snakeBodyLocationsY.getFirst();

            KeyCode direction = keyEvent.getCode();
            animationDirection = keyEvent;

            // bounds
            model.snakeBounds = snakeHead.getBoundsInParent();
            model.fruitBounds = foodImage.getBoundsInParent();

            // move
            model.movePlayer(snakeHeadX, snakeHeadY, direction);

            // draw snake
            snakeHead.setLayoutX(model.snakeBodyLocationsX.getFirst());
            snakeHead.setLayoutY(model.snakeBodyLocationsY.getFirst());

            for (int i = 0; i <= model.snakeBodySize; i++) {
                snakeBody[i].setLayoutX(model.snakeBodyLocationsX.get(i));
                snakeBody[i].setLayoutY(model.snakeBodyLocationsY.get(i));
                snakeBody[i].setVisible(true);
            }

            // data to server
            // multiplayerSnakeStatus();

            if (model.eatFruit == true) {
                foodImage.setVisible(false); // set food invisible if the snake hits its boundaries
                model.generateFood();
                foodImage.setLayoutX(model.fruitX);
                foodImage.setLayoutY(model.fruitY);
                foodImage.setVisible(true);
                model.eatFruit = false;
                point_counter_player1++;
                scorePlayer1.setText(String.valueOf(point_counter_player1));
            }

            if (model.gameOver == true) {
                NetworkFacade.setHighscore(namePlayer1.getText(), point_counter_player1); // set highscore on server database
                gameEnd = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                String formatDateTime = gameEnd.format(formatter);
                String formatDateTimeStart = game_start.format(formatter);
                endDate = new Date();
                gameDuration = (int) ((endDate.getTime() - startDate.getTime()) / 1000);
                player1_id = NetworkFacade.getPlayerId(namePlayer1.getText());  // network
                NetworkFacade.setGamedata(player1_id, player2_id, point_counter_player1, point_counter_player2, formatDateTimeStart, formatDateTime, gameDuration);  // network
                timer.stop();
                timerPlayerTwo.stop();
                gameSelection();
            }
        } else if ((key == KeyCode.W) || (key == KeyCode.D) || (key == KeyCode.S) || (key == KeyCode.A)) {
            double snakeHeadXP2 = modelPlayerTwo.snakeBodyLocationsXP2.getFirst(); // Player Two
            double snakeHeadYP2 = modelPlayerTwo.snakeBodyLocationsYP2.getFirst(); // Player Two

            KeyCode directionPlayerTwo = keyEvent.getCode();
            animationDirectionPlayerTwo = keyEvent;

            // bounds
            modelPlayerTwo.snakeBoundsPlayerTwo = snakeHeadPlayerTwo.getBoundsInParent(); // Player Two
            modelPlayerTwo.fruitBounds = foodImage1.getBoundsInParent(); // Player Two

            // move
            modelPlayerTwo.movePlayerTwo(snakeHeadXP2, snakeHeadYP2, directionPlayerTwo); // Player Two

            // draw snake
            snakeHeadPlayerTwo.setLayoutX(modelPlayerTwo.snakeBodyLocationsXP2.getFirst()); // Player Two
            snakeHeadPlayerTwo.setLayoutY(modelPlayerTwo.snakeBodyLocationsYP2.getFirst()); // Player Two

            /** Player Two **/
            for (int i = 0; i <= modelPlayerTwo.snakeBodySizePlayerTwo; i++) {
                bodyPlayerTwoCircles[i].setLayoutX(modelPlayerTwo.snakeBodyLocationsXP2.get(i));
                bodyPlayerTwoCircles[i].setLayoutY(modelPlayerTwo.snakeBodyLocationsYP2.get(i));
                bodyPlayerTwoCircles[i].setVisible(true);
            }

            // data to server
            // multiplayerSnakeStatus();

            if (modelPlayerTwo.eatFruit == true) {
                foodImage1.setVisible(false); // set food invisible if the snake hits its boundaries
                modelPlayerTwo.generateFood();
                foodImage1.setLayoutX(modelPlayerTwo.fruitX);
                foodImage1.setLayoutY(modelPlayerTwo.fruitY);
                foodImage1.setVisible(true);
                modelPlayerTwo.eatFruit = false;
                point_counter_player2++;
                scorePlayer2.setText(String.valueOf(point_counter_player2));
            }

            if (modelPlayerTwo.gameOverPlayerTwo == true) {
                NetworkFacade.setHighscore(namePlayer2.getText(), point_counter_player2); // set highscore on server database
                gameEnd = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                String formatDateTime = gameEnd.format(formatter);
                String formatDateTimeStart = game_start.format(formatter);
                endDate = new Date();
                gameDuration = (int) ((endDate.getTime() - startDate.getTime()) / 1000);
                player1_id = NetworkFacade.getPlayerId(namePlayer1.getText());  // network
				NetworkFacade.setGamedata(player1_id, player2_id, point_counter_player1, point_counter_player2, formatDateTimeStart, formatDateTime, gameDuration);  // network
                timer.stop();
                timerPlayerTwo.stop();
                gameSelection();
            }
        }
    }

    public void passValues(String text, Image Image) {
        namePlayer1.setText(text);
        avatarPlayerOne.setImage(Image);
    }
}