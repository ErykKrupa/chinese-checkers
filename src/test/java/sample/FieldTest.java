package sample;

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
    void shouldCreateFieldForPlayerNumberEqualsToZero() {
        for(int i = 0; i <= 6; i++) {
            new Field(1, 0, 0);
        }
    }

    @Test
    void shouldSetAllParametersCorrectlyForField() {
        Field field = new Field(1, 0, 0);
        assertEquals(22, field.getRadius());
        assertEquals(6, field.getStrokeWidth());
        assertEquals(1, field.getPawn());
        assertEquals(1, field.getBase());
        assertEquals(Color.web("#ff0000"), field.getStroke());
        assertEquals(Color.web("#ff0000"), field.getFill());
    }
}