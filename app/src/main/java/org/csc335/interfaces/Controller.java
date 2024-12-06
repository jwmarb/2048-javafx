package org.csc335.interfaces;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Provides a framework for building controllers in the application. This
 * abstract class serves as a base for all controller implementations, defining
 * the essential structure and behavior that controllers should adhere to.
 *
 * @param <V> the type of the view, which must extend Parent
 * @param <M> the type of the model
 */
public abstract class Controller<V extends Parent, M> extends Parent {
  /** A view object created by FXML */
  protected V view;

  /** The model used for holding data */
  protected M model;

  protected Controller(V view, M model) {
    this.view = view;
    this.model = model;
  }

  /**
   * For those that need to render the view in the UI, this is the method to do
   * it. It returns the reference to the view so that it can be added as a child
   * to any children-capable component.
   * 
   * @return A view object for rendering on the screen.
   */
  public final V getView() {
    return this.view;
  }

  /**
   * Initializes the controller by loading the corresponding FXML file and setting
   * up the view. The method constructs an FXMLLoader to load an FXML file named
   * after the controller class. It sets the controller of the loader to the
   * current instance and attempts to load the FXML content. If successful, it
   * applies a CSS stylesheet to the view, also named after the controller class.
   *
   * @throws RuntimeException if the FXML file cannot be loaded, wrapping the
   *                          original exception.
   */
  protected void initialize() {
    // Create an FXMLLoader to load the FXML file corresponding to the view class.
    FXMLLoader loader = new FXMLLoader(
        this.getClass().getResource("/view/" + this.getClass().getSimpleName() + ".fxml"));

    // Set the controller of the loader to the provided view instance, assuming the
    // view is also a controller.
    loader.setController(this);
    loader.setRoot(this.view);

    // Attempt to load the FXML content.
    try {
      loader.load();
    } catch (Exception e) {
      // If loading fails, throw a RuntimeException with the original exception.
      throw new RuntimeException(e);
    }

    // Add the stylesheet for this view
    try {
      this.view.getStylesheets()
          .add(this.getClass().getResource("/css/" + this.getClass().getSimpleName() + ".css").toExternalForm());
    } catch (Exception e) {
      // Do nothing since it does not exist
    }
  }
}
