package dochniak_krupa.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args){

        //class for storing info about every client connection
        Connection conn = new Connection();

        //Try-with-resources statement, so in the reason of that
        // we don't have to close listener
        try (ServerSocket listener = new ServerSocket(9090)) {
            System.out.println("Chinese checkers server is running");
            while (true) {
                for(int i=1; i<=conn.clientsConnectedToServer.length; i++){
                    //Don't allow to override connection
                    if(!conn.clientsConnectedToServer[i-1]) {
                        System.out.println("Waiting for the " + i + " client connection");
                        PlayerHandler player = new PlayerHandler(listener.accept(), i);
                        player.start();
                        conn.clientsConnectedToServer[i-1] = true;
                        PlayerHandlers.playerHandlersList.add(player);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to connect players");
        }
    }
}
