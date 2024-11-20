package org.csc335.entity;

import java.util.Optional;

import org.csc335.controllers.Tile;

/**
 * Enum representing possible values for tiles in 2048.
 */
public enum TileValue {
  /** Represents a tile with a value of 2. */
  T2,

  /** Represents a tile with a value of 4. */
  T4,

  /** Represents a tile with a value of 8. */
  T8,

  /** Represents a tile with a value of 16. */
  T16,

  /** Represents a tile with a value of 32. */
  T32,

  /** Represents a tile with a value of 64. */
  T64,

  /** Represents a tile with a value of 128. */
  T128,

  /** Represents a tile with a value of 256. */
  T256,

  /** Represents a tile with a value of 512. */
  T512,

  /** Represents a tile with a value of 1024. */
  T1024,

  /** Represents a tile with a value of 2048. */
  T2048;

  /**
   * Returns the integer value represented by this {@link TileValue}.
   *
   * <p>
   * The value is determined by shifting 2 left by the ordinal position of
   * the enum constant. This approach leverages the fact that the enum constants
   * are ordered and their ordinal positions correspond to powers of two.
   * </p>
   *
   * @return The integer value represented by this {@link TileValue}.
   */

  // public static TileValue mergedValue(TileValue t) {
  // return values()[t.ordinal() + 1]; // returns the next one
  // }

  // next power of 2
  public TileValue next() {
    return values()[ordinal() + 1];
  }

  public int value() {
    return 2 << this.ordinal();
  }

  @Override
  public String toString() {
    return this.value() + "";
  }

  /**
   * Converts a string representation of a tile value into its corresponding
   * {@link TileValue} enum instance.
   *
   * @param value the string representation of the tile value
   * @return an Optional containing the corresponding TileValue if the string is
   *         valid, or an empty Optional otherwise
   */
  public static Optional<TileValue> fromString(String value) {
    if (value == null) {
      return Optional.empty();
    }
    try {
      int parsed = Integer.parseInt(value);
      // Calculate the log base 2 and adjust for array indexing
      return Optional.of(values()[(int) (Math.log(parsed) / Math.log(2)) - 1]);
    } catch (Exception e) {
      // Return an empty Optional if parsing fails
      return Optional.empty();
    }
  }
}
