package org.csc335.controllers;

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

    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Scoreboard.fxml"));
    loader.setRoot(this);
    loader.setController(this);
    this.getStylesheets().add(this.getClass().getResource("/css/scoreboard.css").toExternalForm());

    this.scoreVal = new SimpleIntegerProperty();

    try {
      loader.load();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void addScore(int scoreVal) {
    this.scoreVal.setValue(this.scoreVal.getValue() + scoreVal);
    this.score.setText(String.valueOf(this.scoreVal.get()));
    this.best.setText(String.valueOf(Math.max(this.scoreVal.get(), Integer.parseInt(best.getText()))));
  }

}
