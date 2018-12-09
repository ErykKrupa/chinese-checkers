package dochniak_krupa.client;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @BeforeEach
    void createNewBoardInstance() {
//        for running tests with JavaFX components and controls
		JFXPanel fxPanel = new JFXPanel();
        Board.setInstance(6);
        while (GameController.getInstance().playerTurn != 1) {
            GameController.getInstance().endTurn();
        }
    }

    @Test
    void shouldGetTheSameInstance() {
        Board board1 = Board.getInstance();
        Board board2 = Board.getInstance();
        assertEquals(board1, board2);
    }

    @Test
    void shouldGetCorrectField() {
        Field actualField = Board.getInstance().getField(12, 14);
        assertEquals(4, actualField.getPawn());
        assertEquals(1, actualField.getBase());
        assertEquals(12, actualField.getX());
        assertEquals(14, actualField.getY());
    }

    @Test
    void shouldGetNewInstanceOfBoard() {
        assertEquals(1, Board.getInstance().getField(10, 2).getPawn());
        assertEquals(0, Board.getInstance().getField(8, 4).getPawn());
        GameController.getInstance().handleFieldClick(Board.getInstance().getField(10, 2));
        GameController.getInstance().handleFieldClick(Board.getInstance().getField(8, 4));
        assertEquals(0, Board.getInstance().getField(10, 2).getPawn());
        assertEquals(1, Board.getInstance().getField(8, 4).getPawn());
        Board.setInstance(6);
        assertEquals(1, Board.getInstance().getField(10, 2).getPawn());
        assertEquals(0, Board.getInstance().getField(8, 4).getPawn());}
}
