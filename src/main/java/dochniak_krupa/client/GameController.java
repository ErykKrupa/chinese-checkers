package dochniak_krupa.client;

import java.io.IOException;

class GameController {

//    for Singleton Pattern
    private static GameController gameController = null;

//    for Singleton Pattern
    private GameController() {}

//    method on mouse click for fields
    void handleFieldClick(Field field) {

    	//Sending move handling request to server
    	Client.getInstance().out.println("DO MOVE");

    	//Sending X and Y coordinates to server
		Client.getInstance().out.println(field.getX());
		Client.getInstance().out.println(field.getY());

		String returnedMessage = "";
		try{
			returnedMessage = Client.getInstance().in.readLine();
		}catch (IOException e){
			System.out.println("Unable to read message!");
		}

		System.out.println(returnedMessage);

		//interpreting action command received from server
		switch (returnedMessage){
			case "PAWN NOT CHOSEN":{
				System.out.println("Didn't choose pawn");
			} break;
			case "GO": GameController.goActionPerform(); break;
			case "CHOSEN": System.out.println("CHOSEN"); break;
			default:{
				System.out.println("Error");
			} break;
		}
    }

	//creating singleton because OtherPlayerMovementHandler class uses goActionPerformedMethod()
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

	void endTurn(){
    	Client.getInstance().out.println("END TURN");
	}

	//actualizing pawn positions on board
	static void goActionPerform(){
    	//reading targetField x, targetField y, currentField x, currentField y
		String sx1="",sy1="",sx2="",sy2="";
		try{
			sx1 = Client.getInstance().in.readLine();
			sy1 = Client.getInstance().in.readLine();
			sx2 = Client.getInstance().in.readLine();
			sy2 = Client.getInstance().in.readLine();
		}catch (IOException e){
			System.out.println("Unable to read message!");
		}

		int x1=-1,y1=-1,x2=-1,y2=-1;
		try{
			x1 = Integer.parseInt(sx1);
			y1 = Integer.parseInt(sy1);
			x2 = Integer.parseInt(sx2);
			y2 = Integer.parseInt(sy2);
		}catch (NumberFormatException e){
			System.out.println("Unable to parse message!");
		}

		if(x1!=-1 && y1!=-1 && x2!=-1 && y2!=-1) {
			Field targetField = Board.getInstance().getField(x1, y1);
			Field currentField = Board.getInstance().getField(x2,y2);
			targetField.setPawn(currentField.getPawn());
			currentField.setPawn(0);
		}
	}

}
