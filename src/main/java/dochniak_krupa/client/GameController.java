package dochniak_krupa.client;

import javafx.stage.Stage;

class GameController {

//    for Singleton Pattern
    private static GameController gameController = null;
	static Stage boardStage = new Stage();

//    for Singleton Pattern
    private GameController() {}

//    method on mouse click for fields
    void handleFieldClick(Field field) {
    	//sends coordinates only if it is this client turn now
		if(Player.getInstance().isPlayerTurnNow()) {
			//Sending move handling request to server
			Client.getInstance().out.println("DO MOVE");

			//Sending X and Y coordinates to server
			Client.getInstance().out.println(field.getX());
			Client.getInstance().out.println(field.getY());
		}
    }

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
		if(Player.getInstance().isPlayerTurnNow())
			Client.getInstance().out.println("END TURN");
	}
}
