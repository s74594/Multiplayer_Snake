package controller;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CenterWindowScreen {
	
	@SuppressWarnings("exports")
	public void CenterScreen(Stage stage) {
	/* Center Snake Window on Screen */
    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
	}
}