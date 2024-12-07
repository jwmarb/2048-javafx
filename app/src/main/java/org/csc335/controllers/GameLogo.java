package org.csc335.controllers;

import org.csc335.interfaces.DrawerMenuActionListener;
import org.csc335.interfaces.GameListener;
import org.csc335.javafx_entity.GameMode;
import org.csc335.models.GameLogoModel;
import org.csc335.util.EZLoader;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents the graphical user interface component for displaying the game
 * logo. This class extends VBox and implements the GameListener interface to
 * handle game-related events.
 */
public class GameLogo extends VBox implements GameListener {

  private GameLogoModel model;

  @FXML
  private HBox gameModeContainer;

  @FXML
  private FAIcon icon;

  @FXML
  private Label modeLabel;

  public GameLogo() {
    super();
    this.model = new GameLogoModel();
    EZLoader.load(this, GameLogo.class);
  }

  /**
   * Updates the game mode-related UI components based on the selected game mode.
   * This method sets the icon and label text according to the provided game mode,
   * and adjusts the style class of the game mode container to reflect the current
   * game mode.
   *
   * @post The icon and label text reflect the new game mode; the
   *       gameModeContainer's style class
   *       is updated to include the CSS suffix corresponding to the new game
   *       mode.
   *
   * @param mode The new game mode to be set. This parameter determines the icon,
   *             label text, and CSS style class suffix that will be applied.
   */
  public void gameModeChanged(GameMode mode) {
    this.icon.setIcon(mode.ICON);
    this.modeLabel.setText(mode.TITLE);
    if (this.gameModeContainer.getStyleClass().size() > 1) {
      this.gameModeContainer.getStyleClass().set(1, "game-mode-" + mode.toCSSSuffix());
    } else {
      this.gameModeContainer.getStyleClass().add("game-mode-" + mode.toCSSSuffix());
    }
  }

  /**
   * Opens the main menu by invoking the open menu functionality in the model.
   *
   * @post The main menu is opened.
   */
  @FXML
  private void openMenu() {
    this.model.invokeOpenMenu();
  }

  /**
   * Adds a DrawerMenuActionListener to the model.
   *
   * @param listener The DrawerMenuActionListener to be added to the model.
   */
  public void addMenuActionListener(DrawerMenuActionListener listener) {
    this.model.addMenuActionListener(listener);
  }
}
