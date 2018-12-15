package dochniak_krupa.client;

class Player {
	//	only six players can be create
	private static Player[] players = new Player[6];

	//	increments when a new player enters the game
	private static int playersInGame = 0;

	//	true if player is in game actually
	private boolean inGame = false;

	//	increments when player reaches his base
	private int reachedTargets = 0;

	//	number for player, 1-6
	private int playerNumber;

	//	stores place for which players are playing now
	private static int podium = 1;

	private boolean isReadyForGame = false;

	public boolean isReadyForGame() {
		return isReadyForGame;
	}

	public void setReadyForGame(boolean readyForGame) {
		isReadyForGame = readyForGame;
	}

	//stores actual player's turn
	private volatile boolean isPlayerTurnNow = false;

	public synchronized boolean isPlayerTurnNow() {
		return isPlayerTurnNow;
	}

	public synchronized void setPlayerTurnNow(boolean playerTurnNow) {
		isPlayerTurnNow = playerTurnNow;
	}

	//Singleton
	private static volatile Player player = null;
	//    Singleton Pattern
	static synchronized void setInstance(int playerNumber) {
		player = new Player(playerNumber);
	}

	public static synchronized Player getInstance() {
		return player;
	}


	private Player(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	//	returns player with given number
	static Player getPlayer(int playerNumber) {
		if (players[playerNumber - 1] == null) {
			players[playerNumber - 1] = new Player(playerNumber);
		}
		return players[playerNumber -1];
	}

	//	delete all players, for tests
	static void flushPlayers() {
		for (Player player : players) {
			player = null;
		}
	}

	boolean isInGame() {
		return inGame;
	}

	void setInGame(boolean inGame) {
		if (!this.inGame && inGame) {
			playersInGame++;
		} else if (this.inGame && !inGame) {
			playersInGame--;
		}
		System.out.println("Players: " + playersInGame);
		this.inGame = inGame;
	}


	public String toString() throws IllegalArgumentException {
		switch(playerNumber)
		{
			case 1:
				return "Red";
			case 2:
				return "Orange";
			case 3:
				return "Green";
			case 4:
				return "Teal";
			case 5:
				return "Blue";
			case 6:
				return "Black";
			default:
				throw new IllegalArgumentException("Player number must be in range 0-6");
		}
	}

	int getPlayerNumber() {
		return playerNumber;
	}
}
