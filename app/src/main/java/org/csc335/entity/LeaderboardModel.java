package org.csc335.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.BufferedWriter;

public class LeaderboardModel {

  private final PriorityQueue<Integer> maxHeap;

  private static final String FILE_NAME = "leaderboard.txt";

  public LeaderboardModel() {
    maxHeap = new PriorityQueue<Integer>(10){
      private final int maxSize = 10;

      @Override
      public boolean add(Integer e) {
          if (size() < maxSize) {
              return super.add(e);
          } else {
              if (comparator().compare(e, peek()) > 0) { // Assuming a max heap
                  poll(); // Remove the smallest element
                  return super.add(e);
              } else {
                  return false; // Element not added
              }
          }
      }
    };
  }

  public String load() {
    String top10 = "";

    int c = 0;
    Object[] t = maxHeap.toArray();
    Arrays.sort(t, Collections.reverseOrder());
    for (Object score : t) {
      top10 += String.format("%d.    %d points\n", ++c, (int)score);
    }

    return top10;
  }

  public void addScores() {
    File file = new File(FILE_NAME);
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
      addScores();
      System.out.println(maxHeap);

      maxHeap.add(newScore);
      //addNewScore(newScore);
      System.out.println(maxHeap);

      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FILE_NAME));
      Object[] t = maxHeap.toArray();
      Arrays.sort(t, Collections.reverseOrder());
      for (Object score : t) {
        fileWriter.write(String.format("%d\n", (int)score));
      }

      fileWriter.close();
    } catch (Exception e) {
      System.out.println("File does not exit.");
    }
  }
}