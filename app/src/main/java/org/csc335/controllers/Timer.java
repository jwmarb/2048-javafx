package org.csc335.controllers;

import java.time.Duration;
import org.csc335.interfaces.TimerListener;
import org.csc335.models.TimerModel;
import org.csc335.util.EZLoader;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Timer extends HBox {

  private static final String timerWarning = "timer-warning";

  @FXML
  private Label timerLabel;

  private TimerModel model;

  public Timer() {
    super(); // Initialize the superclass components.
    EZLoader.load(this, Timer.class); // Load resources and settings specific to the Timer class.
    this.model = new TimerModel(); // Create a new instance of the TimerModel to manage timer data.
    this.model.addListener(new TimerListener() { // Add a listener to the model to handle timer changes.
      public void timerChanged(Duration timeLeft) { // This method is called when the timer value changes.
        Timer.this.timerLabel.setText(Timer.this.getTime(timeLeft)); // Update the timer label with the new time.
        if (timeLeft.getSeconds() <= 60 && !Timer.this.timerLabel.getStyleClass().contains(timerWarning)) {
          // If time left is 60 seconds or less and the warning style is not already
          // applied, add the warning style.
          Timer.this.timerLabel.getStyleClass().add(timerWarning);
        } else {
          // Otherwise, remove the warning style if it is applied.
          Timer.this.timerLabel.getStyleClass().remove(timerWarning);
        }
      }
    });
    this.timerLabel.setText(this.getTime(this.model.getTimeLeft())); // Initialize the timer label with the current
                                                                     // time.
  }

  /**
   * Formats the given Duration into a "MM:SS" string representation.
   *
   * @pre timeLeft is a valid Duration object whose time is >= 0.
   * @param timeLeft the Duration object representing the time left.
   *
   * @return A string formatted as "MM:SS" where MM is the minutes part and SS is
   *         the seconds part of the Duration.
   */
  private String getTime(Duration timeLeft) {
    return String.format("%02d:%02d", timeLeft.toMinutesPart(), timeLeft.toSecondsPart());
  }

  /**
   * Checks if the timer is currently active.
   *
   * @return A boolean value indicating whether the timer is active (true) or not
   *         (false).
   */
  public boolean isTimerActive() {
    return !this.model.isDone();
  }

  /**
   * Starts the timer by invoking the start method on the associated model.
   *
   * @post The timer begins counting down
   */
  public void startTimer() {
    this.model.start();
  }

  /**
   * Resets the timer to its initial state.
   *
   * @pre The timer is in any state (running, paused, or stopped).
   * @post The timer is reset to its initial state, and any ongoing
   *       operations are stopped.
   */
  public void resetTimer() {
    this.model.reset();
  }

  /**
   * Stops the timer.
   * 
   * @pre The timer is running
   */
  public void stopTimer() {
    this.model.stop();
  }

  /**
   * Adds a TimerListener to the model to receive timer events.
   *
   * @pre The model is initialized and capable of managing listeners.
   * @post The provided TimerListener is added to the model's list of listeners.
   * @param listener The TimerListener instance to be added. This listener will
   *                 be notified of timer events.
   */
  public void addTimerListener(TimerListener listener) {
    this.model.addListener(listener);
  }

}
