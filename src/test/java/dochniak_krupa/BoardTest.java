package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void shouldGetTheSameInstance() {
        Board board1 = Board.getInstance();
        Board board2 = Board.getInstance();
        assertEquals(board1, board2);
    }

    @Test
    void shouldGetCorrectField() {
        Field expectedField = new Field(4, 12, 14);
        Field actualField = Board.getInstance().getField(12, 14);
        assertEquals(expectedField.getPawn(), actualField.getPawn());
        assertEquals(expectedField.getBase(), actualField.getBase());
        assertEquals(expectedField.getX(), actualField.getX());
        assertEquals(expectedField.getY(), actualField.getY());
    }

    @Test
    void shouldGetNewInstanceOfBoard() {
        assertEquals(1, Board.getInstance().getField(10, 2).getPawn());
        assertEquals(0, Board.getInstance().getField(8, 4).getPawn());
        FieldController.getInstance().handleFieldWithPawnClick(Board.getInstance().getField(10, 2));
        FieldController.getInstance().handleFieldWithoutPawnClick(Board.getInstance().getField(8, 4));
        assertEquals(0, Board.getInstance().getField(10, 2).getPawn());
        assertEquals(1, Board.getInstance().getField(8, 4).getPawn());
        Board.getInstance().destroyInstance();
        assertEquals(1, Board.getInstance().getField(10, 2).getPawn());
        assertEquals(0, Board.getInstance().getField(8, 4).getPawn());}
}
