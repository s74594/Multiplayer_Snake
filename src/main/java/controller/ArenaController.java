package controller;

import com.example.multiplayer_snake.model.Player;
import com.example.multiplayer_snake.model.SocketClient;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	private Player modelPlayerTwo; // Controller -> Model connection
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
	public Pane playGround2;
	/* Snake */
	@SuppressWarnings("exports")
	@FXML
	public Circle snakeHead; // snake head player one
	@SuppressWarnings("exports")
	@FXML
	public Circle snakeBody; // snake body player one
	@SuppressWarnings("exports")
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
	@SuppressWarnings("exports")
	@FXML
	public ImageView foodImage;
	@FXML
	private ImageView foodImage1;
//	@FXML
//	private Label gameOver;
//	private boolean isApplicationRunning = false;
//	private Timeline animation = new Timeline();
	public static double millis = 0.3;
	public int point_counter_player1 = 0;
	public int point_counter_player2 = 0;
	@SuppressWarnings("exports")
	@FXML
	public Circle[] body = new Circle[100];
	@SuppressWarnings("exports")
	@FXML
	public Circle[] bodyPlayerTwoCircles = new Circle[100];

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

	@SuppressWarnings("unchecked")
	@FXML
	void initialize() {
		model = new Player();
		model.snakeBodyLocationsX.addFirst(250.); // store initial position in bodyparts array
		model.snakeBodyLocationsY.addFirst(200.); // store initial position in bodyparts array

		/* Player Two */
		modelPlayerTwo = new Player();
		modelPlayerTwo.snakeBodyLocationsXP2.addFirst(350.); // store initial position in bodyparts array
		modelPlayerTwo.snakeBodyLocationsYP2.addFirst(300.); // store initial position in bodyparts array

		timer.start(); // Animation Timer
		timerPlayerTwo.start(); // Start animation timer for player two
		source.subscribe(new SocketClient()); // Observer Pattern
		namePlayer1.setText("Max");
		namePlayer2.setText("Maxi");
		scorePlayer1.setText(String.valueOf(point_counter_player1));
		scorePlayer2.setText(String.valueOf(point_counter_player2));

		generateFood(); // initialize food
		generateFoodPlayerTwo(); // initialize food for Player Two

		// Read file and set the color of the snake
		try {
			File myObj = new File("color.txt");
			Scanner reader = new Scanner(myObj);
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
//				System.out.println(data);
				snakeHead.setFill(Color.web(data)); // Color Player One
				snakeHeadPlayerTwo.setFill(Color.web(data)); // Color Player Two

				for (int i = 0; i < body.length; i++) {
					body[i] = new Circle(10);
					bodyPlayerTwoCircles[i] = new Circle(10);
					body[i].setFill(Color.web(data));
					bodyPlayerTwoCircles[i].setFill(Color.web(data));
					body[i].setVisible(false);
					bodyPlayerTwoCircles[i].setVisible(false);
					playGround2.getChildren().addAll(body[i], bodyPlayerTwoCircles[i]);
				}
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

	public void generateFood() {
		model.generateFood();
		foodImage.setLayoutX(model.fruitX);
		foodImage.setLayoutY(model.fruitY);
		foodImage.setVisible(true);
	}

	/** Player Two **/
	public void generateFoodPlayerTwo() {
		modelPlayerTwo.generateFood();
		foodImage1.setLayoutX(modelPlayerTwo.fruitX);
		foodImage1.setLayoutY(modelPlayerTwo.fruitY);
		foodImage1.setVisible(true);
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

	// Ungenutzte Funktionen
//	private void startGame() {
//		animation.play();
//		isApplicationRunning = true;
//	}
//
//	private void stopGame() {
//		animation.stop();
//		isApplicationRunning = false;
//		gameSelection();
//	}

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
				body[i].setLayoutX(model.snakeBodyLocationsX.get(i));
				body[i].setLayoutY(model.snakeBodyLocationsY.get(i));
				body[i].setVisible(true);
			}

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
				scorePlayer1.setText(String.valueOf(point_counter_player1));
			}

			if (model.gameOver == true) {
				DatabaseController.Insert_Highscore("7", LocalDateTime.now(), point_counter_player1);
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
			modelPlayerTwo.fruitBounds = foodImage1.getBoundsInParent();

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
			multiplayerSnakeStatus();

			if (modelPlayerTwo.eatFruit == true) {
				foodImage1.setVisible(false); // set food invisible the snake hits its boundaries
				modelPlayerTwo.generateFood();
				foodImage1.setLayoutX(modelPlayerTwo.fruitX);
				foodImage1.setLayoutY(modelPlayerTwo.fruitY);
				foodImage1.setVisible(true);
				modelPlayerTwo.eatFruit = false;
				point_counter_player2++;
				scorePlayer2.setText(String.valueOf(point_counter_player2));
			}

			// Kollisionserkennung der Fensterbounds nocht nicht funktionsfähig
//			if (modelPlayerTwo.gameOver == true) {
//				DatabaseController.Insert_Highscore("7", LocalDateTime.now(), point_counter_player2);
//				timer.stop();
//				timerPlayerTwo.stop();
//				gameSelection();
//			}
		}
	}
}