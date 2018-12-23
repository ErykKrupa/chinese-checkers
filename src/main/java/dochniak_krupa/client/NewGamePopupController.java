package dochniak_krupa.client;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NewGamePopupController {

//	radio buttons created in fxml
	@FXML private RadioButton players2RadioButton;
	@FXML private RadioButton players3RadioButton;
	@FXML private RadioButton players4RadioButton;
	@FXML private RadioButton players6RadioButton;

//	text fields stores amount of bots
	@FXML private TextField numberOfBots;

	@FXML public void newMultiPlayerGameBtnClick() {
		//Creating proper board instance and sending create game response
		// with specific number of players to server
		if (players2RadioButton.isSelected()) {
			Board.setInstance(2);
		} else if (players3RadioButton.isSelected()) {
			Board.setInstance(3);
		} else if (players4RadioButton.isSelected()) {
			Board.setInstance(4);
		} else if (players6RadioButton.isSelected()) {
			Board.setInstance(6);
		}
		GameController.getInstance().createBots(Integer.parseInt(numberOfBots.getText()));

		//		end turn for confidence that first player who will get turn is in the game
		GameController.getInstance().endTurn();
		Board.getInstance().setAlignment(Pos.CENTER);
		Scene scene = new Scene(Board.getInstance(), 750, 800);

		scene.setFill(Color.web("#99ffff7f")); //translucent light blue // why doesn't it work?
		Stage boardStage = new Stage();
		boardStage.setScene(scene);
		boardStage.setTitle("Chinese Checkers");
		boardStage.setResizable(false);
		boardStage.show();
		MenuController.newGamePopupStage.hide();

//		set end turn under space key
			scene.getAccelerators().put(new KeyCodeCombination(KeyCode.SPACE),
					()-> GameController.getInstance().endTurn());
	}

//	increments amount of bots, number od players must be equal to or greater than amount of bots
	@FXML public void incrementBotsClick() {
		if (!(players2RadioButton.isSelected() && numberOfBots.getText().equals("2")) &&
				!(players3RadioButton.isSelected() && numberOfBots.getText().equals("3")) &&
				!(players4RadioButton.isSelected() && numberOfBots.getText().equals("4")) &&
				!(players6RadioButton.isSelected() && numberOfBots.getText().equals("6"))) {
			numberOfBots.setText(Integer.parseInt(numberOfBots.getText()) + 1 + "");
		}
	}

//	decrements amount of bots which must be greater than or equals zero
	@FXML public void decrementBotsClick() {
		if (!numberOfBots.getText().equals("0")) {
			numberOfBots.setText(Integer.parseInt(numberOfBots.getText()) - 1 + "");
		}
	}

//  if player has chosen less player than bots, decrease the amount of bots
	@FXML public void checkNumberOfBots() {
		if (players2RadioButton.isSelected() && Integer.parseInt(numberOfBots.getText()) > 2) {
			numberOfBots.setText("2");
		} else if (players3RadioButton.isSelected() && Integer.parseInt(numberOfBots.getText()) > 3) {
			numberOfBots.setText("3");
		} else if (players4RadioButton.isSelected() && Integer.parseInt(numberOfBots.getText()) > 4) {
			numberOfBots.setText("4");
		}
	}
}
