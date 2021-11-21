package controller;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.multiplayer_snake.Snake;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
//import model.LoginViewModel;

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


    /* Global variables */
    private static final int WIDTH = 600;
    private static final int HEIGHT = 408;
    private static final int ROWS = 25;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = WIDTH / ROWS;
    private GraphicsContext graphicsContext;
    
    CenterWindowScreen centerWindowScreen = new CenterWindowScreen();
    private ActionEvent event;

/*    @SuppressWarnings("exports")
    public LoginViewModel loginViewModel = new LoginViewModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Check Database SQL-Lite Connection
        if (loginViewModel.isDBConnected()) {
            System.out.println("Database connection successful.");
        } else {
            System.out.println("Database connection not succeeded.");
        }
    }*/

    @FXML
    protected void onPlayButtonClick(ActionEvent event) throws SQLException {
        this.event = event;
        try {
            // <Play Button>: opens arena view
            // Parent rootParent = FXMLLoader.load(getClass().getResource("arenaView.fxml"));

            String expectedPW =DatabaseController.Select_Player_PW(player_name.getText());

            if (Objects.equals(player_password.getText(), expectedPW)) {
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
            run();
            }

            else{
                pw_incorrect.setVisible(true);
            }


        } catch (Exception e) {
            // handle error exception
            System.err.println(e.getMessage());
        }
    }

    @FXML
    protected void Enter_Play(KeyEvent e) throws SQLException {
        if(e.getCode().equals(KeyCode.ENTER))
            onPlayButtonClick(event);
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
            Stage this_stage = (Stage) exitButton.getScene().getWindow();
            this_stage.close();
        }
        catch (Exception e) {
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
            // <Play Button>: Open a new game window
            // Parent rootParent = FXMLLoader.load(getClass().getResource("registryView.fxml"));
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


    public void onRegisterButtonClick(ActionEvent actionEvent) {
        String message = DatabaseController.Insert_Player(r_name.getText(), r_pw.getText(), r_pw_check.getText());
        if (Objects.equals(message,"[SQLITE_CONSTRAINT_UNIQUE]  A UNIQUE constraint failed (UNIQUE constraint failed: players.name)")){
            error_msg.setVisible(true);
        }
        if (Objects.equals(message,"Passwörter stimmen nicht überein")){
            error_msg.setText("Passwords aren´t matching");
            error_msg.setVisible(true);
        }

        if (Objects.equals(message,"query does not return ResultSet")){
            error_msg.setText("User " + "'"+ r_name.getText() + "'" + " successfully added.");
            error_msg.setTextFill(Color.BLUE);
            error_msg.setVisible(true);
            r_name.clear();
            r_pw.clear();
            r_pw_check.clear();
        }
    }


    public void onClearClick(ActionEvent actionEvent){
        r_name.clear();
        r_pw.clear();
        r_pw_check.clear();
    }

}