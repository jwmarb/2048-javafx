package org.csc335.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Optional;

import org.csc335.entity.TileValue;

/**
 * A controller class for a Tile in the game. The Tile is represented by a VBox
 * and contains a Label that displays the value of the tile.
 */
public class Tile extends VBox {

  private Optional<TileValue> tileValue;

  @FXML
  private StringProperty value;

  @FXML
  private Label label;

  /**
   * Returns the current value of the tile. Reserved for FXML attributes.
   *
   * @return the current value of the tile as a string
   */
  public String getValue() {
    return this.value.get();
  }

  /**
   * returns tile value enum
   * 
   * @pre isBlank method is called first -> always returns non-null
   * 
   * @return the current tile value rep as the enum
   */
  public TileValue getTileValue() {
    return this.tileValue.orElse(null);
  }

  /**
   * returns bool, true if no value (blank), false otherwise
   * 
   * @return boolean representing if tile is blank or not
   */
  public boolean isBlank() {
    return !this.tileValue.isPresent();
  }

  /**
   * Sets the value of the tile. Reserved for FXML attributes.
   *
   * @param value the new value to be set on the tile
   */
  public void setValue(String value) {
    this.value.set(value);
  }

  /**
   * Sets the value of the tile using a TileValue object. Converts the TileValue
   * to a string before setting to set the FXML attribute.
   *
   * @param value the new TileValue to be set on the tile
   */
  public void setValue(TileValue value) {
    this.value.set(value.toString());
  }

  // TODO: something for joesph. want methods that work here to deal direct w/
  // tile insread of setting attributes
  public void makeBlank() {
    this.value.set(null); // set it to empty
  }

  // public void merge() {
  // this.tileValue = Optional.of(tileValue.get().next());
  // this.value.set(this.tileValue.toString()); // update String
  // }
  // public static void swap(Tile t1, Tile t2) {
  // Optional<TileValue> temp = t1.tileValue;
  // t1.tileValue = t2.tileValue;
  // t1.setValue(t1.tileValue.get());
  // t2.tileValue = temp;
  // t2.setValue(t2.tileValue.get());
  // }

  public Tile() {
    super();
    this.value = new SimpleStringProperty();
    this.tileValue = Optional.empty();
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Tile.fxml"));
    super.getStylesheets().add(this.getClass().getResource("/css/tile.css").toExternalForm());
    loader.setRoot(this);
    loader.setController(this);
    try {
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    super.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.GRAY, 2, 0, 0, 1));
    this.initListeners();
    this.changeLabelClass();
    this.changeTileClass();
  }

  /**
   * Changes the CSS class of the tile based on its value.
   */
  private void changeTileClass() {
    // Remove all existing classes from the tile
    this.getStyleClass().clear();

    // Add base class to all tiles
    this.getStyleClass().add("tile-base");

    // If a tile value is present, add the corresponding CSS class
    if (this.tileValue.isPresent()) {
      this.getStyleClass().add("tile-" + this.tileValue.get().toString());
    } else {
      // If no value is present, add the blank tile class
      this.getStyleClass().add("tile-blank");
    }

  }

  /**
   * Changes the CSS and text of the label based on the current tile value.
   */
  private void changeLabelClass() {
    Tile.this.label.getStyleClass().clear(); // Clear any existing styles on the label

    if (Tile.this.tileValue.isPresent()) { // Check if there is a tile value present
      String value = Tile.this.tileValue.get().toString(); // Convert TileValue to string
      Tile.this.label.setText(value); // Set the label text to the value
      Tile.this.label.getStyleClass().add("value-" + value); // Add CSS class based on the value
    } else {
      Tile.this.label.setText(""); // If no value, clear the label text
    }
  }

  /**
   * Initializes listeners for changes to the tile's value property.
   */
  private void initListeners() {
    ChangeListener<String> valueListener = new ChangeListener<String>() {
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        Tile.this.tileValue = TileValue.fromString(newValue);
        Tile.this.changeLabelClass();
        Tile.this.changeTileClass();
      }
    };
    this.value.addListener(valueListener);
  }
}
