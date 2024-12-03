package org.csc335.interfaces;

import org.csc335.entity.GameMode;

public interface GameListener {
  public void gameModeChanged(GameMode newMode);
}
