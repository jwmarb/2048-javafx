package org.csc335.javafx_entity;

import org.csc335.controllers.DrawerOption;
import org.csc335.controllers.FAIcon;

/**
 * Represents different game modes available in the application.
 * Each enum constant defines a specific type of game mode that can be selected
 * to modify the gameplay experience.
 */
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

  /**
   * Creates and returns a new DrawerOption instance configured with predefined
   * title, description, icon, and the current GameMode instance.
   *
   * @pre The TITLE, DESCRIPTION, and ICON constants are properly initialized and
   *      not null.
   * @post A new DrawerOption object is created with the specified attributes.
   *
   * @returns A new DrawerOption object initialized with the title, description,
   *          icon, and the current GameMode instance.
   */
  public DrawerOption createDrawerOption() {
    return new DrawerOption(TITLE, DESCRIPTION, ICON, this);
  }

  /**
   * Converts the game mode's title to a CSS-friendly suffix.
   * The conversion is done by replacing all spaces with hyphens and converting
   * the entire string to lowercase.
   *
   * @pre The TITLE attribute of the game mode is a non-null string.
   * @post The returned string is a lowercase version of the TITLE with spaces
   *       replaced by hyphens.
   *
   * @return A CSS-friendly suffix derived from the game mode's title.
   */
  public String toCSSSuffix() {
    return this.TITLE.replace(' ', '-').toLowerCase();
  }

  /**
   * Plays the theme music associated with the current game mode.
   *
   * @pre The theme audio file is properly initialized and loaded.
   * @post The theme music begins playing.
   */
  public void playTheme() {
    this.THEME.play();
  }
}
