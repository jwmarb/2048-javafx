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

public class Tile extends VBox {

  private Optional<TileValue> tileValue;

  @FXML
  private StringProperty value;

  @FXML
  private Label label;

  public String getValue() {
    return this.value.get();
  }

  public void setValue(String value) {
    this.value.set(value);
  }

  public void setValue(TileValue value) {
    this.value.set(value.toString());
  }

  // Construct GUI Element (default value of empty)
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

  private void changeTileClass() {
    this.getStyleClass().removeAll();
    this.getStyleClass().add("tile-base");
    if (this.tileValue.isPresent()) {
      this.getStyleClass().add("tile-" + this.tileValue.get().toString());
    } else {
      this.getStyleClass().add("tile-blank");
    }
  }

  private void changeLabelClass() {
    Tile.this.label.getStyleClass().clear();
    if (Tile.this.tileValue.isPresent()) {
      String value = Tile.this.tileValue.get().toString();
      Tile.this.label.setText(value);
      Tile.this.label.getStyleClass().add("value-" + value);
    } else {
      Tile.this.label.setText("");
    }
  }

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
