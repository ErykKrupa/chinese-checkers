package dochniak_krupa.client;

class Player {
	private static Player[] players = new Player[6];
	private static int playersInGame = 0;
	private boolean inGame = false;
	private int reachedTargets = 0;
	private int playerNumber;

	private Player(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	static Player getPlayer(int playerNumber) {
		if (players[playerNumber - 1] == null) {
			players[playerNumber - 1] = new Player(playerNumber);
		}
		return players[playerNumber -1];
	}

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

	void reachTarget() {
		reachedTargets++;
		System.out.println("Reached targets: " + reachedTargets);
		if (reachedTargets == 10) {
			setInGame(false);
			System.out.println("PLAYER " + getPlayerNumber() + " WIN!!!!");
			// TODO Window for winning player
			if (playersInGame == 1) {
				System.out.println("Only one player left, game over!");
				// TODO Only one player left, window and end game
			}
		}
	}

	int getPlayerNumber() {
		return playerNumber;
	}
}
