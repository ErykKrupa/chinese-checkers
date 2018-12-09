package dochniak_krupa.client;

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

        //Sending performed action command to server
        Client.getInstance().out.println("JOIN GAME");

        //Receiving server response
        String privilege = "";
        try{
            privilege = Client.getInstance().in.readLine();
        }catch (IOException e){
            System.out.println("Błąd");
        }

        //Receiving info about current game type to set proper board instance
        //If game is not started the typeOfGame equals 0 and board instance is not initialized
        String typeOfGameStr;
        int typeOfGame = 0;
        try{
            typeOfGameStr = Client.getInstance().in.readLine();
            if(!typeOfGameStr.equals("0"))
            typeOfGame = Integer.parseInt(typeOfGameStr);
        }catch (IOException e){
            System.out.println("Unable to read line");
        }catch(IllegalArgumentException ex){
            System.out.println("Illegal agrument exception");
        }

        //Setting proper board instance
        if(typeOfGame!=0) Board.setInstance(typeOfGame);
        else System.out.println("Unable to join game");

        //Initializing board window after checking privileges for that
        if(privilege.equals("JOIN GAME PRIVILEGE GRANTED") && typeOfGame!=0) {
            Stage boardStage = new Stage();
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
