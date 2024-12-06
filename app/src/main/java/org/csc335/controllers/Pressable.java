package org.csc335.controllers;

import org.csc335.entity.PressableVariant;
import org.csc335.models.PressableModel;
import org.csc335.util.EZLoader;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.StringConverter;

/**
 * Represents a pressable button that adheres to the application's button
 * styling. This class extends the standard Button class and is designed to
 * facilitate the creation of consistently styled buttons throughout the
 * application.
 */
public class Pressable extends Button {

  private PressableModel model;

  @FXML
  private StringProperty variant;

  @FXML
  private BooleanProperty grow;

  /**
   * Sets the variant of the Pressable object by converting the provided string
   * to a PressableVariant enum and updating the model's variant.
   *
   * @post The model's variant is updated to the corresponding PressableVariant.
   * @param variant The new variant to be set for the pressable element.
   * 
   */
  public void setVariant(String variant) {
    this.model.setVariant(PressableVariant.valueOf(variant.toUpperCase()));
  }

  /**
   * Sets the variant of the pressable element.
   *
   * @post The model's variant is updated to the corresponding PressableVariant.
   * @param variant The new variant to be set for the pressable element.
   */
  public void setVariant(PressableVariant variant) {
    this.model.setVariant(variant);
  }

  /**
   * Retrieves the variant of the model associated with this Pressable object.
   *
   * @returns A string representation of the variant of the model.
   */
  public String getVariant() {
    return this.model.getVariant().toString();
  }

  /**
   * Sets the growth state of the model.
   * 
   * @param grow A boolean value indicating whether the model should grow (true)
   *             or not (false).
   */
  public void setGrow(boolean grow) {
    this.model.setGrow(grow);
  }

  /**
   * Retrieves the grow status from the model.
   *
   * @returns A boolean indicating whether the grow status is active.
   */
  public boolean getGrow() {
    return this.model.getGrow();
  }

  public Pressable(String text, PressableVariant variant, boolean shouldGrow) {
    super(text.toUpperCase());
    this.initializeFields();
    this.initListeners();
    EZLoader.load(this, Pressable.class);
    this.setVariant(variant);
    this.setGrow(shouldGrow);
  }

  public Pressable() {
    super();
    this.initializeFields();
    this.initListeners();
    EZLoader.load(this, Pressable.class);
  }

  /**
   * Initializes the fields of the Pressable controller, setting up the properties
   * for grow and variant. It also binds these properties to their corresponding
   * properties in the Pressable model. The grow property is bound bidirectionally
   * without any conversion. The variant property is bound bidirectionally with a
   * custom converter that handles conversion between PressableVariant enum and
   * its string representation, ensuring case insensitivity during conversion.
   *
   * @post The grow and variant properties in the controller are initialized and
   *       bound to their respective properties in the PressableModel.
   */
  private void initializeFields() {
    // Initialize properties for grow and variant
    this.grow = new SimpleBooleanProperty();
    this.variant = new SimpleStringProperty();
    this.model = new PressableModel();

    // Bind the grow property in the model to the grow property in the controller
    this.grow.bindBidirectional(this.model.growProperty());

    // Bind the variant property in the model to the variant property in the
    // controller with a custom converter
    this.variant.bindBidirectional(this.model.variantProperty(), new StringConverter<PressableVariant>() {
      @Override
      public String toString(PressableVariant object) {
        if (object == null) {
          return null;
        }
        // Convert PressableVariant object to its string representation
        return object.toString();
      }

      @Override
      public PressableVariant fromString(String string) {
        // Convert string to PressableVariant enum, handling case insensitivity
        return PressableVariant.valueOf(string.toUpperCase());
      }
    });
  }

  /**
   * Initializes listeners for the model properties to update the Pressable's UI
   * components accordingly.
   *
   * @post Listeners are added to the growProperty and variantProperty of the
   *       model.
   *       The max width and style class of the Pressable are updated based on the
   *       property values.
   */
  private void initListeners() {
    // Add a listener to the growProperty to adjust the max width based on its
    // value.
    this.model.growProperty().addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        // Set max width to Double.MAX_VALUE if grow is true, otherwise use the computed
        // size.
        Pressable.this.setMaxWidth(newValue ? Double.MAX_VALUE : USE_COMPUTED_SIZE);
      }

    });

    // Add a listener to the variantProperty to update the style class based on its
    // value.
    this.model.variantProperty().addListener(new ChangeListener<PressableVariant>() {
      @Override
      public void changed(ObservableValue<? extends PressableVariant> observable, PressableVariant oldValue,
          PressableVariant newValue) {
        // Update the style class based on the new variant value.
        switch (newValue) {
          case FILLED:
            // Set the style class to "btn-filled" for the FILLED variant.
            Pressable.this.getOrSetStyleClass(1, "btn-filled");
            break;
          case OUTLINED:
            // Set the style class to "btn-outlined" for the OUTLINED variant.
            Pressable.this.getOrSetStyleClass(1, "btn-outlined");
            break;
          default:
            // Throw an exception for any invalid PressableVariant.
            throw new RuntimeException(String.format("Invalid attribute for Pressable type: \"%s\"", newValue));
        }
      }
    });
  }

  /**
   * Retrieves or sets the style class at the specified index in the node's style
   * class list. If the list size is less than the specified index, the style
   * class is added to the list. Otherwise, the style class at the specified index
   * is replaced with the new style class.
   *
   * @pre The index (idx) is non-negative; the styleClass is a valid CSS class
   *      name.
   * @post The style class list is modified: either a new style class is added or
   *       an existing one is replaced.
   * @param idx        the index at which to get or set the style class.
   * @param styleClass the style class to be added or set at the specified index.
   */
  private void getOrSetStyleClass(int idx, String styleClass) {
    if (this.getStyleClass().size() >= idx) {
      this.getStyleClass().add(styleClass);
    } else {
      this.getStyleClass().set(idx, styleClass);
    }
  }

}
