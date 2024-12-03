package org.csc335.controllers;

import org.csc335.entity.GameMode;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/DrawerOption.fxml"));

    this.mode = mode;

    loader.setRoot(this);
    loader.setController(this);

    this.getStylesheets().add(this.getClass().getResource("/css/draweroption.css").toExternalForm());

    try {
      loader.load();
      this.icon.setIcon(icon);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

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