package org.csc335.controllers;

import org.csc335.entity.PressableVariant;

public class NewGameDialog extends Dialog {

  public NewGameDialog() {
    super("New Game", "Are you sure you want to start a new game? All progress will be lost.");

    this.setActions(new Pressable("Start New Game", PressableVariant.FILLED, true),
        new Pressable("Cancel", PressableVariant.OUTLINED, true));
  }
}
