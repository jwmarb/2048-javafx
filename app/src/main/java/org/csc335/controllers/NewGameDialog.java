package org.csc335.controllers;

public class NewGameDialog extends Dialog {

  public NewGameDialog() {
    super("New Game", "Are you sure you want to start a new game? All progress will be lost.");

    this.setActions(new Pressable("Start New Game", Pressable.FILLED, true),
        new Pressable("Cancel", Pressable.OUTLINED, true));
  }
}
