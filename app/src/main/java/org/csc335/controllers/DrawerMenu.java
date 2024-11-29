package org.csc335.controllers;

import java.util.ArrayList;
import java.util.List;

import org.csc335.entity.GameMode;
import org.csc335.listeners.DrawerOptionListener;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class DrawerMenu extends VBox {

  private static final Duration MOUNT_DURATION = Duration.millis(150);

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

  public void show() {
    FadeTransition ft = new FadeTransition(MOUNT_DURATION, this);
    TranslateTransition tt = new TranslateTransition(MOUNT_DURATION, this);
    tt.setInterpolator(Interpolator.EASE_IN);

    ft.setFromValue(0.0);
    ft.setToValue(1.0);

    tt.setByX(-50);
    tt.setToX(0);

    ft.play();
    tt.play();

  }

  public void hide() {
    FadeTransition ft = new FadeTransition(MOUNT_DURATION, this);
    TranslateTransition tt = new TranslateTransition(MOUNT_DURATION, this);

    tt.setInterpolator(Interpolator.EASE_OUT);
    ft.setFromValue(1.0);
    ft.setToValue(0.0);

    tt.setByX(0);
    tt.setToX(-50);

    ft.play();
    tt.play();

    tt.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        for (DrawerOptionListener listener : DrawerMenu.this.listeners) {
          listener.becameHidden();
        }
      }
    });
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
