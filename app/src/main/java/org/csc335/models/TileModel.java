package org.csc335.models;

import java.util.Optional;

import org.csc335.entity.TileValue;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

/**
 * Represents a model for a tile in a 2048 game. This model holds the data and
 * state of a single tile, including its value and provides methods to
 * manipulate and retrieve this data. It uses JavaFX properties to allow for
 * easy integration with UI components and to support property change listeners.
 */
public class TileModel {
  // Constant representing the probability of a specific tile value (T4)
  // appearing. This value is used to determine the likelihood of a tile being
  // assigned a value of 4.
  private static final double T4_CHANCE = 0.75;

  // Holds the optional value of the tile, which can be empty if the tile has no
  // assigned value.
  private ObjectProperty<TileValue> tileValue;

  /**
   * Adds a ChangeListener to the tileValue property to listen for changes in the
   * tile's value.
   *
   * @pre The listener is not null and has not already been added.
   * @post The listener is added to the tileValue property's list of listeners.
   *
   * @param listener The ChangeListener to be added. It will be notified whenever
   *                 the tileValue changes.
   */
  public void addListener(ChangeListener<? super TileValue> listener) {
    this.tileValue.addListener(listener);
  }

  public TileModel() {
    this.tileValue = new SimpleObjectProperty<>();
  }

  /**
   * Sets the value of the tile to the specified TileValue.
   *
   * @post The value of this.tileValue is updated to the provided tileValue.
   * @param tileValue the new value to set for the tile; must be a valid
   *                  TileValue.
   */
  public void setValue(TileValue tileValue) {
    this.tileValue.set(tileValue);
  }

  /**
   * Sets the value of the tile to the provided TileValue, or to null if the
   * provided Optional is empty.
   *
   * @post The tileValue field holds the provided TileValue if present, otherwise
   *       it holds null.
   * @param value An Optional containing the TileValue to set, or empty to set the
   *              value to null.
   */
  public void setValue(Optional<TileValue> value) {
    this.tileValue.set(value.orElse(null));
  }

  /**
   * Retrieves the numeric value associated with the tile. If the tile's value is
   * empty, returns 0. Otherwise, returns the numeric value of the tile.
   *
   * @pre The tile's value may or may not be present.
   * @post The tile's value remains unchanged.
   *
   * @return The numeric value of the tile if present, otherwise 0.
   */
  public int getNumericValue() {
    if (this.getValue().isEmpty()) {
      return 0;
    }

    return this.getValue().get().value();
  }

  /**
   * Retrieves the value of the tile.
   *
   * @return An Optional containing the TileValue if it is present, otherwise an
   *         empty Optional.
   */
  public Optional<TileValue> getValue() {
    return Optional.ofNullable(this.tileValue.get());
  }

  /**
   * Retrieves the next value for the current tile, if available.
   *
   * @pre The current tile value is either present or empty, TileValue != T2048
   *
   * @returns An Optional containing the next TileValue if it exists;
   *          otherwise, returns Optional.empty() if the current tile value
   *          is empty or there is no next value.
   */
  public Optional<TileValue> getNextValue() {
    if (this.getValue().isEmpty()) {
      return Optional.empty();
    }
    return Optional.ofNullable(this.tileValue.get().next());
  }

  /**
   * Randomly assigns a value to the tile. With a probability defined by
   * T4_CHANCE, the tile's value is set to TileValue.T4; otherwise, it is set to
   * TileValue.T2.
   *
   * @post The tileValue property is set to either TileValue.T4 or TileValue.T2.
   */
  public void random() {
    this.tileValue.set(Math.random() > T4_CHANCE ? TileValue.T4 : TileValue.T2);
  }

  /**
   * Checks if the tile is blank by determining if its value is null.
   *
   * @pre The tileValue is properly initialized.
   * @post The state of the tileValue remains unchanged.
   *
   * @returns true if the tile's value is null, false otherwise.
   */
  public boolean isBlank() {
    return this.tileValue.isNull().get();
  }

  /**
   * Sets the tile value to null, effectively making the tile blank.
   *
   * @pre The tileValue is currently set to some value.
   * @post The tileValue is set to null, indicating that the tile is now blank.
   */
  public void makeBlank() {
    this.tileValue.set(null);
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }

    if (other instanceof TileModel == false) {
      return false;
    }

    TileModel otherModel = (TileModel) other;

    if (otherModel.isBlank() != this.isBlank()) {
      return false;
    }

    if (otherModel.isBlank() && this.isBlank()) {
      return true;
    }

    return otherModel.getValue().get().equals(this.getValue().get());
  }
}
