package dochniak_krupa.client;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Board extends GridPane
{
//    for Singleton Pattern
    private static Board board = null;

    private Field[][] fields = new Field[25][17];

//    true if given player is in the game actually
    private boolean[] players = new boolean[6];

    private Board(int playersNumber) {
//        sets players according to playersNumber
        players[0] = true;
        if (playersNumber == 2) {
            players[3] = true;
        } else if (playersNumber == 3) {
            players[2] = true;
            players[4] = true;
        }  else if (playersNumber == 4) {
            players[1] = true;
            players[3] = true;
            players[4] = true;
        } else if (playersNumber == 6) {
            for (int i = 1; i <= 5; i++) {
                players[i] = true;
            }
        } else {
            throw new IllegalArgumentException("Players number must equals to 2, 3, 4 or 6.");
        }

//        getConstraints make suitable free space between fields
        for (int i = 0; i < 17; i++) {
            this.getRowConstraints().add(new RowConstraints(46));
        }
        for (int i = 0; i < 25; i++) {
            this.getColumnConstraints().add(new ColumnConstraints(28));
        }

//        TL;DR: this algorithm creates fields on the boards
//        don't waste time for analyse it
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 17; j++) {
                if ((j + i) % 2 == 0) {
                    if (j <= 3 && 12 - j <= i && i <= 12 + j) {
                        fields[i][j] = new Field(1, i, j);
                    } else if (4 <= j && j <= 8 && -4 + j <= i && i <= 28 - j) {
                        if (i - j >= 14 && players[1]) {
                            fields[i][j] = new Field(2, i, j);
                        } else if (i + j <= 10  && players[5]) {
                            fields[i][j] = new Field(6, i, j);
                        } else {
                            fields[i][j] = new Field(0, i, j);
                        }
                    } else if (9 <= j && j <= 12 && 12 - j <= i && i <= 12 + j) {
                        if (i + j >= 30 && players[2]) {
                            fields[i][j] = new Field(3, i, j);
                        } else if (j - i >= 6 && players[4]) {
                            fields[i][j] = new Field(5, i, j);
                        } else {
                            fields[i][j] = new Field(0, i, j);
                        }
                    } else if (13 <= j && -4 + j <= i && i <= 28 - j) {
                        if (players[3]) {
                            fields[i][j] = new Field(4, i, j);
                        } else {
                            fields[i][j] = new Field(0, i, j);
                        }
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
//                add prepared fields to pane
                GridPane.setConstraints(fields[i][j], i, j);
                this.getChildren().add(fields[i][j]);
            }
        }
    }

//    Singleton Pattern
    static void setInstance(int playerNumber) {
        board = new Board(playerNumber);
    }

    public static Board getInstance() {
        return board;
    }

//    get object of field at given position
    public Field getField(int x, int y) {
        return fields[x][y];
    }

//    return true if is this player in game
    public boolean isPlayerInGame(int playerNumber) {
        return players[playerNumber - 1];
    }
}
