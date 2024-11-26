package org.csc335.controllers;

import java.io.IOException;

import org.csc335.navigation.Navigation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

public class MainMenu {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Stage main;
    private Audio sound;

    public void setMainWindow(Stage main) {
        this.main = main;
        sound = new Audio();
    }

    @FXML
    public void switchToStandard(ActionEvent event) {
        Navigation.navigate(new Game());
        sound.stopMainTheme();
    }

    @FXML
    public void switchToModeOne(ActionEvent event) {
        Navigation.navigate(new Game());
        sound.stopMainTheme();
    }

    @FXML
    public void switchToModeTwo(ActionEvent event) {
        Navigation.navigate(new Game());
        sound.stopMainTheme();
    }

}
