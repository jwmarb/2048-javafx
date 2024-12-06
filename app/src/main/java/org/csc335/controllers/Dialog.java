package org.csc335.controllers;

import org.csc335.interfaces.DialogActionListener;
import org.csc335.models.DialogModel;
import org.csc335.util.EZLoader;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Represents a dialog component extending from VBox. This class is designed to
 * encapsulate the layout and behavior of a dialog box, providing a structured
 * way to display information or prompt for user input within a JavaFX
 * application.
 */
public class Dialog extends VBox {

  private DialogModel model;

  @FXML
  private VBox dialogContainer;

  @FXML
  private Label titleLabel;

  @FXML
  private Label descriptionLabel;

  @FXML
  private VBox actionContainer;

  public Dialog(String title, String description) {
    super();
    EZLoader.load(this, Dialog.class); // Load the FXML associated with this Dialog class.
    this.model = new DialogModel();
    this.model.titleProperty().bindBidirectional(this.titleLabel.textProperty()); // Bind the model's title property to
                                                                                  // the title label's text property for
                                                                                  // two-way synchronization.
    this.model.descriptionProperty().bindBidirectional(this.descriptionLabel.textProperty()); // Bind the model's
                                                                                              // description property to
                                                                                              // the description label's
                                                                                              // text property for
                                                                                              // two-way
                                                                                              // synchronization.
    this.setTitle(title); // Set the title of the dialog using the provided title parameter.
    this.setDescription(description); // Set the description of the dialog using the provided description parameter.
  }

  /**
   * Sets the title of the dialog.
   *
   * @post The title of the dialog has been updated to the specified title.
   * @param title The new title to be set for the dialog.
   */
  public void setTitle(String title) {
    this.model.setTitle(title);
  }

  /**
   * Sets the description for the dialog.
   *
   * @post The description of the dialog is updated to the provided value.
   * @param description the new description to be set for the dialog.
   */
  public void setDescription(String description) {
    this.model.setDescription(description);
  }

  /**
   * Retrieves the title of the dialog from the associated model.
   *
   * @return The title of the dialog as a String.
   */
  public String getTitle() {
    return this.model.getTitle();
  }

  /**
   * Retrieves the description from the associated model.
   *
   * @returns The description string stored in the model.
   */
  public String getDescription() {
    return this.model.getDescription();
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
    this.model.addListener(listener);
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
   */
  public void show() {
    // Notify all registered listeners that the dialog is being shown.
    this.model.setHidden(false);

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
        Dialog.this.model.setHidden(true); // Hide the dialog after the animation finishes.
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
          Dialog.this.model.invokeAction(k);
        }

      });
    }
  }
}
