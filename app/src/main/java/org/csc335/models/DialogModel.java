package org.csc335.models;

import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.DialogActionListener;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a model for managing dialog data and state. This class
 * encapsulates the logic and data necessary for interacting with dialog
 * components in a user interface, providing methods to update and retrieve
 * dialog information.
 */
public class DialogModel {
  private List<DialogActionListener> listeners;

  private StringProperty title;

  private StringProperty description;

  public DialogModel() {
    this.listeners = new ArrayList<>();
    this.title = new SimpleStringProperty();
    this.description = new SimpleStringProperty();
  }

  /**
   * Sets the hidden state of the dialog by notifying all registered listeners.
   *
   * @post The hidden state of the dialog is updated, and the appropriate method
   *       (dialogHidden or dialogShown) is called on each listener.
   * @param hidden A boolean indicating whether the dialog should be hidden (true)
   *               or shown (false).
   */
  public void setHidden(boolean hidden) {
    for (DialogActionListener listener : this.listeners) {
      if (hidden) {
        listener.dialogHidden();
      } else {
        listener.dialogShown();
      }
    }
  }

  /**
   * Sets the title for the dialog.
   *
   * @post The dialog's title is updated to the provided title.
   * @param title The new title to be set for the dialog.
   */
  public void setTitle(String title) {
    this.title.set(title);
  }

  /**
   * Retrieves the current title of the dialog.
   *
   * @returns The current title of the dialog as a String.
   */
  public String getTitle() {
    return this.title.get();
  }

  /**
   * Returns the StringProperty associated with the title of the dialog.
   *
   * @returns The StringProperty representing the title of the dialog.
   */
  public StringProperty titleProperty() {
    return this.title;
  }

  /**
   * Sets the description for the dialog.
   *
   * @pre The description parameter is a non-null string.
   * @post The dialog's description is updated to the provided value.
   * @param description The new description to be set for the dialog.
   */
  public void setDescription(String description) {
    this.description.set(description);
  }

  /**
   * Retrieves the current description stored in the model.
   *
   * @returns The current description as a String.
   */
  public String getDescription() {
    return this.description.get();
  }

  /**
   * Returns the StringProperty associated with the earthquake description.
   *
   * @returns The StringProperty object representing the earthquake description.
   */
  public StringProperty descriptionProperty() {
    return this.description;
  }

  /**
   * Adds a listener to the list of DialogActionListeners.
   *
   * @param listener The DialogActionListener to be added. This listener will be
   *                 notified of dialog actions.
   */
  public void addListener(DialogActionListener listener) {
    this.listeners.add(listener);
  }

  /**
   * Invokes the dialog action for the specified index by notifying all registered
   * DialogActionListeners.
   *
   * @pre The listeners list contains one or more DialogActionListener instances.
   * @post Each registered DialogActionListener's dialogAction method has been
   *       called with the specified index.
   * @param idx The index of the dialog action to be invoked. This index is passed
   *            to each registered listener.
   */
  public void invokeAction(int idx) {
    for (DialogActionListener listener : this.listeners) {
      listener.dialogAction(idx);
    }
  }
}
