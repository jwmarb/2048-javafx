package org.csc335.entity_tests;

import static org.junit.Assert.assertEquals;

import org.csc335.entity.Direction;
import org.junit.Test;

public class DirectionTest {

  @Test
  public void fromValTest() {
    assertEquals(Direction.DOWN, Direction.fromVal("Down"));
    assertEquals(Direction.DOWN, Direction.fromVal("S"));
    assertEquals(Direction.UP, Direction.fromVal("Up"));
    assertEquals(Direction.UP, Direction.fromVal("W"));
    assertEquals(Direction.RIGHT, Direction.fromVal("Right"));
    assertEquals(Direction.RIGHT, Direction.fromVal("D"));
    assertEquals(Direction.LEFT, Direction.fromVal("Left"));
    assertEquals(Direction.LEFT, Direction.fromVal("A"));
  }
}
