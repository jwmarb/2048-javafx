package org.csc335.entity;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.BufferedWriter;

public class LeaderboardModel {

  // instance variables
  private ArrayList<Integer> scores;

  private String top10;

  private static String FILE_NAME = "leaderboard.txt";

  public LeaderboardModel() {
    scores = new ArrayList<>();
  }

  public String load() {
    // ArrayList<Player> top5 = new ArrayList<>();
    this.top10 = "";
    int count = 10;
    for (int i = 0; i < scores.size(); i++) {
      int score = scores.get(i);
      if (count > 0) {
        this.top10 += String.format("%d.    %d points\n", i + 1, score);
        count--;
      }
    }

    return top10;
  }

  // helper method
  public void addNewScore(int newScore) {
    if (scores.size() == 0) {
      scores.add(newScore);
    } else {
      for (int i = 0; i < this.scores.size(); i++) {
        // System.out.println(scores.get(i));
        if (scores.get(i) < newScore) {
          scores.add(i, newScore);
          break;
        }
        if (i == this.scores.size() - 1) {
          scores.add(newScore);
          break;
        }
      }
    }
  }

  // adds players from leaderboard file
  public void addScores() {
    File file = new File(FILE_NAME);
    Scanner fileReader = null;

    try {
      fileReader = new Scanner(file);

      while (fileReader.hasNext()) {
        int score = Integer.parseInt(fileReader.nextLine().trim());
        addNewScore(score);
      }

      fileReader.close();

    } catch (Exception e) {
      System.out.println("File does not exist");
      System.out.println(file.getAbsolutePath());
    }
  }

  // write in file
  public void writeNewPlayerScore(int newScore) {
    try {

      // get the saved data
      addScores();
      System.out.println(scores);
      addNewScore(newScore);
      System.out.println(scores);

      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FILE_NAME));

      for (int score : scores) {
        fileWriter.write(String.format("%d\n", score));
      }

      fileWriter.close();
    } catch (Exception e) {
      System.out.println("File does not exit.");
    }
  }
}
