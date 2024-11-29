package org.csc335.controllers;

import java.util.ArrayList;
import java.util.List;

import org.csc335.listeners.DialogActionListener;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Dialog extends VBox {

  private List<DialogActionListener> listeners;

  private boolean hidden;

  @FXML
  private VBox dialogContainer;

  @FXML
  private StringProperty title;

  public void setTitle(String title) {
    this.title.set(title);
  }

  public void setDescription(String description) {
    this.description.set(description);
  }

  public void setPrimaryActionText(String text) {
    this.primaryActionText.set(text.toUpperCase());
  }

  public void setSecondaryActionText(String text) {
    this.secondaryActionText.set(text.toUpperCase());
  }

  public String getTitle() {
    return this.title.get();
  }

  public String getDescription() {
    return this.description.get();
  }

  public String getPrimaryActionText() {
    return this.primaryActionText.get();
  }

  public String getSecondaryActionText() {
    return this.secondaryActionText.get();
  }

  @FXML
  private StringProperty description;

  @FXML
  private StringProperty primaryActionText;

  @FXML
  private StringProperty secondaryActionText;

  @FXML
  private Pressable primaryActionPressable;

  @FXML
  private Pressable secondaryActionPressable;

  @FXML
  private Label titleLabel;

  @FXML
  private Label descriptionLabel;

  public Dialog(String title, String description, String primaryActionText, String secondaryActionText) {
    super();
    this.description = new SimpleStringProperty();
    this.title = new SimpleStringProperty();
    this.primaryActionText = new SimpleStringProperty();
    this.secondaryActionText = new SimpleStringProperty();

    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Dialog.fxml"));

    loader.setRoot(this);
    loader.setController(this);

    this.getStylesheets().add(this.getClass().getResource("/css/dialog.css").toExternalForm());

    try {
      loader.load();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    this.hidden = true;
    this.listeners = new ArrayList<>();
    this.title.bindBidirectional(this.titleLabel.textProperty());
    this.description.bindBidirectional(this.descriptionLabel.textProperty());
    this.primaryActionText.bindBidirectional(this.primaryActionPressable.textProperty());
    this.secondaryActionText.bindBidirectional(this.secondaryActionPressable.textProperty());
    this.setTitle(title);
    this.setDescription(description);
    this.setPrimaryActionText(primaryActionText);
    this.setSecondaryActionText(secondaryActionText);
  }

  private void setHidden(boolean hidden) {
    this.hidden = hidden;

    if (this.hidden) {
      for (DialogActionListener listener : this.listeners) {
        listener.dialogHidden();
      }
    }
  }

  public void addDialogActionListener(DialogActionListener listener) {
    this.listeners.add(listener);
  }

  public void show() {
    FadeTransition ft = new FadeTransition(Duration.millis(250), this);
    ft.setFromValue(0.0);
    ft.setToValue(1.0);

    TranslateTransition tt = new TranslateTransition(Duration.millis(250), this.dialogContainer);
    tt.setFromY(-50.0);
    tt.setToY(0.0);
    tt.setInterpolator(Interpolator.EASE_OUT);

    tt.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Dialog.this.setHidden(false);
      }
    });

    tt.play();
    ft.play();
  }

  public void hide() {
    FadeTransition ft = new FadeTransition(Duration.millis(100), this);
    ft.setFromValue(1.0);
    ft.setToValue(0.0);

    TranslateTransition tt = new TranslateTransition(Duration.millis(100), this.dialogContainer);
    tt.setToY(-50.0);
    tt.setFromY(0.0);
    tt.setInterpolator(Interpolator.EASE_OUT);

    tt.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Dialog.this.setHidden(true);
      }
    });

    tt.play();
    ft.play();

  }

  @FXML
  public void primaryAction() {
    for (DialogActionListener listener : this.listeners) {
      listener.primaryAction();
    }
  }

  @FXML
  public void secondaryAction() {
    for (DialogActionListener listener : this.listeners) {
      listener.secondaryAction();
    }
  }

}
