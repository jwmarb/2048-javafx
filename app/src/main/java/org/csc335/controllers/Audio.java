package org.csc335.controllers;

import org.csc335.entity.GameMode;

import javafx.scene.media.AudioClip;

public class Audio {
    private static AudioClip mergeSound = new AudioClip(
            Audio.class.getResource("/sounds/mergesound.mp3").toExternalForm());
    private static AudioClip mainTheme = new AudioClip(
            Audio.class.getResource("/sounds/mainTheme.mp3").toExternalForm());
    private static AudioClip timeTheme = new AudioClip(
            Audio.class.getResource("/sounds/timeTheme.mp3").toExternalForm());
    private static AudioClip moveTheme = new AudioClip(
            Audio.class.getResource("/sounds/moveTheme.mp3").toExternalForm());

    private Audio() {
    }

    public static void selectMusic(GameMode mode) {
        stopMusic();
        switch (mode) {
            case TRADITIONAL:
                playMainTheme();
                break;
            case TIME_TRIAL:
                playTimeTheme();
                break;
            case MOVE_LIMIT:
                playMoveTheme();
                break;
        }
    }

    public static void playMainTheme() {
        mainTheme.setVolume(0.05);
        mainTheme.play();
        mainTheme.setCycleCount(AudioClip.INDEFINITE);
    }

    public static void playMoveTheme() {
        moveTheme.setVolume(0.03);
        moveTheme.play();
        moveTheme.setCycleCount(AudioClip.INDEFINITE);
    }

    public static void playTimeTheme() {
        timeTheme.setVolume(0.05);
        timeTheme.play();
        timeTheme.setCycleCount(AudioClip.INDEFINITE);
    }

    public static void playMergeSound() {
        mergeSound.setVolume(0.15);
        mergeSound.play();
    }

    public static void stopMusic() {
        mainTheme.stop();
        moveTheme.stop();
        timeTheme.stop();
    }
}
