/*
 * This source file was generated by the Gradle 'init' task
 */
package org.csc335;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(this.getClass().getResource("/app.fxml"));
    Scene scene = new Scene(root);

    stage.setTitle("2048 FX");
    stage.setWidth(800);
    stage.setHeight(600);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
