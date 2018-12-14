package dochniak_krupa.server;

public class Game {

    int declaredNumberOfPlayersInGame;
    int currNumOfPlayers=0;
    private volatile int playerTurn;//
    private volatile boolean[] isClientInGame;
    private volatile boolean turnChanged = false;

    //Singleton
    private static volatile Game game;

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

    public static synchronized Game getInstance() {
        return game;
    }

    public synchronized int getPlayerTurn() {
        return playerTurn;
    }

    public synchronized void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public synchronized boolean getIsClientInGame(int i) {
        return isClientInGame[i-1];
    }

    public synchronized void setIsClientInGame(int i, boolean isClientInGame) {
        this.isClientInGame[i-1] = isClientInGame;
    }

    public synchronized boolean isTurnChanged() {
        return turnChanged;
    }

    public synchronized void setTurnChanged(boolean turnChanged) {
        this.turnChanged = turnChanged;
    }

    public int getDeclaredNumberOfPlayersInGame() {
        return declaredNumberOfPlayersInGame;
    }

    public void setDeclaredNumberOfPlayersInGame(int declaredNumberOfPlayersInGame) {
        this.declaredNumberOfPlayersInGame = declaredNumberOfPlayersInGame;
    }
}
