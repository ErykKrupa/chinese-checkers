package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Field extends Circle
{
//    current pawn standing on the field
//    1-6 mean player's 1-6 pawn
//    0 means no pawn on this field
//    -1 means there is not that field in the game
    private int pawn;
//    represents which player started the game on this field
//    1-6 mean player's 1-6 base
//    0 means no base on this field, it is neutral, central part of the board
//    -1 means there is not that field in the game
    private final int base;

    Field(int playerNumber) {
        setRadius(22);
        base = playerNumber;
        if(base == -1) {
            setStrokeWidth(0);
        } else {
            setStrokeWidth(6);
        }
        setStroke(getColor(playerNumber));
        setPawn(playerNumber);
    }

    public int getPawn() {
        return pawn;
    }

//    sets Pawn on this field, changes field's color
    void setPawn(int pawn) {
        this.pawn = pawn;
        setFill(getColor(pawn));
    }

    public int getBase() {
        return base;
    }

//    return color basing on given number,
//    each player has own color, grey represents neutral fields, and transparent- no field
    private Color getColor(int colorNumber) {
        switch(colorNumber)
        {
            case 0:
                return Color.web("#c0c0c0"); //grey
            case 1:
                return Color.web("#ff0000"); //red
            case 2:
                return Color.web("#cc00cc"); //purple
            case 3:
                return Color.web("#ff9933"); //orange
            case 4:
                return Color.web("#0000ff"); //blue
            case 5:
                return Color.web("#00ff00"); //green
            case 6:
                return Color.web("#000000"); //black
            default:
                return Color.web("#ffffff00"); //transparent
        }
    }

}
