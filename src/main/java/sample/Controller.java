package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.lang.System.exit;

public class Controller {

    public void newMultiplayerGameBtnClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/newGamePopup.fxml"));
        Stage newGamePopupStage = new Stage();
        newGamePopupStage.setTitle("Game settings");
        newGamePopupStage.setScene(new Scene(root, 300, 400));
        newGamePopupStage.show();
    }

    public void joinGameBtnClick(){
        //TODO send join existing game request to server
    }

    public void startSinglePlayerGameBtnClick(){
        //TODO
    }

    public void exitBtnClick(){
        exit(0);
    }
}
