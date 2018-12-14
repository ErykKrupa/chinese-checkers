package dochniak_krupa.server;

import java.util.ArrayList;

//thread that sends to every client all needed coordinates for client-side Board update
public class BoardChangeInfoSender {//extends Thread{
    //ArrayList that stores all playerHandler objects
    //This approach allows us to use their PrintWriters and send message to every connected client.
    ArrayList<PlayerHandler> playerHandlersList;

    //Singleton
    private static volatile BoardChangeInfoSender BCIS;

    BoardChangeInfoSender(){
        playerHandlersList = new ArrayList<>();
    }

    //    Singleton Pattern
    static synchronized void setInstance() {
        BCIS = new BoardChangeInfoSender();
    }

    public static synchronized BoardChangeInfoSender getInstance() {
        return BCIS;
    }

    /*public void run(){
        while(true){
            if(Board.getInstance()!=null){

                //sending info about turn change to proper client TO TEST!!//
                if(Game.getInstance().isTurnChanged()){
                    int i = Game.getInstance().getPlayerTurn();
                    playerHandlersList.get(i-1).output.println("YOUR TURN");
                    Game.getInstance().setTurnChanged(false);
                }else{
                        //sending coordinates to all clients except the one
                        //which has it's turn now
                    if(Board.getInstance().getWasModified()){
                        Board b = Board.getInstance();
                        if( Game.getInstance().getPlayerTurn()!=1
                                && Game.getInstance().getPlayerTurn()!=-1
                                && Game.getInstance().getIsPlayerInGame(1)){
                            PlayerHandler p0 = playerHandlersList.get(0);
                            p0.output.println("OTHER PLAYER MOVED");
                            p0.output.println(b.getTargetX());
                            p0.output.println(b.getTargetY());
                            p0.output.println(b.getCurrentX());
                            p0.output.println(b.getCurrentY());
                        }

                        if( Game.getInstance().getPlayerTurn()!=2
                                && Game.getInstance().getPlayerTurn()!=-1
                                && Game.getInstance().getIsPlayerInGame(2)){
                            PlayerHandler p1 = playerHandlersList.get(1);
                            p1.output.println("OTHER PLAYER MOVED");
                            p1.output.println(b.getTargetX());
                            p1.output.println(b.getTargetY());
                            p1.output.println(b.getCurrentX());
                            p1.output.println(b.getCurrentY());
                        }

                        if( Game.getInstance().getPlayerTurn()!=3
                                && Game.getInstance().getPlayerTurn()!=-1
                                && Game.getInstance().getIsPlayerInGame(3)){
                            PlayerHandler p1 = playerHandlersList.get(2);
                            p1.output.println("OTHER PLAYER MOVED");
                            p1.output.println(b.getTargetX());
                            p1.output.println(b.getTargetY());
                            p1.output.println(b.getCurrentX());
                            p1.output.println(b.getCurrentY());
                        }

                        if( Game.getInstance().getPlayerTurn()!=4
                                && Game.getInstance().getPlayerTurn()!=-1
                                && Game.getInstance().getIsPlayerInGame(4)){
                            PlayerHandler p1 = playerHandlersList.get(3);
                            p1.output.println("OTHER PLAYER MOVED");
                            p1.output.println(b.getTargetX());
                            p1.output.println(b.getTargetY());
                            p1.output.println(b.getCurrentX());
                            p1.output.println(b.getCurrentY());
                        }

                        if( Game.getInstance().getPlayerTurn()!=5
                                && Game.getInstance().getPlayerTurn()!=-1
                                && Game.getInstance().getIsPlayerInGame(5)){
                            PlayerHandler p1 = playerHandlersList.get(4);
                            p1.output.println("OTHER PLAYER MOVED");
                            p1.output.println(b.getTargetX());
                            p1.output.println(b.getTargetY());
                            p1.output.println(b.getCurrentX());
                            p1.output.println(b.getCurrentY());
                        }

                        if( Game.getInstance().getPlayerTurn()!=6
                                && Game.getInstance().getPlayerTurn()!=-1
                                && Game.getInstance().getIsPlayerInGame(6)){
                            PlayerHandler p1 = playerHandlersList.get(5);
                            p1.output.println("OTHER PLAYER MOVED");
                            p1.output.println(b.getTargetX());
                            p1.output.println(b.getTargetY());
                            p1.output.println(b.getCurrentX());
                            p1.output.println(b.getCurrentY());
                        }
                        Board.getInstance().setWasModified(false);
                    }
                }
            }
        }
    }*/
}
