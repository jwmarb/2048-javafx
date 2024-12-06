package org.csc335.models;

import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.DrawerMenuActionListener;

/**
 * Represents the model for GameLogo. When the logo is interacted with, it will
 * invoke actions related to the logo such as opening the dropdown menu for
 * displaying a list of gamemodes.
 */
public class GameLogoModel {
  private List<DrawerMenuActionListener> listeners;

  public GameLogoModel() {
    this.listeners = new ArrayList<>();
  }

  /**
   * Adds a DrawerMenuActionListener to the list of listeners.
   * 
   * @post The listener has been added to the list of listeners.
   * @param listener The DrawerMenuActionListener to be added. This listener will
   *                 be notified of menu actions.
   */
  public void addMenuActionListener(DrawerMenuActionListener listener) {
    this.listeners.add(listener);
  }

  /**
   * Notifies all registered DrawerMenuActionListener instances that the menu
   * should be opened. This method iterates over the list of listeners and calls
   * the menuClick method on each listener.
   *
   * 
   * @post The menuClick method has been invoked on each listener in the listeners
   *       list.
   */
  public void invokeOpenMenu() {
    for (DrawerMenuActionListener listener : this.listeners) {
      listener.menuClick();
    }
  }
}
