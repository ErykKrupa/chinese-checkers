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
                Player player1 = new Player(listener.accept(), 1);
                Player player2 = new Player(listener.accept(), 2);
                Player player3 = new Player(listener.accept(), 3);
                Player player4 = new Player(listener.accept(), 4);
                Player player5 = new Player(listener.accept(), 5);
                Player player6 = new Player(listener.accept(), 6);
            }
        } catch (IOException e) {
            System.out.println("Unable to connect players");
        }
    }
}
