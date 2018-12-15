package dochniak_krupa.server;

import java.util.ArrayList;

class PlayerHandlers {

    private PlayerHandlers(){}

    //ArrayList that stores all playerHandler objects.
    //This approach allows us to use their PrintWriters and send message to every connected client.
    static ArrayList<PlayerHandler> playerHandlersList = new ArrayList<>();
}
