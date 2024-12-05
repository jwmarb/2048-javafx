package org.csc335.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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

  private void initListeners() {
    this.icon.addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        FAIcon.this.setText(newValue);
      }
    });
  }

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