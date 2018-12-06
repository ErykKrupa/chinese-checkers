package dochniak_krupa.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

import static java.lang.System.exit;

public class Client extends Application {

    Socket socket;
    BufferedReader in;
    PrintWriter out;
    //    for Singleton Pattern
    private static Client client = null;
    String clientNumber;

    public boolean isAbleToJoinGame;
    public boolean isHost = false;

    static Stage menuStage = new Stage();
//    sets and shows menu window
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/menuSample.fxml"));
        menuStage.setScene(new Scene(root, 600, 400));
        menuStage.setTitle("Chinese Checkers - Menu");
        menuStage.setResizable(false);
        menuStage.show();
    }

    //Handling server connection and setting input and output buffers
    private void connectToServer() throws IOException{
        socket = new Socket("",9091);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);

        //Printing server initial message
        for(int i=0; i<2; i++){
            String initialMessage = in.readLine();
            System.out.println(initialMessage);
        }

        clientNumber = in.readLine();
    }

    //    Singleton Pattern
    private static void setInstance() {
        client = new Client();
    }

    public static Client getInstance() {
        return client;
    }

    public static void main(String[] args) throws InterruptedException{
        Client.setInstance();
        try{
            client.connectToServer();
        }catch (IOException e){
            System.out.println("Unable to connect with server!");
            Thread.sleep(3000);
            exit(0);
        }
        //Launching client first window
        launch(args);
    }
}
