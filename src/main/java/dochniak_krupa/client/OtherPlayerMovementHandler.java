package dochniak_krupa.client;

import java.io.IOException;

//Thread which checks if board was modified by other player.
//It reads server messages only if it's other player turn now.
public class OtherPlayerMovementHandler extends Thread{
         //Reacting to client response
        public void run(){
            try {
                while (true) {
                    if(!Player.getInstance().isPlayerTurnNow() && Player.getInstance().isReadyForGame()) {
                        String command = "";
                        command = Client.getInstance().in.readLine();

                        switch (command) {
                            case "OTHER PLAYER MOVED": {
                                //board update
                                GameController.goActionPerform();
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
}
