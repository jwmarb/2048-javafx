package org.csc335.controllers;

import org.controlsfx.control.PopOver;
import org.csc335.entity.GameMode;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Game extends BorderPane {

  @FXML
  private GameBoard gameBoard;

  @FXML
  private Scoreboard scoreboard;

  private GameMode mode;

  public Game() {
    this.mode = GameMode.TRADITIONAL;

    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Game.fxml"));
    loader.setRoot(this);
    loader.setController(this);
    this.getStylesheets().add(this.getClass().getResource("/css/game.css").toExternalForm());

    try {
      loader.load();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    this.gameBoard.setParent(this);
  }

  public void addScore(int score) {
    scoreboard.addScore(score);
  }

  /**
   * Invoked when the user presses "New game"
   * 
   * TODO: Add a warning popup, but for now make this reset the game
   */
  @FXML
  public void newGame() {

  }

  @FXML
  public void openMenu() {
    if (this.leftProperty().isNull().get()) {
      this.setLeft(new DrawerMenu());
    } else {
      this.setLeft(null);
    }
  }
}
