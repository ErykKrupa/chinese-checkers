package dochniak_krupa.server;

public class Game {

    int declaredNumberOfPlayersInGame;
    int currNumOfPlayers=0;

    //Singleton
    private static Game game;

    private Game(int numberOfPlayersInGame){
        this.declaredNumberOfPlayersInGame=numberOfPlayersInGame;
        Board.setInstance(numberOfPlayersInGame);
    }

    //    Singleton Pattern
    static void setInstance(int declaredNumberOfPlayersInGame) {
        game = new Game(declaredNumberOfPlayersInGame);
    }

    public static Game getInstance() {
        return game;
    }

}
