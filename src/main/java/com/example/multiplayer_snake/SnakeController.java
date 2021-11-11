package com.example.multiplayer_snake;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SnakeController {

	@FXML
	private Button playButton;

	@FXML
	private Button exitButton;

	@FXML
	private MenuItem exitBTNMenu;
	
	@FXML
    private MenuItem gameInfoBTNMenu;
	
	@FXML
    private Text scoreLabel;

    @FXML
    private Label scoreLabelNumber;

	private static final int WIDTH = 600;
	private static final int HEIGHT = 408;
	private static final int ROWS = 25;
	private static final int COLUMNS = ROWS;
	private static final int SQUARE_SIZE = WIDTH / ROWS;
	private GraphicsContext graphicsContext;

	@FXML
	protected void onPlayButtonClick(ActionEvent event) {
		try {
			// <Play Button>: Open a new game window
			Parent rootParent = FXMLLoader.load(getClass().getResource("game.fxml"));

			// Create a new Window
			Pane rootPane = new Pane();
			Canvas canvas = new Canvas(WIDTH, HEIGHT);
			rootPane.getChildren().addAll(canvas,rootParent);
			Scene scene = new Scene(rootPane);
			Stage stage = new Stage();
			stage.setTitle("Snake");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			
			centerWindowOnScreen(stage);	// call methode center screen

			graphicsContext = canvas.getGraphicsContext2D();
			run();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
	}

	private void run() {
		drawBackground(graphicsContext);
	}

	// Drawing light and dark squares
	private void drawBackground(GraphicsContext graphicsContext) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if ((i + j) % 2 == 0) {
					graphicsContext.setFill(Color.web("AAD751"));
				} else {
					graphicsContext.setFill(Color.web("A2D149"));
				}
				graphicsContext.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
	}

	@FXML
	protected void onExitButtonClick(ActionEvent event) {
		try {
			// <Exit Button>: Close login window
			Stage stage = (Stage) exitButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
	}

	@FXML
	void onExitMenuClick(ActionEvent event) {
		try {
			// <Menu Exit>: Close game window
			System.exit(0);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
	}
	
	@FXML
    void onGameInfoMenuClick(ActionEvent event) {
		try {
			// <Play Button>: Open a new game window
			Parent rootParent = FXMLLoader.load(getClass().getResource("frameGameInfo.fxml"));
			
			Scene scene = new Scene(rootParent);
			Stage stage = new Stage();
			stage.setTitle("Snake - Gameplay Info");
			stage.initModality(Modality.APPLICATION_MODAL);	// disable minimize, maximize button
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			
			centerWindowOnScreen(stage);	// call methode center screen
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
    }
	
	void centerWindowOnScreen(Stage stage) {
		/** Center Snake Window on Screen **/
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
	}
}