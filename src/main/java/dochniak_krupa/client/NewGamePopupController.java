package dochniak_krupa.client;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NewGamePopupController {

	@FXML public void newMultiPlayerGameBtnClick() {
		Stage boardStage = new Stage();
		Board board = Board.getInstance();
        Scene scene = new Scene(board, 800, 800);
        scene.setFill(Color.web("#99ffff7f"));
        boardStage.setScene(scene);
		boardStage.setTitle("Chinese Checkers - Menu");
		boardStage.setResizable(false);
		boardStage.show();
		MenuController.newGamePopupStage.hide();
	}
}
