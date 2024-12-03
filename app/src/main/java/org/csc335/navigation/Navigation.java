package org.csc335.navigation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * The Navigation class provides a simple framework for handling navigation
 * between scenes in a JavaFX application.
 * It manages a stack of scenes and notifies listeners when the current scene is
 * changed.
 */
public class Navigation {

  // Keeps track of the history of stacks
  private static Deque<Scene> stack = new ArrayDeque<>();

  // Listeners that track stack changes
  private static Set<NavigationListener> listeners = new HashSet<>();

  /**
   * Navigates to a new scene by pushing it onto the stack. It notifies all
   * listeners listening for navigation state changes.
   *
   * @param root The root node of the new scene.
   */
  public static void navigate(Parent root) {
    try {
      Scene scene = new Scene(root);
      notifyListeners(scene);
      stack.push(scene);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns to the previous scene by popping from the stack and notifying all
   * registered listeners.
   * If there is only one scene in the stack, no action is taken.
   */
  public static void goBack() {
    if (stack.size() > 1) {

      stack.pop();
      notifyListeners(stack.peek());
    }
  }

  /**
   * Sets an event handler for key press events on the current scene.
   *
   * @param eventHandler The EventHandler to be set.
   */
  public static void setOnKeyPressed(EventHandler<? super KeyEvent> eventHandler) {
    addListener(new NavigationListener() {
      public void handleMount(Scene scene) {
        scene.setOnKeyPressed(eventHandler);
        removeListener(this);
      }
    });
  }

  /**
   * Sets an event handler for key release events on the current scene.
   *
   * @param eventHandler The EventHandler to be set.
   */
  public static void setOnKeyReleased(EventHandler<? super KeyEvent> eventHandler) {
    addListener(new NavigationListener() {
      public void handleMount(Scene scene) {
        scene.setOnKeyReleased(eventHandler);
        removeListener(this);
      }
    });
  }

  /**
   * Sets the current stage's scene to the given new scene and shows the stage.
   *
   * @param stage The Stage object whose scene is to be set.
   */
  public static void setStage(Stage stage) {
    addListener(new NavigationListener() {
      public void handleMount(Scene newScene) {
        stage.setScene(newScene);
        stage.show();
      }
    });
  }

  /**
   * Adds a listener to the list of navigation listeners that will be notified on
   * scene changes.
   *
   * @param listener The NavigationListener to add.
   */
  public static void addListener(NavigationListener listener) {
    listeners.add(listener);
  }

  /**
   * Removes a listener from the list of navigation listeners.
   *
   * @param listener The NavigationListener to remove.
   */
  public static void removeListener(NavigationListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all registered listeners about the new scene being mounted.
   *
   * @param newScene The newly mounted Scene.
   */
  private static void notifyListeners(Scene newScene) {
    List<NavigationListener> copy = new ArrayList<>(listeners);
    for (NavigationListener l : copy) {
      l.handleMount(newScene);
    }
  }
}
