package org.csc335.util;

public class Logger {
  private static boolean isProduction = false;

  private Logger() {
  }

  public static void println(String message) {
    if (!isProduction) {
      System.out.println(message);
    }
  }

  public static void printf(String format, Object... args) {
    if (!isProduction) {
      System.out.printf(format, args);
    }
  }

  public static boolean isProduction() {
    return Logger.isProduction;
  }

  public static boolean isDevelopment() {
    return !Logger.isProduction;
  }
}
