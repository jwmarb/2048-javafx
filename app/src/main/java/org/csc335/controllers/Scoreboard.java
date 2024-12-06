package org.csc335.controllers;

import org.csc335.interfaces.Resettable;
import org.csc335.interfaces.ScoreboardListener;
import org.csc335.models.ScoreboardModel;
import org.csc335.util.EZLoader;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Represents a Scoreboard component that extends HBox and implements the
 * Resettable interface. This class is responsible for displaying and managing
 * the scores for 2048. It provides functionality to reset the scoreboard to its
 * initial state.
 */
public class Scoreboard extends HBox implements Resettable {

  @FXML
  private Label score;

  @FXML
  private Label best;

  private ScoreboardModel model;

  public Scoreboard() {
    super();
    this.model = new ScoreboardModel();
    EZLoader.load(this, Scoreboard.class);
    // Register a listener to update the scoreboard when the score changes.
    this.model.addListener(new ScoreboardListener() {
      // Update the displayed score and best score when the score changes.
      public void scoreChanged(int current, int best) {
        // Set the text of the score label to the current score.
        Scoreboard.this.score.setText(String.valueOf(current));
        // Set the text of the best score label to the best score.
        Scoreboard.this.best.setText(String.valueOf(best));
      }
    });
  }

  /**
   * Adds a new score to the scoreboard.
   *
   * @pre The scoreVal is a positive integer.
   * @post The scoreVal has been added to the scoreboard model.
   * @param scoreVal The score value to be added to the scoreboard.
   */
  public void addScore(int scoreVal) {
    this.model.addScore(scoreVal);
  }

  /**
   * Retrieves the current score from the model.
   *
   * @returns The current score as an integer.
   */
  public int getScore() {
    return this.model.getScore();
  }

  /**
   * Resets the scoreboard to its initial state.
   *
   * @post The scoreboard and its model are reset to their initial state.
   */
  public void reset() {
    this.model.reset();
  }
}
