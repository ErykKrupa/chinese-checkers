package dochniak_krupa.server;

import java.util.ArrayList;

public class PlayerHandlers {
    //ArrayList that stores all playerHandler objects
    //This approach allows us to use their PrintWriters and send message to every connected client.
    ArrayList<PlayerHandler> playerHandlersList;

    private static volatile PlayerHandlers playerHandlers;

    private PlayerHandlers(){
        playerHandlersList = new ArrayList<>();
    }

    static synchronized void setInstance() {
        playerHandlers = new PlayerHandlers();
    }

    public static synchronized PlayerHandlers getInstance() {
        return playerHandlers;
    }
}
