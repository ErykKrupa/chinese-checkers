package dochniak_krupa.client;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    @Test
    void shouldNotCreateFieldForIncorrectPlayerNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Field(-1, 0, 0)
        );
        assertEquals("Player number must be in range 0-6", exception.getMessage());
    }

    @Test
    void shouldCreateFieldForAllPlayers() {
        for(int i = 0; i <= 6; i++) {
            new Field(1, 0, 0);
        }
    }

    @Test
    void shouldSetAllParametersCorrectlyForField() {
        Field field = new Field(1, 0, 0);
        assertEquals(23, field.getRadius());
        assertEquals(4, field.getStrokeWidth());
        assertEquals(0, field.getPawn());
        assertEquals(1, field.getBase());
        assertEquals(0, field.getX());
        assertEquals(0, field.getY());
        assertEquals(Color.web("#ff0000"), field.getStroke());
        assertEquals(Color.web("#c0c0c0"), field.getFill());
    }

    @Test
    void shouldChangePositionOfPawn() {
        Field field1 = new Field(3, 4, 6);
        field1.setPawn(3);
        Field field2 = new Field(0, 6, 6);
        field2.setPawn(field1.getPawn());
        field1.setPawn(0);
        assertEquals(0, field1.getPawn());
        assertEquals(3, field1.getBase());
        assertEquals(3, field2.getPawn());
        assertEquals(0, field2.getBase());
    }
}