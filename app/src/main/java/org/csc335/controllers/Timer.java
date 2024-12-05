package org.csc335.controllers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.TimerListener;
import org.csc335.util.EZLoader;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Timer extends HBox {

  private static final int DEFAULT_TIME = 15;
  private static final String timerWarning = "timer-warning";

  private List<TimerListener> listeners;

  @FXML
  private Label timerLabel;

  private Duration duration;

  private Timeline timer;

  private boolean isDone;

  public Timer() {
    super();
    EZLoader.load(this, Timer.class);
    this.isDone = true;
    this.listeners = new ArrayList<>();
    this.duration = Duration.ofMinutes(DEFAULT_TIME);
    this.timer = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), ev -> {
      this.decrementSecond();
    }));
    this.timer.setOnFinished(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        Timer.this.isDone = true;
      }

    });
    this.timer.setCycleCount(Animation.INDEFINITE);
    Timer.this.handleDurationChange();
  }

  public boolean isTimerActive() {
    return !this.isDone;
  }

  private void decrementSecond() {
    Timer.this.duration = Timer.this.duration.minusSeconds(1);
    Timer.this.handleDurationChange();
  }

  public void startTimer() {
    this.timer.play();
    this.timerLabel.getStyleClass().remove(timerWarning);
    this.isDone = false;
  }

  public void resetTimer() {
    Timer.this.duration = Duration.ofMinutes(Timer.DEFAULT_TIME);
    Timer.this.handleDurationChange();
  }

  private void handleDurationChange() {
    this.timerLabel.setText(String.format("%02d:%02d", this.duration.toMinutesPart(), this.duration.toSecondsPart()));
    if (this.duration.isZero()) {
      for (TimerListener listener : this.listeners) {
        listener.timerFinished();
      }
      this.stopTimer();
    } else if (this.duration.getSeconds() <= 60) {
      this.timerLabel.getStyleClass().add(timerWarning);
    } else {
      this.timerLabel.getStyleClass().remove(timerWarning);
    }
  }

  public void stopTimer() {
    this.timer.stop();
    this.isDone = true;
  }

  public void addTimerListener(TimerListener listener) {
    this.listeners.add(listener);
  }

}
