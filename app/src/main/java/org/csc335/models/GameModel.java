package org.csc335.models;

import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.GameListener;
import org.csc335.javafx_entity.GameMode;

/**
 * Represents the model component of the 2048 game, managing the game's state
 * and logic. This class holds the current game mode. It provides methods to
 * update and retrieve the game mode, and notifies listeners of those changes.
 */
public class GameModel {
  private List<GameListener> listeners;
  private GameMode gameMode;

  public GameModel() {
    this.listeners = new ArrayList<>();
    this.setGameMode(GameMode.TRADITIONAL);
  }

  /**
   * Sets the game mode for the current game session.
   *
   * @post The gameMode of the current game session has been updated to the
   *       specified mode, and all dependent components have been notified of the
   *       change.
   *
   * @param mode The new game mode to be set for the game session.
   */
  public void setGameMode(GameMode mode) {
    this.gameMode = mode;
    this.notifyDependencies();
  }

  /**
   * Retrieves the current game mode of the game.
   *
   * @return The current game mode as an instance of GameMode.
   */
  public GameMode getGameMode() {
    return this.gameMode;
  }

  /**
   * Notifies all registered GameListeners about a change in the game mode.
   */
  private void notifyDependencies() {
    for (GameListener listener : this.listeners) {
      listener.gameModeChanged(this.gameMode);
    }
  }

  /**
   * Adds a GameListener to the list of listeners.
   *
   * @post The listener is added to the list of listeners.
   * @param listener The GameListener to be added.
   */
  public void addListener(GameListener listener) {
    this.listeners.add(listener);
  }
}
