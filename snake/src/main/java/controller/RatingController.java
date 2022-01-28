package controller;

import connector.NetworkFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.util.LinkedList;
import java.util.List;

/**
 * controls rating window
 */
public class RatingController {

	@FXML
	private Label Player_Name1;
	@FXML
	private Label Player1_Played_Games;
	@FXML
	private Label Player1_Average_Score;
	@FXML
	private Label Player_Playing_Time;

	/**
	 * first method that will be executed, requests rating data from server, fills local array
	 *
	 * @throws InterruptedException
	 */
	@FXML
	void initialize() throws InterruptedException {
		String playerName = "Testaccount";
		List<String[]> rating_list = NetworkFacade.getRatingTable(playerName);  // network

		// filling fields
		Player_Name1.setText(playerName);
		Player1_Played_Games.setText(rating_list.get(0)[0]);
		Player1_Average_Score.setText(rating_list.get(0)[1]);
		Player_Playing_Time.setText(rating_list.get(0)[2]);
	}
}