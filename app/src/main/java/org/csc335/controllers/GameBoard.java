package org.csc335.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.csc335.entity.TileValue;
import org.csc335.navigation.Navigation;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * Represents the game board in a grid layout.
 */
public class GameBoard extends GridPane {

  // debug flag so we can turn the print statements on and off
  private final boolean PRINT_STATEMENT_FLAG = true;

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
    generateRandomValues();
    this.initEventListeners();
  }

  private void generateRandomValues() {
    ArrayList<Tile> emptyTiles = new ArrayList<>();

    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board.length; col++) {
        if (board[row][col].isBlank()) {
          emptyTiles.add(board[row][col]);
        }
      }
    }

    // int randomIndex = (int)(Math.random()*emptyTiles.size());

    if (!emptyTiles.isEmpty()) {
      int randomIndex = (int) (Math.random() * emptyTiles.size());
      Tile randomTile = emptyTiles.get(randomIndex);

      double chance = Math.random();

      if (chance < .75) {
        randomTile.setValue(TileValue.T2);
      } else {
        randomTile.setValue(TileValue.T4);
      }
    } else {
      // TODO: make it so the game will report an end screen or something
      System.out.println("GAME END");
      System.exit(0);
    }
  }

  public void printBoard() {
    for (int row = 0; row < board.length; row++) {
      System.out.print("[");
      for (int col = 0; col < board[row].length; col++) {
        if (col < board[row].length) {
          System.out.printf("%4s", board[row][col].getValue());
        } else {
          System.out.printf("%4s", board[row][col].getValue());
        }
      }
      System.out.println(" ]");
    }
  }

  /*
   * shift logic
   */
  private void shift(Direction direction) {
    switch (direction) {
      case UP:
        up();
        break;
      case DOWN:
        down();
        break;
      case LEFT:
        left();
        break;
      case RIGHT:
        right();
        break;
      case null:
    }
  }

  private void up() {
    for (int col = 0; col < board.length; col++) {
      int first = 0;
      int next = 1;

      while (next < board.length) {
        Tile nextTile = board[next][col];
        if (nextTile.isBlank()) {
          next++;
          continue;
        }

        Tile firstTile = board[first][col];
        if (nextTile.getValue().equals(firstTile.getValue())) {
          firstTile.setValue(firstTile.getTileValue().next());
          nextTile.makeBlank();
          first++;
        } else if (firstTile.isBlank()) {
          firstTile.setValue(nextTile.getValue());
          nextTile.makeBlank();
        } else {
          // they cannot be merged -> swap nextTile w/ the tile after firsttile
          // firstTile + 1 can ONLY be either blank ot nextTile so it will work
          swap(board[first + 1][col], nextTile);
          first++;
        }
        next++;
      }
    }
  }

  private void down() {
    for (int col = 0; col < board.length; col++) {
      int first = board.length - 1;
      int next = board.length - 2;

      while (next >= 0) {
        Tile nextTile = board[next][col];
        if (nextTile.isBlank()) {
          next--;
          continue;
        }

        Tile firstTile = board[first][col];
        if (nextTile.getValue().equals(firstTile.getValue())) {
          firstTile.setValue(firstTile.getTileValue().next());
          nextTile.makeBlank();
          first--;
        } else if (firstTile.isBlank()) {
          firstTile.setValue(nextTile.getValue());
          nextTile.makeBlank();
        } else {
          // they cannot be merged -> swap nextTile w/ the tile after firsttile
          // firstTile + 1 can ONLY be either blank ot nextTile so it will work
          swap(board[first - 1][col], nextTile);
          first--;
        }
        next--;
      }
    }
  }

  private void left() {
    for (int row = 0; row < board.length; row++) {
      int first = 0;
      int next = 1;

      while (next < board.length) {
        Tile nextTile = board[row][next];
        if (nextTile.isBlank()) {
          next++;
          continue;
        }

        Tile firstTile = board[row][first];
        if (nextTile.getValue().equals(firstTile.getValue())) {
          firstTile.setValue(firstTile.getTileValue().next());
          nextTile.makeBlank();
          first++;
        } else if (firstTile.isBlank()) {
          firstTile.setValue(nextTile.getValue());
          nextTile.makeBlank();
        } else {
          // they cannot be merged -> swap nextTile w/ the tile after firsttile
          // firstTile + 1 can ONLY be either blank ot nextTile so it will work
          swap(board[row][first + 1], nextTile);
          first++;
        }
        next++;
      }
    }
  }

  private void right() {
    for (int row = 0; row < board.length; row++) {
      int first = board.length - 1;
      int next = board.length - 2;

      while (next >= 0) {
        Tile nextTile = board[row][next];
        if (nextTile.isBlank()) {
          next--;
          continue;
        }

        Tile firstTile = board[row][first];
        if (nextTile.getValue().equals(firstTile.getValue())) {
          firstTile.setValue(firstTile.getTileValue().next());
          nextTile.makeBlank();
          first--;
        } else if (firstTile.isBlank()) {
          firstTile.setValue(nextTile.getValue());
          nextTile.makeBlank();
        } else {
          // they cannot be merged -> swap nextTile w/ the tile after firsttile
          // firstTile + 1 can ONLY be either blank ot nextTile so it will work
          swap(board[row][first - 1], nextTile);
          first--;
        }
        next--;
      }
    }
  }

  private void swap(Tile t1, Tile t2) {
    String temp = t1.getValue();
    t1.setValue(t2.getValue());
    t2.setValue(temp);
  }

  /**
   * Initializes the event listeners for key presses and releases.
   */
  private void initEventListeners() {
    Navigation.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        // TODO: do stuff while key is pressed
        if (PRINT_STATEMENT_FLAG)
          System.out.printf("PRESSED: %s\n", event.getCode().getName());
        Direction direction = Direction.fromVal(event.getCode().getName());
        shift(direction);
        if (direction != null) {
          generateRandomValues();
        }
        if (PRINT_STATEMENT_FLAG)
          printBoard();
      }
    });
    Navigation.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        // TODO: do stuff when key gets released
        if (PRINT_STATEMENT_FLAG)
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
