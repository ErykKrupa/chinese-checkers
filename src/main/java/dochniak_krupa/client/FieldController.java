package dochniak_krupa.client;

public class FieldController {

//	  store player's field which is currently clicked
    private Field currentField = null;

//    store neutral field which is target for player's pawn
    private Field targetField = null;

//    true, if pawn has already jumped in this turn
    private boolean jumped = false;

    private int playerTurn = 1;

//    for Singleton Pattern
    private static FieldController fieldController = null;

//    for Singleton Pattern
    private FieldController() {}

//    method on mouse click for field without pawn
    void handleFieldWithoutPawnClick(Field field) {
        targetField = field;
//        if player hasn't chosen his pawn yet
        if (currentField == null) {
			System.out.println("Didn't choose");
			return;
		}
//		check if pawn can go on this field
		if ((Math.abs(currentField.getX() - targetField.getX()) == 1 &&
				Math.abs(currentField.getY() - targetField.getY()) == 1 ||
				Math.abs(currentField.getX() - targetField.getX()) == 2 &&
					currentField.getY() - targetField.getY() == 0) && !jumped) {
			targetField.setPawn(currentField.getPawn());
			currentField.setPawn(0);
			System.out.println("I'm going " + targetField.getPawn());
			endTurn();
		}
//			check if pawn can jump on this field
		else if (isAbleToJump()) {
			targetField.setPawn(currentField.getPawn());
			currentField.setPawn(0);
			currentField = targetField;
			System.out.println("I'm jumping " + targetField.getPawn());
			jumped = true;
		} else {
			System.out.println("I can't go there");
		}
    }

//    method on mouse click for field with pawn
    void handleFieldWithPawnClick(Field field) {
//    	player can choose only his pawn
    	if (field.getPawn() == playerTurn) {
			currentField = field;
			System.out.println("Chose " + currentField.getPawn());
			if (jumped) {
				endTurn();
			}
		}
	}

//	long condition to check the jumping ability
	private boolean isAbleToJump () {
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

//	end turn for this player
	private void endTurn() {
		if (playerTurn == 6) {
			playerTurn = 1;
		} else {
			playerTurn++;
		}
		jumped = false;
		currentField = null;
		targetField = null;
		System.out.println("End turn");
	}

//	Singleton Pattern
	static FieldController getInstance() {
    	if (fieldController == null) {
			fieldController = new FieldController();
    	}
    	return fieldController;
	}

//    for tests, it's necessary to run all tests with fresh FieldController
	void destroyInstance() {
		fieldController = null;
	}


}
