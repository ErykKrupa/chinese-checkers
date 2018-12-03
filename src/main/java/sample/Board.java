package sample;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

class Board extends GridPane
{
    private static Board board = null;
    private Field[][] fields = new Field[25][17];

    private Board() {
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
                        if (i - j >= 14) {
                            fields[i][j] = new Field(2, i, j);
                        } else if (i + j <= 10) {
                            fields[i][j] = new Field(6, i, j);
                        } else {
                            fields[i][j] = new Field(0, i, j);
                        }
                    } else if (9 <= j && j <= 12 && 12 - j <= i && i <= 12 + j) {
                        if (i + j >= 30) {
                            fields[i][j] = new Field(3, i, j);
                        } else if (j - i >= 6) {
                            fields[i][j] = new Field(5, i, j);
                        } else {
                            fields[i][j] = new Field(0, i, j);
                        }
                    } else if (13 <= j && -4 + j <= i && i <= 28 - j) {
                        fields[i][j] = new Field(4, i, j);
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
    static Board getInstance() {
        if (board == null) {
            board = new Board();
        }
        return board;
    }

    Field getField(int x, int y) {
        return fields[x][y];
    }

}
