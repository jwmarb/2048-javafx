package org.csc335.entity;

/**
 * Represents the direction of movement in a 2048 game based on key presses.
 * This enum includes constants for RIGHT, LEFT, UP, and DOWN directions.
 */
public enum Direction {
  // Constants that represent direction
  RIGHT, LEFT, UP, DOWN;

  /**
   * Converts a string that represents the key that has been pressed to the
   * corresponding Direction enum value.
   *
   * @pre The key should be one of the predefined direction strings: "W", "Up",
   *      "S", "Down", "A", "Left", "D", "Right".
   * @post If the key matches a predefined direction, the corresponding Direction
   *       enum value is returned. Otherwise, null is returned.
   * @param key the string representation of the direction.
   *
   * @returns The Direction enum value corresponding to the provided string, or
   *          null if no match is found.
   */
  public static Direction fromVal(String key) {
    switch (key) {
      case "W":
      case "Up":
        return UP;
      case "S":
      case "Down":
        return DOWN;
      case "A":
      case "Left":
        return LEFT;
      case "D":
      case "Right":
        return RIGHT;
      default:
        return null;
    }
  }
}