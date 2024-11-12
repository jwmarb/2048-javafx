/*
 * This source file was generated by the Gradle 'init' task
 */
package org.csc335;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.File;
import java.net.MalformedURLException;

public class App extends Application {

  @Override
  public void start(Stage primarystage) throws MalformedURLException {
    StackPane root = new StackPane();
    root.setStyle("-fx-background-color: #faf8f0");
    Scene scene = new Scene(root, 520, 520);
    Stage stage = new Stage();

    Text title = new Text("2048 Game");
    title.setTextAlignment(TextAlignment.CENTER);
    title.setFont(Font.font("Verdana", 50));

    Text modeSelect = new Text("Select a gamemode");
    modeSelect.setTextAlignment(TextAlignment.CENTER);
    modeSelect.setFont(Font.font("Verdana", 20));

    Button modeOne = new Button("Mode One");
    modeOne.setAlignment(Pos.CENTER);
    modeOne.setFont(new Font("Verdana", 20));
    modeOne.setStyle("-fx-background-color: #eae7d9");

    Button modeTwo = new Button("Mode Two");
    modeTwo.setAlignment(Pos.CENTER);
    modeTwo.setFont(new Font("Verdana", 20));
    modeTwo.setStyle("-fx-background-color: #eae7d9");

    Image image = new Image(new File("app\\build\\resources\\graphics\\2048.png").toURI().toURL().toExternalForm());
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(200);
    imageView.setFitWidth(250);

    root.getChildren().add(title);
    root.getChildren().add(modeSelect);
    root.getChildren().add(modeOne);
    root.getChildren().add(modeTwo);
    root.getChildren().add(imageView);

    StackPane.setAlignment(title, Pos.TOP_CENTER);
    StackPane.setAlignment(modeSelect, Pos.BOTTOM_CENTER);
    StackPane.setAlignment(modeOne, Pos.BOTTOM_LEFT);
    StackPane.setAlignment(modeTwo, Pos.BOTTOM_RIGHT);
    StackPane.setAlignment(imageView, Pos.CENTER);

    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
