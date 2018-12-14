package dochniak_krupa.client;

import java.io.IOException;

//Thread which checks if board was modified by other player.
//It reads server messages only if it's other player turn now.
public class OtherPlayerMovementHandler extends Thread{
         //Reacting to client response
        public void run(){
            try {
                while (true) {
                    if(Player.getInstance().isReadyForGame()) {
                        String command = "";
                        command = Client.getInstance().in.readLine();

                        switch (command) {
                            //getting info from server that now is your turn
                            case  "YOUR TURN": {
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
                            default: {
                                System.out.println("Error");
                            }
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Unable to read line!");
            } finally {
                try {
                    Client.getInstance().in.close();
                } catch (IOException e) {
                    System.out.println("Unable to close stream!");
                }
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
