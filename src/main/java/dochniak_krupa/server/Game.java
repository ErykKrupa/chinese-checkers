package dochniak_krupa.server;

public class Game {

    int declaredNumberOfPlayersInGame;
    int currNumOfPlayers=0;
    private volatile int playerTurn;//-1 ma być
    private volatile boolean[] isPlayerInGame;
    private volatile boolean turnChanged = false;

    //Singleton
    private static volatile Game game;

    private Game(int numberOfPlayersInGame){
        this.declaredNumberOfPlayersInGame=numberOfPlayersInGame;
        this.isPlayerInGame = new boolean[6];
        this.setPlayerTurn(1);
        for(int i=0; i<5; i++)
            isPlayerInGame[i] = false;
    }

    //    Singleton Pattern
    static synchronized void setInstance(int declaredNumberOfPlayersInGame) {
        game = new Game(declaredNumberOfPlayersInGame);
    }

    public static synchronized Game getInstance() {
        return game;
    }

    public synchronized int getPlayerTurn() {
        return playerTurn;
    }

    public synchronized void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    //if < 6
    public synchronized boolean getIsPlayerInGame(int i) {
        return isPlayerInGame[i-1];
    }

    public synchronized void setIsPlayerInGame(int i, boolean isPlayerInGame) {
        this.isPlayerInGame[i-1] = isPlayerInGame;
    }

    public synchronized boolean isTurnChanged() {
        return turnChanged;
    }

    public synchronized void setTurnChanged(boolean turnChanged) {
        this.turnChanged = turnChanged;
    }

}
