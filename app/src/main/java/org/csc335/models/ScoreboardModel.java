package org.csc335.models;

import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.Resettable;
import org.csc335.interfaces.ScoreboardListener;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents the model component of a Scoreboard controller, responsible for
 * managing the data and logic related to the scoreboard. This class implements
 * the Resettable interface, allowing the scoreboard data to be reset to its
 * initial state.
 */
public class ScoreboardModel implements Resettable {

  private List<ScoreboardListener> listeners;

  private IntegerProperty best;
  private IntegerProperty current;

  public ScoreboardModel() {
    this.listeners = new ArrayList<>();
    this.best = new SimpleIntegerProperty();
    this.current = new SimpleIntegerProperty();

    // Add a listener to the current score that updates the best score and notifies
    // all listeners of the change.
    this.current.addListener((__, ___, ____) -> {
      // Update the best score if the current score is higher.
      this.best.set(Math.max(this.best.get(), this.current.get()));

      // Notify each listener about the score change.
      for (ScoreboardListener listener : this.listeners) {
        listener.scoreChanged(this.current.get(), this.best.get());
      }
    });
  }

  /**
   * Retrieves the current score from the scoreboard.
   *
   * @returns The current score as an integer.
   */
  public int getScore() {
    return this.current.get();
  }

  /**
   * Adds the specified score to the current total score.
   *
   * @pre The score to be added is a positive integer.
   * @post The current score is updated by adding the specified score to it.
   * @param score The score value to be added to the current total.
   */
  public void addScore(int score) {
    this.current.set(this.current.get() + score);
  }

  /**
   * Resets the current score to zero.
   *
   * @post The current score is set to zero.
   */
  public void reset() {
    this.current.set(0);
  }

  /**
   * Adds a ScoreboardListener to the list of listeners.
   *
   * @post The specified listener is added to the listeners list.
   * @param listener the ScoreboardListener to be added. This listener
   *                 will receive notifications about changes in the scoreboard.
   */
  public void addListener(ScoreboardListener listener) {
    this.listeners.add(listener);
  }
}
