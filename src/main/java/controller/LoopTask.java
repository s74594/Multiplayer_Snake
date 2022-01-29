package controller;

import com.example.multiplayer_snake.model.Player;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/* Thread: Nebenläufigkeit */
/**
 * Date: Jan-28-2022 23:36:00
 * 
 * @author Daniel
 * @version 1.0
 */

public class LoopTask implements Runnable {

	private String loopTaskName;
	private int timerDelay = 10000;
	public Player model = new Player();

	@FXML
	private ImageView foodImage;

	public LoopTask(String loopTaskName, ImageView foodImage) {
		super();
		this.loopTaskName = loopTaskName;
		this.foodImage = foodImage;
	}

	@Override
	public void run() {
		System.out.println("Starting " + loopTaskName);

		for (int i = 1; i <= 10; i++) {
			System.out.println("Executing " + loopTaskName + " with " + Thread.currentThread().getName() + "====" + i);

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Ending " + loopTaskName);
	}
}