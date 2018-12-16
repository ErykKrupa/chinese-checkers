package dochniak_krupa.client;

import javafx.stage.Stage;

class GameController {
	static Stage boardStage = new Stage();

	private GameController() {}

	//    method on mouse click for fields
	static void handleFieldClick(Field field) {
		//sends coordinates only if it is this client turn now
		if(Player.getInstance().isPlayerTurnNow()) {
			//Sending move handling request to server
			ServerConnection.getInstance().out.println("DO MOVE");

			//Sending X and Y coordinates to server
			ServerConnection.getInstance().out.println(field.getX());
			ServerConnection.getInstance().out.println(field.getY());
		}
	}

	static void endTurn(){
		if(Player.getInstance().isPlayerTurnNow())
			ServerConnection.getInstance().out.println("END TURN");
	}
}