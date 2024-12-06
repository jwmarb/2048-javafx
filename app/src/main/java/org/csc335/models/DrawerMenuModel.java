package org.csc335.models;

import org.csc335.entity.GameMode;
import org.csc335.interfaces.DrawerOptionListener;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrawerMenuModel {
  private Optional<GameMode> selected;
  private BooleanProperty isHidden;
  private List<DrawerOptionListener> listeners;

  public DrawerMenuModel() {
    this.selected = Optional.empty();
    this.listeners = new ArrayList<>();
    this.isHidden = new SimpleBooleanProperty();
    this.isHidden.addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
          for (DrawerOptionListener listener : DrawerMenuModel.this.listeners) {
            listener.becameHidden();
          }
        }
      }

    });
  }

  public void setSelected(GameMode option) {
    this.selected = Optional.of(option);
    for (DrawerOptionListener listener : listeners) {
      listener.selectOption(option);
    }
  }

  public void setHidden() {
    this.isHidden.set(true);
  }

  public void setVisible() {
    this.isHidden.set(false);
  }

  public Optional<GameMode> getSelected() {
    return this.selected;
  }

  public Optional<Integer> getSelectedOrdinal() {
    if (this.selected.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(this.selected.get().ordinal());
  }

  public void addListener(DrawerOptionListener listener) {
    this.listeners.add(listener);
  }
}