package dochniak_krupa.server;

public class Field {
    //    current pawn standing on the field
    //    1-6 mean player's 1-6 pawn
    //    0 means no pawn on this field
    private volatile int pawn;

    //    represents which player has to reach this field for win the game
    //    1-6 mean player's 1-6 base
    //    0 means no base on this field, it is neutral, central part of the board
    private final int base;

    //    position x and y on the board
    private final int x, y;

    public Field(int playerNumber, int x, int y) {
        this.x = x;
        this.y = y;
        setPawn(0);
        base = playerNumber;
    }

    void setPawn(int pawn) {
        this.pawn = pawn;
    }

    synchronized int getPawn() {
        return pawn;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    synchronized int getBase() {
        return base;
    }
}
