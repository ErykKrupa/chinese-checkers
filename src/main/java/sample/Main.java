package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage menuStage = new Stage();

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/menuSample.fxml"));
        menuStage.setScene(new Scene(root, 600, 400));
        menuStage.setTitle("Chinese Checkers - Menu");
        menuStage.setResizable(false);
        menuStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
