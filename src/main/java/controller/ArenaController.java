package controller;

import com.example.multiplayer_snake.model.Player;
import com.example.multiplayer_snake.model.SocketClient;
import javafx.animation.KeyFrame;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
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
	@FXML
	public Circle snakeBody; // snake body
	@FXML
	public Circle snakeTailCircle; // snake tail
	@FXML
	private MenuItem exitBTNMenu;
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

	CenterWindowScreen centerWindowScreen = new CenterWindowScreen();
	SubmissionPublisher source = new SubmissionPublisher<String>(); // Observer Pattern

	@FXML
	void initialize() {
		model = new Player();

		// Observer Pattern
		source.subscribe(new SocketClient());

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

	private void startGame() {
		animation.play();
		isApplicationRunning = true;
	}

	private void stopGame() {
		animation.stop();
		isApplicationRunning = false;
	}

	private void multiplayerSnakeStatus() {
		// Observer Pattern
		JSONObject snakeStatus = new JSONObject();
		snakeStatus.put("Player", String.valueOf(model.name));
		snakeStatus.put("Points", String.valueOf(model.points));
		snakeStatus.put("Eatfruit", String.valueOf(model.eatFruit));
		snakeStatus.put("Gameover", String.valueOf(model.gameOver));
		snakeStatus.put("SnakeX", String.valueOf(model.snakeX));
		snakeStatus.put("SnakeY", String.valueOf(model.snakeY));
		source.submit(String.valueOf(snakeStatus));
	}

	@FXML
	void snakeSteering(KeyEvent keyEvent) {
		@SuppressWarnings("unused")
		Player move;

		KeyFrame frame = new KeyFrame(Duration.seconds(millis), event -> {
			if (!isApplicationRunning) {
				return;
			}

			double snakeHeadX = snakeHead.getLayoutX();
			double snakeHeadY = snakeHead.getLayoutY();
			KeyCode direction = keyEvent.getCode();

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
			}

			if (model.gameOver == true) {
				gameOver.setVisible(true);
				gameOver.setText("Game Over!");
				gameOver.setTextFill(Color.RED);
				stopGame();
			}
		});

		animation.getKeyFrames().add(frame);
		animation.setCycleCount(Timeline.INDEFINITE);
		startGame();
	}
}