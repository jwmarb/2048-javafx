package org.csc335.controllers;

import java.util.Optional;

import org.csc335.entity.Direction;
import org.csc335.entity.TileValue;
import org.csc335.interfaces.GameBoardListener;
import org.csc335.interfaces.Resettable;
import org.csc335.models.GameBoardModel;
import org.csc335.navigation.Navigation;
import org.csc335.util.EZLoader;
import org.csc335.util.Logger;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * Represents the game board in a grid layout.
 */
public class GameBoard extends GridPane implements Resettable {

  // Define the size of the game board (e.g., 4x4 for a standard 2048 game).
  private static final int SIZE = 4;

  private GameBoardModel model;

  private Tile[][] tiles;

  public GameBoard() {
    EZLoader.load(this, GameBoard.class);
    this.model = new GameBoardModel(SIZE);

    this.initializeTiles();
    this.initEventListeners();
  }

  /**
   * Enables the recording of keystrokes.
   *
   * @post Keystroke recording is enabled in the model.
   */
  public void enableKeystrokeRecording() {
    this.model.enableKeystrokeRecording();
  }

  /**
   * Disables the recording of keystrokes in the game model.
   *
   * @post Keystroke recording is disabled in the game model.
   *
   * @returns None
   */
  public void disableKeystrokeRecording() {
    this.model.disableKeystrokeRecording();
  }

  /**
   * Retrieves the total number of moves made in the game.
   *
   * @return The total number of moves made in the game.
   */
  public int getMoves() {
    return this.model.getMoves();
  }

  /**
   * Initializes event listeners for key press and key release events on the game
   * navigation. Specifically, it sets up handlers for the 'W', 'A', 'S', 'D'
   * keys, and the arrow keys to navigate the game board. The method ensures that
   * key presses are only processed when keystroke recording is enabled and
   * updates the game state accordingly.
   *
   * <p>
   * For key press events:
   * <ul>
   * <li>Determines the direction based on the key pressed.</li>
   * <li>Logs the key press for debugging purposes.</li>
   * <li>Shifts the tiles in the specified direction and checks if any tiles
   * moved.</li>
   * <li>Updates the list of blank tiles to determine if the game is over.</li>
   * <li>If tiles moved, generates a new random tile.</li>
   * <li>Checks if the game has ended if there is only one blank tile
   * remaining.</li>
   * <li>Logs additional information and prints the final game board state if the
   * game ends.</li>
   * <li>Notifies the game board that the game is over.</li>
   * <li>Prints the current state of the game board after the key press.</li>
   * </ul>
   *
   * <p>
   * For key release events:
   * <ul>
   * <li>Logs the key release for debugging purposes.</li>
   * </ul>
   *
   * @pre The Navigation node is properly initialized and added to the scene
   *      graph.
   *      The game board (GameBoard.this) is in a state where it can handle key
   *      events.
   *      The shouldRecordKeystrokes flag is set appropriately.
   * @post Key press and key release event handlers are set up for the Navigation
   *       node.
   *       The game board state is updated based on the key press events.
   *       Debugging information is logged for key press and key release events.
   */
  private void initEventListeners() {
    // Set up key press event handler for the game navigation.
    Navigation.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        // Determine the direction based on the key pressed.
        Direction direction = Direction.fromVal(event.getCode().getName());

        // Ignore the event if the key is not one of the navigation keys (WASD or arrow
        // keys)
        // or if we are not currently recording keystrokes.
        if (direction == null || !GameBoard.this.model.shouldRecordKeystrokes()) {
          return;
        }

        // Log the key press for debugging purposes.
        Logger.printf("PRESSED: %s\n", event.getCode().getName());

        GameBoard.this.model.handleDirection(direction);
        GameBoard.this.rerenderTiles();
      }
    });

    // Set up key release event handler for the game navigation.
    Navigation.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        // Log the key release for debugging purposes.
        Logger.printf("RELEASED: %s\n", event.getCode().getName());
      }
    });
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
    this.model.addGameBoardListener(listener);
  }

  /**
   * Resets the game board to its initial state. This involves resetting the
   * underlying model and then re-rendering the tiles on the board to reflect the
   * reset state.
   * 
   * @post The game board and its model are reset to their initial states.
   */
  public void reset() {
    this.model.reset();
    this.rerenderTiles();
  }

  /**
   * Rerenders the tiles on the game board by updating each tile's value based on
   * the current state of the game model. For each tile position, it retrieves the
   * corresponding value from the model and sets it on the tile. If no value is
   * present in the model for a given position, the tile is set to blank.
   *
   * @pre The game model is initialized and contains the current state of the
   *      board.
   *      The tiles array is properly initialized with Tile objects.
   * @post Each tile on the board reflects the current value from the game model.
   *       Tiles with no corresponding value in the model are set to blank.
   */
  private void rerenderTiles() {
    for (int i = 0; i < SIZE; ++i) {
      for (int j = 0; j < SIZE; ++j) {
        Tile tile = this.tiles[i][j];

        Optional<TileValue> value = this.model.getValue(i, j);
        if (value.isPresent()) {
          tile.setValue(value);
        } else {
          tile.makeBlank();
        }
      }
    }
  }

  /**
   * Initializes the game board tiles by creating a 2D array of Tile objects with
   * dimensions defined by the constant SIZE. Each tile is instantiated and added
   * to the game board at its corresponding position. After all tiles are
   * initialized, the board is rerendered to reflect the new tile setup.
   *
   * @post The tiles array is fully populated with Tile objects, and each tile is
   *       added to the game board at the correct position. The game board is
   *       rerendered.
   */
  private void initializeTiles() {
    this.tiles = new Tile[SIZE][SIZE];
    for (int i = 0; i < SIZE; ++i) {
      for (int j = 0; j < SIZE; ++j) {
        this.tiles[i][j] = new Tile();
        this.add(this.tiles[i][j], j, i);

      }
    }

    this.rerenderTiles();
  }
}