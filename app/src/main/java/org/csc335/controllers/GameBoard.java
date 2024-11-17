package org.csc335.controllers;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class GameBoard extends GridPane {
  private Tile[][] board;

  public GameBoard() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameBoard.fxml"));
    loader.setRoot(this);
    loader.setController(this);
    super.getStylesheets().add(this.getClass().getResource("/css/gameboard.css").toExternalForm());
    super.getStyleClass().add("gameboard");

    try {
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.board = makeBoard();
    this.initEventListeners();
  }

  private void initEventListeners() {
    super.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        System.out.printf("PRESSED: %s\n", event.getCode().getName());
      }
    });
    super.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        System.out.printf("RELEASED: %s\n", event.getCode().getName());
      }
    });

  }

  private Tile[][] makeBoard() {
    Tile[][] temp = new Tile[4][4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        Tile tile = new Tile();
        temp[row][col] = tile;
        this.add(tile, col, row);
      }
    }
    return temp;
  }
}
