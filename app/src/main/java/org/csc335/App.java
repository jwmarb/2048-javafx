/*
 * This source file was generated by the Gradle 'init' task
 */
package org.csc335;

import java.io.IOException;

import org.csc335.navigation.Navigation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class App extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle("2048 FX");
    stage.setWidth(800);
    stage.setHeight(800);

    Navigation.setStage(stage);

    Navigation.navigate(FXMLLoader.load(this.getClass().getResource("/view/2048menu.fxml")));
    // Navigation.navigate(FXMLLoader.load(this.getClass().getResource("/stories/TileStory.fxml")));

    // GameBoard g = new GameBoard();
    // g.printBoard();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
