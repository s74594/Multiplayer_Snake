package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.SubmissionPublisher;

import com.example.multiplayer_snake.model.SocketClient;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;

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
	private static String sqlLoginUser = null;
	private static String sqlLoginUserPass = null;
	private static String sqlRegisterUserAnswer = null;
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

	SubmissionPublisher source = new SubmissionPublisher<String>(); // Observer Pattern

	@FXML
	void initialize() {
		SocketClient.connect();
		source.subscribe(new SocketClient()); // Observer Pattern

		/* add images to arraylist */
		pictures.add("image/avatar/img_1.png");
		pictures.add("image/avatar/img_2.png");
		pictures.add("image/avatar/img_3.png");
		pictures.add("image/avatar/img_4.png");
	}

	@FXML
	protected void onPlayButtonClick(ActionEvent event) throws SQLException {
		this.event = event;
		try {
			// send message with user and password to server
			JSONObject messageJSON = new JSONObject();
			messageJSON.put("sql_login_user", player_name.getText());
			source.submit(String.valueOf(messageJSON));
			Thread.sleep(2000);  // waiting for password retrieve
			// System.out.println("Username SQL: " + sqlLoginUser + "  Password SQL: " + sqlLoginUserPass + "  Player Name: " + player_name.getText() + "  Player Password: " + player_password.getText());
			if ((sqlLoginUser.equals(player_name.getText())) && (sqlLoginUserPass.equals(player_password.getText()))) {
				URL url = new File("src/main/resources/com/example/multiplayer_snake/arenaView.fxml").toURI().toURL();
				Parent rootParent = FXMLLoader.load(url);
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
//				run();
			} else {
				pw_incorrect.setVisible(true);
			}
			sqlLoginUser = null; // reset
			sqlLoginUserPass = null; // reset
		} catch (Exception e) {
			// handle error exception
			System.err.println(e.getMessage());
		}
	}

	// Customize the color of a snake
	@FXML
	void customizeSnakeColor(ActionEvent event) {
		/* Instance herstellen */
		ArenaController customSnakeColor = new ArenaController();
		customSnakeColor.custom_Snake_Color(snakeColorPicker.getValue());
	}

	@FXML
	protected void Enter_Play(KeyEvent e) throws SQLException {
		if (e.getCode().equals(KeyCode.ENTER))
			onPlayButtonClick(event);
	}

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

	@FXML
	protected void onBackButton_Click(ActionEvent event) {
		try {
			Stage stage = (Stage) backButton.getScene().getWindow();
			stage.close();
			URL url = new File("src/main/resources/com/example/multiplayer_snake/loginView.fxml").toURI().toURL();
			Parent rootParent = FXMLLoader.load(url);
			Scene scene = new Scene(rootParent);
			stage.setTitle("Snake");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			centerWindowScreen.CenterScreen(stage);
		} catch (Exception e) {
			// handle error exception
			System.err.println(e.getMessage());
		}
	}

	@FXML
	protected void onSignupButtonClick(ActionEvent event) {
		try {
			URL url = new File("src/main/resources/com/example/multiplayer_snake/registryView.fxml").toURI().toURL();
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
			// handle error exception
			System.err.println(e.getMessage());
		}
	}

	@SuppressWarnings("exports")
	public void onRegisterButtonClick(ActionEvent actionEvent) throws InterruptedException {
		if (r_pw.getText().equals(r_pw_check.getText())) {
			JSONObject messageJSON = new JSONObject();
			messageJSON.put("sql_register_user", r_name.getText());
			messageJSON.put("sql_register_pass", r_pw.getText());
			source.submit(String.valueOf(messageJSON));
		} else {
			error_msg.setText("Passwords aren´t matching");
			error_msg.setVisible(true);
		}

		while(sqlRegisterUserAnswer == null) {
			// waiting for successfully register
		}
		Thread.sleep(2000); // waiting for password retrieve
		error_msg.setText(sqlRegisterUserAnswer);
		error_msg.setVisible(true);

		sqlRegisterUserAnswer = null;  // reset

		//String message = DatabaseController.Insert_Player(r_name.getText(), r_pw.getText(), r_pw_check.getText());
/*		if (Objects.equals(message,
				"[SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: players.name)")) {
			error_msg.setVisible(true);
		}

		if (Objects.equals(message, "Passwörter stimmen nicht überein")) {
			error_msg.setText("Passwords aren´t matching");
			error_msg.setVisible(true);
		}

		if (Objects.equals(message, "Query does not return ResultSet")) {
			error_msg.setText("User " + "'" + r_name.getText() + "'" + " successfully added.");
			error_msg.setTextFill(Color.BLUE);
			error_msg.setVisible(true);
			r_name.clear();
			r_pw.clear();
			r_pw_check.clear();
		}*/
	}

	@SuppressWarnings("exports")
	public void onClearClick(ActionEvent actionEvent) {
		r_name.clear();
		r_pw.clear();
		r_pw_check.clear();
	}

	/* Image slider */
	@FXML
	void onNextClick(ActionEvent event) {
		try {
			Image image = new Image(new FileInputStream(pictures.get(indexIMGCounter)));
			avatarIMG.setImage(image);
			indexIMGCounter++;
//			System.out.println(">> " + indexIMGCounter); // debug counter
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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

	@FXML
	void onBackClick(ActionEvent event) {
		indexIMGCounter--;
		nextBTN.setDisable(false);
//		System.out.println(">> " + indexIMGCounter); // debug counter
		try {
			Image image = new Image(new FileInputStream(pictures.get(indexIMGCounter)));
			avatarIMG.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Prevent index out of bounds error
		// and disable back button
		if (indexIMGCounter == 0)
			backBTN.setDisable(true);
	}

	public static void setSqlLoginUserPass(String sqlLoginUserPass) {
		LoginController.sqlLoginUserPass = sqlLoginUserPass;
	}

	public static void setSqlLoginUser(String sqlLoginUser) {
		LoginController.sqlLoginUser = sqlLoginUser;
	}

	public static void setSqlRegisterUserAnswer(String sqlRegisterUserAnswer) {
		LoginController.sqlRegisterUserAnswer = sqlRegisterUserAnswer;
	}

}