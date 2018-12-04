package dochniak_krupa.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.lang.System.exit;

public class MenuController {
    static Stage newGamePopupStage = new Stage();

    public void newMultiPlayerGameBtnClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/newGamePopupSample.fxml"));
        newGamePopupStage.setTitle("Game settings");
        newGamePopupStage.setScene(new Scene(root, 300, 400));
        newGamePopupStage.show();
        Main.menuStage.hide();
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
