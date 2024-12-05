package org.csc335.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Provides utility methods for loading FXML files and setting up JavaFX views
 * with controllers. This class helps streamline the process of initializing
 * JavaFX components by automating the loading of FXML content and applying CSS
 * stylesheets.
 */
public final class EZLoader {
  /**
   * Loads an FXML file corresponding to the provided view class and sets up the
   * view with the specified controller. The FXML file and CSS stylesheet are
   * expected to be located in the "/view/" and "/css/" directories respectively,
   * with filenames matching the simple name of the view class.
   *
   * @pre The provided view instance is not null and is an instance of the
   *      provided view class.
   *      The FXML and CSS files corresponding to the view class exist in the
   *      expected directories.
   * @post The FXML content is loaded into the provided view instance, which is
   *       also set as the controller.
   *       The CSS stylesheet is added to the view's stylesheets.
   * @param view      the view instance to be populated with the FXML content.
   *                  This instance will also serve as the controller.
   * @param viewClass the class of the view, used to locate the corresponding FXML
   *                  and CSS files.
   * @throws RuntimeException if loading the FXML content fails, wrapping the
   *                          original exception.
   */
  public static <T extends Parent> void load(T view, Class<T> viewClass) {
    // Create an FXMLLoader to load the FXML file corresponding to the view class.
    FXMLLoader loader = new FXMLLoader(view.getClass().getResource("/view/" + viewClass.getSimpleName() + ".fxml"));

    // Set the root of the loader to the provided view instance.
    loader.setRoot(view);
    // Set the controller of the loader to the provided view instance, assuming the
    // view is also a controller.
    loader.setController(view);

    // Add the CSS stylesheet corresponding to the view class to the view's
    // stylesheets.
    view.getStylesheets()
        .add(view.getClass().getResource("/css/" + viewClass.getSimpleName() + ".css").toExternalForm());

    // Attempt to load the FXML content.
    try {
      loader.load();
    } catch (Exception e) {
      // If loading fails, throw a RuntimeException with the original exception.
      throw new RuntimeException(e);
    }
  }

  /**
   * A private constructor; do not mind this. This is a utility class that doesn't
   * need instances
   */
  private EZLoader() {
  }

}
