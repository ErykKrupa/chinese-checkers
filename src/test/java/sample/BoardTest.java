package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void shouldGetTheSameInstance() {
        Board board1 = Board.getInstance();
        Board board2 = Board.getInstance();
        assertEquals(board1, board2);
    }
}
