package org.csc335.entity;

import org.csc335.controllers.DrawerOption;
import org.csc335.controllers.FAIcon;

public enum GameMode {
  TRADITIONAL("Traditional", "Experience the original challenge", FAIcon.GRID),
  TIME_TRIAL("Time Trial", "Beat the game within a time limit", FAIcon.STOPWATCH),
  MOVE_LIMIT("Move Limit", "Limits the moves you can make", FAIcon.HAND);

  public final String TITLE;
  public final String DESCRIPTION;
  public final String ICON;

  private GameMode(String title, String description, String icon) {
    this.TITLE = title;
    this.DESCRIPTION = description;
    this.ICON = icon;
  }

  public DrawerOption createDrawerOption() {
    return new DrawerOption(TITLE, DESCRIPTION, ICON, this);
  }

  public String toCSSSuffix() {
    return this.TITLE.replace(' ', '-').toLowerCase();
  }
}
