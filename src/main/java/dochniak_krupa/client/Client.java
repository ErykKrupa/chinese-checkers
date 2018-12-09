package dochniak_krupa.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

import static java.lang.System.exit;

public class Client extends Application {

    Socket socket;
    BufferedReader in;
    PrintWriter out;


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

    //Handling server connection and setting input and output buffers
    private void connectToServer() throws IOException{
        socket = new Socket("",9090);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);

        //Printing server initial message
        for(int i=0; i<2; i++){
            String initialMessage = in.readLine();
            System.out.println(initialMessage);
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Client client = new Client();
        try{
            client.connectToServer();
        }catch (IOException e){
            System.out.println("Unable to connect with server!");
            Thread.sleep(3000);
            exit(0);
        }finally {
            try{
                client.in.close();
                client.out.close();
                client.socket.close();
            }catch(IOException e){
                System.out.println("Unable to close streams and socket");
            }
        }
        //Launching client first window
        launch(args);
    }
}
