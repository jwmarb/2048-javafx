package org.csc335.util;

/**
 * A utility class for logging messages during development and debugging. This
 * logger will only print messages when the application is running in
 * non-production mode, which is determined by an environment variable.
 */
public class Logger {
  private static boolean isProduction;

  static {
    isProduction = System.getenv("production") != null;
  }

  private Logger() {
  }

  /**
   * Prints the specified message to the standard output stream (console)
   * if the application is not running in production mode.
   *
   * @param message the string message to be printed to the console.
   */
  public static void println(String message) {
    if (!isProduction) {
      System.out.println(message);
    }
  }

  /**
   * Prints a formatted string to the standard output stream if the application is
   * not in production mode.
   *
   * @param format the format string, which may contain format specifiers.
   * @param args   the arguments to be formatted and inserted into the format
   *               string.
   */
  public static void printf(String format, Object... args) {
    if (!isProduction) {
      System.out.printf(format, args);
    }
  }

  /**
   * Checks if the current environment is set to production.
   *
   * @returns A boolean value indicating whether the environment is production.
   */
  public static boolean isProduction() {
    return Logger.isProduction;
  }

  /**
   * Checks if the current environment is set to development.
   *
   * @returns A boolean value indicating whether the environment is in development
   *          mode.
   *          Returns true if isProduction is false, indicating development mode;
   *          otherwise, returns false, indicating production mode.
   */
  public static boolean isDevelopment() {
    return !Logger.isProduction;
  }
}
