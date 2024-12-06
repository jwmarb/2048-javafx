package org.csc335.models;

import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.MoveCounterListener;
import org.csc335.interfaces.Resettable;

/**
 * Represents a model for counting moves, implementing the Resettable interface.
 * This class maintains a count of moves and provides functionality to reset the
 * count.
 */
public class MoveCounterModel implements Resettable {
  public static final int TOTAL_MOVES = 1000;
  private int movesMade;
  private List<MoveCounterListener> listeners;

  public MoveCounterModel() {
    this.movesMade = 0;
    this.listeners = new ArrayList<>();
  }

  /**
   * Sets the number of moves that have been made.
   *
   * @pre The input value should be a non-negative integer.
   * @post The number of moves made is updated to the specified value.
   * @param moves The number of moves to set.
   */
  public void setMovesMade(int moves) {
    this.movesMade = moves;
    for (MoveCounterListener listener : this.listeners) {
      listener.userMoved(this.getRemainingMoves());
    }
  }

  /**
   * Returns the number of remaining moves as a string.
   *
   * @return The number of remaining moves as a string.
   */
  public Integer getRemainingMoves() {
    return (TOTAL_MOVES - this.movesMade);
  }

  /**
   * Checks if there are no more moves remaining.
   *
   * @return True if no more moves are remaining, otherwise false.
   */
  public boolean hasNoMoreMoves() {
    return TOTAL_MOVES - this.movesMade == 0;
  }

  /**
   * Resets the number of moves made to zero.
   *
   */
  public void reset() {
    this.movesMade = 0;
  }

  /**
   * Adds a listener to the list of listeners that are notified of move count
   * changes.
   *
   * @param listener The MoveCounterListener to be added.
   */
  public void addListener(MoveCounterListener listener) {
    this.listeners.add(listener);
  }
}
