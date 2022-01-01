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
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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

	static final String DB_URL = "jdbc:sqlite:src/SQL Lite Database/Snake_System.db";
	static String HEXCOLOR = "0x000000ff"; // Color = black
	static final String QUERY = "SELECT snakecolor.id, snakecolor.hexcolor FROM snakecolor WHERE snakecolor.id = 1";

	@SuppressWarnings("unchecked")
	@FXML
	void initialize() {
		model = new Player();

		// Observer Pattern
		source.subscribe(new SocketClient());

		namePlayer1.setText("Max");
		namePlayer2.setText("Maxi");
		scorePlayer1.setText("9014");
		scorePlayer2.setText(String.valueOf(point_counter_player1));

		gameOver.setVisible(false);
		generateFood(); // initialize food

		/**
		 * Customize the color of a snake
		 */
		// Open a database connection
		try (Connection conn = DriverManager.getConnection(DB_URL, HEXCOLOR, null);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(QUERY);) {
			while (rs.next()) {
				// Display values
				System.out.println("----");
				System.out.println("Read from Database");
				System.out.println(">> ID: " + rs.getInt("id"));
				System.out.println(">> HEXCOLOR VALUE: " + rs.getString("hexcolor"));
				
				HEXCOLOR = rs.getString("hexcolor");
				snakeHead.setFill(Color.valueOf(HEXCOLOR));
			}
		} catch (SQLException e) {
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
				point_counter_player1++;
				scorePlayer2.setText(String.valueOf(point_counter_player1));
			}

			if (model.gameOver == true) {
				gameOver.setVisible(true);
				gameOver.setText("Game Over!");
				gameOver.setTextFill(Color.RED);
				DatabaseController.Insert_Highscore("7", LocalDateTime.now(), point_counter_player1);
				stopGame();
			}
		});

		animation.getKeyFrames().add(frame);
		animation.setCycleCount(Timeline.INDEFINITE);
		startGame();
	}
}