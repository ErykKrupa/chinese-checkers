package dochniak_krupa.client;

public class Bot {
	private final int playerNumber;
	private Field[] paws = new Field[10];
	private Field[] bases = new Field[10];
	public Bot (int playerNumber) {
		this.playerNumber = playerNumber;
		int pawsIterator = 0, basesIterator = 0;
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 17; j++) {
				Field field = Board.getInstance().getField(i, j);
				if (field != null) {
					if (field.getPawn() == playerNumber) {
						paws[pawsIterator++] = field;
					} else if (field.getBase() == playerNumber) {
						int distance = calculateDistance(new Field(0, 12, 8), field);
						if (distance == 8) {
							bases[0] = field;
						} else if (distance == 7) {
							bases[1] = field;
						} else if (distance == 6) {
							bases[3] = field;
						} else if (distance == 5) {
							bases[6] = field;
						}
					}
				}
			}
		}

	}

	private int calculateDistance (Field currentField, Field targetField) {
		int distanceX = Math.abs(currentField.getX() - targetField.getX());
		int distanceY = Math.abs(currentField.getY() - targetField.getY());
		if (distanceY > distanceX) {
			return distanceY;
		} else {
			return (distanceX + distanceY) / 2;
		}
	}
}
