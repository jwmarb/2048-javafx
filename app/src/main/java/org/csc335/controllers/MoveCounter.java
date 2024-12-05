package org.csc335.controllers;

import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.MoveCounterListener;
import org.csc335.util.EZLoader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class MoveCounter extends HBox {

  private static int MOVES = 10;

  @FXML
  private Label counter;

  private List<MoveCounterListener> listeners;

  public MoveCounter() {
    super();
    EZLoader.load(this, MoveCounter.class);
    this.listeners = new ArrayList<>();
    this.counter.setText(MOVES + "");
  }

  public void totalMovesMade(int movesMade) {
    int remaining = MOVES - movesMade;
    this.counter.setText(remaining + "");

    if (remaining == 0) {
      for (MoveCounterListener listener : this.listeners) {
        listener.noMoreMovesLeft();
      }
    }
  }

  public void addMoveCounterListener(MoveCounterListener listener) {
    this.listeners.add(listener);
  }

  public void reset() {
    this.counter.setText(MOVES + "");
  }
}
