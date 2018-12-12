package dochniak_krupa.server;

import java.io.*;
import java.net.Socket;

public class PlayerHandler extends Thread {
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    int number;

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

    //	  player turn counter
    //int playerTurn = (int) (Math.random() * 6 + 1);
    int playerTurn = 1;

    PlayerHandler(Socket socket, int number) {
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
                            Board.setInstance(2);
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
                            Board.setInstance(3);
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
                            Board.setInstance(4);
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
                            Board.setInstance(6);
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
                    case "END TURN": {
                        endTurn();
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
                if (field.getPawn() == playerTurn &&
                        (startingField == null || startingField.getPawn() == playerTurn)) {
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

    //	end turn for this player
    void endTurn() {
        if (currentField != null && currentField.getBase() == playerTurn &&
                startingField != null && startingField.getBase() != playerTurn) {
            Player.getPlayer(playerTurn).reachTarget();
        }
        startingField = null;
        currentField = null;
        targetField = null;
        jumped = false;
        went = false;
        System.out.println("End turn");
//		if player isn't in game- ignore his turn
        do {
            if (playerTurn == 6) {
                playerTurn = 1;
            } else {
                playerTurn++;
            }
        } while (!(Player.getPlayer(playerTurn).isInGame()));
    }

    //	push pawn on target field
    private void go() {
        //saving coordinates that must be sent to all connected client
        //except the one which has it's turn now
        Board b = Board.getInstance();
        b.setWasModified(true);
        b.setTargetX(targetField.getX());
        b.setTargetY(targetField.getY());
        b.setCurrentX(currentField.getX());
        b.setCurrentY(currentField.getY());

        //sending communicate to client which has it's turn now
        output.println("GO");
        //sending target field x
        output.println(targetField.getX());
        //sending target field y
        output.println(targetField.getY());
        //sending current field x
        output.println(currentField.getX());
        //sending current field y
        output.println(currentField.getY());

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
