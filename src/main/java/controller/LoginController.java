package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

    /* Global variables */
    private static final int WIDTH = 600;
    private static final int HEIGHT = 408;
    private static final int ROWS = 25;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = WIDTH / ROWS;
    private GraphicsContext graphicsContext;

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
    protected void onPlayButtonClick(ActionEvent event) {
        try {
            // <Play Button>: opens arena view
            // Parent rootParent = FXMLLoader.load(getClass().getResource("arenaView.fxml"));
            URL url = new File("src/main/resources/com/example/multiplayer_snake/arenaView.fxml").toURI().toURL();
            Parent rootParent = FXMLLoader.load(url);

            // Setting a frame on top of the scene builders created frame
            Pane rootPane = new Pane();
            Canvas canvas = new Canvas(WIDTH, HEIGHT);
            rootPane.getChildren().addAll(canvas, rootParent);
            Scene scene = new Scene(rootPane);
            Stage stage = new Stage();
            stage.setTitle("Snake");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            centerWindowOnScreen(stage); // call method: center frame on screen
            graphicsContext = canvas.getGraphicsContext2D();
            run();
        } catch (Exception e) {
            // handle error exception
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
            // handle error exception
            System.err.println(e.getMessage());
        }
    }

    void centerWindowOnScreen(Stage stage) {
        /* Center Snake Window on Screen */
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    @FXML
    protected void onSignupButtonClick(ActionEvent event) {
        try {
            // <Play Button>: Open a new game window
            // Parent rootParent = FXMLLoader.load(getClass().getResource("registryView.fxml"));
            URL url = new File("src/main/resources/com/example/multiplayer_snake/registryView.fxml").toURI().toURL();
            Parent rootParent = FXMLLoader.load(url);

            Scene scene = new Scene(rootParent);
            Stage stage = new Stage();
            stage.setTitle("??????");
            stage.initModality(Modality.APPLICATION_MODAL); // disable minimize, maximize button
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            centerWindowOnScreen(stage); // call method: center frame on screen
        } catch (Exception e) {
            // handle error exception
            System.err.println(e.getMessage());
        }
    }
}