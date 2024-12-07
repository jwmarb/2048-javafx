package org.csc335.models;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.Resettable;
import org.csc335.interfaces.TimerListener;

/**
 * Represents a timer model that manages a countdown timer with the ability to
 * start, stop, reset, and notify listeners of time changes.
 * The timer counts down from a default duration of 15 minutes and can be
 * controlled programmatically through its methods.
 */
public class TimerModel implements Resettable {
  public static final int DEFAULT_TIME = 15;

  private List<TimerListener> listeners;

  private Duration duration;

  private boolean isDone;

  public TimerModel() {
    // Initialize the timer as done initially.
    this.isDone = true;

    // Create a list to hold listeners for timer events.
    this.listeners = new ArrayList<>();

    // Reset the timer to its default duration.
    this.reset();
  }

  /**
   * Sets the completion status of the TimerModel.
   *
   * @param isDone The new completion status for the TimerModel. True if the task
   *               associated with this model is considered completed, false
   *               otherwise.
   */
  public void setIsDone(boolean isDone) {
    this.isDone = isDone;
  }

  /**
   * Checks if the timer has completed its countdown.
   *
   * @returns A boolean value indicating whether the timer has finished counting
   *          down. Returns true if the timer is done, otherwise false.
   */
  public boolean isDone() {
    return this.isDone;
  }

  /**
   * Decrements the duration by one second.
   *
   * @post The duration is reduced by one second; handleDurationChange() is
   *       invoked to process the change.
   */
  public void decrementSecond() {
    this.duration = this.duration.minusSeconds(1);
    this.handleDurationChange();
  }

  /**
   * Retrieves the remaining duration of the timer.
   *
   * @returns The remaining duration of the timer as a Duration object.
   */
  public Duration getTimeLeft() {
    return this.duration;
  }

  /**
   * Resets the timer duration to the default time of 15 minutes.
   * This method sets the duration field to a new Duration object
   * representing the default time and then triggers a duration change
   * notification by calling handleDurationChange().
   *
   * @post The timer duration is set to the default time, and a duration
   *       change event has been handled.
   */
  public void reset() {
    this.duration = Duration.ofMinutes(DEFAULT_TIME);
    this.handleDurationChange();
  }

  /**
   * Handles the change in the duration of the timer.
   * If the duration is zero, the timer is stopped.
   * Notifies all registered TimerListeners about the change in the timer
   * duration.
   *
   * @pre The duration and listeners fields are properly initialized.
   * @post If duration is zero, the timer is stopped; all listeners are notified
   *       of the duration change.
   */
  private void handleDurationChange() {
    for (TimerListener listener : this.listeners) {
      listener.timerChanged(this.duration);
    }
  }

  /**
   * Adds a TimerListener to the list of listeners that will be notified
   * when timer events occur.
   *
   * @post The listener has been added to the list of listeners.
   * @param listener the TimerListener to be added; must not be null.
   */
  public void addListener(TimerListener listener) {
    this.listeners.add(listener);
  }
}
