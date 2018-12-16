package dochniak_krupa.client;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;

class Board extends GridPane
{
//    for Singleton Pattern
    private static Board board = null;

    private Field[][] fields = new Field[25][17];

    private Board() {
//        getConstraints make suitable free space between fields
        for (int i = 0; i < 17; i++) {
            this.getRowConstraints().add(new RowConstraints(46));
        }
        for (int i = 0; i < 26; i++) {
            this.getColumnConstraints().add(new ColumnConstraints(28));
        }

        //Sending request for board built by server
        Client.getInstance().out.println("FIELD SET");

        //Interpreting server response
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 17; j++) {
                try {
                    String s = Client.getInstance().in.readLine();
                    String b = Client.getInstance().in.readLine();
                    if(!s.equals("no") && !b.equals("no")){
                        int si = Integer.parseInt(s);
                        int sb = Integer.parseInt(b);
                        fields[i][j] = new Field(sb,i,j);
                        fields[i][j].setPawn(si);
                        GridPane.setConstraints(fields[i][j], i, j);
                        this.getChildren().add(fields[i][j]);
                    }
                }catch (IOException e){
                    System.out.println("Read line err");
                }
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
    static void setInstance() {
        board = new Board();
    }

    static Board getInstance() {
        return board;
    }

    static void deleteInstance(){board = null;}

//    get object of field at given position
    Field getField(int x, int y) {
        return fields[x][y];
    }
}
