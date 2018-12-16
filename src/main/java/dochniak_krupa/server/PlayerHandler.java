package dochniak_krupa.server;

import java.io.*;
import java.net.Socket;

public class PlayerHandler extends Thread {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private boolean isHost;

    //client number sending to client after connection
    private int clientNumber;

    //player number of client in game
    private int playerNumber;

    private int hostPlayerNumber;

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
                            hostPlayerNumber = 1;
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
                            hostPlayerNumber = 1;
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
                            hostPlayerNumber = 2;
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
                            hostPlayerNumber = 1;
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
                            hostPlayerNumber = 1;

                            switch(g.declaredNumberOfPlayersInGame) {
                                case 2: playerNumber = 4; break;
                                case 3: {
                                    switch (g.currNumOfPlayers) {
                                        case 1: playerNumber = 3; break;
                                        case 2: playerNumber = 5; break;
                                    }
                                } break;
                                case 4: {
                                    hostPlayerNumber=4;
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
                                for(int j=0; j<PlayerHandlers.playerHandlersList.size(); j++) {
                                    if (PlayerHandlers.playerHandlersList.get(j).isHost) {
                                        PlayerHandlers.playerHandlersList.get(j).output.println("START GAME");
                                        break;
                                    }
                                }
                            }

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
                    case "DO MOVE": playerMoveHandler(null); break;
                    case "END TURN": endTurn(); break;
                    case "CLIENT EXITED THE GAME": onClientExitActionPerform(); break;
                    case "HOST EXITED THE GAME": onHostExitActionPerform(); break;
                    case "SET BOARD": setClientBoard(); break;
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

    private void prepareBots(){
        if(Game.getInstance() != null){
            try {
                String s = input.readLine();
                Game.currentNumberOfBots = Integer.parseInt(s);
                Game g = Game.getInstance();
                Bot b = null;

                /*if(Game.getInstance().declaredNumberOfPlayersInGame == Game.currentNumberOfBots){
                    hostPlayerNumber=-1;
                    isHost=false;
                    b = new Bot(1,this);
                }*/

                for(int i=0; i<Game.currentNumberOfBots; i++) {

                    switch (g.declaredNumberOfPlayersInGame) {
                        case 2: {
                            b = new Bot(4, this);
                            Game.getInstance().setIsPlayerBot(4, true);
                        }break;
                        case 3: {
                            switch (Game.getInstance().currNumOfPlayers) {
                                case 1: {
                                    b = new Bot(3, this);
                                    Game.getInstance().setIsPlayerBot(3,true);
                                }break;
                                case 2: {
                                    b = new Bot(5, this);
                                    Game.getInstance().setIsPlayerBot(5, true);
                                }break;
                            }
                        }
                        break;
                        case 4: {
                            switch (g.currNumOfPlayers) {
                                case 1: {
                                    b = new Bot(3, this);
                                    Game.getInstance().setIsPlayerBot(3, true);
                                }break;
                                case 2: {
                                    b = new Bot(5, this);
                                    Game.getInstance().setIsPlayerBot(5, true);
                                } break;
                                case 3: {
                                    b = new Bot(6, this);
                                    Game.getInstance().setIsPlayerBot(6, true);
                                } break;
                            }
                        }
                        break;
                        case 6: {
                            int n = Game.getInstance().currNumOfPlayers + 1;
                            b = new Bot(n, this);
                            Game.getInstance().setIsPlayerBot(n,true);
                        } break;
                    }
                    if (b != null) {
                        Thread t = new Thread(b);
                        t.start();
                    }
                    Game.getInstance().currNumOfPlayers++;
                    //Game starts, host's turn
                    if(g.currNumOfPlayers==g.declaredNumberOfPlayersInGame){
                        for(int j=0; j<PlayerHandlers.playerHandlersList.size(); j++) {
                            if (PlayerHandlers.playerHandlersList.get(j).isHost) {
                                PlayerHandlers.playerHandlersList.get(j).output.println("START GAME");
                                System.out.println("player num: "+ playerNumber);//
                                System.out.println(g.getPlayerTurn());//
                                break;
                            }
                        }
                    }
                }

            }catch (IOException e){
                System.out.println("Unable to read!");
            }
        }
    }

    private void onClientExitActionPerform(){
        Game.getInstance().setIsClientInGame(clientNumber,false);
        output.println("YOU EXITED");
        Bot b = new Bot(playerNumber,this);
        Player.bots.add(b);
        Thread t = new Thread(b);
        t.start();
    }

    private void onHostExitActionPerform(){
        for(int i=0; i<Game.getInstance().currNumOfPlayers; i++) {
            if(Game.getInstance().getIsClientInGame(i+1)) {
                //sending communicates to all clients in game
                PlayerHandlers.playerHandlersList.get(i).output.println("HOST EXITED");
            }
        }
        for(Bot bot:Player.bots){
            bot.terminate();
        }
        Player.bots.clear();

        for(int i=0; i<Player.players.length; i++) Player.players[i].setInGame(false);
        Game.getInstance().deleteInstance();
        Board.getInstance().deleteInstance();
    }

    private void setClientBoard(){
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 17; j++) {
                if(Board.getInstance().getField(i,j)!=null){
                    output.println(Board.getInstance().getField(i,j).getPawn());
                    output.println(Board.getInstance().getField(i,j).getBase());
                }else {
                    output.println("no");
                    output.println("no");
                }
            }
        }
    }

    void playerMoveHandler(Field field) {
        int x = -1, y = -1;
        int wnull=0;
        if(field==null){
            wnull=1;

            String sx = "";
            String sy = "";

            try {
                sx = input.readLine();
                sy = input.readLine();
            } catch (IOException e) {
                System.out.println("Unable to read line");
            }


            try {
                x = Integer.parseInt(sx);
                y = Integer.parseInt(sy);
            } catch (NumberFormatException e) {
                System.out.println("NFException");
            }
        }else{
            x=field.getX();
            y=field.getY();
        }

        if (x != -1 && y != -1) {
            field = Board.getInstance().getField(x, y);
            //    on mouse click for field without pawn
            if (field.getPawn() == 0) {
                targetField = field;
                //if player hasn't chosen his pawn yet
                if (currentField == null) {
                    System.out.println("Didn't choose");
                    if(wnull==1)output.println("PAWN NOT CHOSEN");
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
                        if(wnull==1)output.println("CANT GO THERE");
                    }
                } else {
                    System.out.println("I can't go there");
                    if(wnull==1)output.println("CANT GO THERE");
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
                    if(wnull==1)output.println("CHOSEN");
                } else {
                    System.out.println("I can't choose that");
                    if(wnull==1)output.println("CANT CHOSE");
                }
            }
        }
    }

    //	end turn for this player
    void endTurn() {
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

        if(g.getIsPlayerBot(g.getPlayerTurn())){
            for(int j=0; j<PlayerHandlers.playerHandlersList.size(); j++) {
                if (PlayerHandlers.playerHandlersList.get(j).isHost) {
                    PlayerHandlers.playerHandlersList.get(j).output.println("END OF YOUR TURN");
                    PlayerHandlers.playerHandlersList.get(j).playerNumber = g.getPlayerTurn();
                    break;
                }
            }
        }else if(hostPlayerNumber==g.getPlayerTurn()){
            for(int j=0; j<PlayerHandlers.playerHandlersList.size(); j++) {
                if (PlayerHandlers.playerHandlersList.get(j).isHost) {
                    PlayerHandlers.playerHandlersList.get(j).output.println("YOUR TURN NOW");
                    PlayerHandlers.playerHandlersList.get(j).playerNumber = g.getPlayerTurn();
                    break;
                }
            }
        }

        //sending communicates to all clients in game
        for(int j=0; j<PlayerHandlers.playerHandlersList.size(); j++) {
            if(!g.getIsPlayerBot(g.getPlayerTurn()))
                if (g.getPlayerTurn() == PlayerHandlers.playerHandlersList.get(j).playerNumber) {
                    PlayerHandlers.playerHandlersList.get(j).output.println("YOUR TURN NOW");
                }
            if(!g.getIsPlayerBot(previousPlayerTurn))
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
