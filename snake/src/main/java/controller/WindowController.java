package controller;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * centers application on screen
 */
public class WindowController {

	/**
	 * centers application window on screen
	 *
	 * @param stage that will be centered
	 */
	public void CenterScreen(Stage stage) {
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
	}
}