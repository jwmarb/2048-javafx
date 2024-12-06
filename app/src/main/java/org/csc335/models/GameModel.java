package org.csc335.models;

import java.util.ArrayList;
import java.util.List;

import org.csc335.entity.GameMode;
import org.csc335.interfaces.GameListener;

public class GameModel {
  private List<GameListener> listeners;
  private GameMode gameMode;

  public GameModel() {
    this.listeners = new ArrayList<>();
    this.setGameMode(GameMode.TRADITIONAL);
  }

  public void setGameMode(GameMode mode) {
    this.gameMode = mode;
    this.notifyDependencies();
  }

  public GameMode getGameMode() {
    return this.gameMode;
  }

  private void notifyDependencies() {
    for (GameListener listener : this.listeners) {
      listener.gameModeChanged(this.gameMode);
    }
  }

  public void addListener(GameListener listener) {
    this.listeners.add(listener);
  }
}
