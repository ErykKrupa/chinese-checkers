package dochniak_krupa.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws IOException {

        int playerNumber = 1;
        int playerTurn;

        //Try-with-resources statement, so in the reason of that
        // we don't have to close listener
        try (ServerSocket listener = new ServerSocket(9090)) {
            System.out.println("Chinese checkers server is running");
            while (true) {
                System.out.println("Waiting for the first player connection");
                Player player1 = new Player(listener.accept(), 1);
                player1.start();
                System.out.println("Waiting for the second player connection");
                Player player2 = new Player(listener.accept(), 2);
                player2.start();
                System.out.println("Waiting for the third player connection");
                Player player3 = new Player(listener.accept(), 3);
                player3.start();
                System.out.println("Waiting for the fourth player connection");
                Player player4 = new Player(listener.accept(), 4);
                player4.start();
                System.out.println("Waiting for the fifth player connection");
                Player player5 = new Player(listener.accept(), 5);
                player5.start();
                System.out.println("Waiting for the sixth player connection");
                Player player6 = new Player(listener.accept(), 6);
                player6.start();
            }
        } catch (IOException e) {
            System.out.println("Unable to connect players");
        }
    }
}
