package org.csc335.interfaces;

import org.csc335.models.TileModel;

public interface GameBoardListener {
  public void scoreChanged(int diff);

  public void gameOver();

  public void tileMoved();

}
