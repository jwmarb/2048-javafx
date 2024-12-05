package org.csc335.controllers;

import org.csc335.util.EZLoader;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Scoreboard extends HBox {

  @FXML
  private Label score;

  @FXML
  private Label best;

  private IntegerProperty scoreVal;

  public Scoreboard() {
    super();
    EZLoader.load(this, Scoreboard.class);
    this.scoreVal = new SimpleIntegerProperty();
  }

  public void addScore(int scoreVal) {
    this.scoreVal.setValue(this.scoreVal.getValue() + scoreVal);
    this.score.setText(String.valueOf(this.scoreVal.get()));
    this.best.setText(String.valueOf(Math.max(this.scoreVal.get(), Integer.parseInt(best.getText()))));
  }

  public int getScore() {
    return this.scoreVal.get();
  }

  public void reset() {
    // todo
    this.scoreVal.set(0);
    this.score.setText(String.valueOf(this.scoreVal.get()));
  }

}
