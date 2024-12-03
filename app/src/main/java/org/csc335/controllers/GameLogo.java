package org.csc335.controllers;

import java.util.ArrayList;
import java.util.List;

import org.csc335.entity.GameMode;
import org.csc335.interfaces.DrawerMenuActionListener;
import org.csc335.interfaces.GameListener;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameLogo extends VBox implements GameListener {

  private List<DrawerMenuActionListener> listeners;

  @FXML
  private HBox gameModeContainer;

  @FXML
  private FAIcon icon;

  @FXML
  private Label modeLabel;

  public GameLogo() {
    super();

    this.listeners = new ArrayList<>();
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/GameLogo.fxml"));

    loader.setRoot(this);
    loader.setController(this);
    this.getStylesheets().add(this.getClass().getResource("/css/gamelogo.css").toExternalForm());

    try {
      loader.load();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void gameModeChanged(GameMode mode) {
    this.icon.setIcon(mode.ICON);
    this.modeLabel.setText(mode.TITLE);
    if (this.gameModeContainer.getStyleClass().size() > 1) {
      this.gameModeContainer.getStyleClass().set(1, "game-mode-" + mode.toCSSSuffix());
    } else {
      this.gameModeContainer.getStyleClass().add("game-mode-" + mode.toCSSSuffix());
    }
  }

  @FXML
  private void openMenu() {
    for (DrawerMenuActionListener listener : this.listeners) {
      listener.menuClick();
    }
  }

  public void addMenuActionListener(DrawerMenuActionListener listener) {
    this.listeners.add(listener);
  }
}
