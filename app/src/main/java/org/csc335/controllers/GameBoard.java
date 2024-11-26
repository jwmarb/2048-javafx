package org.csc335.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.csc335.entity.TileValue;
import org.csc335.navigation.Navigation;
import org.csc335.util.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * Represents the game board in a grid layout.
 */
public class GameBoard extends GridPane {
  private ArrayList<Tile> emptyTiles = new ArrayList<>();
  private Tile[][] board;
  private Game parent;
  private Audio sound;

  public GameBoard() throws URISyntaxException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameBoard.fxml"));
    loader.setRoot(this);
    loader.setController(this);
    super.getStylesheets().add(this.getClass().getResource("/css/gameboard.css").toExternalForm());
    sound = new Audio();
    // super.getStyleClass().add("gameboard");
    try {
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.board = makeBoard();
    initialTileSetup();
    this.initEventListeners();
  }

  public void setParent(Game parent) {
    this.parent = parent;
  }

  private void initialTileSetup() {
    updateBlankTiles();

    int idx1 = (int) (Math.random() * emptyTiles.size());
    int idx2;
    do {
      idx2 = (int) (Math.random() * emptyTiles.size());
    } while (idx2 == idx1);

    // Tile randomTile = emptyTiles.get(randomIndex);
    emptyTiles.get(idx1).setValue(
        (Math.random() < 0.75) ? TileValue.T2 : TileValue.T4);
    emptyTiles.get(idx2).setValue(
        (Math.random() < 0.75) ? TileValue.T2 : TileValue.T4);
  }

  private void updateBlankTiles() {
    emptyTiles = new ArrayList<>(); // make it empty again
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board.length; col++) {
        if (board[row][col].isBlank()) {
          emptyTiles.add(board[row][col]);
        }
      }
    }
  }

  private void generateRandomValues() {
    int randomIndex = (int) (Math.random() * emptyTiles.size());
    Tile randomTile = emptyTiles.get(randomIndex);

    double chance = Math.random();

    if (chance < .75) {
      randomTile.setValue(TileValue.T2);
    } else {
      randomTile.setValue(TileValue.T4);
    }
  }

  public void printBoard() {
    if (Logger.isDevelopment()) {
      System.out.println("+----+----+----+----+");
      for (int row = 0; row < board.length; row++) {
        System.out.print("|");
        for (int col = 0; col < board[row].length; col++) {
          String value = board[row][col].getValue();
          value = (value == null) ? "" : value;
          System.out.printf("%4s|", value);
        }
        System.out.println("\n+----+----+----+----+");
      }
    }
  }

  /*
   * shift logic
   * returns true if anything actaully happened on board
   */
  private boolean shift(Direction direction) {
    switch (direction) {
      case UP:
        return shiftOperation(true, true);
      case DOWN:
        return shiftOperation(false, true);
      case LEFT:
        return shiftOperation(true, false);
      case RIGHT:
        return shiftOperation(false, false);
      case null:
    }
    return false;
  }

  /*
   * @param collapseTowards0: true if up/left, false if down, right
   */
  private boolean shiftOperation(boolean collapseTowards0, boolean loopOverCols) {
    boolean somethingHappened = false;

    // toMerge is general name for what we collapse (rows or cols)
    for (int toMerge = 0; toMerge < board.length; toMerge++) {

      final int OFFSET = (collapseTowards0) ? 1 : -1;

      // determine starting point within row/column
      int first = (collapseTowards0) ? 0 : board.length - 1;
      int next = (collapseTowards0) ? 1 : board.length - 2;

      while (true) {
        boolean loopCondition = (collapseTowards0) ? next < board.length : next >= 0;
        if (!loopCondition)
          break;

        // if loop over cols, we need loop idx to be in col pos!
        Tile nextTile = (loopOverCols) ? board[next][toMerge] : board[toMerge][next];

        if (nextTile.isBlank()) {
          next += OFFSET;
          continue;
        }

        // if loop over cols, we need loop idx to be in col pos!
        Tile firstTile = (loopOverCols) ? board[first][toMerge] : board[toMerge][first];

        if (nextTile.getValue().equals(firstTile.getValue())) { // we can merge them!
          somethingHappened = true;

          firstTile.setValue(firstTile.getTileValue().next());

          this.parent.addScore(firstTile.getIntValue());

          nextTile.makeBlank();
          first += OFFSET;
          sound.playMergeSound();
        } else if (firstTile.isBlank()) { // move tile to new spot
          somethingHappened = true;

          firstTile.setValue(nextTile.getValue());
          nextTile.makeBlank();
        } else {
          // unroll ternary so it doesn't look so bad
          int row = (!loopOverCols) ? toMerge : (first + OFFSET);
          int col = (loopOverCols) ? toMerge : (first + OFFSET);

          if (swap(board[row][col], nextTile)) {
            somethingHappened = true;
          }

          first += OFFSET;
        }
        next += OFFSET;
      }
    }
    return somethingHappened;
  }

  // returns true if a swap ACTUALLY happened (t1 != t1)
  private boolean swap(Tile t1, Tile t2) {
    String temp = t1.getValue();
    t1.setValue(t2.getValue());
    t2.setValue(temp);
    return t1 != t2;
  }

  /**
   * nasty brute-force checker for if the game is done. looks to see if any
   * adjacent tiles have
   * the same value (which means player has a move). Returns true if player cannot
   * make any moves.
   * 
   * @return true if player cannot make another move, false otherwise
   */
  private boolean testGameEndMethod() {
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board[0].length; c++) {
        Tile thisTile = board[r][c];

        Tile[] toCompare = new Tile[4];

        if (r + 1 < board.length) {
          toCompare[0] = board[r + 1][c];
        }
        if (r - 1 >= 0) {
          toCompare[1] = board[r - 1][c];
        }
        if (c + 1 < board[0].length) {
          toCompare[2] = board[r][c + 1];
        }
        if (c - 1 >= 0) {
          toCompare[3] = board[r][c - 1];
        }

        for (Tile t : toCompare) {
          if (thisTile.equals(t)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Initializes the event listeners for key presses and releases.
   */
  private void initEventListeners() {
    Navigation.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {

        Direction direction = Direction.fromVal(event.getCode().getName());

        // don't do anything if key is not WASD or up/down/left/right
        if (direction == null) {
          return;
        }

        Logger.printf("PRESSED: %s\n", event.getCode().getName());

        boolean somethingHappened = shift(direction);

        // update blank tile list -> we can see if game is done
        updateBlankTiles();

        if (somethingHappened) {
          generateRandomValues();

          // this is the size BEFORE adding the new tile -> means that right now all tiles
          // are filled
          if (emptyTiles.size() == 1) {
            if (testGameEndMethod()) {
              Logger.println("Something Happened? " + somethingHappened);
              Logger.printf("PRESSED: %s\n", event.getCode().getName());
              Logger.println("GAME END");
              printBoard();

              System.exit(0);
            }
          }
        }

        printBoard();
      }
    });

    Navigation.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        Logger.printf("RELEASED: %s\n", event.getCode().getName());
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