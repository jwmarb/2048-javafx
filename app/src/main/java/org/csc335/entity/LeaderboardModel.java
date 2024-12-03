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

  private final PriorityQueue<Integer> maxHeap;

  private static Path leaderboardPath;
  static {
    try (Stream<Path> walkStream = Files.walk(Paths.get("."))) {
      walkStream.filter(p -> p.toFile().isFile()).forEach(f -> {
          if (f.toString().endsWith("leaderboard.txt")) {
            leaderboardPath = f;
          }
      });
    } catch (Exception e) {
      System.out.println("Error reading files...");
    }

    if (leaderboardPath == null) {
      System.out.println("Leaderboard file does not exist");
      System.exit(1);
    }
  }

  public LeaderboardModel() {
    maxHeap = new PriorityQueue<Integer>(10){
      private final int maxSize = 10;

      @Override
      public boolean add(Integer e) {
          if (size() < maxSize) {
            return super.add(e);
          } else {
            if (e.compareTo(peek()) > 0) {
              poll(); // Remove the smallest element
              return super.add(e);
            } else {
              return false;
            }
          }
      }
    };

    addScores();
  }

  public String load() {
    String top10 = "";

    int c = 0;
    Object[] t = maxHeap.toArray();
    Arrays.sort(t, Collections.reverseOrder());
    for (Object score : t) {
      top10 += String.format("%d.    %d points\n", ++c, (int)score);
    }
    while (c < 10) {
      top10 += String.format("%d.    0 points\n", ++c);
    }

    return top10;
  }

  private void addScores() {
    File file = leaderboardPath.toFile();
    Scanner fileReader = null;

    try {
      fileReader = new Scanner(file);

      while (fileReader.hasNext()) {
        int score = Integer.parseInt(fileReader.nextLine().trim());
        maxHeap.add(score);
      }

      fileReader.close();

    } catch (Exception e) {
      System.out.println("File does not exist");
      System.out.println(file.getAbsolutePath());
    }
  }

  // write in file
  public void writeNewPlayerScore(int newScore) {
    System.out.println(maxHeap.size());
    try {

      // get the saved data
      maxHeap.add(newScore);

      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(leaderboardPath.toFile()));
      Object[] t = maxHeap.toArray();
      Arrays.sort(t, Collections.reverseOrder());
      for (Object score : t) {
        fileWriter.write(String.format("%d\n", (int)score));
      }

      fileWriter.close();
    } catch (Exception e) {
      System.out.println("File does not exit.");
      e.printStackTrace();
    }
  }
}