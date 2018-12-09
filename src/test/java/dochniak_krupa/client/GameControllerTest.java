package dochniak_krupa.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

	@BeforeEach
	void createNewInstancesToHaveAppropriateConditionsForAllTest () {
		GameController.getInstance().destroyInstance();
		Board.setInstance(6);
		Player.flushPlayers();
	}
	@Test
	void shouldGetNewInstanceOfFieldController() {
		GameController.getInstance().handleFieldWithPawnClick(Board.getInstance().getField(10, 2));
		GameController.getInstance().handleFieldWithoutPawnClick(Board.getInstance().getField(8, 4));
		GameController.getInstance().destroyInstance();
		GameController.getInstance().handleFieldWithPawnClick(Board.getInstance().getField(18, 4));
		GameController.getInstance().handleFieldWithoutPawnClick(Board.getInstance().getField(16, 4));
		assertEquals(2, Board.getInstance().getField(18, 4).getPawn());
		assertEquals(0, Board.getInstance().getField(16, 4).getPawn());

	}

	@Test
	void shouldChangePositionOfPlayersPawnIfIsHisTurn () {
		Field field1 = new Field(2, 4, 6);
		field1.setPawn(1);
		Field field2 = new Field(0, 6, 6);
		GameController.getInstance().handleFieldWithPawnClick(field1);
		GameController.getInstance().handleFieldWithoutPawnClick(field2);
		assertEquals(0, field1.getPawn());
		assertEquals(1, field2.getPawn());
	}

	@Test
	void shouldNotChangePositionOfPlayersPawnIfIsNotHeTurn () {
		Field field1 = new Field(6, 4, 6);
		field1.setPawn(6);
		Field field2 = new Field(0, 6, 6);
		GameController.getInstance().handleFieldWithPawnClick(field1);
		GameController.getInstance().handleFieldWithoutPawnClick(field2);
		assertEquals(6, field1.getPawn());
		assertEquals(0, field2.getPawn());
	}

	@Test
	void shouldNotChangePositionOfPlayersPawnIfTargetFieldIsIncorrect () {
		Field field1 = new Field(1, 4, 6);
		field1.setPawn(1);
		Field field2 = new Field(0, 8, 6);
		GameController.getInstance().handleFieldWithPawnClick(field1);
		GameController.getInstance().handleFieldWithoutPawnClick(field2);
		assertEquals(1, field1.getPawn());
		assertEquals(0, field2.getPawn());
	}

	@Test
	void shouldNotChangePositionOfPlayersOnePawnIfNeutralFieldWasChosenFirst () {
		Field field1 = new Field(1, 4, 6);
		field1.setPawn(1);
		Field field2 = new Field(0, 8, 6);
		GameController.getInstance().handleFieldWithoutPawnClick(field2);
		GameController.getInstance().handleFieldWithPawnClick(field1);
		assertEquals(1, field1.getPawn());
		assertEquals(0, field2.getPawn());
	}

	@Test
	void shouldChangePositionOfTwoPlayersPawnIfIsAppropriateTurn () {
		Field field0 = new Field(0, 4, 6);
		Field field1 = new Field(0, 6, 6);
		field1.setPawn(1);
		Field field2 = new Field(0, 8, 6);
		field2.setPawn(2);
		GameController.getInstance().handleFieldWithPawnClick(field1);
		GameController.getInstance().handleFieldWithoutPawnClick(field0);
		GameController.getInstance().endTurn();
		GameController.getInstance().handleFieldWithPawnClick(field2);
		GameController.getInstance().handleFieldWithoutPawnClick(field1);
		assertEquals(1, field0.getPawn());
		assertEquals(2, field1.getPawn());
		assertEquals(0, field2.getPawn());
	}

	@Test
	void shouldJumpIfItIsPawnBetweenCurrentAndTargetPosition() {
		assertEquals(1, Board.getInstance().getField(10, 2).getPawn());
		assertEquals(0, Board.getInstance().getField(8, 4).getPawn());
		GameController.getInstance().handleFieldWithPawnClick(Board.getInstance().getField(10, 2));
		GameController.getInstance().handleFieldWithoutPawnClick(Board.getInstance().getField(8, 4));
		assertEquals(0, Board.getInstance().getField(10, 2).getPawn());
		assertEquals(1, Board.getInstance().getField(8, 4).getPawn());
	}

	@Test
	void shouldNotJumpIfItIsNotPawnBetweenCurrentAndTargetPosition() {
		assertEquals(1, Board.getInstance().getField(9, 3).getPawn());
		assertEquals(0, Board.getInstance().getField(7, 5).getPawn());
		GameController.getInstance().handleFieldWithPawnClick(Board.getInstance().getField(9, 3));
		GameController.getInstance().handleFieldWithoutPawnClick(Board.getInstance().getField(7, 5));
		assertEquals(1, Board.getInstance().getField(9, 3).getPawn());
		assertEquals(0, Board.getInstance().getField(7, 5).getPawn());
	}
}