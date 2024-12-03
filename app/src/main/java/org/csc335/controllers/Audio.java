package org.csc335.controllers;

import javafx.scene.media.AudioClip;

public class Audio {
    private static AudioClip mergeSound = new AudioClip(
            Audio.class.getResource("/sounds/mergesound.mp3").toExternalForm());
    private static AudioClip mainTheme = new AudioClip(
            Audio.class.getResource("/sounds/mainTheme.mp3").toExternalForm());

    private Audio() {
    }

    public static void playMainTheme() {
        mainTheme.setVolume(0.05);
        mainTheme.play();
        mainTheme.setCycleCount(AudioClip.INDEFINITE);
    }

    public static void playMergeSound() {
        mergeSound.setVolume(0.15);
        mergeSound.play();
    }

    public static void stopMainTheme() {
        mainTheme.stop();
    }
}
