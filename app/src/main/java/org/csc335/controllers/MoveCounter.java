package org.csc335.controllers;

import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.MoveCounterListener;
import org.csc335.interfaces.Resettable;
import org.csc335.models.MoveCounterModel;
import org.csc335.util.EZLoader;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class MoveCounter extends HBox implements Resettable {

  @FXML
  private Label counter;

  private MoveCounterModel model;

  private List<MoveCounterListener> listeners;

  public MoveCounter() {
    super();
    EZLoader.load(this, MoveCounter.class);
    this.model = new MoveCounterModel();
    this.listeners = new ArrayList<>();
    this.counter.setText(this.model.getRemainingMoves());
    this.initListeners();
  }

  /**
   * Initializes listeners for the model's changes. Specifically, it adds a
   * ChangeListener to the model that updates the counter text to reflect the
   * remaining moves whenever the model's move count changes. When the model
   * indicates that there are no more moves left, it notifies all registered
   * MoveCounterListeners by calling their noMoreMovesLeft method.
   *
   * @pre The model is properly initialized and has a method to add listeners.
   * @post A ChangeListener is added to the model, and the counter text is updated
   *       to reflect the remaining moves. If there are no more moves left, all
   *       registered MoveCounterListeners are notified.
   */
  private void initListeners() {
    this.model.addListener(new ChangeListener<Number>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        MoveCounter.this.counter.setText(MoveCounter.this.model.getRemainingMoves());

        if (MoveCounter.this.model.hasNoMoreMoves()) {
          for (MoveCounterListener listener : MoveCounter.this.listeners) {
            listener.noMoreMovesLeft();
          }
        }
      }

    });
  }

  /**
   * Sets the total number of moves made in the game.
   *
   * @post The number of moves made in the model is updated to the specified
   *       value.
   * @param movesMade the total number of moves made to be set in the model.
   */
  public void totalMovesMade(int movesMade) {
    this.model.setMovesMade(movesMade);
  }

  /**
   * Adds a listener to the list of move counter listeners.
   *
   * @post The provided listener is added to the list of listeners.
   * @param listener the MoveCounterListener to be added.
   */
  public void addMoveCounterListener(MoveCounterListener listener) {
    this.listeners.add(listener);
  }

  /**
   * Resets the move counter to its initial state.
   *
   * @post The move counter in the model is reset to zero.
   */
  public void reset() {
    this.model.reset();
  }
}
