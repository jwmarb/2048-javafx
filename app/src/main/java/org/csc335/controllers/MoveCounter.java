package org.csc335.controllers;

import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.MoveCounterListener;

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
    this.load();
    this.listeners = new ArrayList<>();
    this.counter.setText(MOVES + "");
  }

  private void load() {
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/MoveCounter.fxml"));
    loader.setRoot(this);
    loader.setController(this);

    this.getStylesheets().add(this.getClass().getResource("/css/movecounter.css").toExternalForm());

    try {
      loader.load();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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
