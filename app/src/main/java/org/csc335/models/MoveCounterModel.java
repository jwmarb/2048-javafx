package org.csc335.models;

import org.csc335.interfaces.Resettable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

/**
 * Represents a model for counting moves, implementing the Resettable interface.
 * This class maintains a count of moves and provides functionality to reset the
 * count.
 */
public class MoveCounterModel implements Resettable {
  public static final int TOTAL_MOVES = 1000;
  private IntegerProperty movesMade;

  public MoveCounterModel() {
    this.movesMade = new SimpleIntegerProperty(0);
  }

  /**
   * Sets the number of moves that have been made.
   *
   * @pre The input value should be a non-negative integer.
   * @post The number of moves made is updated to the specified value.
   * @param moves The number of moves to set.
   */
  public void setMovesMade(int moves) {
    this.movesMade.set(moves);
  }

  /**
   * Adds a listener to be notified when the number of moves changes.
   *
   * @pre The listener is not null.
   * @post The provided listener is added to the list of listeners.
   * @param listener The listener to add.
   */
  public void addListener(ChangeListener<? super Number> listener) {
    this.movesMade.addListener(listener);
  }

  /**
   * Returns the number of remaining moves as a string.
   *
   * @return The number of remaining moves as a string.
   */
  public String getRemainingMoves() {
    return (TOTAL_MOVES - this.movesMade.get()) + "";
  }

  /**
   * Checks if there are no more moves remaining.
   *
   * @return True if no more moves are remaining, otherwise false.
   */
  public boolean hasNoMoreMoves() {
    return TOTAL_MOVES - this.movesMade.get() == 0;
  }

  /**
   * Resets the number of moves made to zero.
   *
   */
  public void reset() {
    this.movesMade.set(0);
  }

}
