package org.csc335.interfaces;

import org.csc335.entity.GameMode;

public interface DrawerOptionListener {
  public void selectOption(GameMode selected);

  public void becameHidden();
}
