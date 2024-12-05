package org.csc335.controllers;

import org.csc335.entity.GameMode;
import org.csc335.util.EZLoader;
import org.csc335.util.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DrawerOption extends HBox {
  @FXML
  private FAIcon icon;

  @FXML
  private Label title;

  @FXML
  private Label description;

  private GameMode mode;

  public DrawerOption(String title, String description, String icon, GameMode mode) {
    super();
    EZLoader.load(this, DrawerOption.class);

    this.mode = mode;
    this.icon.setIcon(icon);
    this.title.setText(title);
    this.description.setText(description);
  }

  public void select() {
    this.getStyleClass().add("drawer-option-selected");
  }

  public void deselect() {
    this.getStyleClass().remove("drawer-option-selected");
  }

  public boolean isSelected() {
    return this.getStyleClass().contains("drawer-option-selected");
  }

  public GameMode getMode() {
    return this.mode;
  }
}