package org.csc335.entity;

import java.util.Optional;

public enum TileValue {
  T2, T4, T8, T16, T32, T64, T128, T256, T512, T1024, T2048;

  // since the enum is ordered, uses its ordinal value to shift 2 left to
  // yield the value represented by the enum
  public int value() {
    return 2 << this.ordinal();
  }

  @Override
  public String toString() {
    return this.value() + "";
  }

  public static Optional<TileValue> fromString(String value) {
    try {
      int parsed = Integer.parseInt(value);
      return Optional.of(values()[(int) (Math.log(parsed) / Math.log(2)) - 1]);
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
