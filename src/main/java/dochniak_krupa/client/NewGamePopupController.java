package dochniak_krupa.client;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
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
		} else {
			Board.setInstance(6);
		}
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
