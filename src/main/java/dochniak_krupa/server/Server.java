package dochniak_krupa.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args){

        //class for staring current info about every client connection
        Connection conn = new Connection();

        //class in which arrayList all created PlayerHandler objects are stored
        //in order to use their PrintWriters
        PlayerHandlers.setInstance();

        //Try-with-resources statement, so in the reason of that
        // we don't have to close listener
        try (ServerSocket listener = new ServerSocket(9090)) {
            System.out.println("Chinese checkers server is running");
            while (true) {
                if(!conn.clientsConectedToServer[0]) {
                    System.out.println("Waiting for the first player connection");
                    PlayerHandler player1 = new PlayerHandler(listener.accept(), 1);
                    player1.start();
                    conn.clientsConectedToServer[0] = true;
                    PlayerHandlers.getInstance().playerHandlersList.add(player1);
                }
                if(!conn.clientsConectedToServer[1]) {
                    System.out.println("Waiting for the second player connection");
                    PlayerHandler player2 = new PlayerHandler(listener.accept(), 2);
                    player2.start();
                    conn.clientsConectedToServer[1] = true;
                    PlayerHandlers.getInstance().playerHandlersList.add(player2);
                }
                if(!conn.clientsConectedToServer[2]) {
                    System.out.println("Waiting for the third player connection");
                    PlayerHandler player3 = new PlayerHandler(listener.accept(), 3);
                    player3.start();
                    conn.clientsConectedToServer[2] = true;
                    PlayerHandlers.getInstance().playerHandlersList.add(player3);
                }
                if(!conn.clientsConectedToServer[3]) {
                    System.out.println("Waiting for the fourth player connection");
                    PlayerHandler player4 = new PlayerHandler(listener.accept(), 4);
                    player4.start();
                    conn.clientsConectedToServer[3] = true;
                    PlayerHandlers.getInstance().playerHandlersList.add(player4);
                }
                if(!conn.clientsConectedToServer[4]) {
                    System.out.println("Waiting for the fifth player connection");
                    PlayerHandler player5 = new PlayerHandler(listener.accept(), 5);
                    player5.start();
                    conn.clientsConectedToServer[4] = true;
                    PlayerHandlers.getInstance().playerHandlersList.add(player5);
                }
                if(!conn.clientsConectedToServer[5]) {
                    System.out.println("Waiting for the sixth player connection");
                    PlayerHandler player6 = new PlayerHandler(listener.accept(), 6);
                    player6.start();
                    conn.clientsConectedToServer[5] = true;
                    PlayerHandlers.getInstance().playerHandlersList.add(player6);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to connect players");
        }
    }
}
