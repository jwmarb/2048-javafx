package org.csc335.entity;

import org.csc335.controllers.DrawerOption;
import org.csc335.controllers.FAIcon;

public enum GameMode {
  TRADITIONAL("Traditional", "Experience the original challenge", FAIcon.GRID, Audio.MAIN_THEME),
  TIME_TRIAL("Time Trial", "Beat the game within a time limit", FAIcon.STOPWATCH, Audio.TIME_THEME),
  MOVE_LIMIT("Move Limit", "Limits the moves you can make", FAIcon.HAND, Audio.MOVE_THEME);

  public final String TITLE;
  public final String DESCRIPTION;
  public final String ICON;
  public final Audio THEME;

  private GameMode(String title, String description, String icon, Audio theme) {
    this.TITLE = title;
    this.DESCRIPTION = description;
    this.ICON = icon;
    this.THEME = theme;
  }

  public DrawerOption createDrawerOption() {
    return new DrawerOption(TITLE, DESCRIPTION, ICON, this);
  }

  public String toCSSSuffix() {
    return this.TITLE.replace(' ', '-').toLowerCase();
  }

  public void playTheme() {
    this.THEME.play();
  }
}
