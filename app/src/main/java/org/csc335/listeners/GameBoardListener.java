package org.csc335.listeners;

public interface GameBoardListener {
  public void scoreChanged(int diff);

  public void gameOver();

  public void tileMoved();
}
