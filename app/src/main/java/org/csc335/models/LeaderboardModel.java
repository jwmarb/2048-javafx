package org.csc335.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedWriter;

public class LeaderboardModel {

  private final PriorityQueue<Integer> LEADERBOARD;
  private final int LEADERBOARD_SIZE = 10;
  private static Path leaderboardPath;

  /**
   * Static assignment block to init leaderboardPath
   */
  static {
    getLeaderboard();
  }

  /**
   * Recursively searches all files under the current working directory (cwd) to
   * locate a file named "leaderboard.txt". If the file is found, the method sets
   * the {@code leaderboardPath} to the path of the found file. If no such file
   * exists, the method generates a new file named "leaderboard.txt" in the cwd
   * and sets the {@code leaderboardPath} to the path of this newly created file.
   *
   * @pre The current working directory is accessible and contains files.
   * @post If "leaderboard.txt" is found, {@code leaderboardPath} points to its
   *       location. If not found, "leaderboard.txt" is created in the cwd and
   *       {@code leaderboardPath} points to the new file's location.
   */
  private static void getLeaderboard() {
    // recursively find file leaderboard.txt
    try (Stream<Path> walkStream = Files.walk(Paths.get("."))) {
      walkStream.filter(p -> p.toFile().isFile()).forEach(f -> {
        if (f.toString().endsWith("leaderboard.txt")) {
          leaderboardPath = f;
        }
      });
    } catch (Exception e) {
      System.out.println("Error reading files...");
    }

    // if leaderboard.txt DNE, create it and set path
    if (leaderboardPath == null) {
      leaderboardPath = Paths.get("leaderboard.txt");
    }
  }

  // constructor
  public LeaderboardModel() {
    fileGen(leaderboardPath.toFile());

    // fixed size maxheap
    LEADERBOARD = new PriorityQueue<Integer>() {
      private final int maxSize = LEADERBOARD_SIZE;

      /**
       * Adds an element to the priority queue while ensuring that the size of the
       * queue does not exceed maxSize. If the queue is already at maxSize, the
       * element is added only if it is larger than the smallest element currently in
       * the queue. In such a case, the smallest element is removed to maintain the
       * queue size.
       *
       * @post The queue contains the new element if it is larger than the smallest
       *       element and the size does not exceed maxSize. Otherwise, the queue
       *       remains unchanged.
       * @param e the element to be added to the queue
       * @return true if the element was added to the queue, false otherwise
       */
      public boolean add(Integer e) {
        if (size() < maxSize) {
          return super.add(e);
        } else {
          if (e.compareTo(peek()) > 0) {
            poll();
            return super.add(e);
          } else {
            return false;
          }
        }
      }
    };

    addScores();
  }

  /**
   * Generates a String representation of the leaderboard by iterating over the
   * scores stored in the max-heap.
   * If the number of scores in the heap is less than LEADERBOARD_SIZE, the
   * remaining entries are filled with 0 point scores.
   *
   * @pre The max-heap (LEADERBOARD) contains scores in no particular order.
   * @post The max-heap remains unchanged; a String representation of the
   *       leaderboard is returned.
   *
   * @return A String representing the top scores on the leaderboard, formatted
   *         with rank and score.
   *         If there are fewer scores than LEADERBOARD_SIZE, the missing entries
   *         are filled with "0 points".
   */
  @Override
  public String toString() {
    String top10 = "";

    int c = 0;

    Object[] scores = LEADERBOARD.toArray();
    Arrays.sort(scores, Collections.reverseOrder());

    for (Object score : scores) {
      top10 += String.format("%d.    %d points\n", ++c, (int) score);
    }
    while (c < LEADERBOARD_SIZE) {
      top10 += String.format("%d.    0 points\n", ++c);
    }

    return top10;
  }

  /**
   * Reads the leaderboard file located at the specified path and adds its entries
   * to the priority queue.
   * Each line in the file is expected to contain a single integer representing a
   * score.
   *
   * @pre leaderboardPath != null and points to a valid file containing integer
   *      scores, one per line.
   * @post The scores from the leaderboard file are added to the LEADERBOARD
   *       priority queue.
   * @throws FileNotFoundException if the file at leaderboardPath does not exist.
   * @throws NumberFormatException if a line in the file cannot be parsed as an
   *                               integer.
   */
  private void addScores() {
    Scanner fileReader = null;

    try {
      File file = leaderboardPath.toFile();
      fileReader = new Scanner(file);

      while (fileReader.hasNext()) {
        int score = Integer.parseInt(fileReader.nextLine().trim());
        LEADERBOARD.add(score);
      }

      fileReader.close();

    } catch (IOException io) {
      throw new UncheckedIOException("leaderboard.txt file does not exist", io);
    }
  }

  /**
   * Adds the current game score to the leaderboard if it is higher than the
   * lowest entry. The leaderboard maintains a fixed number of top scores, as
   * defined in the constructor of the Leaderboard object. After attempting to add
   * the new score, the method writes the updated leaderboard (priority queue
   * contents) to the leaderboard file.
   *
   * @pre The leaderboard and leaderboardPath must not be null.
   * @post The leaderboard file is updated with the new score if it is high enough
   *       to be included.
   *
   * @param newScore The current game score that will be attempted to be added to
   *                 the leaderboard.
   */
  public void writeNewPlayerScore(int newScore) {
    LEADERBOARD.add(newScore);
    fileGen(leaderboardPath.toFile());
    try {
      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(leaderboardPath.toFile()));

      Object[] t = LEADERBOARD.toArray();
      Arrays.sort(t, Collections.reverseOrder());

      for (Object score : t) {
        fileWriter.write(String.format("%d\n", (int) score));
      }

      fileWriter.close();
    } catch (IOException io) {
      throw new UncheckedIOException("leaderboard.txt file does not exist", io);
    }
  }

  /**
   * Ensures the existence of the specified file by creating it if it does not
   * already exist.
   *
   * @post If the file did not exist, it will be created; if an error occurs
   *       during file creation, an error message is printed and the application
   *       exits.
   * @param file The file object representing the file to be checked or created.
   */
  private static void fileGen(File file) {
    if (!file.exists()) {
      try {
        file.createNewFile();
      }  catch (IOException io) {
        throw new UncheckedIOException("error creating file, path unresolvable", io);
      }
    }
  }
}