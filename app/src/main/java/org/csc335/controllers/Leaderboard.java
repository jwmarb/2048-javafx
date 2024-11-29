package org.csc335.controllers;

import org.csc335.navigation.Navigation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class Leaderboard extends BorderPane {

  public Leaderboard() {
    super();

    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Leaderboard.fxml"));

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
}
