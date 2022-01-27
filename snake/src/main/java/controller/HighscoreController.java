package controller;

import connector.NetworkFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

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
	Label arr[];

	{
		arr = new Label[]{Player_Pos1, Player_Pos2, Player_Pos3, Player_Pos4, Player_Pos5};
	}

	CenterWindowScreen centerWindowScreen = new CenterWindowScreen();

	/**
	 * first method that will be executed, requests highscore data from server, fills local array
	 *
	 * @throws InterruptedException
	 */
	@FXML
	void initialize() throws InterruptedException {
		List<String[]> highscore_list = NetworkFacade.getHighscoreTable();  // network

		for (int i = 0; i < highscore_list.size(); ++i) {
			for (int j = 0; j < highscore_list.get(i).length; ++j) {
				Player_Pos1.setText(highscore_list.get(i)[j]);
			}
		}

		// ToDo : Auslagern in eine schöne Schleife
		Player_Pos1.setText(highscore_list.get(0)[0]);
		Player_Pos2.setText(highscore_list.get(1)[0]);
		Player_Pos3.setText(highscore_list.get(2)[0]);
		Player_Pos4.setText(highscore_list.get(3)[0]);
		Player_Pos5.setText(highscore_list.get(4)[0]);

		Player_Pos1_Points.setText(highscore_list.get(0)[1]);
		Player_Pos2_Points.setText(highscore_list.get(1)[1]);
		Player_Pos3_Points.setText(highscore_list.get(2)[1]);
		Player_Pos4_Points.setText(highscore_list.get(3)[1]);
		Player_Pos5_Points.setText(highscore_list.get(4)[1]);

		Player_Pos1_Date.setText(highscore_list.get(0)[2]);
		Player_Pos2_Date.setText(highscore_list.get(1)[2]);
		Player_Pos3_Date.setText(highscore_list.get(2)[2]);
		Player_Pos4_Date.setText(highscore_list.get(3)[2]);
		Player_Pos5_Date.setText(highscore_list.get(4)[2]);
	}
}