package org.csc335;

import javafx.geometry.Pos;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.Optional;

public class Tile extends VBox {

  // Enum TileValue, which represents the values a tile can hold
  public static enum TileValue {
    T2, T4, T8, T16, T32, T64, T128, T256, T512, T1024, T2048;

    // since the enum is ordered, uses its ordinal value to shift 2 left to
    // yield the value represented by the enum
    public int value() {
      return 2<<this.ordinal();
    }
  }

  // GUI tile size
  private static final int SIZE = 128;

  // Value that the tile holds
  private Optional<TileValue> tileValue;

  // Construct GUI Element (default value of empty)
  public Tile() {
    super();
    super.setAlignment(Pos.CENTER);
    super.setPrefSize(SIZE, SIZE);
    super.setStyle(
        "-fx-background-color: #bdac97; -fx-padding: 5px; -fx-background-insets: 6; -fx-background-radius: 12;");
    super.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.GRAY, 2, 0, 0, 1));

    tileValue = Optional.empty();
  }

  // Tile constuction with default value
  public Tile(TileValue value) {
    super();
    super.setAlignment(Pos.CENTER);
    super.setPrefSize(SIZE, SIZE);
    super.setStyle(
        "-fx-background-color: #bdac97; -fx-padding: 5px; -fx-background-insets: 6; -fx-background-radius: 12;");
    super.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.GRAY, 2, 0, 0, 1));

    tileValue = Optional.of(value);
  }

  // returns true if the tile is blank (empty tileValue)
  public boolean isBlank() {
    return !tileValue.isPresent();
  }

  /**
   * @pre value != null (it is valid)
   * @param value
   */
  public void setValue(TileValue value) {
    tileValue = Optional.of(value);
  }

  // empty the tile
  public void makeBlank() { 
    tileValue = Optional.empty();
  }

  // basic get for the value. Can use later fot the empty optional
  // or TODO: refactor such that isBlank is used and this returns
  // the raw enum?
  public Optional<TileValue> getValue() {
    return tileValue;
  }
}
