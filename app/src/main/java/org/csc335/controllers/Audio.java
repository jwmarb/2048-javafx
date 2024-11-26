package org.csc335.controllers;

import javafx.scene.media.AudioClip;

public class Audio {
    private AudioClip mergeSound;
    private AudioClip mainTheme;

    public Audio() {
        mergeSound = new AudioClip(getClass().getResource("/sounds/mergesound.mp3").toExternalForm());
        mainTheme = new AudioClip(getClass().getResource("/sounds/peak.mp3").toExternalForm());
    }

    public void playMainTheme() {
        this.mainTheme.setVolume(0.1);
        this.mainTheme.play();
        this.mainTheme.setCycleCount(AudioClip.INDEFINITE);
    }

    public void playMergeSound() {
        this.mergeSound.play();
    }

    public void stopMainTheme() {
        this.mainTheme.stop();
    }
}
