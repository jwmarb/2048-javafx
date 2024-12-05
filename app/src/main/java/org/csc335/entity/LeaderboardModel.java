package org.csc335.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedWriter;

public class LeaderboardModel {

  private final PriorityQueue<Integer> leaderboard;
  private final int LEADERBOARD_SIZE = 10;
  private static Path leaderboardPath;

  /**
   * Static assignment block to init leaderboardPath
   */
  static {
    getLeaderboard();
  }

  /**
   * Recursively walks over all files under current working directory (cwd) to find a match of
   * leaderboard.txt. If the file is found, then it will set our leaderboardPath to the path. If
   * no such file is found, it generates a filepath pointing to a leaderboard.txt in cwd and then
   * makes that file
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
      fileGen(new File("leaderboard.txt"));
    }
    leaderboardPath = Paths.get("leaderboard.txt");
  }

  // constructor
  public LeaderboardModel() {

    // fixed size maxheap
    leaderboard = new PriorityQueue<Integer>(){
      private final int maxSize = LEADERBOARD_SIZE;

      @Override
      /**
       * overloaded add method such that each added element will ensure that the size of priority
       * queue is <= maxSize. Enforces max queue by polling (poppin smallest element) each time
       * a new added element makes size larger than maxSize.
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
   * Loops over scores in the maxheap, returns a String representation of the leaderboard. If there
   * are less than LEADERBOARD_SIZE scores in the heap, fill with 0 pt scores.
   * 
   * @return String representation of data contained w/in leaderboard
   */
  @Override
  public String toString() {
    String top10 = "";

    int c = 0;

    Object[] scores = leaderboard.toArray();
    Arrays.sort(scores, Collections.reverseOrder());

    for (Object score : scores) {
      top10 += String.format("%d.    %d points\n", ++c, (int)score);
    }
    while (c < LEADERBOARD_SIZE) {
      top10 += String.format("%d.    0 points\n", ++c);
    }

    return top10;
  }

  /**
   * reads the leaderboard file and adds its entries to our priority queue
   * 
   * @pre leaderboardPath != null
   */
  private void addScores() {
    Scanner fileReader = null;

    try {
      File file = leaderboardPath.toFile();
      fileReader = new Scanner(file);

      while (fileReader.hasNext()) {
        int score = Integer.parseInt(fileReader.nextLine().trim());
        leaderboard.add(score);
      }

      fileReader.close();

    } catch (Exception e) {
      System.out.println("leaderboard.txt file does not exist");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * adds the current game score to the leaderboard. If it is lower than the lowest entry it is not
   * added (see constructor def for leaderboard object). Then it writes the priority queue contents
   * out to the leaderbaord file
   * 
   * @pre leaderboard,leaderboardPath != null
   * 
   * @param newScore the current game score we will attempt to add to leaderboard
   */
  public void writeNewPlayerScore(int newScore) {
    leaderboard.add(newScore);
    fileGen(leaderboardPath.toFile());
    try {
      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(leaderboardPath.toFile()));

      Object[] t = leaderboard.toArray();
      Arrays.sort(t, Collections.reverseOrder());

      for (Object score : t) {
        fileWriter.write(String.format("%d\n", (int)score));
      }

      fileWriter.close();
    } catch (Exception e) {
      System.out.println("leaderboard.txt file does not exist");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * creates the given file if it does not exist
   * 
   * @pre file != null
   * @param file the file object that may or may not exist
   */
  private static void fileGen(File file) {
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (Exception e) {
        System.out.println("Error with leaderboard file. Path was not resolved");
        System.exit(1);
      }
    }
  }
}