package dochniak_krupa.client;

import javafx.application.Platform;

import java.util.ArrayList;

class Bot implements Runnable{
	private final int playerNumber;
	private Field[] pawns = new Field[10];
	private Field[] bases = new Field[10];
	ArrayList<ArrayList<Field>> paths = new ArrayList<>();
	int reachedBases = 0;
	private volatile boolean isRunning = true;
	Bot (int playerNumber) {
		this.playerNumber = playerNumber;
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

	private int distance(Field currentField, Field targetField) {
		int distanceX = Math.abs(currentField.getX() - targetField.getX());
		int distanceY = Math.abs(currentField.getY() - targetField.getY());
		if (distanceY > distanceX) {
			return distanceY;
		} else {
			return (distanceX + distanceY) / 2;
		}
	}

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

	private void executeMovement() {
		ArrayList<Field> theBestMove = findTheBestMove();
		for (Field step : theBestMove) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException ignored) {}
			GameController.getInstance().handleFieldClick(step);
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

	@Override
	public void run() {
		while (isRunning) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException ignored) {}
			if (GameController.getInstance().playerTurn == playerNumber) {
				executeMovement();
				if (reachedBases == 10) {
					terminate();
				}
				Platform.runLater(() ->	GameController.getInstance().endTurn());
			}
		}
	}

	public void terminate() {
		isRunning = false;
	}
}
