package controller;

import com.example.snake.model.Player;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/**
 * starts thread for generating fruits
 */
public class LoopTaskController implements Runnable {

    private String loopTaskName;
    private int timerDelay = 10000;
    public Player model = new Player();

    @FXML
    private ImageView foodImage;

    public LoopTaskController(String loopTaskName, ImageView foodImage) {
        super();
        this.loopTaskName = loopTaskName;
        this.foodImage = foodImage;
    }

    /**
     * task runner
     */
    @Override
    public void run() {
        System.out.println("Starting " + loopTaskName);

        for (int i = 1; i <= 10; i++) {
            // System.out.println("Executing " + loopTaskName + " with " + Thread.currentThread().getName() + "====" + i);
            try {
                Thread.sleep(timerDelay);

                /**
                 * generates food for player1
                 */
                model.generateFood();
                foodImage.setLayoutX(model.fruitX);
                foodImage.setLayoutY(model.fruitY);
                foodImage.setVisible(true);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}