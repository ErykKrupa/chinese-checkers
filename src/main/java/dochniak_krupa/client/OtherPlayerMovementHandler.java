package dochniak_krupa.client;

import javafx.application.Platform;

import java.io.IOException;

//Thread which checks if board was modified by other player.
//It reads server messages only if it's other player turn now.
public class OtherPlayerMovementHandler extends Thread{
         //Reacting to client response
        public void run(){
            try {
                while (true) {
                    if(Player.getInstance().isReadyForGame()) {
                        String command = Client.getInstance().in.readLine();

                        switch (command) {
                            //getting info from server that now is your turn
                            case  "START GAME": {
                                Player.getInstance().setPlayerTurnNow(true);
                            } break;
                            case "PAWN NOT CHOSEN": {
                                System.out.println("Didn't choose pawn");
                            }
                            break;
                            case "GO":
                                goActionPerform();
                                break;
                            case "CHOSEN":
                                System.out.println("CHOSEN");
                                break;
                            case "YOUR TURN NOW": {
                                System.out.println("Your turn now");
                                Player.getInstance().setPlayerTurnNow(true);
                            } break;
                            case "END OF YOUR TURN":{
                                System.out.println("Your turn've ended");
                                Player.getInstance().setPlayerTurnNow(false);
                            } break;
                            case "HOST EXITED":{
                                Player.getInstance().setPlayerTurnNow(false);
                                Player.getInstance().setReadyForGame(false);
                                Board.deleteInstance();
                                Platform.runLater(()->{
                                    GameController.boardStage.close();
                                    Client.menuStage.show();
                                });
                                System.out.println("GOT BACK TO MENU, GAME'VE ENDED");
                            } break;
                            case "YOU EXITED":{
                                Player.getInstance().setPlayerTurnNow(false);
                                Player.getInstance().setReadyForGame(false);
                                Board.deleteInstance();
                                Platform.runLater(()->{
                                    GameController.boardStage.close();
                                    Client.menuStage.show();
                                });
                                System.out.println("YOU'VE LEFT THE GAME");
                            } break;
                            default: {
                                System.out.println("Error");
                            }
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    Client.getInstance().in.close();
                } catch (IOException e) {
                    System.out.println("Unable to close stream!");
                }
            }
        }

    private void boardSet(){
         try {
             for (int i = 0; i < 25; i++) {
                 for (int j = 0; j < 17; j++) {
                     String s = Client.getInstance().in.readLine();
                     String b = Client.getInstance().in.readLine();

                     if(!s.equals("no") && !b.equals("no")){
                     int si = Integer.parseInt(s);
                     int sb = Integer.parseInt(b);

                     Board.getInstance().getField(i,j).setPawn(si);
                     Board.getInstance().getField(i,j).setBase(sb);}
                 }
             }

         }catch (IOException e){
             System.out.println("CHJI");
         }
    }

    private void goActionPerform() {
        //reading targetField x, targetField y, currentField x, currentField y
        String sx1 = "", sy1 = "", sx2 = "", sy2 = "";
        try {
            sx1 = Client.getInstance().in.readLine();
            sy1 = Client.getInstance().in.readLine();
            sx2 = Client.getInstance().in.readLine();
            sy2 = Client.getInstance().in.readLine();
        } catch (IOException e) {
            System.out.println("Unable to read message!");
        }

        int x1 = -1, y1 = -1, x2 = -1, y2 = -1;
        try {
            x1 = Integer.parseInt(sx1);
            y1 = Integer.parseInt(sy1);
            x2 = Integer.parseInt(sx2);
            y2 = Integer.parseInt(sy2);
        } catch (NumberFormatException e) {
            System.out.println("Unable to parse message!");
        }

        if (x1 != -1 && y1 != -1 && x2 != -1 && y2 != -1) {
            Field targetField = Board.getInstance().getField(x1, y1);
            Field currentField = Board.getInstance().getField(x2, y2);
            targetField.setPawn(currentField.getPawn());
            currentField.setPawn(0);
        }
    }
}
