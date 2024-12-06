package org.csc335.controllers;

import org.csc335.entity.GameMode;
import org.csc335.util.EZLoader;

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

  private GameMode mode; // this is the model

  public DrawerOption(String title, String description, String icon, GameMode mode) {
    super();
    EZLoader.load(this, DrawerOption.class);

    this.mode = mode;
    this.icon.setIcon(icon);
    this.title.setText(title);
    this.description.setText(description);
  }

  /**
   * Selects the option
   */
  public void select() {
    this.getStyleClass().add("drawer-option-selected");
  }

  /**
   * Deselects the option
   */
  public void deselect() {
    this.getStyleClass().remove("drawer-option-selected");
  }

  /**
   * Determines whether the current DrawerOption is selected.
   *
   * @returns true if the DrawerOption's style class contains
   *          "drawer-option-selected", false otherwise.
   */
  public boolean isSelected() {
    return this.getStyleClass().contains("drawer-option-selected");
  }

  /**
   * Gets the option (aka mode) of this drawer option
   * 
   * @return The gamemode value of this option
   */
  public GameMode getMode() {
    return this.mode;
  }
}