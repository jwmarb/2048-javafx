package org.csc335.controllers;

import org.csc335.entity.LeaderboardModel;
import org.csc335.navigation.Navigation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class Leaderboard extends BorderPane {

  @FXML
  private Label top10;

  private LeaderboardModel model;

  public Leaderboard() {
    super();

    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Leaderboard.fxml"));
    this.getStylesheets().add(this.getClass().getResource("/css/leaderboard.css").toExternalForm());

    loader.setRoot(this);
    loader.setController(this);

    try {
      loader.load();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  public void goBack() {
    Navigation.goBack();
  }

  // set the top five scores
  public Leaderboard setTop10(String fileName, int newScore) {
    this.model = new LeaderboardModel();

    model.writeNewPlayerScore(fileName, newScore);

    this.top10.setText(model.load());

    return this;
  }
}
