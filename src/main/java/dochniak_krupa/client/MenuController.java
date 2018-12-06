package dochniak_krupa.client;

import dochniak_krupa.server.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class MenuController {
    static Stage newGamePopupStage = new Stage();

//    sets and shows window for selection number of players
    public void newMultiPlayerGameBtnClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/newGamePopupSample.fxml"));
        newGamePopupStage.setTitle("Game settings");
        newGamePopupStage.setScene(new Scene(root, 300, 400));
        newGamePopupStage.show();
        Client.menuStage.hide();
    }

    public void joinGameBtnClick(){
        Client.getInstance().out.println("JOIN GAME");

        String s = "";
        try{
            s = Client.getInstance().in.readLine();
        }catch (IOException e){
            System.out.println("Błąd");
        }

        if(s.equals("JOIN GAME PRIVILEGE")) {
            Stage boardStage = new Stage();
            Board.setInstance(2);
            Scene scene = new Scene(Board.getInstance(), 800, 800);
            scene.setFill(Color.web("#99ffff7f"));
            boardStage.setScene(scene);
            boardStage.setTitle("Chinese Checkers - Menu");
            boardStage.setResizable(false);
            boardStage.show();
        }
    }

    public void startSinglePlayerGameBtnClick(){
        //TODO
    }

    public void exitBtnClick(){
        exit(0);
    }
}
