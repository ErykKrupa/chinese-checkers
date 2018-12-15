package dochniak_krupa.client;

import dochniak_krupa.server.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.beans.EventHandler;
import java.io.IOException;
import java.lang.reflect.GenericArrayType;

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
		} else if (players6RadioButton.isSelected()) {
			Board.setInstance(6);
			//Sending action message to server
			Client.getInstance().out.println("CREATE MULTIPLAYER 6");
		}

		//GameController.getInstance().createBots(Integer.parseInt(numberOfBots.getText()));

		//Reading response message form server
		String privilege = "";
		try{
			privilege = Client.getInstance().in.readLine();
		}catch (IOException e){
			System.out.println("Unable to read line");
		}

		//Initializing board window after checking privileges for that
		if (privilege.equals("CREATE GAME PRIVILEGE GRANTED")) {
			//		end turn for confidence that first player who will get turn is in the game
			//GameController.getInstance().endTurn();
			Board.getInstance().setAlignment(Pos.CENTER);
			Scene scene = new Scene(Board.getInstance(), 750, 800);

//		why doesn't it work?
			scene.setFill(Color.web("#99ffff7f")); //translucent light blue

			GameController.boardStage.setScene(scene);
			GameController.boardStage.setTitle("Chinese Checkers");
			GameController.boardStage.setResizable(false);
			GameController.boardStage.show();
			MenuController.newGamePopupStage.hide();

			GameController.boardStage.setOnCloseRequest(e -> {
				Client.menuStage.show();
				Client.getInstance().out.println("HOST EXITED THE GAME");
			});

			//false till all players will connect
			Player.getInstance().setPlayerTurnNow(false);

			//player was connected successfully
			Player.getInstance().setReadyForGame(true);

//		set end turn under space key
			scene.getAccelerators().put(new KeyCodeCombination(KeyCode.SPACE), ()-> GameController.getInstance().endTurn());
		}
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
