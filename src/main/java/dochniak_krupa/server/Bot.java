package dochniak_krupa.server;

import javafx.application.Platform;

import java.util.ArrayList;

class Bot implements Runnable{
	private final int playerNumber; // bot needs to know which player represents
	private Field[] pawns = new Field[10]; // stores info about pawns of bot
	private Field[] bases = new Field[10]; // stores info about bases of bot
	private ArrayList<ArrayList<Field>> paths = new ArrayList<>(); // stores all possible paths to movement
	private int reachedBases = 0; // bot needs to know how many bases reached, because have to finish his works in in a timely manner
	private volatile boolean running = false; // true if bot is running
    private PlayerHandler playerHandler;

	//	gathers info about his pawns and bases
	Bot (int playerNumber, PlayerHandler playerHandler) {
		this.playerHandler = playerHandler;
		System.out.println(Game.getInstance().getPlayerTurn());//
		this.playerNumber = playerNumber;
		//this.playerHandler.playerNumber = playerNumber;
		int pawsIterator = 0;
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 17; j++) {
                Field field = Board.getInstance().getField(i, j);
				if (field != null) {
					if (field.getPawn() == playerNumber) {
						pawns[pawsIterator++] = field;
					} else if (field.getBase() == playerNumber) {
						int distance = distance(new Field(0, 12, 8), field);
						if (distance == 8) {
							bases[0] = field;
						} else if (distance == 7) {
							if (bases[1] == null) {
								bases[1] = field;
							} else {
								bases[2] = field;
							}
						} else if (distance == 6) {
							int number = 3;
							while (bases[number] != null) {
								number++;
							}
							bases[number] = field;
						} else {
							int number = 6;
							while (bases[number] != null) {
								number++;
							}
							bases[number] = field;
						}
					}
				}
			}
		}
	}

	//	counts distance in single no-jump movement
	private int distance(Field currentField, Field targetField) {
		int distanceX = Math.abs(currentField.getX() - targetField.getX());
		int distanceY = Math.abs(currentField.getY() - targetField.getY());
		if (distanceY > distanceX) {
			return distanceY;
		} else {
			return (distanceX + distanceY) / 2;
		}
	}

	//	checks all paths to neighbouring fields (no need to jump)
	private void checkNeighbouringFields(Field startingField) {
		for (int j = -1; j <= 1; j++) {
			for (int i = -1; i <= 1; i+=2) {
				if (startingField.getY() + j < 0 || 16 < startingField.getY() + j) {
					return;
				}
				Field targetField = null;
				if (j == 0 && (0 <= startingField.getX() + (2 * i) && startingField.getX() + (2 * i) <= 24)) {
					targetField = Board.getInstance().getField(startingField.getX() + (2 * i), startingField.getY() + j);
				} else if (j != 0 && 0 <= startingField.getX() + i && startingField.getX() + i <= 24) {
					targetField = Board.getInstance().getField(startingField.getX() + i, startingField.getY() + j);
				}
				if (targetField != null && targetField.getPawn() == 0) {
					ArrayList<Field> path = new ArrayList<>();
					path.add(startingField);
					path.add(targetField);
					paths.add(path);
				}
			}
		}
	}

	//	checks all paths to distant fields where there is a need to jump one or more time (by recursion)
	private void checkDistantFields(Field field, int deltaX, int deltaY, ArrayList<Field> oldPath) {
		if (field.getX() + 2 * deltaX < 0 || 24 < field.getX() + 2 * deltaX ||
				field.getY() + 2 * deltaY < 0 || 16 < field.getY() + 2 * deltaY) {
			return;
		}
		Field obstacleField = Board.getInstance().getField(field.getX() + deltaX, field.getY() + deltaY);
		Field targetField = Board.getInstance().getField(field.getX() + 2 * deltaX, field.getY() + 2 * deltaY);
		boolean isTargetOnTheOldPath = false;
		for (Field step : oldPath) {
			if (targetField == step) {
				isTargetOnTheOldPath = true;
			}
		}
		if (!isTargetOnTheOldPath && ((deltaX == 0 && deltaY == 0) ||
				(targetField != null && obstacleField.getPawn() != 0 && targetField.getPawn() == 0))) {
			ArrayList<Field> newPath = new ArrayList<>(oldPath);
			newPath.add(targetField);
			paths.add(newPath);
			checkDistantFields(targetField, 1, 1, newPath);
			checkDistantFields(targetField, 1, - 1, newPath);
			checkDistantFields(targetField, - 1, 1, newPath);
			checkDistantFields(targetField, - 1, - 1, newPath);
			checkDistantFields(targetField, 2, 0, newPath);
			checkDistantFields(targetField, - 2, 0, newPath);
		}
	}

	//	finds the best paths from gathered
	private ArrayList<Field> findTheBestMove() {
		for (int i = 0; i < 10; i ++) {
			boolean pawnOnTarget = false;
			for (int j = 0; j < reachedBases; j++) {
				if (bases[j] == pawns[i]) {
					pawnOnTarget = true;
				}
			}
			if (!pawnOnTarget) {
				checkNeighbouringFields(pawns[i]);
				checkDistantFields(pawns[i], 0, 0, new ArrayList<>());
			}
		}
		ArrayList<Field> bestPath = new ArrayList<>();
		int maxProgress = 0;
		int maxDistance = 0;
		for (ArrayList<Field> path : paths) {
			int distance = distance(path.get(0), bases[reachedBases]);
			int progress = distance - distance(path.get(path.size() - 1), bases[reachedBases]);
			if (progress > maxProgress) {
				maxProgress = progress;
				maxDistance = distance;
				bestPath = path;
			} else if (progress == maxProgress && distance > maxDistance) {
				maxDistance = distance;
				bestPath = path;
			}
		}
		paths = new ArrayList<>();
		return bestPath;
	}

	//	executes movement
	private void executeMovement() {
		ArrayList<Field> theBestMove = findTheBestMove();
		for (Field step : theBestMove) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException ignored) {}
			playerHandler.playerMoveHandler(step);
		}
		if (theBestMove.size() > 0) {
			for (int i = 0; i < 10; i++) {
				if (pawns[i] == theBestMove.get(0)) {
					pawns[i] = theBestMove.get(theBestMove.size() - 1);
				}
			}
		}
		while (reachedBases != 10 && bases[reachedBases].getBase() == bases[reachedBases].getPawn()) {
			reachedBases++;
		}
	}

	//	runs the bot, executes movement and ends turn. Terminate the thread if all bases are reached
	@Override
	public void run() {
		running = true;
		while (running) {
			try {
				Thread.sleep(250); //less than 5 milliseconds can generate graphic artifact
			} catch (InterruptedException ignored) {}
			if (Game.getInstance().getPlayerTurn() == playerNumber) {
				executeMovement();
				if (reachedBases == 10) {
					terminate();
					Platform.runLater(() ->	playerHandler.endTurn());
				} else {
					playerHandler.endTurn();
				}
			}
		}
	}

	//	terminate thread
	public void terminate() {
		running = false;
	}
}
