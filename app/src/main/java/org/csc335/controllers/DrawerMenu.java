package org.csc335.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class DrawerMenu extends VBox {

  public DrawerMenu() {
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/DrawerMenu.fxml"));

    loader.setRoot(this);
    loader.setController(this);
    this.getStylesheets().add(this.getClass().getResource("/css/drawermenu.css").toExternalForm());
    try {
      loader.load();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }
}
