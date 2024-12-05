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
import org.csc335.util.EZLoader;

/**
 * A controller class for a Tile in the game. The Tile is represented by a VBox
 * and contains a Label that displays the value of the tile.
 */
public class Tile extends VBox {

  // Constant representing the probability of a specific tile value (T4)
  // appearing. This value is used to determine the likelihood of a tile being
  // assigned a value of 4.
  private static final double T4_CHANCE = 0.75;

  // Holds the optional value of the tile, which can be empty if the tile has no
  // assigned value.
  private Optional<TileValue> tileValue;

  // JavaFX StringProperty used to bind the tile's value to UI elements.
  // This allows for automatic updates of the UI when the tile's value changes.
  @FXML
  private StringProperty value;

  // JavaFX Label used to display the tile's value in the UI.
  // This label is bound to the 'value' StringProperty, ensuring the UI reflects
  // any changes to the tile's value.
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
   * returns the value of the tile as an int. If blank tile, returns 0.
   * 
   * @return integer value representing the number the tile is
   */
  public int getIntValue() {
    if (isBlank()) {
      return 0;
    }
    return tileValue.get().value();
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

  /**
   * Sets a random value to the tile. The value is determined by a random
   * probability where there is a specific chance (T4_CHANCE) of setting the
   * tile's value to T4. If the random probability does not meet the T4_CHANCE,
   * the tile's value is set to T2.
   *
   * @pre The Tile object has been instantiated, and the T4_CHANCE constant
   *      is defined and within a valid probability range (0.0 to 1.0).
   * @post The tile's value is set to either T2 or T4 based on the random
   *       probability. The tile object is modified with the new value.
   */
  public void setRandomValue() {
    this.value.set(Math.random() < T4_CHANCE ? TileValue.T2.toString() : TileValue.T4.toString());
  }

  /**
   * Sets the tile to a blank state, removing any current value.
   */
  public void makeBlank() {
    this.value.set(null); // set it to empty
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (other.getClass() != this.getClass()) {
      return false;
    }

    final Tile otherTile = (Tile) other;
    return this.getTileValue() == otherTile.getTileValue();
  }

  public Tile() {
    super();
    EZLoader.load(this, Tile.class);
    this.value = new SimpleStringProperty();
    this.tileValue = Optional.empty();
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