package org.csc335.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.csc335.entity.Direction;
import org.csc335.entity.TileValue;
import org.csc335.interfaces.GameBoardListener;
import org.csc335.interfaces.Resettable;
import org.csc335.javafx_entity.Audio;
import org.csc335.util.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents the model for the 2048 game, handling the game logic. This class
 * manages the game board state, including tile values, movements, and game
 * progression.
 */
public class GameBoardModel implements Resettable {
  private List<GameBoardListener> listeners;
  private TileModel[][] board;
  private List<TileModel> emptyTiles;
  private boolean shouldRecordKeystrokes;
  private IntegerProperty moves;

  public GameBoardModel(int size) {
    this.listeners = new ArrayList<>();
    this.emptyTiles = new ArrayList<>();
    this.board = this.makeBoard(size);
    this.shouldRecordKeystrokes = true;
    this.moves = new SimpleIntegerProperty(0);

    this.initialTileSetup();
    printBoard();
  }

  /**
   * Creates a 4x4 board of Tile objects, adds them to the grid layout, and
   * returns the board.
   *
   * @return A 2D array representing the game board
   */
  private TileModel[][] makeBoard(int size) {
    TileModel[][] temp = new TileModel[size][size];
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        TileModel tile = new TileModel();
        temp[row][col] = tile;
      }
    }

    return temp;
  }

  public void reset() {
    // Iterate through each cell of the game board and reset it to a blank state.
    for (int i = 0; i < board.length; ++i) {
      for (int j = 0; j < board[i].length; ++j) {
        board[i][j].makeBlank(); // Set the current cell to a blank state.
      }
    }

    // Perform the initial setup of tiles on the game board.
    initialTileSetup();

    // Reset the move counter to zero since the game has been reset.
    this.moves.set(0);
  }

  /**
   * Initializes the tile setup on the game board by setting random values to two
   * distinct randomly selected tiles.
   * This method updates the state of blank tiles and assigns random values to two
   * different tiles from the list of empty tiles.
   *
   * @pre The game board has been initialized and the {@code emptyTiles} list
   *      contains at least two tiles.
   * @post Two distinct tiles from the {@code emptyTiles} list have been assigned
   *       random values.
   *       The state of the game board is updated with these new values.
   *       The {@code emptyTiles} list remains unchanged in terms of the tiles it
   *       contains, only their values are modified.
   */
  private void initialTileSetup() {
    // Update the state of blank tiles on the game board
    updateBlankTiles();

    // Select the first random index from the list of empty tiles and create a
    // variable for idx2 to reassign later
    int idx1 = (int) (Math.random() * emptyTiles.size()), idx2;

    // Ensure that the second random index is different from the first one
    do {
      idx2 = (int) (Math.random() * emptyTiles.size());
    } while (idx2 == idx1);

    // Set a random value to the tile at the first selected index
    emptyTiles.get(idx1).random();

    // Set a random value to the tile at the second selected index
    emptyTiles.get(idx2).random();
  }

  /**
   * Updates the list of empty tiles on the game board.
   * This method clears the current list of empty tiles and then iterates
   * through each cell of the game board. For each cell, it checks if the cell
   * is blank, and if so, adds it to the list of empty tiles.
   *
   * @pre The game board (board) is initialized and contains valid tile objects.
   *      The emptyTiles list is a valid mutable list that can be cleared and have
   *      items added.
   * @post The emptyTiles list contains references to all and only the blank tiles
   *       from the board.
   */
  private void updateBlankTiles() {
    // Clear the list of empty tiles
    this.emptyTiles.clear();

    // Iterate over each cell in the board to update the list of empty tiles.
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board.length; col++) {
        // Check if the current cell is blank and add it to the list of empty tiles.
        if (board[row][col].isBlank()) {
          emptyTiles.add(board[row][col]);
        }
      }
    }
  }

  /**
   * Randomly selects an empty tile from the list of empty tiles and assigns a
   * random value to it.
   * This method modifies the state of the selected tile by setting its value to a
   * random value as defined by the Tile class's setRandomValue method.
   *
   * @pre emptyTiles is not empty; it contains one or more Tile objects
   *      representing the empty tiles on the game board.
   * @post One of the tiles in emptyTiles has been selected and its value has been
   *       set to a random value.
   */
  private void generateRandomValues() {
    int randomIndex = (int) (Math.random() * emptyTiles.size());
    TileModel randomTile = emptyTiles.get(randomIndex);

    randomTile.random();
  }

  /**
   * Prints the current state of the game board to the console if the application
   * is in development mode.
   * This method is intended for debugging purposes to visualize the board's
   * configuration.
   */
  private void printBoard() {
    // Check if the logger is in development mode. If true, print the board to the
    // console.
    if (Logger.isDevelopment()) {
      String boardString = "";

      // top border of the board.
      boardString += "+----+----+----+----+\n";

      // Iterate over each row of the board.
      for (int row = 0; row < board.length; row++) {
        // left border of the current row.
        boardString += "|";

        // Iterate over each column within the current row.
        for (int col = 0; col < board[row].length; col++) {
          // Retrieve the value at the current position, defaulting to an empty string if
          // the value is null.
          Optional<TileValue> value = board[row][col].getValue();

          String tileValue = value.isEmpty() ? "" : value.get().toString();

          // value, padded to 4 characters, followed by the column separator.
          boardString += String.format("%4s|", tileValue);
        }

        // Move to the next row and put the row's bottom border.
        boardString += "\n+----+----+----+----+\n";
      }
      System.out.println(boardString);
    }
  }

  /**
   * Shifts the game board in the specified direction by calling the
   * {@code shiftOperation} method with the appropriate parameters. This method
   * handles the logic of shifting tiles based on the player's input (WASD keys).
   * It prevents any actions from being performed if the direction is null, which
   * typically occurs when an unrecognized or unintended key press is detected.
   *
   * The method uses a switch statement to determine which direction was specified
   * and passes the correct boolean parameters to the {@code shiftOperation}
   * method. The first boolean parameter indicates whether tiles should be shifted
   * upwards or downwards, and the second parameter indicates whether tiles should
   * be shifted left or right.
   *
   * @pre The game board is initialized and contains tiles that can be shifted.
   * @post The game board is updated according to the direction specified, if the
   *       direction
   *       is not null. The method does not modify the board if the direction is
   *       null.
   *
   * @param direction A {@code Direction} enum representing the directional key
   *                  pressed (WASD).
   *                  Valid directions are UP, DOWN, LEFT, RIGHT. If null, no
   *                  action is performed.
   *
   * @return A boolean indicating whether any tiles were actually shifted on the
   *         board:
   *         {@code true} if the board state changed, {@code false} if no changes
   *         occurred
   *         (e.g., due to a null direction or no available shifts).
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

  /**
   * Merges tiles on the game board in the specified direction. This method is
   * generalized to handle merges from both rows and columns, as well as
   * collapsing towards either end of the board. It uses two pointers within each
   * row or column to determine the tile merge behavior:
   * <ul>
   * <li><strong>Tile merges with empty space:</strong> The tile moves into the
   * empty space.</li>
   * <li><strong>Tile merges with same-value tile:</strong> The tiles combine into
   * a higher-value tile.</li>
   * <li><strong>Tile merges with a different-value tile:</strong> The tile moves
   * to the adjacent space instead.</li>
   * </ul>
   * If any tiles are merged or moved, a boolean flag is set to true to indicate
   * that some action occurred, and this flag is returned at the end of the
   * method.
   *
   * @param collapseTowards0 Indicates the direction of collapse:
   *                         <ul>
   *                         <li><strong>true:</strong> Tiles move towards index 0
   *                         (collapsed towards the start).</li>
   *                         <li><strong>false:</strong> Tiles move towards the
   *                         end of the array.</li>
   *                         </ul>
   *
   * @param loopOverCols     Indicates whether the operation should be performed
   *                         over columns:
   *                         <ul>
   *                         <li><strong>true:</strong> The operation loops over
   *                         columns.</li>
   *                         <li><strong>false:</strong> The operation loops over
   *                         rows.</li>
   *                         </ul>
   *
   * @return A boolean flag indicating whether any tiles were merged or moved
   *         during the operation.
   */
  private boolean shiftOperation(boolean collapseTowards0, boolean loopOverCols) {
    // Flag to indicate if any tiles have been merged or moved during this process.
    boolean somethingHappened = false;

    // Iterate over each row or column that we need to merge (depending on the
    // context). `toMerge` acts as an index representing either a row or a column.
    for (int toMerge = 0; toMerge < board.length; toMerge++) {

      // Determine the direction of merging based on `collapseTowards0`:
      // - If true, rows/cols collapse towards position 0, so pointers move from right
      // to left.
      // - If false, rows/cols collapse towards the end, so pointers move from left to
      // right.
      final int OFFSET = (collapseTowards0) ? 1 : -1;

      // `first` points to the tile that we're trying to merge into or move next to.
      // `next` points to the tile that we're examining for merging or moving.
      int first = (collapseTowards0) ? 0 : board.length - 1;
      int next = (collapseTowards0) ? 1 : board.length - 2;

      // Loop through tiles in the current row/column to perform merging or moving.
      while (true) {
        // Define loop condition based on the direction of the collapse.
        boolean loopCondition = (collapseTowards0) ? (next < board.length) : (next >= 0);
        if (!loopCondition) {
          break; // Exit loop if no more tiles to process in this row/column.
        }

        // Fetch the `nextTile` based on whether we're iterating over columns or rows.
        TileModel nextTile = (loopOverCols) ? board[next][toMerge] : board[toMerge][next];

        // If `nextTile` is blank, move the `next` pointer in the appropriate direction.
        if (nextTile.isBlank()) {
          next += OFFSET;
          continue;
        }

        // Fetch the `firstTile` based on whether we're iterating over columns or rows.
        TileModel firstTile = (loopOverCols) ? board[first][toMerge] : board[toMerge][first];

        // If values of `nextTile` and `firstTile` are equal, we can merge them.
        if (nextTile.equals(firstTile)) {
          somethingHappened = true;

          // Update `firstTile` with the next value in the sequence.
          firstTile.setValue(firstTile.getNextValue());
          // Notify listeners about the score change.
          this.notifyScoreChanged(firstTile.getNumericValue());
          // Make `nextTile` blank after merging.
          nextTile.makeBlank();

          // Move `first` pointer and play the merge sound.
          first += OFFSET;
          Audio.MERGE_SOUND.play();
        }
        // If `firstTile` is blank, move `nextTile` to `firstTile`'s position.
        else if (firstTile.isBlank()) {
          somethingHappened = true;

          firstTile.setValue(nextTile.getValue());
          nextTile.makeBlank();
        }
        // If `firstTile` and `nextTile` are not equal and `firstTile` is not blank,
        // move `nextTile` next to `firstTile`.
        else {

          // Determine the target position for `nextTile` based on whether we're iterating
          // over columns or rows.
          int row = (!loopOverCols) ? toMerge : (first + OFFSET);
          int col = (loopOverCols) ? toMerge : (first + OFFSET);

          // Swap the positions of `nextTile` and the tile at the target position.
          if (swap(board[row][col], nextTile)) {
            somethingHappened = true;
          }
          first += OFFSET;
        }
        // Move `next` pointer to the next tile to process.
        next += OFFSET;
      }
    }

    // If any changes were made to the board, update the move count and notify
    // listeners.
    if (somethingHappened) {
      this.moves.set(this.moves.get() + 1);
      for (GameBoardListener listener : this.listeners) {
        listener.tileMoved();
      }
    }

    // Return the flag indicating if any changes were made to the board during this
    // process.
    Logger.println("Something Happened: " + somethingHappened);
    return somethingHappened;
  }

  /**
   * Swaps the values of two given tiles, {@code t1} and {@code t2}.
   *
   * The method exchanges the values of the two tiles if they are not the same.
   * Specifically, it copies the value from {@code t2} into {@code t1}, and the
   * value from {@code t1} into {@code t2}.
   *
   * @pre {@code t1} and {@code t2} are not null and are part of the game board.
   * @post The values of {@code t1} and {@code t2} have been swapped if they were
   *       different; otherwise, the board remains unchanged.
   * @param t1 the first tile involved in the swap. This tile's value will be
   *           replaced with the value of the second tile.
   * @param t2 the second tile involved in the swap. This tile's value will be
   *           replaced with the value of the first tile.
   *
   * @return {@code true} if the swap occurred (i.e., the values of {@code t1} and
   *         {@code t2} were different and, as a result, swapped); {@code false}
   *         if no swap was performed because both tiles already had the same
   *         value.
   */
  private boolean swap(TileModel t1, TileModel t2) {
    Optional<TileValue> temp = t1.getValue();
    t1.setValue(t2.getValue());
    t2.setValue(temp);
    return t1 != t2;
  }

  /**
   * Checks if the game is in an end state by determining if there are no adjacent
   * tiles with the same value. This method implements a brute-force approach to
   * iterate through each tile and compare it with its neighboring tiles (up,
   * down, left, right). If any two adjacent tiles have the same value, it
   * indicates that the player can make a move.
   * 
   * @pre The game board (board) must be initialized with valid Tile objects and
   *      have at least one row and one column.
   * @post The state of the game board remains unchanged.
   *
   * @return true if the player cannot make any more moves (i.e., there are no
   *         adjacent tiles with the same value), false otherwise (i.e., there are
   *         adjacent tiles with the same value).
   */
  private boolean testGameEndMethod() {
    // Iterate through each row of the game board.
    for (int r = 0; r < board.length; r++) {
      // Iterate through each column of the current row.
      for (int c = 0; c < board[0].length; c++) {
        // Retrieve the current tile at position (r, c).
        TileModel thisTile = board[r][c];

        // Create an array to hold up to 4 neighboring tiles (up, down, left, right).
        TileModel[] toCompare = new TileModel[4];

        // Check if there is a tile below the current tile and store it.
        if (r + 1 < board.length) {
          toCompare[0] = board[r + 1][c];
        }
        // Check if there is a tile above the current tile and store it.
        if (r - 1 >= 0) {
          toCompare[1] = board[r - 1][c];
        }
        // Check if there is a tile to the right of the current tile and store it.
        if (c + 1 < board[0].length) {
          toCompare[2] = board[r][c + 1];
        }
        // Check if there is a tile to the left of the current tile and store it.
        if (c - 1 >= 0) {
          toCompare[3] = board[r][c - 1];
        }

        // Iterate through all the neighboring tiles.
        for (TileModel t : toCompare) {
          // If a neighboring tile is found and is the same as the current tile, return
          // false.
          if (t != null && thisTile.equals(t)) {
            return false;
          }
        }
      }
    }
    // If no neighboring tiles are the same as any tile on the board, return true.
    return true;
  }

  /**
   * Retrieves the total number of moves made on the game board.
   *
   * @return The current count of moves.
   */
  public int getMoves() {
    return this.moves.get();
  }

  /**
   * Enables the recording of keystrokes.
   *
   * @post Keystroke recording is enabled.
   */
  public void enableKeystrokeRecording() {
    this.shouldRecordKeystrokes = true;
  }

  /**
   * Disables the recording of keystrokes.
   *
   * @post Keystroke recording is disabled.
   */
  public void disableKeystrokeRecording() {
    this.shouldRecordKeystrokes = false;
  }

  /**
   * Checks if keystroke recording is enabled.
   *
   * @return True if keystroke recording is enabled, false otherwise.
   */
  public boolean shouldRecordKeystrokes() {
    return this.shouldRecordKeystrokes;
  }

  /**
   * Adds a {@link GameBoardListener} to the list of listeners that will be
   * notified of changes or events related to the game board.
   *
   * @pre The provided listener is not null and is not already added to the list
   *      of listeners.
   * @post The listener is added to the list of listeners, ensuring it will
   *       receive notifications.
   *
   * @param listener the {@link GameBoardListener} to be added. This listener will
   *                 be notified
   *                 of any changes or events that occur on the game board.
   */
  public void addGameBoardListener(GameBoardListener listener) {
    listeners.add(listener);
  }

  /**
   * Notifies all registered {@link GameBoardListener}s that the game has ended.
   * This method iterates through the list of listeners and calls their
   * {@link GameBoardListener#gameOver()} method to inform them of the game's
   * termination.
   *
   * @pre The listeners list is initialized and may contain zero or more
   *      {@link GameBoardListener} instances.
   * @post Each listener in the list has been notified of the game over event.
   */
  private void notifyScoreChanged(int diff) {
    for (GameBoardListener listener : this.listeners) {
      listener.scoreChanged(diff);
    }
  }

  /**
   * Handles the movement of tiles in the specified direction on the game board.
   * This method shifts the tiles, updates the list of blank tiles, and generates
   * a new random tile if any tiles moved. It also checks if the game has ended
   * when the board is full.
   *
   * @post The tiles are shifted in the specified direction, the list of blank
   *       tiles is updated, a new random tile is generated if necessary, and the
   *       game state is checked for completion.
   * @param direction The direction in which the tiles should be shifted (UP,
   *                  DOWN, LEFT, RIGHT).
   */
  public void handleDirection(Direction direction) {

    // Shift the tiles in the specified direction and check if any tiles moved.
    boolean somethingHappened = shift(direction);

    // Update the list of blank tiles, which helps determine if the game is over.
    updateBlankTiles();

    // If any tiles moved, generate a new random tile.
    if (somethingHappened) {
      generateRandomValues();

      // If there is only one blank tile remaining (indicating the board is full),
      // check if the game has ended.
      if (emptyTiles.size() == 1) {
        if (testGameEndMethod()) {
          // Print the final game board state.
          printBoard();

          // Notify the game board that the game is over.
          this.notifyGameOver();
        }
      }
    }

    // Print the current state of the game board after the key press.
    printBoard();
  }

  /**
   * Notifies all registered {@link GameBoardListener}s that the game has ended.
   * This method iterates through the list of listeners and calls their
   * {@link GameBoardListener#gameOver()} method to inform them of the game's
   * termination.
   *
   * @pre The listeners list is initialized and may contain zero or more
   *      {@link GameBoardListener} instances.
   * @post Each listener in the list has been notified of the game over event.
   */
  private void notifyGameOver() {
    for (GameBoardListener listener : this.listeners) {
      listener.gameOver();
    }
  }

  /**
   * Retrieves the value of the tile at the specified row and column on the game
   * board.
   *
   * @pre The indices i and j are within the valid range of the board dimensions.
   * @param i the row index of the tile (0-based index)
   * @param j the column index of the tile (0-based index)
   *
   * @returns An Optional containing the value of the tile at position (i, j) if
   *          it exists, otherwise an empty Optional.
   */
  public Optional<TileValue> getValue(int i, int j) {
    return this.board[i][j].getValue();
  }
}
