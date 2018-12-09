package dochniak_krupa.client;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Board extends GridPane
{
//    for Singleton Pattern
    private static Board board = null;

    private Field[][] fields = new Field[25][17];

    private Board(int playersNumber) {
//        sets players according to playersNumber
        if (playersNumber == 2) {
            Player.getPlayer(1).setInGame(true);
            Player.getPlayer(4).setInGame(true);
        } else if (playersNumber == 3) {
            Player.getPlayer(1).setInGame(true);
            Player.getPlayer(3).setInGame(true);
            Player.getPlayer(5).setInGame(true);
        }  else if (playersNumber == 4) {
            Player.getPlayer(2).setInGame(true);
            Player.getPlayer(3).setInGame(true);
            Player.getPlayer(5).setInGame(true);
            Player.getPlayer(6).setInGame(true);
        } else if (playersNumber == 6) {
            for (int i = 1; i <= 6; i++) {
                Player.getPlayer(i).setInGame(true);
            }
        } else {
            throw new IllegalArgumentException("Players number must equals to 2, 3, 4 or 6.");
        }

//        getConstraints make suitable free space between fields
        for (int i = 0; i < 17; i++) {
            this.getRowConstraints().add(new RowConstraints(46));
        }
        for (int i = 0; i < 26; i++) {
            this.getColumnConstraints().add(new ColumnConstraints(28));
        }

//        TL;DR: this algorithm creates fields and pawns on the boards
//        don't waste time for analyse it
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 17; j++) {
                if ((j + i) % 2 == 0) {
                    if (j <= 3 && 12 - j <= i && i <= 12 + j) {
                        fields[i][j] = new Field(Player.getPlayer(4).isInGame() ? 4 : 0, i, j);
                        fields[i][j].setPawn(Player.getPlayer(1).isInGame() ? 1 : 0);
                    } else if (4 <= j && j <= 8 && -4 + j <= i && i <= 28 - j) {
                        if (i - j >= 14){
                            fields[i][j] = new Field(Player.getPlayer(5).isInGame() ? 5 : 0, i, j);
                            fields[i][j].setPawn(Player.getPlayer(2).isInGame() ? 2 : 0);
                        } else if (i + j <= 10) {
                            fields[i][j] = new Field(Player.getPlayer(3).isInGame() ? 3 : 0, i, j);
                            fields[i][j].setPawn(Player.getPlayer(6).isInGame() ? 6 : 0);
                        } else {
                            fields[i][j] = new Field(0, i, j);
                        }
                    } else if (9 <= j && j <= 12 && 12 - j <= i && i <= 12 + j) {
                        if (i + j >= 30) {
                            fields[i][j] = new Field(Player.getPlayer(6).isInGame() ? 6 : 0, i, j);
                            fields[i][j].setPawn(Player.getPlayer(3).isInGame() ? 3 : 0);
                        } else if (j - i >= 6) {
                            fields[i][j] = new Field(Player.getPlayer(2).isInGame() ? 2 : 0, i, j);
                            fields[i][j].setPawn(Player.getPlayer(5).isInGame() ? 5 : 0);
                        } else {
                            fields[i][j] = new Field(0, i, j);
                        }
                    } else if (13 <= j && -4 + j <= i && i <= 28 - j) {
                        fields[i][j] = new Field(Player.getPlayer(1).isInGame() ? 1 : 0, i, j);
                        fields[i][j].setPawn(Player.getPlayer(4).isInGame() ? 4 : 0);
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

//        end turn button and action for it
        Button endTurnBtn = new Button("End Turn");
        endTurnBtn.setMinSize(90, 40);
        endTurnBtn.setOnAction(t -> GameController.getInstance().endTurn());
        GridPane.setConstraints(endTurnBtn, 19, 15);
        this.getChildren().add(endTurnBtn);
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
}
