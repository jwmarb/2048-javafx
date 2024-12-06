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

public class TimerModel implements Resettable {
  public static final int DEFAULT_TIME = 15;

  private List<TimerListener> listeners;

  private Duration duration;

  private Timeline timer;

  private boolean isDone;

  public TimerModel() {
    this.isDone = true;
    this.listeners = new ArrayList<>();
    this.timer = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), ev -> {
      this.decrementSecond();
    }));
    this.timer.setOnFinished(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        TimerModel.this.isDone = true;
      }

    });

    this.timer.setCycleCount(Animation.INDEFINITE);
    this.reset();
  }

  public boolean isDone() {
    return this.isDone;
  }

  private void decrementSecond() {
    this.duration = this.duration.minusSeconds(1);
    this.handleDurationChange();
  }

  public Duration getTimeLeft() {
    return this.duration;
  }

  public void reset() {
    this.duration = Duration.ofMinutes(DEFAULT_TIME);
    this.handleDurationChange();
  }

  public void stop() {
    this.timer.stop();
    this.isDone = true;
  }

  public void start() {
    this.timer.play();
    this.isDone = false;
  }

  private void handleDurationChange() {
    if (this.duration.isZero()) {
      this.stop();
    }
    for (TimerListener listener : this.listeners) {
      listener.timerChanged(this.duration);
    }
  }

  public void addListener(TimerListener listener) {
    this.listeners.add(listener);
  }
}
