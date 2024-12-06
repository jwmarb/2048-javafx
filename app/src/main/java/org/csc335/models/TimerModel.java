package org.csc335.models;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.Resettable;
import org.csc335.interfaces.TimerListener;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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

  private Timeline timer;

  private boolean isDone;

  public TimerModel() {
    // Initialize the timer as done initially.
    this.isDone = true;

    // Create a list to hold listeners for timer events.
    this.listeners = new ArrayList<>();

    // Set up a timeline to decrement the timer every second.
    this.timer = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), ev -> {
      this.decrementSecond();
    }));

    // Define the action to take when the timer finishes.
    this.timer.setOnFinished(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        // Mark the timer as done when it finishes.
        TimerModel.this.isDone = true;
      }

    });

    // Set the timer to repeat indefinitely.
    this.timer.setCycleCount(Animation.INDEFINITE);

    // Reset the timer to its default duration.
    this.reset();
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
  private void decrementSecond() {
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
   * Stops the timer and marks it as done.
   *
   * @pre The timer is currently running.
   * @post The timer has been stopped and isDone is set to true.
   */
  public void stop() {
    this.timer.stop();
    this.isDone = true;
  }

  /**
   * Starts the timer.
   *
   * @pre The timer is in a stopped state.
   * @post The timer is started and isDone is set to false.
   */
  public void start() {
    this.timer.play();
    this.isDone = false;
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
    if (this.duration.isZero()) {
      this.stop();
    }
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
