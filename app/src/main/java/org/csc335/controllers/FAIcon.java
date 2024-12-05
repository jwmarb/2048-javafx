package org.csc335.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FAIcon is a custom class that extends JavaFX's Label component to represent a
 * Font Awesome icon. It allows for easy integration and manipulation of Font
 * Awesome icons within a JavaFX application. The class handles the creation and
 * display of icons by leveraging Font Awesome font files.
 */
public class FAIcon extends Label {
  /**
   * FontAwesome 6 implementation of icons
   * 
   * For a full list of icons, see https://fontawesome.com/icons
   * 
   * To implement an icon, simply add the icon name here and its unicode value.
   */
  public static final String BARS = "\uf0c9";
  public static final String STOPWATCH = "\uf2f2";
  public static final String GRID = "\uf84c";
  public static final String HAND = "\uf256";

  @FXML
  private StringProperty icon;

  public FAIcon() {
    this.icon = new SimpleStringProperty();
    this.initListeners();
  }

  /**
   * Initializes a ChangeListener on the {@code icon} property of the FAIcon
   * class. The listener is set up to update the text of the FAIcon whenever the
   * icon property changes. This ensures that the text displayed in the FAIcon
   * component accurately reflects the current icon value.
   *
   * @pre The {@code icon} property must be initialized and not null.
   *      The FAIcon instance should be properly constructed before this method is
   *      called.
   * @post A ChangeListener is added to the {@code icon} property. Whenever the
   *       icon value changes, the text of the FAIcon is updated to match the new
   *       icon value.
   */
  private void initListeners() {
    this.icon.addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        FAIcon.this.setText(newValue);
      }
    });
  }

  /**
   * Sets the icon for the current instance by attempting to retrieve the
   * corresponding Unicode mapping from the pre-defined constants in the
   * {@code FAIcon} class. If the icon name does not correspond to any predefined
   * constant, it defaults to the provided icon string.
   *
   * @pre The provided icon string should be a valid identifier that exists as a
   *      constant in the {@code FAIcon} class or a valid Unicode string.
   * @post The icon property of the current instance is set to either the Unicode
   *       mapping of the provided icon name or the provided icon string itself if
   *       no mapping exists.
   *
   * @param icon The name of the icon to be set, which can be a constant from the
   *             {@code FAIcon}
   *             class or a direct Unicode string. This name is case-insensitive
   *             and will be
   *             converted to uppercase for lookup purposes.
   */
  public void setIcon(String icon) {
    String iconProp;
    try {
      // Try to get the unicode mapping
      iconProp = (String) FAIcon.class.getField(icon.toUpperCase()).get(null);
    } catch (Exception e) {
      // Otherwise use what has been passed in
      iconProp = icon;
    }
    this.icon.set(iconProp);
  }

  public StringProperty iconProperty() {
    return this.icon;
  }

  public String getIcon() {
    return this.icon.get();
  }
}
