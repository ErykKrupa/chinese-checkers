package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        probably, we won't need Parent root and sample.fxml
//        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
//        Board extends GridPane, it uses Singleton Pattern
        Board board = Board.getInstance();
        primaryStage.setTitle("Chinese Checkers");
        Scene scene = new Scene(board, 800, 800);
        scene.setFill(Color.web("#99ffff7f")); //translucent light blue
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
