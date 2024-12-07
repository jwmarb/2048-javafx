package org.csc335.interfaces;

import org.csc335.javafx_entity.GameMode;

public interface DrawerOptionListener {
  public void selectOption(GameMode selected);

  public void becameHidden();
}
