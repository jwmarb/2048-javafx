package org.csc335.models;

import org.csc335.interfaces.DrawerOptionListener;
import org.csc335.javafx_entity.GameMode;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents the model for a drawer menu, responsible for managing the state
 * of the menu, including visibility and the currently selected game mode.
 *
 * This class maintains the hidden/visible state of the drawer menu and keeps
 * track of the selected game mode. It provides methods to update and retrieve
 * this information, as well as to notify listeners of changes.
 */
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