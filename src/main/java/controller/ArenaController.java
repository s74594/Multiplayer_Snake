package controller;

import com.example.multiplayer_snake.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.Random;

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
	public Circle snake; // snake head

	@FXML
	private MenuItem exitBTNMenu;

	@FXML
	private MenuItem gameInfoBTNMenu;

	@FXML
	public ImageView foodImage;

	CenterWindowScreen centerWindowScreen = new CenterWindowScreen();
	Random random = new Random();

	@FXML
	void initialize() {
		player = new Player();

		namePlayer1.setText("Max");
		namePlayer2.setText("Maxi");
		scorePlayer1.setText("9014");
		scorePlayer2.setText("9541");

		generateFood(); // initialize food
	}

	/* Method to generate food at a random position(x,y) */
	public void generateFood() {

		/* place food within defined borders */
		int minWidth = 0;
		int maxWidth = 580;
		int minHeight = 25;
		int maxHeight = 380;

		/* generates a random value */
		int posX = (int) (Math.random() * (maxWidth - minWidth)) + minWidth;
		int posY = (int) (Math.random() * (maxHeight - minHeight) + minHeight);

		/* set fruit */
		foodImage.setLayoutX(posX);
		foodImage.setLayoutY(posY);
		foodImage.setVisible(true);

		System.out.println("X: " + posX + " Y: " + posY); // Debug
	}

	/* snake eats food and generate new food at position(x,y) */
	public void eatFood() {
		foodImage.setVisible(false); // set food invisible the snake hits its boundaries
		generateFood();
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

	public void checkCollision() {
		// Head touches a fruit
		if (foodImage.getBoundsInParent().intersects(snake.getBoundsInParent())) {
			System.out.println("Food collition detected"); // Debug
			eatFood();
		}

		// Head touches any border
		if (snake.getLayoutX() <= 9)
			System.out.println("Left border touched: Game Over!"); // Collision with left border
		if (snake.getLayoutX() >= 590)
			System.out.println("Right border touched: Game Over!"); // Collision with right border
		if (snake.getLayoutY() <= 35)
			System.out.println("Top border touched: Game Over!"); // Collision with top border
		if (snake.getLayoutY() > 395)
			System.out.println("Bottom border touched: Game Over!");// Collision with bottom border
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
		final double snakeSpeed = 3; // speed adjust
		double snakeX = snake.getLayoutX();
		double snakeY = snake.getLayoutY();

		/*
		 * Path path = new Path(); path.getElements().add(new MoveTo(snake.getLayoutX(),
		 * snake.getLayoutY())); path.getElements().add(new LineTo(0, 200));
		 */

		System.out.println("Key: " + keyEvent.getCode() + "  SnakeX: " + snakeX + "  SnakeY: " + snakeY); // Debug

		switch (keyEvent.getCode()) {
		case UP -> {
			// Detect food collision
			checkCollision();
			snake.setLayoutY(snakeY - snakeSpeed);
		}
		case DOWN -> {
			// Detect food collision
			checkCollision();
			snake.setLayoutY(snakeY + snakeSpeed);
		}
		case LEFT -> {
			// Detect food collision
			checkCollision();
			snake.setLayoutX(snakeX - snakeSpeed);
		}
		case RIGHT -> {
			// Detect food collision
			checkCollision();
			snake.setLayoutX(snakeX + snakeSpeed);
		}
		default -> throw new IllegalArgumentException("Unexpected value: " + keyEvent.getCode());
		}

		/*
		 * PathTransition pathTransition = new PathTransition();
		 * pathTransition.setDuration(Duration.millis(6000));
		 * pathTransition.setPath(path); pathTransition.setNode(snake);
		 * pathTransition.setOrientation(PathTransition.OrientationType.
		 * ORTHOGONAL_TO_TANGENT); pathTransition.play();
		 */
	}
}