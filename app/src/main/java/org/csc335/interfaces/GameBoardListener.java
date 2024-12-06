package org.csc335.interfaces;

public interface GameBoardListener {
  public void scoreChanged(int diff);

  public void gameOver();

  public void tileMoved();

}
