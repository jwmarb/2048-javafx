package org.csc335.controllers;

import org.csc335.entity.GameMode;
import org.csc335.interfaces.DrawerOptionListener;
import org.csc335.models.DrawerMenuModel;
import org.csc335.util.EZLoader;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Represents the DrawerMenu class, which extends VBox to provide a vertical
 * layout for a menu that can be used as a drawer in a user interface. This
 * class is designed to manage menu items and their interactions within a JavaFX
 * application.
 */
public class DrawerMenu extends VBox {

  // Duration for the mount animation of the drawer menu in milliseconds.
  private static final Duration MOUNT_DURATION = Duration.millis(150);

  @FXML
  private VBox container;

  private DrawerMenuModel model;

  public DrawerMenu(GameMode defaultMode) {
    EZLoader.load(this, DrawerMenu.class);
    this.model = new DrawerMenuModel();

    this.createOptions(defaultMode);
  }

  /**
   * Displays the drawer menu by making it visible and applying a fade-in and
   * slide-in animation from the left side.
   *
   * @pre The drawer menu is currently hidden.
   * @post The drawer menu is visible and has completed a fade-in and slide-in
   *       animation from the left side.
   */
  public void show() {
    this.model.setVisible();
    FadeTransition ft = new FadeTransition(MOUNT_DURATION, this);
    TranslateTransition tt = new TranslateTransition(MOUNT_DURATION, this);
    tt.setInterpolator(Interpolator.EASE_IN);

    ft.setFromValue(0.0);
    ft.setToValue(1.0);

    tt.setByX(-50);
    tt.setToX(0);

    ft.play();
    tt.play();
  }

  /**
   * Hides the drawer menu by performing a fade-out and slide-out animation. The
   * fade transition reduces the opacity from 1.0 to 0.0, while the translate
   * transition moves the menu 50 pixels to the left. Once the animation is
   * complete, the menu's hidden state is set in the model.
   *
   * @pre The drawer menu is visible
   * 
   * @post The drawer menu is hidden with a fade-out and slide-out animation;
   *       the model's hidden state is updated to reflect the change.
   */
  public void hide() {
    FadeTransition ft = new FadeTransition(MOUNT_DURATION, this);
    TranslateTransition tt = new TranslateTransition(MOUNT_DURATION, this);

    tt.setInterpolator(Interpolator.EASE_OUT);
    ft.setFromValue(1.0);
    ft.setToValue(0.0);

    tt.setByX(0);
    tt.setToX(-50);

    ft.play();
    tt.play();

    tt.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        DrawerMenu.this.model.setHidden();
      }
    });
  }

  /**
   * Initializes and populates the drawer menu with options for each game mode.
   * It creates a drawer option for each game mode, sets the default option based
   * on the provided default mode, and adds click event handlers to select the
   * corresponding game mode when an option is clicked.
   *
   * @post The drawer menu is populated with options for each game mode.
   *       The default option is selected based on the provided default mode or
   *       the first option if the default mode is not found.
   * @param defaultMode The game mode to be set as the default selected option.
   */
  private void createOptions(GameMode defaultMode) {
    GameMode[] modes = GameMode.values();
    DrawerOption defaultOption = null;

    // Iterate over each game mode to create corresponding drawer options
    for (GameMode mode : modes) {
      DrawerOption option = mode.createDrawerOption();
      // Set the default option if it matches the default mode
      if (defaultOption == null && option.getMode().equals(defaultMode)) {
        defaultOption = option;
      }
      // Add click event handler to set the selected mode when an option is clicked
      option.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          setSelected(option.getMode());
        }
      });
      // Add the option to the container
      container.getChildren().add(option);
    }
    // If a default option is found, set it as selected
    if (defaultOption != null) {
      this.setSelected(defaultOption.getMode());
    } else {
      // Otherwise, select the first option as the default selected option
      this.setSelected(((DrawerOption) container.getChildren().get(0)).getMode());
    }
  }

  /**
   * Sets the selected game mode in the DrawerMenu.
   * This method updates the model with the new selected game mode and handles the
   * selection and deselection of DrawerOption elements.
   *
   * @post The previously selected DrawerOption (if any) is deselected.
   *       The new DrawerOption corresponding to the provided GameMode is
   *       selected.
   *       The model is updated with the new selected GameMode.
   * @param option The GameMode to be set as selected. It must be a valid GameMode
   *               that corresponds to an element in the 'container'.
   */
  private void setSelected(GameMode option) {
    // Retrieve the DrawerOption corresponding to the given option's ordinal from
    // the container.
    DrawerOption optionToSelect = (DrawerOption) container.getChildren().get(option.ordinal());

    // If no option is currently selected, select the new option and update the
    // model.
    if (this.model.getSelected().isEmpty()) {
      optionToSelect.select();
      this.model.setSelected(option);
    }
    // If a different option is currently selected, deselect the previous option,
    // select the new one, and update the model.
    else if (option != this.model.getSelected().get()) {
      DrawerOption previousSelected = (DrawerOption) container.getChildren().get(this.model.getSelectedOrdinal().get());

      previousSelected.deselect();
      optionToSelect.select();
      this.model.setSelected(option);
    }
  }

  /**
   * Adds a listener to the list of listeners that will be notified of changes to
   * the drawer options.
   *
   * @param listener The DrawerOptionListener to be added.
   */
  public void addOptionListener(DrawerOptionListener listener) {
    this.model.addListener(listener);
  }
}
