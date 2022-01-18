package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

/**
 * controls highscore window
 */
public class HighscoreController {

	@FXML
	public Label Player_Pos1;
	@FXML
	public Label Player_Pos2;
	@FXML
	public Label Player_Pos3;
	@FXML
	private Label Player_Pos4;
	@FXML
	private Label Player_Pos5;
	@FXML
	private Label Player_Pos1_Points;
	@FXML
	private Label Player_Pos2_Points;
	@FXML
	private Label Player_Pos3_Points;
	@FXML
	private Label Player_Pos4_Points;
	@FXML
	private Label Player_Pos5_Points;
	@FXML
	private Label Player_Pos1_Date;
	@FXML
	private Label Player_Pos2_Date;
	@FXML
	private Label Player_Pos3_Date;
	@FXML
	private Label Player_Pos4_Date;
	@FXML
	private Label Player_Pos5_Date;
	@FXML
	public TableView highscore_table;
	private static List<String[]> highscoreList;
	Label[] arr;

	{
		arr = new Label[] { Player_Pos1, Player_Pos2, Player_Pos3, Player_Pos4, Player_Pos5 };
	}

	CenterWindowScreen centerWindowScreen = new CenterWindowScreen();

	/**
	 * first method that will be executed, requests highscore data from server, fills local array
	 *
	 * @throws InterruptedException
	 */
	@FXML
	void initialize() throws InterruptedException {
		// List<String[]> highscore_list = DatabaseController.GetHighscore();
		JSONObject messageJSON = new JSONObject();
		messageJSON.put("sql_get_highscore", "true");
		//source.submit(String.valueOf(messageJSON));
		Thread.sleep(2000);

		for (int i = 0; i < highscoreList.size(); ++i) {
			for (int j = 0; j < highscoreList.get(i).length; ++j) {
				System.out.println(highscoreList.get(i)[j]);
				Player_Pos1.setText(highscoreList.get(i)[j]);
			}
		}

		// ToDo : Auslagern in eine schöne Schleife
		Player_Pos1.setText(highscoreList.get(0)[0]);
		Player_Pos2.setText(highscoreList.get(1)[0]);
		Player_Pos3.setText(highscoreList.get(2)[0]);
		Player_Pos4.setText(highscoreList.get(3)[0]);
		Player_Pos5.setText(highscoreList.get(4)[0]);

		Player_Pos1_Points.setText(highscoreList.get(0)[1]);
		Player_Pos2_Points.setText(highscoreList.get(1)[1]);
		Player_Pos3_Points.setText(highscoreList.get(2)[1]);
		Player_Pos4_Points.setText(highscoreList.get(3)[1]);
		Player_Pos5_Points.setText(highscoreList.get(4)[1]);

		Player_Pos1_Date.setText(highscoreList.get(0)[2]);
		Player_Pos2_Date.setText(highscoreList.get(1)[2]);
		Player_Pos3_Date.setText(highscoreList.get(2)[2]);
		Player_Pos4_Date.setText(highscoreList.get(3)[2]);
		Player_Pos5_Date.setText(highscoreList.get(4)[2]);
	}

	/**
	 * toDo
	 *
	 * @param list
	 */
	public static void setHighscoreList(JSONArray list) {
		HighscoreController.highscoreList = null;
		HighscoreController.highscoreList = Collections.singletonList(list.join(",").split(","));
	}
}