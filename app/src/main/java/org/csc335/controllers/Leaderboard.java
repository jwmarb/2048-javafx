package org.csc335.controllers;

import org.csc335.entity.LeaderboardModel;
import org.csc335.navigation.Navigation;
import org.csc335.util.EZLoader;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class Leaderboard extends BorderPane {

  @FXML
  private Label top10;

  private static LeaderboardModel model = new LeaderboardModel();

  public Leaderboard() {
    super();

    EZLoader.load(this, Leaderboard.class);

    this.top10.setText(model.toString());
  }

  @FXML
  public void goBack() {
    Navigation.goBack();
  }

  // set the top five scores
  public static void addLeaderboardScore(int newScore) {
    model.writeNewPlayerScore(newScore);

  }
}
