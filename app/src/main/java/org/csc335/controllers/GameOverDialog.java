package org.csc335.controllers;

import org.csc335.entity.PressableVariant;

/**
 * Represents a dialog that is displayed when a game is over.
 * This dialog can be used to inform the user about the outcome of the game,
 * such as whether they won or lost, and includes additional information
 * like the final score or options to restart the game.
 */
public class GameOverDialog extends Dialog {
  public GameOverDialog(int score, int moves) {
    super("Game Over!",
        String.format("You scored %d points in %d moves", score,
            moves));

    this.setActions(new Pressable("Play Again", PressableVariant.FILLED, true),
        new Pressable("Quit Game", PressableVariant.OUTLINED, true),
        new Pressable("View Leaderboard", PressableVariant.OUTLINED, true));
  }
}
