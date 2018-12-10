package dochniak_krupa.client;

import java.util.ArrayList;

public class Bot {
	private final int playerNumber;
	private Field[] pawns = new Field[10];
	private Field[] bases = new Field[10];
	ArrayList<ArrayList<Field>> paths = new ArrayList<>();
	public Bot (int playerNumber) {
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
				Field targetField = Board.getInstance().getField(
						startingField.getX() + (j == 0 ? 2 * i : i),startingField.getY() + j);
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
		Field obstacleField = Board.getInstance().getField(field.getX() + deltaX, field.getY() + deltaY);
		Field targetField = Board.getInstance().getField(field.getX() + 2 * deltaX, field.getY() + 2 * deltaY);
		boolean isTargetOnTheOldPath = false;
		for (Field step : oldPath) {
			if (targetField == step) {
				isTargetOnTheOldPath = true;
			}
		}
		if (!isTargetOnTheOldPath && (deltaX == 0 && deltaY == 0) ||
				(targetField != null && obstacleField.getPawn() != 0 && targetField.getPawn() == 0)) {
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

	private void checkMoves() {
		for (int i = 0; i < 10; i ++) {
			checkNeighbouringFields(pawns[i]);
			checkDistantFields(pawns[i], 0, 0, new ArrayList<>());
		}
		int i = 0;
		while (bases[i].getBase() == bases[i].getPawn()) {
			i++;
		}
		int maxProgress = 0;
		ArrayList<Field> bestPath;
		for (ArrayList<Field> path : paths) {
			int progress = distance(path.get(0), bases[i]) - distance(path.get(path.size() - 1), bases[i]);
			if (progress > maxProgress) {
				maxProgress = progress;
				bestPath = path;
			}
		}
	}
}
