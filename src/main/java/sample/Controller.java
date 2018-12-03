package sample;

public class Controller {
    private static Field currentField = null;
    private static Field targetField = null;
    private static boolean jumped = false;
    private static int playerTurn = 1;

    static void handleFieldWithoutPawnClick(Field field) {
        targetField = field;
        if (currentField == null) {
			System.out.println("Didn't choose");
			return;
		}
		if ((Math.abs(currentField.getX() - targetField.getX()) == 1 &&
				Math.abs(currentField.getY() - targetField.getY()) == 1 ||
				Math.abs(currentField.getX() - targetField.getX()) == 2 &&
					currentField.getY() - targetField.getY() == 0) && !jumped) {
			targetField.setPawn(currentField.getPawn());
			currentField.setPawn(0);
			currentField = targetField;
			System.out.println("I'm going " + targetField.getPawn());
			endTurn();

		} else if (isAbleToJump()) {
			targetField.setPawn(currentField.getPawn());
			currentField.setPawn(0);
			currentField = targetField;
			System.out.println("I'm jumping " + targetField.getPawn());
			jumped = true;
		} else {
			System.out.println("I can't go there");
		}
    }

    static void handleFieldWithPawnClick(Field field) {
    	if (field.getPawn() == playerTurn) {
			currentField = field;
			System.out.println("Chose " + currentField.getPawn());
			if (jumped) {
				endTurn();
			}
		}
	}

	private static boolean isAbleToJump () {
		int deltaX = targetField.getX() - currentField.getX();
		int deltaY = targetField.getY() - currentField.getY();
		if (deltaY == -2 && (deltaX == 2 || deltaX == -2) ||
				deltaY == 0 && (deltaX == -4 || deltaX == 4) ||
				deltaY == 2 && (deltaX == 2 || deltaX == -2)) {
			return (Board.getInstance().getField((currentField.getX() + deltaX / 2),
					(currentField.getY() + deltaY / 2)).getPawn() != 0);
		}
		return false;
	}

	private static void endTurn() {
		if (playerTurn == 6) {
			playerTurn = 1;
		} else {
			playerTurn++;
		}
		jumped = false;
		currentField = null;
		System.out.println("End turn");
	}


}
