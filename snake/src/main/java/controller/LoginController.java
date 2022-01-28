package controller;

import connector.NetworkFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * controls the login window
 */
public class LoginController {
	@FXML
	private Button playButton;
	@FXML
	private Button button_signup;
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
	@FXML
	private TextField player_name;
	@FXML
	private PasswordField player_password;
	@FXML
	private Label pw_incorrect;
	@FXML
	private Label error_msg;
	@FXML
	private TextField r_name;
	@FXML
	private TextField r_pw;
	@FXML
	private TextField r_pw_check;
	@FXML
	private Button backButton;
	@FXML
	private ColorPicker snakeColorPicker; // color picker to customize a snake

	/* Image Slider */
	@FXML
	private ImageView avatarIMG;
	@FXML
	private Button backBTN;
	@FXML
	private Button nextBTN;

	private static final String sqlRegisterUserAnswer = null;
	ArrayList<String> pictures = new ArrayList<String>();
	public int indexIMGCounter = 1; // Index counter, iterating an arraylist

	/* Global variables: board */
	private static final int WIDTH = 1532;
	private static final int HEIGHT = 786;
	private static final int ROWS = 350;
	private static final int COLUMNS = ROWS;
	private static final int SQUARE_SIZE = WIDTH / ROWS;
	private GraphicsContext graphicsContext;

	CenterWindowScreen centerWindowScreen = new CenterWindowScreen();
	private ActionEvent event;

	/**
	 * first method
	 *
	 * connects to server
	 * adds avatar images to list
	 */
	@FXML
	void initialize() {
		NetworkFacade.connect();

		/* add images to arraylist */
		pictures.add("snake/src/main/resources/com/example/snake/image/avatar/img_1.png");
		pictures.add("snake/src/main/resources/com/example/snake/image/avatar/img_2.png");
		pictures.add("snake/src/main/resources/com/example/snake/image/avatar/img_3.png");
		pictures.add("snake/src/main/resources/com/example/snake/image/avatar/img_4.png");
	}

	/**
	 * if play button clicked method will be executed
	 *
	 * @param event
	 */
	@FXML
	protected void onPlayButtonClick(ActionEvent event) {
		this.event = event;
		try {
			if (NetworkFacade.login(player_name.getText(), player_password.getText())) {
				URL url = new File("snake/src/main/resources/com/example/snake/arenaView.fxml").toURI().toURL();
				//Parent rootParent = FXMLLoader.load(url);

				FXMLLoader loader = new FXMLLoader(url); // change
				Parent rootParent = loader.load(); // change

				ArenaController arenaController = loader.getController(); // change
				Image image = new Image(new FileInputStream(pictures.get(indexIMGCounter-1)));
				arenaController.passValues(player_name.getText(), image);

				Stage stage = (Stage) exitButton.getScene().getWindow();
				stage.close();
				// Setting a frame on top of the scene builders created frame
				Pane rootPane = new Pane();
				Canvas canvas = new Canvas(WIDTH, HEIGHT);
				rootPane.getChildren().addAll(canvas, rootParent);

				Scene scene = new Scene(rootPane);
				Stage arena_stage = new Stage();
				arena_stage.setTitle("Snake");
				arena_stage.setResizable(false);
				arena_stage.setScene(scene);
				arena_stage.show();

				centerWindowScreen.CenterScreen(stage); // call method: center frame on screen
				graphicsContext = canvas.getGraphicsContext2D();
			} else {
				pw_incorrect.setVisible(true);
			}
		} catch (Exception e) {
			// handle error exception
			System.err.println(e.getMessage());
		}
	}

	/**
	 * customize the color of the snake
	 *
	 * @param event
	 */
	@FXML
	void customizeSnakeColor(ActionEvent event) {
		ArenaController customSnakeColor = new ArenaController();
		customSnakeColor.custom_Snake_Color(snakeColorPicker.getValue());
	}

	/**
	 * toDo
	 *
	 * @param e
	 * @throws SQLException
	 */
	@FXML
	protected void Enter_Play(KeyEvent e) throws SQLException {
		if (e.getCode().equals(KeyCode.ENTER))
			onPlayButtonClick(event);
	}

	/**
	 * if exit button clicked method will be executed
	 *
	 * @param event
	 */
	@FXML
	protected void onExitButtonClick(ActionEvent event) {
		try {
			// <Exit Button>: Close login window
			Stage this_stage = (Stage) exitButton.getScene().getWindow();
			this_stage.close();
		} catch (Exception e) {
			// handle error exception
			System.err.println(e.getMessage());
		}
	}

	/**
	 * if back button clicked method will be executed
	 *
	 * @param event
	 */
	@FXML
	protected void onBackButton_Click(ActionEvent event) {
		try {
			Stage stage = (Stage) backButton.getScene().getWindow();
			stage.close();
			URL url = new File("snake/src/main/resources/com/example/snake/loginView.fxml").toURI().toURL();
			Parent rootParent = FXMLLoader.load(url);
			Scene scene = new Scene(rootParent);
			stage.setTitle("Snake");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			centerWindowScreen.CenterScreen(stage);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * if signup button clicked method will be executed
	 *
	 * @param event
	 */
	@FXML
	protected void onSignupButtonClick(ActionEvent event) {
		try {
			URL url = new File("snake/src/main/resources/com/example/snake/registryView.fxml").toURI().toURL();
			Parent rootParent = FXMLLoader.load(url);
			Stage stage = (Stage) exitButton.getScene().getWindow();
			stage.hide();
			Scene scene = new Scene(rootParent);
			Stage signUp_stage = new Stage();
			signUp_stage.setTitle("Create a new player");
			signUp_stage.initModality(Modality.APPLICATION_MODAL); // disable minimize, maximize button
			signUp_stage.setResizable(false);
			signUp_stage.setScene(scene);
			signUp_stage.show();
			centerWindowScreen.CenterScreen(stage); // call method: center frame on screen
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}


	/**
	 * if register button clicked method will be executed
	 *
	 * @param actionEvent
	 * @throws InterruptedException
	 */
	public void onRegisterButtonClick(ActionEvent actionEvent) throws InterruptedException {
		if (r_pw.getText().equals(r_pw_check.getText())) {
			if(NetworkFacade.register(r_name.getText(), r_pw.getText()) == true) {
				error_msg.setText("User registered sucessfully");
				error_msg.setVisible(true);
			} else {
				error_msg.setText("User not registered");
				error_msg.setVisible(true);
			}
		} else {
			error_msg.setText("Passwords aren´t matching");
			error_msg.setVisible(true);
		}
	}


	/**
	 * if clear button clicked method will be executed
	 *
	 * @param actionEvent
	 */
	public void onClearClick(ActionEvent actionEvent) {
		r_name.clear();
		r_pw.clear();
		r_pw_check.clear();
	}

	/**
	 * if image slider selection clicked method will be executed
	 *
	 * @param event
	 */
	@FXML
	void onNextClick(ActionEvent event) {
		try {
			Image image = new Image(new FileInputStream(pictures.get(indexIMGCounter)));
			avatarIMG.setImage(image);
			indexIMGCounter++;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Prevent index out of bounds error
		// and disable next button
		if (indexIMGCounter == 4)
			nextBTN.setDisable(true);

		// Enable back button
		if (indexIMGCounter == 1)
			backBTN.setDisable(false);
	}

	/**
	 * if back button clicked method will be executed
	 *
	 * @param event
	 */
	@FXML
	void onBackClick(ActionEvent event) {
		indexIMGCounter--;
		nextBTN.setDisable(false);
		// System.out.println(">> " + indexIMGCounter); // debug counter
		try {
			Image image = new Image(new FileInputStream(pictures.get(indexIMGCounter)));
			avatarIMG.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Prevent index out of bounds error and disable back button
		if (indexIMGCounter == 0)
			backBTN.setDisable(true);
	}

}