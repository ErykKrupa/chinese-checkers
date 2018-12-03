package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Field extends Circle
{
//    current pawn standing on the field
//    1-6 mean player's 1-6 pawn
//    0 means no pawn on this field
    private int pawn;

//    represents which player started the game on this field
//    1-6 mean player's 1-6 base
//    0 means no base on this field, it is neutral, central part of the board
    private final int base;

//    position x and y on the board
    private int x, y;

    Field(int playerNumber, int x, int y) {
        setX(x);
        setY(y);
        setPawn(playerNumber);
        base = playerNumber;
        setRadius(22);
        setStrokeWidth(6);
        setStroke(getColor(playerNumber));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPawn() {
        return pawn;
    }

//    sets Pawn on this field, changes field's color
    void setPawn(int pawn) {
        this.pawn = pawn;
        setFill(getColor(pawn));
        if (pawn == 0) {
            setOnMouseClicked(t -> Controller.handleFieldWithoutPawnClick(this));
        } else {
            setOnMouseClicked(t -> Controller.handleFieldWithPawnClick(this));
        }
    }

    public int getBase() {
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
                throw new IllegalArgumentException("Player number must be in range 0-6");
        }
    }

}
