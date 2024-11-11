package org.csc335;

import javafx.geometry.Pos;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Tile extends VBox {
  private static final int SIZE = 128;

  public Tile() {
    super();
    super.setAlignment(Pos.CENTER);
    super.setPrefSize(SIZE, SIZE);
    super.setStyle(
        "-fx-background-color: #bdac97; -fx-padding: 5px; -fx-background-insets: 6; -fx-background-radius: 12;");
    super.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.GRAY, 2, 0, 0, 1));
  }
}
