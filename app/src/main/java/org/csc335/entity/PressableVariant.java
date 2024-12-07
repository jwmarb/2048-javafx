package org.csc335.entity;

public enum PressableVariant {
  // Represents a filled variant of a button.
  FILLED,
  // Represents an outlined variant of a button.
  OUTLINED;

  // Overrides the default toString method to return the name of the enum in
  // lowercase.
  @Override
  public String toString() {
    return this.name().toLowerCase();
  }
}
