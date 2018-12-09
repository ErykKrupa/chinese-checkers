package dochniak_krupa.client;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NewGamePopupController {

	@FXML private RadioButton players2RadioButton;
	@FXML private RadioButton players3RadioButton;
	@FXML private RadioButton players4RadioButton;
	@FXML private RadioButton players6RadioButton;

	@FXML public void newMultiPlayerGameBtnClick() {
//		create appropriate board and fields for players numbers
		if (players2RadioButton.isSelected()) {
			Board.setInstance(2);
		} else if (players3RadioButton.isSelected()) {
			Board.setInstance(3);
		} else if (players4RadioButton.isSelected()) {
			Board.setInstance(4);
		} else if (players6RadioButton.isSelected()) {
			Board.setInstance(6);
		}
		GameController.getInstance().endTurn();
		Board.getInstance().setAlignment(Pos.CENTER);
		Scene scene = new Scene(Board.getInstance(), 750, 800);

//		why doesn't it work?
		scene.setFill(Color.web("#99ffff7f")); //translucent light blue

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
}
