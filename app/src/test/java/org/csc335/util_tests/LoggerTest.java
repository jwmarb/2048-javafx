package org.csc335.util_tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.csc335.util.Logger;
import org.junit.jupiter.api.Test;

public class LoggerTest {
  @Test
  public void testPrintLn() {
    assertDoesNotThrow(() -> Logger.println("Hello World!"));
  }

  @Test
  public void testPrintf() {
    assertDoesNotThrow(() -> Logger.printf("Hello %s%s\n", "world", "!"));
  }

  @Test
  public void testProdPrint() throws Exception {

    assertFalse(Logger.isProduction());
    assertTrue(Logger.isDevelopment());

    assertDoesNotThrow(() -> Logger.printf("hi"));
    assertDoesNotThrow(() -> Logger.println("hi"));

    Field isProduction = Logger.class.getDeclaredField("isProduction");
    isProduction.setAccessible(true);
    isProduction.set(null, true);

    assertTrue(Logger.isProduction());
    assertFalse(Logger.isDevelopment());

    assertDoesNotThrow(() -> Logger.printf("hi"));
    assertDoesNotThrow(() -> Logger.println("hi"));
  }
}
