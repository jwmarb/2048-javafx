package org.csc335.entity;

public enum PressableVariant {
  FILLED, OUTLINED;

  @Override
  public String toString() {
    return this.name().toLowerCase();
  }
}
