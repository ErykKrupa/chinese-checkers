package sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldControllerTest {

	@BeforeEach
	void destroyInstanceToHaveAppropriateConditionsForAllTest () {
		FieldController.getInstance().destroyInstance();
		Board.getInstance().destroyInstance();
	}

	@Test
	void shouldGetNewInstanceOfFieldController() {
		FieldController.getInstance().handleFieldWithPawnClick(Board.getInstance().getField(10, 2));
		FieldController.getInstance().handleFieldWithoutPawnClick(Board.getInstance().getField(8, 4));
		FieldController.getInstance().destroyInstance();
		FieldController.getInstance().handleFieldWithPawnClick(Board.getInstance().getField(18, 4));
		FieldController.getInstance().handleFieldWithoutPawnClick(Board.getInstance().getField(16, 4));
		assertEquals(2, Board.getInstance().getField(18, 4).getPawn());
		assertEquals(0, Board.getInstance().getField(16, 4).getPawn());

	}

	@Test
	void shouldChangePositionOfPlayersPawnIfIsHeTurn () {
		Field field1 = new Field(1, 4, 6);
		Field field2 = new Field(0, 6, 6);
		FieldController.getInstance().handleFieldWithPawnClick(field1);
		FieldController.getInstance().handleFieldWithoutPawnClick(field2);
		assertEquals(0, field1.getPawn());
		assertEquals(1, field2.getPawn());
	}

	@Test
	void shouldNotChangePositionOfPlayersPawnIfIsNotHeTurn () {
		Field field1 = new Field(6, 4, 6);
		Field field2 = new Field(0, 6, 6);
		FieldController.getInstance().handleFieldWithPawnClick(field1);
		FieldController.getInstance().handleFieldWithoutPawnClick(field2);
		assertEquals(6, field1.getPawn());
		assertEquals(0, field2.getPawn());
	}

	@Test
	void shouldNotChangePositionOfPlayersPawnIfTargetFieldIsIncorrect () {
		Field field1 = new Field(1, 4, 6);
		Field field2 = new Field(0, 8, 6);
		FieldController.getInstance().handleFieldWithPawnClick(field1);
		FieldController.getInstance().handleFieldWithoutPawnClick(field2);
		assertEquals(1, field1.getPawn());
		assertEquals(0, field2.getPawn());
	}

	@Test
	void shouldNotChangePositionOfPlayersOnePawnIfNeutralFieldWasChosenFirst () {
		Field field1 = new Field(1, 4, 6);
		Field field2 = new Field(0, 8, 6);
		FieldController.getInstance().handleFieldWithoutPawnClick(field2);
		FieldController.getInstance().handleFieldWithPawnClick(field1);
		assertEquals(1, field1.getPawn());
		assertEquals(0, field2.getPawn());
	}

	@Test
	void shouldChangePositionOfTwoPlayersPawnIfIsAppropriateTurn () {
		Field field0 = new Field(0, 4, 6);
		Field field1 = new Field(1, 6, 6);
		Field field2 = new Field(2, 8, 6);
		System.out.println("" + field0.getPawn() + field1.getPawn() + field2.getPawn());
		FieldController.getInstance().handleFieldWithPawnClick(field1);
		System.out.println("" + field0.getPawn() + field1.getPawn() + field2.getPawn());
		FieldController.getInstance().handleFieldWithoutPawnClick(field0);
		System.out.println("" + field0.getPawn() + field1.getPawn() + field2.getPawn());
		FieldController.getInstance().handleFieldWithPawnClick(field2);
		System.out.println("" + field0.getPawn() + field1.getPawn() + field2.getPawn());
		FieldController.getInstance().handleFieldWithoutPawnClick(field1);
		System.out.println("" + field0.getPawn() + field1.getPawn() + field2.getPawn());
		assertEquals(1, field0.getPawn());
		assertEquals(2, field1.getPawn());
		assertEquals(0, field2.getPawn());
	}

	@Test
	void shouldJumpIfItIsPawnBetweenCurrentAndTargetPosition() {
		assertEquals(1, Board.getInstance().getField(10, 2).getPawn());
		assertEquals(0, Board.getInstance().getField(8, 4).getPawn());
		FieldController.getInstance().handleFieldWithPawnClick(Board.getInstance().getField(10, 2));
		FieldController.getInstance().handleFieldWithoutPawnClick(Board.getInstance().getField(8, 4));
		assertEquals(0, Board.getInstance().getField(10, 2).getPawn());
		assertEquals(1, Board.getInstance().getField(8, 4).getPawn());
	}

	@Test
	void shouldNotJumpIfItIsNotPawnBetweenCurrentAndTargetPosition() {
		assertEquals(1, Board.getInstance().getField(9, 3).getPawn());
		assertEquals(0, Board.getInstance().getField(7, 5).getPawn());
		FieldController.getInstance().handleFieldWithPawnClick(Board.getInstance().getField(9, 3));
		FieldController.getInstance().handleFieldWithoutPawnClick(Board.getInstance().getField(7, 5));
		assertEquals(1, Board.getInstance().getField(9, 3).getPawn());
		assertEquals(0, Board.getInstance().getField(7, 5).getPawn());
	}
}