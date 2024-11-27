package org.csc335.controllers;

import java.util.ArrayList;
import java.util.List;

import org.csc335.entity.GameMode;
import org.csc335.listeners.DrawerOptionListener;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class DrawerMenu extends VBox {

  @FXML
  private VBox container;

  private DrawerOption selected;

  private List<DrawerOptionListener> listeners;

  public DrawerMenu() {
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/DrawerMenu.fxml"));
    this.listeners = new ArrayList<>();
    loader.setRoot(this);
    loader.setController(this);
    this.getStylesheets().add(this.getClass().getResource("/css/drawermenu.css").toExternalForm());
    try {
      loader.load();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    this.createOptions();
  }

  private void createOptions() {
    GameMode[] modes = GameMode.values();

    for (GameMode mode : modes) {
      DrawerOption option = mode.createDrawerOption();
      option.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          setSelected(option);
        }
      });
      container.getChildren().add(option);
    }

    // Automatically select the first child as the first selected option by default
    this.setSelected(((DrawerOption) container.getChildren().get(0)));
  }

  private void setSelected(DrawerOption option) {
    // Ensure that these are not the same reference
    if (option != this.selected) {
      if (this.selected != null) {
        this.selected.deselect();
      }
      option.select();
      this.selected = option;
      this.notifyListeners();
    }
  }

  private void notifyListeners() {
    for (DrawerOptionListener listener : this.listeners) {
      listener.selectOption(this.selected);
    }
  }

  public void addOptionListener(DrawerOptionListener listener) {
    this.listeners.add(listener);
  }
}
