package org.csc335.listeners;

import org.csc335.controllers.DrawerOption;

public interface DrawerOptionListener {
  public void selectOption(DrawerOption selected);

  public void becameHidden();
}
