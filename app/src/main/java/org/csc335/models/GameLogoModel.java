package org.csc335.models;

import java.util.ArrayList;
import java.util.List;

import org.csc335.interfaces.DrawerMenuActionListener;

public class GameLogoModel {
  private List<DrawerMenuActionListener> listeners;

  public GameLogoModel() {
    this.listeners = new ArrayList<>();
  }

  public void addMenuActionListener(DrawerMenuActionListener listener) {
    this.listeners.add(listener);
  }

  public void invokeOpenMenu() {
    for (DrawerMenuActionListener listener : this.listeners) {
      listener.menuClick();
    }
  }
}
