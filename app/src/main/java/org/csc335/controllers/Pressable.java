package org.csc335.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class Pressable extends Button {
  public static final String FILLED = "filled";
  public static final String OUTLINED = "outlined";

  @FXML
  private StringProperty variant;

  @FXML
  private BooleanProperty grow;

  public void setVariant(String variant) {
    this.variant.set(variant);
  }

  public String getVariant() {
    return this.variant.get();
  }

  public void setGrow(boolean grow) {
    this.grow.set(grow);
    this.setMaxWidth(grow ? Double.MAX_VALUE : USE_COMPUTED_SIZE);
  }

  public boolean getGrow() {
    return this.grow.get();
  }

  public Pressable(String text, String variant, boolean shouldGrow) {
    super(text.toUpperCase());

    this.variant = new SimpleStringProperty();
    this.grow = new SimpleBooleanProperty();

    this.loadAssets();
    this.initListeners();

    this.setVariant(variant);
    this.setGrow(shouldGrow);
  }

  public Pressable() {
    super();

    this.variant = new SimpleStringProperty();
    this.grow = new SimpleBooleanProperty();

    this.loadAssets();
    this.initListeners();
  }

  private void loadAssets() {
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Pressable.fxml"));

    loader.setRoot(this);
    loader.setController(this);

    this.getStylesheets().add(this.getClass().getResource("/css/pressable.css").toExternalForm());

    try {
      loader.load();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void initListeners() {
    this.variant.addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        switch (newValue) {
          case Pressable.FILLED:
            Pressable.this.getOrSetStyleClass(1, "btn-filled");
            break;
          case Pressable.OUTLINED:
            Pressable.this.getOrSetStyleClass(1, "btn-outlined");
            break;
          default:
            throw new RuntimeException(String.format("Invalid attribute for Pressable type: \"%s\"", newValue));
        }
      }
    });
  }

  private void getOrSetStyleClass(int idx, String styleClass) {
    if (this.getStyleClass().size() >= idx) {
      this.getStyleClass().add(styleClass);
    } else {
      this.getStyleClass().set(idx, styleClass);
    }
  }

}
