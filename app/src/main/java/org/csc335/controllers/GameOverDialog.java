package org.csc335.controllers;

public class GameOverDialog extends Dialog {
  public GameOverDialog(int score, int moves) {
    super("Game Over!",
        String.format("You scored %d points in %d moves", score,
            moves));

    this.setActions(new Pressable("Play Again", Pressable.FILLED, true),
        new Pressable("Quit Game", Pressable.OUTLINED, true),
        new Pressable("View Leaderboard", Pressable.OUTLINED, true));
  }
}
