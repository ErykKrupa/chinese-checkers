package dochniak_krupa.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args){

        //class for staring current info about every client connection
        Connection conn = new Connection();

        //Starting thread that sends to every client all needed coordinates for client-side Board update
        BoardChangeInfoSender BCIS = new BoardChangeInfoSender();
        //BCIS.start();

        //Try-with-resources statement, so in the reason of that
        // we don't have to close listener
        try (ServerSocket listener = new ServerSocket(9090)) {
            System.out.println("Chinese checkers server is running");
            while (true) {
                if(!conn.playerConectedToServer[0]) {
                    System.out.println("Waiting for the first player connection");
                    PlayerHandler player1 = new PlayerHandler(listener.accept(), 1);
                    player1.start();
                    conn.playerConectedToServer[0] = true;
                    BCIS.playerHandlersList.add(player1);
                }
                if(!conn.playerConectedToServer[1]) {
                    System.out.println("Waiting for the second player connection");
                    PlayerHandler player2 = new PlayerHandler(listener.accept(), 2);
                    player2.start();
                    conn.playerConectedToServer[1] = true;
                    BCIS.playerHandlersList.add(player2);
                }
                if(!conn.playerConectedToServer[2]) {
                    System.out.println("Waiting for the third player connection");
                    PlayerHandler player3 = new PlayerHandler(listener.accept(), 3);
                    player3.start();
                    conn.playerConectedToServer[2] = true;
                    BCIS.playerHandlersList.add(player3);
                }
                if(!conn.playerConectedToServer[3]) {
                    System.out.println("Waiting for the fiourth player connection");
                    PlayerHandler player4 = new PlayerHandler(listener.accept(), 4);
                    player4.start();
                    conn.playerConectedToServer[3] = true;
                    BCIS.playerHandlersList.add(player4);
                }
                if(!conn.playerConectedToServer[4]) {
                    System.out.println("Waiting for the fifth player connection");
                    PlayerHandler player5 = new PlayerHandler(listener.accept(), 5);
                    player5.start();
                    conn.playerConectedToServer[4] = true;
                    BCIS.playerHandlersList.add(player5);
                }
                if(!conn.playerConectedToServer[5]) {
                    System.out.println("Waiting for the sixth player connection");
                    PlayerHandler player6 = new PlayerHandler(listener.accept(), 6);
                    player6.start();
                    conn.playerConectedToServer[5] = true;
                    BCIS.playerHandlersList.add(player6);
                }
                Board.getInstance().setWasModified(false);
            }
        } catch (IOException e) {
            System.out.println("Unable to connect players");
        }
    }
}
