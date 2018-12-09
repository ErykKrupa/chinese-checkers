package dochniak_krupa.client;

import javafx.scene.control.Alert;

class Player {
	private static Player[] players = new Player[6];
	private static int playersInGame = 0;
	private boolean inGame = false;
	private int reachedTargets = 0;
	private int playerNumber;
	private static int iterator = 1;

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
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle(iterator++ + ". Place!");
			alert.setHeaderText(null);
			alert.setContentText("Player " + getPlayerNumber() + " has reached his targets!");
			alert.showAndWait();
			if (playersInGame == 1) {
				alert.setTitle("Game over!");
				alert.setContentText("Only one player left...");
				alert.showAndWait();
				System.exit(0);
			}
		}
	}

	int getPlayerNumber() {
		return playerNumber;
	}
}
