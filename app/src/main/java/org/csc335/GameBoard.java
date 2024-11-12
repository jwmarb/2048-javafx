package org.csc335;

import javafx.scene.layout.GridPane;

public class GameBoard extends GridPane {
    private Tile[][] board;

    public static void main(String[] args ) {
        GameBoard g = new GameBoard();
        
    }

    public GameBoard() {
        board = makeBoard();
    }

    private Tile[][] makeBoard() {
        Tile[][] temp = new Tile[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                temp[row][col] = new Tile();
            }
        }
        return temp;
    }
}
