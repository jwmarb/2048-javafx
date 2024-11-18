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

public class Navigation {
  private static Deque<Scene> stack = new ArrayDeque<>();
  private static Set<NavigationListener> listeners = new HashSet<>();

  public static void navigate(Parent root) {
    try {
      Scene scene = new Scene(root);
      notifyListeners(scene);
      stack.push(scene);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void goBack() {
    if (stack.size() > 1) {
      stack.pop();
      notifyListeners(stack.peek());
    }
  }

  public static void setOnKeyPressed(EventHandler<? super KeyEvent> eventHandler) {
    addListener(new NavigationListener() {
      public void handleMount(Scene scene) {
        scene.setOnKeyPressed(eventHandler);
        removeListener(this);
      }
    });
  }

  public static void setOnKeyReleased(EventHandler<? super KeyEvent> eventHandler) {
    addListener(new NavigationListener() {
      public void handleMount(Scene scene) {
        scene.setOnKeyReleased(eventHandler);
        removeListener(this);
      }
    });
  }

  public static void setStage(Stage stage) {
    addListener(new NavigationListener() {
      public void handleMount(Scene newScene) {
        stage.setScene(newScene);
        stage.show();
      }
    });
  }

  public static void addListener(NavigationListener listener) {
    listeners.add(listener);
  }

  public static void removeListener(NavigationListener listener) {
    listeners.remove(listener);
  }

  private static void notifyListeners(Scene newScene) {
    List<NavigationListener> copy = new ArrayList<>(listeners);
    for (NavigationListener l : copy) {
      l.handleMount(newScene);
    }
  }
}
