package dochniak_krupa.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class PlayerHandler extends Thread {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private boolean isHost;
    private boolean isInGame;

    //private ArrayList<Bot> bots = new ArrayList<>();

    //client number sending to client after connection
    private int clientNumber;

    //player number of client in game
    private int playerNumber;

    //	  starting position of pawn
    private Field startingField = null;

    //	  player's field which is currently clicked
    private Field currentField = null;

    //    neutral field which is target for player's pawn
    private Field targetField = null;

    //    true, if pawn has already gone in this turn
    private boolean went = false;

    //    true, if pawn has already jumped in this turn
    private boolean jumped = false;

    PlayerHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    public void run() {
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            //Sending initial message to the client
            output.println("Welcome to the Chinese Checkers server!");
            output.println("You are the client number: " + clientNumber);
            //Sending client number
            output.println(clientNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Reacting to client response
        try {
            while (true) {
                String command =  input.readLine();

                switch (command) {
                    case "CREATE MULTIPLAYER 2": {
                        if (Game.getInstance() == null) {
                            Game.setInstance(2);
                            Board.setInstance(2);
                            Game.getInstance().currNumOfPlayers++;
                            Game.getInstance().setIsClientInGame(clientNumber,true);
                            playerNumber = 1;
                            isHost=true;
                            //sending game create permission to it's host
                            output.println("CREATE GAME PRIVILEGE GRANTED");

                        } else {
                            output.println("CREATE GAME PRIVILEGE REVOKED");
                        }
                    }
                    break;
                    case "CREATE MULTIPLAYER 3": {
                        if (Game.getInstance() == null) {
                            Game.setInstance(3);
                            Board.setInstance(3);
                            Game.getInstance().currNumOfPlayers++;
                            Game.getInstance().setIsClientInGame(clientNumber,true);
                            playerNumber = 1;
                            isHost=true;
                            //sending game create permission to it's host
                            output.println("CREATE GAME PRIVILEGE GRANTED");
                        } else {
                            output.println("CREATE GAME PRIVILEGE REVOKED");
                        }
                    }
                    break;
                    case "CREATE MULTIPLAYER 4": {
                        if (Game.getInstance() == null) {
                            Game.setInstance(4);
                            Board.setInstance(4);
                            Game.getInstance().setIsClientInGame(clientNumber,true);
                            playerNumber = 2;
                            isHost=true;
                            //sending game create permission to it's host
                            output.println("CREATE GAME PRIVILEGE GRANTED");
                        } else {
                            output.println("CREATE GAME PRIVILEGE REVOKED");
                        }

                        Game.getInstance().currNumOfPlayers++;
                    }
                    break;
                    case "CREATE MULTIPLAYER 6": {
                        if (Game.getInstance() == null) {
                            Game.setInstance(6);
                            Board.setInstance(6);
                            Game.getInstance().currNumOfPlayers++;
                            Game.getInstance().setIsClientInGame(clientNumber,true);
                            playerNumber = 1;
                            isHost=true;
                            //sending game create permission to it's host
                            output.println("CREATE GAME PRIVILEGE GRANTED");
                        } else {
                            output.println("CREATE GAME PRIVILEGE REVOKED");
                        }
                    }
                    break;
                    case "JOIN GAME": {
                        Game g = Game.getInstance();
                        if (g != null && g.currNumOfPlayers < g.declaredNumberOfPlayersInGame) {
                            g.setIsClientInGame(clientNumber,true);

                            //sending privilege for game joining to client
                            output.println("JOIN GAME PRIVILEGE GRANTED");

                            switch(g.declaredNumberOfPlayersInGame) {
                                case 2: playerNumber = 4; break;
                                case 3: {
                                    switch (g.currNumOfPlayers) {
                                        case 1: playerNumber = 3; break;
                                        case 2: playerNumber = 5; break;
                                    }
                                } break;
                                case 4: {
                                    switch (g.currNumOfPlayers) {
                                        case 1: playerNumber = 3; break;
                                        case 2: playerNumber = 5; break;
                                        case 3: playerNumber = 6; break;
                                    }
                                } break;
                                case 6:{
                                    playerNumber = g.currNumOfPlayers + 1;
                                } break;
                            }

                            g.currNumOfPlayers++;

                            //Game starts, host's turn
                            if(g.currNumOfPlayers==g.declaredNumberOfPlayersInGame){
                                for(int j=0; j<g.currNumOfPlayers; j++) {
                                    if (PlayerHandlers.playerHandlersList.get(j).isHost) {
                                        PlayerHandlers.playerHandlersList.get(j).output.println("START GAME");
                                        break;
                                    }
                                }
                            }
                                PlayerHandlers.playerHandlersList.get(0).output.println("START GAME");

                            //Sending to client type of game
                            output.println(g.declaredNumberOfPlayersInGame);
                        } else {
                            output.println("JOIN GAME PRIVILEGE REVOKED");
                            output.println("0");
                        }
                    }
                    break;
                    case "GAME WITH BOTS": {
                        prepareBots();
                    } break;
                    case "DO MOVE": playerMoveHandler(); break;
                    case "END TURN": endTurn(); break;
                    case "CLIENT EXITED THE GAME": onClientExitActionPerform(); break;
                    case "HOST EXITED THE GAME": onHostExitActionPerform(); break;
                    case "DOES GAME EXIST": {
                        if(Game.getInstance()==null)
                            output.println("GAME DOESNT EXIST");
                        else
                            output.println("GAME ALREADY EXISTS");
                    } break;
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to read line!");
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                System.out.println("Unable to close stream!");
            }
        }
    }

    private void playerMoveHandler() {
        String sx = "";
        String sy = "";

        try {
            sx = input.readLine();
            sy = input.readLine();
        } catch (IOException e) {
            System.out.println("Unable to read line");
        }

        int x = -1, y = -1;
        try {
            x = Integer.parseInt(sx);
            y = Integer.parseInt(sy);
        } catch (NumberFormatException e) {
            System.out.println("NFException");
        }

        Field field;
        if (x != -1 && y != -1) {
            field = Board.getInstance().getField(x, y);
            //    on mouse click for field without pawn
            if (field.getPawn() == 0) {
                targetField = field;
                //if player hasn't chosen his pawn yet
                if (currentField == null) {
                    System.out.println("Didn't choose");
                    output.println("PAWN NOT CHOSEN");
                    return;
                }
                //check if it is starting field of current pawn
                if (targetField == startingField) {
                    go();
                    System.out.println("Return");
                    went = false;
                    jumped = false;
                    return;
                }
                //check if pawn is in its own base and it tries to go outside
                if (!(currentField.getPawn() == currentField.getBase() && targetField.getBase() == 0)) {
                    //check if pawn can go on this field
                    if ((Math.abs(currentField.getX() - targetField.getX()) == 1 &&
                            Math.abs(currentField.getY() - targetField.getY()) == 1 ||
                            Math.abs(currentField.getX() - targetField.getX()) == 2 &&
                                    currentField.getY() - targetField.getY() == 0) && !jumped && !went) {
                        go();
                        System.out.println("I'm going " + targetField.getPawn());
                        went = true;
                    }
                    //check if pawn can jump on this field
                    else if (isAbleToJump() && !went) {
                        go();
                        System.out.println("I'm jumping " + targetField.getPawn());
                        jumped = true;
                    } else {
                        System.out.println("I can't go there");
                        output.println("CANT GO THERE");
                    }
                } else {
                    System.out.println("I can't go there");
                    output.println("CANT GO THERE");
                }
            }
            //on mouse click for field with pawn
            else {
                //player can choose only his pawn and only,
                //when he didn't choose pawn in this turn yet,
                //or didn't make a move
                if (field.getPawn() == playerNumber &&
                        (startingField == null || startingField.getPawn() == Game.getInstance().getPlayerTurn())) {
                    startingField = field;
                    currentField = field;
                    System.out.println("Chose " + currentField.getPawn());
                    output.println("CHOSEN");
                } else {
                    System.out.println("I can't choose that");
                    output.println("CANT CHOSE");
                }
            }
        }
    }

    private void prepareBots(){
        if(Game.getInstance() != null){
            try {
                String s = input.readLine();
                Game.currentNumberOfBots = Integer.parseInt(s);
            }catch (IOException e){
                System.out.println("Unable to read!");
            }
        }
    }

    private void onClientExitActionPerform(){
        isInGame = false;
        Game.getInstance().setIsClientInGame(clientNumber,false);
        output.println("YOU EXITED");
    }

    private void onHostExitActionPerform(){
        for(int i=0; i<Game.getInstance().currNumOfPlayers; i++) {
            if(Game.getInstance().getIsClientInGame(i+1)) {
                //sending communicates to all clients in game
                PlayerHandlers.playerHandlersList.get(i).output.println("HOST EXITED");
            }
        }
        Game.getInstance().deleteInstance();
        Board.getInstance().deleteInstance();
    }

    //	end turn for this player
    private void endTurn() {
        //reference just to make if statements a little bit cleaner
        Game g = Game.getInstance();

        if (currentField != null
                && currentField.getBase() == g.getPlayerTurn()
                && startingField != null
                && startingField.getBase() != g.getPlayerTurn()) {
            Player.getPlayer(g.getPlayerTurn()).reachTarget();
        }
        startingField = null;
        currentField = null;
        targetField = null;
        jumped = false;
        went = false;
        System.out.println("End turn");

        int previousPlayerTurn = Game.getInstance().getPlayerTurn();

//		if player isn't in game- ignore his turn
        do {
            if (g.getPlayerTurn() == 6) {
                g.setPlayerTurn(1);
            } else {
                g.setPlayerTurn(g.getPlayerTurn()+1);
            }
        } while (!Player.getPlayer(g.getPlayerTurn()).isInGame());

                //sending communicates to all clients in game
        for(int j=0; j<g.currNumOfPlayers; j++) {
            if (g.getPlayerTurn() == PlayerHandlers.playerHandlersList.get(j).playerNumber) {
                PlayerHandlers.playerHandlersList.get(j).output.println("YOUR TURN NOW");
            }
            if (previousPlayerTurn == PlayerHandlers.playerHandlersList.get(j).playerNumber) {
                PlayerHandlers.playerHandlersList.get(j).output.println("END OF YOUR TURN");
            }
        }
    }

    //	push pawn on target field
    private void go() {

        for(int i=0; i<Game.getInstance().declaredNumberOfPlayersInGame; i++) {
            if(Game.getInstance().getIsClientInGame(i+1)) {
                //sending communicates to all clients in game
                PlayerHandlers.playerHandlersList.get(i).output.println("GO");
                PlayerHandlers.playerHandlersList.get(i).output.println(targetField.getX());
                PlayerHandlers.playerHandlersList.get(i).output.println(targetField.getY());
                PlayerHandlers.playerHandlersList.get(i).output.println(currentField.getX());
                PlayerHandlers.playerHandlersList.get(i).output.println(currentField.getY());
            }
        }

        //server-side Board update
        targetField.setPawn(currentField.getPawn());
        currentField.setPawn(0);
        currentField = targetField;
    }

    //	long condition to check the jumping ability
    private boolean isAbleToJump () {
        int deltaX = targetField.getX() - currentField.getX();
        int deltaY = targetField.getY() - currentField.getY();
        if (deltaY == -2 && (deltaX == 2 || deltaX == -2) ||
                deltaY == 0 && (deltaX == -4 || deltaX == 4) ||
                deltaY == 2 && (deltaX == 2 || deltaX == -2)) {
            return (Board.getInstance().getField((currentField.getX() + deltaX / 2),
                    (currentField.getY() + deltaY / 2)).getPawn() != 0);
        }
        return false;
    }
}
