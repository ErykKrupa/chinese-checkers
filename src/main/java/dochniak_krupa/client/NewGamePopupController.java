package dochniak_krupa.client;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class NewGamePopupController {

	@FXML private RadioButton players2RadioButton;
	@FXML private RadioButton players3RadioButton;
	@FXML private RadioButton players4RadioButton;
	@FXML private RadioButton players6RadioButton;

	@FXML public void newMultiPlayerGameBtnClick() {
		//Creating proper board instance and sending create game response
		// with specific number of players to server
		if (players2RadioButton.isSelected()) {
			Board.setInstance(2);
			//Sending action message to server
			Client.getInstance().out.println("CREATE MULTIPLAYER 2");
		} else if (players3RadioButton.isSelected()) {
			Board.setInstance(3);
			//Sending action message to server
			Client.getInstance().out.println("CREATE MULTIPLAYER 3");
		} else if (players4RadioButton.isSelected()) {
			Board.setInstance(4);
			//Sending action message to server
			Client.getInstance().out.println("CREATE MULTIPLAYER 4");
		} else {
			Board.setInstance(6);
			//Sending action message to server
			Client.getInstance().out.println("CREATE MULTIPLAYER 6");
		}

		//Reading response message form server
		String privilege = "";
		try{
			privilege = Client.getInstance().in.readLine();
		}catch (IOException e){
			System.out.println("Unable to read line");
		}

		//Initializing board window after checking privileges for that
		if (privilege.equals("CREATE GAME PRIVILEGE GRANTED")){
			Stage boardStage = new Stage();
			Scene scene = new Scene(Board.getInstance(), 800, 800);
			scene.setFill(Color.web("#99ffff7f"));
			boardStage.setScene(scene);
			boardStage.setTitle("Chinese Checkers - Menu");
			boardStage.setResizable(false);
			boardStage.show();
			MenuController.newGamePopupStage.hide();
		}
	}
}
