package dochniak_krupa.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Client extends Application {

    static Stage menuStage = new Stage();
//    sets and shows menu window
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/menuSample.fxml"));
//        color doesn't work
        menuStage.setScene(new Scene(root, 600, 400, Color.WHITE));
        menuStage.setTitle("Chinese Checkers - Menu");
        menuStage.setResizable(false);
        menuStage.show();
    }

    public static void main(String[] args) throws InterruptedException {
        //Launching client first window
        launch(args);
    }
}
