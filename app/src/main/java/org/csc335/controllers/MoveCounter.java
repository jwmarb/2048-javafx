package org.csc335.controllers;

import org.csc335.interfaces.MoveCounterListener;
import org.csc335.interfaces.Resettable;
import org.csc335.models.MoveCounterModel;
import org.csc335.util.EZLoader;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Represents a move counter user interface component that extends HBox.
 * This class is designed to display and manage a count of moves, typically
 * used in games or applications that require tracking of user actions.
 * It implements the Resettable interface to allow the move count to be reset.
 */
public class MoveCounter extends HBox implements Resettable {

  @FXML
  private Label counter;

  private MoveCounterModel model;

  public MoveCounter() {
    super();
    EZLoader.load(this, MoveCounter.class);
    this.model = new MoveCounterModel();
    this.counter.setText(this.model.getRemainingMoves().toString());
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
    this.model.addListener(new MoveCounterListener() {
      @Override
      public void userMoved(int movesLeft) {
        MoveCounter.this.counter.setText(MoveCounter.this.model.getRemainingMoves().toString());
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
    this.model.addListener(listener);
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
