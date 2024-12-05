package org.csc335.controllers;

import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.DialogActionListener;
import org.csc335.util.EZLoader;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Dialog extends VBox {

  private List<DialogActionListener> listeners;

  private boolean hidden;

  @FXML
  private VBox dialogContainer;

  @FXML
  private StringProperty title;

  public void setTitle(String title) {
    this.title.set(title);
  }

  public void setDescription(String description) {
    this.description.set(description);
  }

  public String getTitle() {
    return this.title.get();
  }

  public String getDescription() {
    return this.description.get();
  }

  @FXML
  private StringProperty description;

  @FXML
  private Label titleLabel;

  @FXML
  private Label descriptionLabel;

  @FXML
  private VBox actionContainer;

  public Dialog(String title, String description) {
    super();
    this.description = new SimpleStringProperty();
    this.title = new SimpleStringProperty();

    EZLoader.load(this, Dialog.class);

    this.hidden = true;
    this.listeners = new ArrayList<>();
    this.title.bindBidirectional(this.titleLabel.textProperty());
    this.description.bindBidirectional(this.descriptionLabel.textProperty());
    this.setTitle(title);
    this.setDescription(description);
  }

  /**
   * Sets the hidden state of the dialog and notifies listeners if the dialog is
   * hidden.
   *
   * @pre The dialog's state can be either hidden or not hidden.
   * @post If the dialog is set to hidden, all registered DialogActionListeners
   *       are notified.
   * @param hidden a boolean indicating whether the dialog should be hidden (true)
   *               or shown (false).
   */
  private void setHidden(boolean hidden) {
    this.hidden = hidden;

    if (this.hidden) {
      for (DialogActionListener listener : this.listeners) {
        listener.dialogHidden();
      }
    }
  }

  /**
   * Adds a listener to the list of dialog action listeners.
   * The listener will be notified whenever a dialog action is performed.
   *
   * @pre The listener parameter is not null.
   * @post The specified listener is added to the list of listeners.
   * @param listener the DialogActionListener to be added; must not be null
   */
  public void addDialogActionListener(DialogActionListener listener) {
    this.listeners.add(listener);
  }

  /**
   * Displays the dialog with a fade-in effect and a sliding transition from
   * off-screen to its final position on the screen. The method notifies all
   * registered listeners that the dialog is being shown.
   *
   * The dialog instance and its container (dialogContainer) are properly
   * initialized; the list of listeners may be empty or contain instances of
   * DialogActionListener.
   * 
   * @post The dialog is not marked as hidden; it becomes visible with full
   *       opacity and its container is at the intended on-screen position.
   *
   * @returns None
   */
  public void show() {
    // Notify all registered listeners that the dialog is being shown.
    for (DialogActionListener listener : this.listeners) {
      listener.dialogShown();
    }

    // Create a fade transition to gradually change the opacity of the dialog from
    // 0.0 to 1.0 over 250 milliseconds.
    FadeTransition ft = new FadeTransition(Duration.millis(250), this);
    ft.setFromValue(0.0);
    ft.setToValue(1.0);

    // Create a translate transition to move the dialog container from -50.0 to 0.0
    // on the Y-axis over 250 milliseconds.
    TranslateTransition tt = new TranslateTransition(Duration.millis(250), this.dialogContainer);
    tt.setFromY(-50.0);
    tt.setToY(0.0);
    tt.setInterpolator(Interpolator.EASE_OUT); // Use an ease-out interpolator for a smooth ending of the transition.

    // Set an event handler to mark the dialog as not hidden once the translation
    // transition is complete.
    tt.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Dialog.this.setHidden(false);
      }
    });

    // Start the translation and fade transitions simultaneously.
    tt.play();
    ft.play();
  }

  /**
   * Hides the dialog by smoothly fading it out and moving it upwards off-screen.
   * The fade transition decreases the opacity from fully visible to fully
   * invisible over 100 milliseconds. Simultaneously, a translate transition moves
   * the dialog container 50 pixels upwards, using an ease-out interpolator for
   * smooth animation. The dialog is marked as hidden after the translation
   * transition completes.
   *
   * @pre The dialog is currently visible on the screen.
   * @post The dialog is smoothly animated out of view and marked as hidden.
   */
  public void hide() {
    // Create a fade transition to smoothly decrease the opacity of the dialog.
    FadeTransition ft = new FadeTransition(Duration.millis(100), this);
    ft.setFromValue(1.0); // Start with full opacity.
    ft.setToValue(0.0); // End with no opacity.

    // Create a translate transition to move the dialog container upwards
    // off-screen.
    TranslateTransition tt = new TranslateTransition(Duration.millis(100), this.dialogContainer);
    tt.setToY(-50.0); // Move the dialog 50 pixels upwards.
    tt.setFromY(0.0); // Start from the original position.
    tt.setInterpolator(Interpolator.EASE_OUT); // Use an ease-out interpolator for smooth animation.

    // Set an event handler to mark the dialog as hidden after the translation is
    // complete.
    tt.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Dialog.this.setHidden(true); // Hide the dialog after the animation finishes.
      }
    });

    // Play the translation transition.
    tt.play();
    // Play the fade transition simultaneously.
    ft.play();
  }

  /**
   * Sets the action buttons for the dialog. This method clears any existing
   * buttons in the action container and adds the provided buttons. Each button is
   * assigned an action handler that notifies all registered listeners about the
   * action when the button is pressed.
   *
   * @pre The action container can hold child nodes; buttons is a varargs array of
   *      Pressable objects.
   * @post The action container contains only the provided buttons; each button
   *       has an action handler.
   * @param buttons the varargs array of Pressable objects representing the action
   *                buttons to be added.
   */
  public void setActions(Pressable... buttons) {
    // Clear the existing children in the action container and add the new buttons.
    this.actionContainer.getChildren().setAll(buttons);

    // Iterate over each button to assign an action handler.
    for (int i = 0; i < buttons.length; ++i) {
      final int k = i; // Capture the current index in a final variable for use in the lambda.
      // Set the action for the current button.
      buttons[i].setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          // Notify all registered listeners about the action with the button index.
          for (DialogActionListener listener : Dialog.this.listeners) {
            listener.dialogAction(k);
          }
        }

      });
    }
  }
}
