package dochniak_krupa.server;

import java.util.ArrayList;

//thread that sends to every client all needed coordinates for client-side Board update
public class BoardChangeInfoSender extends Thread{
    //ArrayList that stores all playerHandler objects
    //This approach allows us to use their PrintWriters and send message to every connected client.
    ArrayList<PlayerHandler> playerHandlersList;

    BoardChangeInfoSender(){
        playerHandlersList = new ArrayList<>();
    }

    public void run(){
        while(true){
            if(Board.getInstance()!=null){
                //sending coordinates to all clients except the one
                //which has it's turn now
                if(Board.getInstance().getWasModified()){
                    PlayerHandler p0 = playerHandlersList.get(0);
                    Board b = Board.getInstance();
                    /*p0.output.println("OTHER PLAYER MOVED");
                    p0.output.println(b.getTargetX());
                    p0.output.println(b.getTargetY());
                    p0.output.println(b.getCurrentX());
                    p0.output.println(b.getCurrentY());*/

                    PlayerHandler p1 = playerHandlersList.get(1);
                    p1.output.println("OTHER PLAYER MOVED");
                    p1.output.println(b.getTargetX());
                    p1.output.println(b.getTargetY());
                    p1.output.println(b.getCurrentX());
                    p1.output.println(b.getCurrentY());

                    /*PlayerHandler p2 = playerHandlersList.get(2);
                    p2.output.println("OTHER PLAYER MOVED");
                    p2.output.println(b.getTargetX());
                    p2.output.println(b.getTargetY());
                    p2.output.println(b.getCurrentX());
                    p2.output.println(b.getCurrentY());

                    PlayerHandler p3 = playerHandlersList.get(3);
                    p2.output.println("OTHER PLAYER MOVED");
                    p2.output.println(b.getTargetX());
                    p2.output.println(b.getTargetY());
                    p2.output.println(b.getCurrentX());
                    p2.output.println(b.getCurrentY());

                    PlayerHandler p4 = playerHandlersList.get(4);
                    p4.output.println("OTHER PLAYER MOVED");
                    p4.output.println(b.getTargetX());
                    p4.output.println(b.getTargetY());
                    p4.output.println(b.getCurrentX());
                    p4.output.println(b.getCurrentY());

                    PlayerHandler p5 = playerHandlersList.get(1);
                    p5.output.println("OTHER PLAYER MOVED");
                    p5.output.println(b.getTargetX());
                    p5.output.println(b.getTargetY());
                    p5.output.println(b.getCurrentX());
                    p5.output.println(b.getCurrentY());*/
                }
                Board.getInstance().setWasModified(false);
            }
        }
    }
}
