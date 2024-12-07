package org.csc335.javafx_entity;

import java.util.Optional;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;

/**
 * Represents different audio clips used in the application, including themes
 * and sound effects. Each audio clip is associated with a file path, volume
 * level, and a flag indicating if it is music. The enum provides methods to
 * play audio clips and manage their playback state.
 */
public enum Audio {

  // Audios for 2048
  TIME_THEME("/sounds/timeTheme.mp3", 0.05, true),
  MOVE_THEME("/sounds/moveTheme.mp3", 0.03, true),
  MAIN_THEME("/sounds/mainTheme.mp3", 0.05, true),
  MERGE_SOUND("/sounds/mergesound.mp3", 0.15, false);

  private Optional<AudioClip> audioClip;

  private boolean isMusic;

  /**
   * Initializes an Audio object with the specified file path, volume, and type
   * (music or sound effect).
   * 
   * @param filePath The path to the audio file. This should be a valid resource
   *                 path accessible via getClass().getResource().
   * @param volume   The volume level for the audio clip, ranging from 0.0
   *                 (silent) to 1.0 (full volume).
   * @param isMusic  A flag indicating whether the audio is music (true) or a
   *                 sound effect (false). If true, the audio will loop
   *                 indefinitely.
   * 
   * @returns An Audio object configured with the specified audio file, volume,
   *          and type.
   */
  private Audio(String filePath, double volume, boolean isMusic) {
    try {
      // Attempt to create an AudioClip from the specified file path.
      this.audioClip = Optional.of(new AudioClip(this.getClass().getResource(filePath).toExternalForm()));
      this.isMusic = isMusic;

      // If the AudioClip is successfully created, configure its cycle count and
      // volume.
      if (audioClip.isPresent()) {
        // Set the cycle count to indefinite if it is music, otherwise set it to play
        // once.
        if (isMusic) {
          this.audioClip.get().setCycleCount(AudioClip.INDEFINITE);
        } else {
          this.audioClip.get().setCycleCount(1);
        }
        // Set the volume of the AudioClip.
        this.audioClip.get().setVolume(volume);
      }
    } catch (MediaException e) {
      // Handle any MediaException by setting audioClip to null and isMusic to false.
      this.audioClip = Optional.empty();
      this.isMusic = false;
    }
  }

  /**
   * Stops all currently playing audio clips.
   *
   * @pre At least one Audio instance is playing its audio clip.
   * @post All audio clips associated with Audio instances are stopped.
   */
  private static void forceStop() {
    for (Audio audio : Audio.values()) {
      if (audio.audioClip.isPresent() && audio.audioClip.get().isPlaying()) {
        audio.audioClip.get().stop();
      }

    }
  }

  /**
   * Plays the audio clip associated with this instance. If the audio clip is
   * identified
   * as music (isMusic is true), it forces any currently playing music to stop
   * before playing
   * the new clip. The state of the audio clip (isMusic and audioClip) remains
   * unchanged
   * after this method execution.
   *
   * @post Any previously playing music is stopped if the audio clip is music; the
   *       audioClip is now playing.
   */
  public void play() {
    if (this.audioClip.isPresent()) {
      if (this.isMusic) {
        Audio.forceStop();
      }

      this.audioClip.get().play();
    }
  }

}
