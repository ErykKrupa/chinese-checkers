package dochniak_krupa.server;

public class Game {

    int declaredNumberOfPlayersInGame;
    int currNumOfPlayers=0;
    volatile private int playerTurn;
    private boolean[] isClientInGame;
    static volatile int currentNumberOfBots;
    private boolean areAllPlayersConnected;

    //Singleton
    private static  Game game;

    private Game(int numberOfPlayersInGame){
        this.declaredNumberOfPlayersInGame=numberOfPlayersInGame;
        this.isClientInGame = new boolean[6];
        if(numberOfPlayersInGame == 4)
            this.setPlayerTurn(2);
        else
            this.setPlayerTurn(1);
        for(int i=0; i<5; i++)
            isClientInGame[i] = false;
    }

    //    Singleton Pattern
    static synchronized void setInstance(int declaredNumberOfPlayersInGame) {
        game = new Game(declaredNumberOfPlayersInGame);
    }

    static synchronized Game getInstance() {
        return game;
    }

    synchronized int getPlayerTurn() {
        return playerTurn;
    }

    synchronized void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    boolean getIsClientInGame(int i) {
        return isClientInGame[i-1];
    }

    void setIsClientInGame(int i, boolean isClientInGame) {
        this.isClientInGame[i-1] = isClientInGame;
    }

    public int getDeclaredNumberOfPlayersInGame() {
        return declaredNumberOfPlayersInGame;
    }

    public void setDeclaredNumberOfPlayersInGame(int declaredNumberOfPlayersInGame) {
        this.declaredNumberOfPlayersInGame = declaredNumberOfPlayersInGame;
    }

    void deleteInstance(){game = null;}
}
