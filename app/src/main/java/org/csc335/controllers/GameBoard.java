package org.csc335.controllers;

import java.io.IOException;
import java.util.ArrayList;

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
    generateRandomValues();
    this.initEventListeners();
  }


  private void generateRandomValues() {
    ArrayList<Tile> emptyTiles = new ArrayList<>();

    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board.length; col++) {
        if (board[row][col].getValue() == null) {
          emptyTiles.add(board[row][col]);
        }
      }
    }

    // int randomIndex = (int)(Math.random()*emptyTiles.size());

    
    if (!emptyTiles.isEmpty()) {
      int randomIndex = (int)(Math.random()*emptyTiles.size());
      Tile randomTile = emptyTiles.get(randomIndex);
      
      double chance = Math.random();

      if (chance < .75) {
        randomTile.setValue("2");
      } else {
        randomTile.setValue("4");
      }
    }

  }

  public void printBoard() {
    // System.out.println("The following is an agumented matrix with a vector: ");
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

  private void shift(Direction direction) {
    if (Direction.UP == direction) {
      up();
    } else if (Direction.LEFT == direction) {
      left();
    }
  }

  
  // the directions
  private void up() {
    for (int row = board.length-1; row > 0; row--) {
      for (int col = 0; col < board[row].length; col++) {

        String currTileVal = board[row][col].getValue();
        String tileAboveVal = board[row-1][col].getValue();

        if (tileAboveVal == null) {
          board[row-1][col].setValue(currTileVal);
          board[row][col] = new Tile();
        } else if (currTileVal == null) {
          continue;
        } else {
          if (currTileVal.equals(tileAboveVal)) {
            String newVal = Integer.parseInt(currTileVal) + Integer.parseInt(tileAboveVal) + "";
            board[row-1][col].setValue(newVal);

            board[row][col] = new Tile();
          }

        }
    
      }
    }
  }

  private void left() {
    
    for (int col = board[0].length-1; col > 0; col--) {
      for (int row = 0; row < board.length; row++) {
        String currTileVal = board[row][col].getValue();
        String tileLeftVal = board[row][col-1].getValue();
        
        if (tileLeftVal == null) {
          board[row][col-1].setValue(currTileVal);
          board[row][col] = new Tile();
        } else if (currTileVal == null) {
          continue;
        } else {
          if (currTileVal.equals(tileLeftVal)) {
            String newVal = Integer.parseInt(currTileVal) + Integer.parseInt(tileLeftVal) + "";
            board[row][col-1].setValue(newVal);

            board[row][col] = new Tile();
          }

        }
      }
    }
    
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
        Direction direction = Direction.fromVal(event.getCode().getName());
        shift(direction);
        generateRandomValues();
        printBoard();
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
