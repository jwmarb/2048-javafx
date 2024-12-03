package org.csc335.controllers;

import org.csc335.util.Logger;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;

public enum Audio {

  TIME_THEME("/sounds/timeTheme.mp3", 0.05, true),
  MOVE_THEME("/sounds/moveTheme.mp3", 0.03, true),
  MAIN_THEME("/sounds/mainTheme.mp3", 0.05, true),
  MERGE_SOUND("/sounds/mergesound.mp3", 0.15, false);

  private final AudioClip audioClip;
  private final boolean isMusic;

  private Audio(String filePath, double volume, boolean isMusic) {
    this.audioClip = new AudioClip(this.getClass().getResource(filePath).toExternalForm());
    this.isMusic = isMusic;
    if (isMusic) {
      this.audioClip.setCycleCount(AudioClip.INDEFINITE);
    } else {
      this.audioClip.setCycleCount(1);
    }
    this.audioClip.setVolume(volume);
  }

  private static void forceStop() {
    for (Audio audio : Audio.values()) {
      if (audio.audioClip.isPlaying()) {
        audio.audioClip.stop();
      }

    }
  }

  public void play() {
    if (this.isMusic) {
      Audio.forceStop();
    }

    this.audioClip.play();

  }

}
