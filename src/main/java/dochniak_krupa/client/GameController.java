package dochniak_krupa.client;

class GameController {

//	  starting position of pawn
	private Field startingField = null;

//	  player's field which is currently clicked
    private Field currentField = null;

//    neutral field which is target for player's pawn
    private Field targetField = null;

//    true, if pawn has already gone in this turn
	private boolean went = false;

//    true, if pawn has already jumped in this turn
	private boolean jumped = false;

//	  player turn counter
    private int playerTurn = (int) (Math.random() * 6 + 1);

//    for Singleton Pattern
    private static GameController gameController = null;

//    for Singleton Pattern
    private GameController() {}

//    method on mouse click for field without pawn
    void handleFieldWithoutPawnClick(Field field) {
        targetField = field;
//        if player hasn't chosen his pawn yet
        if (currentField == null) {
			System.out.println("Didn't choose");
			return;
		}
//		check if it is starting field of current pawn
		if (targetField == startingField) {
			go();
			System.out.println("Return");
			went = false;
			jumped = false;
		}
//		check if pawn try to go outside tha base
		if (!(currentField.getPawn() == currentField.getBase() && targetField.getBase() == 0)) {
			//		check if pawn can go on this field
			if ((Math.abs(currentField.getX() - targetField.getX()) == 1 &&
					Math.abs(currentField.getY() - targetField.getY()) == 1 ||
					Math.abs(currentField.getX() - targetField.getX()) == 2 &&
							currentField.getY() - targetField.getY() == 0) && !jumped && !went) {
				go();
				System.out.println("I'm going " + targetField.getPawn());
				went = true;
			}
			//		check if pawn can jump on this field
			else if (isAbleToJump() && ! went) {
				go();
				System.out.println("I'm jumping " + targetField.getPawn());
				jumped = true;
			}
		} else {
			System.out.println("I can't go there");
		}
    }

//    method on mouse click for field with pawn
    void handleFieldWithPawnClick(Field field) {
//    	player can choose only his pawn and only,
// 		when he didn't choose pawn in this turn yet,
// 		or didn't make a move
    	if (field.getPawn() == playerTurn && (startingField == null || startingField.getPawn() == playerTurn)) {
    		startingField = field;
    		currentField = field;
			System.out.println("Chose " + currentField.getPawn());
		} else {
			System.out.println("I can't choose that");
		}
	}

//	end turn for this player
	void endTurn() {
		if (currentField != null && currentField.getBase() == playerTurn &&
				startingField != null && startingField.getBase() != playerTurn) {
			Player.getPlayer(playerTurn).reachTarget();
		}
		startingField = null;
		currentField = null;
		targetField = null;
		jumped = false;
		went = false;
		System.out.println("End turn");
//		if player isn't in game- ignore his turn
		do {
			if (playerTurn == 6) {
				playerTurn = 1;
			} else {
				playerTurn++;
			}
		} while (!(Player.getPlayer(playerTurn).isInGame()));
	}

//	push pawn on target field
	private void go() {
		targetField.setPawn(currentField.getPawn());
		currentField.setPawn(0);
		currentField = targetField;
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

//	Singleton Pattern
	static GameController getInstance() {
    	if (gameController == null) {
			gameController = new GameController();
    	}
    	return gameController;
	}

//    for tests, it's necessary to run all tests with fresh FieldController
	void destroyInstance() {
		gameController = null;
	}


}
