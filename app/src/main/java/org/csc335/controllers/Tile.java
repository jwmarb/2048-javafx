package org.csc335.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Optional;

import org.csc335.entity.TileValue;
import org.csc335.interfaces.Controller;
import org.csc335.models.TileModel;

/**
 * A controller class for a Tile in the game of 2048.
 */
public class Tile extends Controller<VBox, TileModel> {
  @FXML
  private Label label;

  /**
   * Retrieves the current value of the tile represented as an enum.
   *
   * @return An {@code Optional<TileValue>} containing the current tile value
   *         represented as an enum. If the tile value is null, returns an empty
   *         {@code Optional}.
   */
  public Optional<TileValue> getTileValue() {

    return this.model.getValue();
  }

  /**
   * Retrieves the next tile value from the model.
   *
   * @returns An Optional containing the next TileValue if available, or an empty
   *          Optional if no more values are present.
   */
  public Optional<TileValue> getNextTileValue() {
    return this.model.getNextValue();
  }

  /**
   * Retrieves the string representation of the value associated with this tile.
   *
   * @pre The tile's model is initialized and contains a value or is empty.
   * @post The tile's model and its value remain unchanged.
   *
   * @returns The string representation of the tile's value if it exists,
   *          otherwise an empty string.
   */
  public String getValue() {
    Optional<TileValue> tileValue = this.model.getValue();

    if (tileValue.isEmpty()) {
      return "";
    }

    return tileValue.get().toString();
  }

  /**
   * Retrieves the numeric value of the tile. If the tile is blank, it returns 0.
   *
   * @return The integer value representing the number on the tile, or 0 if the
   *         tile is blank.
   */
  public int getNumericValue() {
    return this.model.getNumericValue();
  }

  /**
   * Checks if the tile is blank (i.e., has no value assigned).
   *
   * @return A boolean value indicating whether the tile is blank (true) or not
   *         (false).
   */
  public boolean isBlank() {
    return this.model.isBlank();
  }

  /**
   * Sets the value of the tile using a TileValue object. Converts the TileValue
   * to a string before setting to set the FXML attribute.
   *
   * @param value the new TileValue to be set on the tile
   */
  public void setValue(TileValue value) {
    this.model.setValue(value);
  }

  /**
   * Sets the value of the tile using the provided Optional TileValue.
   *
   * @param value An Optional containing the TileValue to set for the tile.
   *              If the Optional is empty, the tile's value will be set to an
   *              empty value.
   */
  public void setValue(Optional<TileValue> value) {
    this.model.setValue(value);
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
    this.model.random();
  }

  /**
   * Sets the tile to a blank state, removing any current value.
   */
  public void makeBlank() {
    this.model.makeBlank();
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

    if (this.isBlank() != otherTile.isBlank()) {
      return false;
    }

    if (this.isBlank() && otherTile.isBlank()) {
      return true;
    }

    return this.getTileValue().get().equals(otherTile.getTileValue().get());
  }

  public Tile() {
    super(new VBox(), new TileModel());
    super.initialize();
    this.view.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.GRAY, 2, 0, 0, 1));
    this.initListeners();
    this.changeLabelClass();
    this.changeTileClass();
  }

  /**
   * Changes the CSS class of the tile based on its value.
   */
  private void changeTileClass() {
    // Remove all existing classes from the tile
    this.view.getStyleClass().clear();

    // Add base class to all tiles
    this.view.getStyleClass().add("tile-base");

    // If a tile value is present, add the corresponding CSS class
    if (!Tile.this.model.isBlank()) {
      this.view.getStyleClass().add("tile-" + this.model.getValue().get().toString());
    } else {
      // If no value is present, add the blank tile class
      this.view.getStyleClass().add("tile-blank");
    }

  }

  /**
   * Changes the CSS and text of the label based on the current tile value.
   */
  private void changeLabelClass() {
    Tile.this.label.getStyleClass().clear(); // Clear any existing styles on the label

    if (!Tile.this.model.isBlank()) { // Check if there is a tile value present
      String value = Tile.this.model.getValue().get().toString(); // Convert TileValue to string
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
    ChangeListener<TileValue> valueListener = new ChangeListener<TileValue>() {
      public void changed(ObservableValue<? extends TileValue> observable, TileValue oldValue, TileValue newValue) {
        Tile.this.changeLabelClass();
        Tile.this.changeTileClass();
      }
    };
    this.model.addListener(valueListener);
  }
}