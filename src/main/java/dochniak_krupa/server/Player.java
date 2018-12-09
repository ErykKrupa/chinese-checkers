package dochniak_krupa.server;

import java.io.*;
import java.net.Socket;

public class Player extends Thread {
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    int number;

    Player(Socket socket, int number) {
        this.socket = socket;
        this.number = number;
    }

    public void run() {
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);


            //Sending initial message to the client
            output.println("Welcome to the Chinese Checkers server!");
            output.println("You are the player number: " + number);
            //Sending client number
            output.println(number);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Reacting to client response
        try {
            while (true) {
                String command = "";
                command = input.readLine();

                switch (command) {
                    case "CREATE MULTIPLAYER 2": {

                        if (Game.getInstance() == null) {
                            Game.setInstance(2);
                            Game.getInstance().currNumOfPlayers++;
                            output.println("CREATE GAME PRIVILEGE GRANTED");
                        } else {
                            output.println("CREATE GAME PRIVILEGE REVOKED");
                        }
                    }
                    break;
                    case "CREATE MULTIPLAYER 3": {
                        if (Game.getInstance() == null) {
                            Game.setInstance(3);
                            Game.getInstance().currNumOfPlayers++;
                            output.println("CREATE GAME PRIVILEGE GRANTED");
                        } else {
                            output.println("CREATE GAME PRIVILEGE REVOKED");
                        }
                    }
                    break;
                    case "CREATE MULTIPLAYER 4": {
                        if (Game.getInstance() == null) {
                            Game.setInstance(4);
                            Game.getInstance().currNumOfPlayers++;
                            output.println("CREATE GAME PRIVILEGE GRANTED");
                        } else {
                            output.println("CREATE GAME PRIVILEGE REVOKED");
                        }
                    }
                    break;
                    case "CREATE MULTIPLAYER 6": {
                        if (Game.getInstance() == null) {
                            Game.setInstance(6);
                            Game.getInstance().currNumOfPlayers++;
                            output.println("CREATE GAME PRIVILEGE GRANTED");
                        } else {
                            output.println("CREATE GAME PRIVILEGE REVOKED");
                        }
                    }
                    break;
                    case "JOIN GAME": {
                        if (Game.getInstance() != null && Game.getInstance().currNumOfPlayers < Game.getInstance().declaredNumberOfPlayersInGame) {
                            Game.getInstance().currNumOfPlayers++;
                            output.println("JOIN GAME PRIVILEGE GRANTED");

                            //Sending to client type of game
                            output.println(Game.getInstance().declaredNumberOfPlayersInGame);
                        } else {
                            output.println("JOIN GAME PRIVILEGE REVOKED");
                            output.println("0");
                        }
                    }
                    break;
                    case "DO MOVE": {

                        playerMoveHandler();
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to read line!");
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                System.out.println("Unable to close stream!");
            }
        }

    }

    private void playerMoveHandler() {
        //TODO
    }
}
