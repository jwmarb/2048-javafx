package org.csc335.models;

import org.csc335.entity.PressableVariant;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a model that can be pressed. This class encapsulates the behavior
 * and state of the `Pressable` component, which can be used in various UI
 * components or interactive elements within the application.
 */
public class PressableModel {
  private ObjectProperty<PressableVariant> variant;
  private BooleanProperty grow;

  public PressableModel() {
    this.variant = new SimpleObjectProperty<>(); // default
    this.grow = new SimpleBooleanProperty();
  }

  /**
   * Sets the variant of the pressable model.
   *
   * @post The internal variant of the pressable model is updated to the new
   *       variant.
   * @param variant The new variant to be set for the pressable model. This
   *                parameter must be a non-null instance of PressableVariant.
   */
  public void setVariant(PressableVariant variant) {
    this.variant.set(variant);
  }

  /**
   * Retrieves the current variant of the pressable model.
   *
   * @returns The current PressableVariant object.
   */
  public PressableVariant getVariant() {
    return this.variant.get();
  }

  /**
   * Returns the ObjectProperty associated with the variant of this
   * PressableModel.
   * 
   * @returns The ObjectProperty<PressableVariant> representing the variant of
   *          this PressableModel.
   */
  public ObjectProperty<PressableVariant> variantProperty() {
    return this.variant;
  }

  /**
   * Sets the growth state of the pressable model.
   *
   * @post The growth state of the pressable model is updated to the specified
   *       value.
   * @param grow A boolean indicating whether the model should grow (true) or not
   *             (false).
   */
  public void setGrow(boolean grow) {
    this.grow.set(grow);
  }

  /**
   * Retrieves the current state of the grow property.
   *
   * @returns The current boolean value of the grow property.
   */
  public boolean getGrow() {
    return this.grow.get();
  }

  /**
   * Returns the BooleanProperty associated with the grow state of the model.
   *
   * @returns The BooleanProperty representing the grow state of the model.
   */
  public BooleanProperty growProperty() {
    return this.grow;
  }
}
