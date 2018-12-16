package dochniak_krupa.client;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;

public class Field extends Circle
{
//    current pawn standing on the field
//    1-6 mean player's 1-6 pawn
//    0 means no pawn on this field
    private int pawn;

//    represents which player has to reach this field for win the game
//    1-6 mean player's 1-6 base
//    0 means no base on this field, it is neutral, central part of the board
    private int base;

//    position x and y on the board
    private final int x, y;

    public Field(int playerNumber, int x, int y) {
        this.x = x;
        this.y = y;
        setPawn(0);
        base = playerNumber;
        setRadius(23);
        setStrokeWidth(4);
        setStroke(getColor(playerNumber));
        setOnMouseClicked(t -> GameController.handleFieldClick(this));
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getPawn() {
        return pawn;
    }

//    sets Pawn on this field, changes field's color, set activity for mouse click
    void setPawn(int pawn) {
        this.pawn = pawn;
        setFill(getColor(pawn));
    }

    int getBase() {
        return base;
    }

//    return color basing on given number,
//    each player has own color, grey represents neutral fields
//    given number must be in range 0-6, else throws IllegalArgumentException
    private Color getColor(int colorNumber) throws IllegalArgumentException {
        switch(colorNumber)
        {
            case 0:
                return Color.web("#c0c0c0"); //grey
            case 1:
                return Color.web("#ff0000"); //red
            case 2:
                return Color.web("#ff9933"); //orange
            case 3:
                return Color.web("#00ff00"); //green
			case 4:
				return Color.web("#40d0e0"); //teal
            case 5:
                return Color.web("#0000ff"); //blue
            case 6:
                return Color.web("#000000"); //black
            default:
                throw new IllegalArgumentException("Player number must be in range 0-6");
        }
    }

}
