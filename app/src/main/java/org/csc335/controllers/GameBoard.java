package org.csc335.controllers;

import java.io.IOException;

import org.csc335.navigation.Navigation;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * Represents the game board in a grid layout.
 */
public class GameBoard extends GridPane {
  private Tile[][] board;

  public GameBoard() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameBoard.fxml"));
    loader.setRoot(this);
    loader.setController(this);
    super.getStylesheets().add(this.getClass().getResource("/css/gameboard.css").toExternalForm());
    // super.getStyleClass().add("gameboard");
    try {
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.board = makeBoard();
    this.initEventListeners();
  }

  /**
   * Initializes the event listeners for key presses and releases.
   */
  private void initEventListeners() {
    Navigation.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        // TODO: do stuff while key is pressed
        System.out.printf("PRESSED: %s\n", event.getCode().getName());
      }
    });
    Navigation.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        // TODO: do stuff when key gets released
        System.out.printf("RELEASED: %s\n", event.getCode().getName());
      }
    });
  }

  /**
   * Creates a 4x4 board of Tile objects, adds them to the grid layout, and
   * returns the board.
   *
   * @return A 2D array representing the game board
   */
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
