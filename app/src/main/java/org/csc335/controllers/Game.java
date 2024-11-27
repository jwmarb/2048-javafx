package org.csc335.controllers;

import org.csc335.entity.GameMode;
import org.csc335.listeners.DrawerMenuActionListener;
import org.csc335.listeners.DrawerOptionListener;
import org.csc335.listeners.GameBoardListener;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class Game extends BorderPane {

  @FXML
  private GameBoard gameBoard;

  @FXML
  private Scoreboard scoreboard;

  @FXML
  private GameLogo logo;

  private GameMode mode;

  public Game() {
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Game.fxml"));
    loader.setRoot(this);
    loader.setController(this);
    this.getStylesheets().add(this.getClass().getResource("/css/game.css").toExternalForm());

    try {
      loader.load();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    this.setMode(GameMode.TRADITIONAL);

    this.logo.addMenuActionListener(new DrawerMenuActionListener() {
      @Override
      public void menuClick() {
        if (Game.this.leftProperty().isNull().get()) {
          DrawerMenu drawerMenu = new DrawerMenu();
          Game.this.setLeft(drawerMenu);
          drawerMenu.addOptionListener(new DrawerOptionListener() {
            @Override
            public void selectOption(DrawerOption selected) {
              Game.this.setMode(selected.getMode());
            }
          });
        } else {
          Game.this.setLeft(null);
        }
      }

    });

    this.gameBoard.addGameBoardListener(new GameBoardListener() {
      @Override
      public void scoreChanged(int diff) {
        Game.this.scoreboard.addScore(diff);
      }

    });
  }

  /**
   * Invoked when the user presses "New game"
   * 
   * TODO: Add a warning popup, but for now make this reset the game
   */
  @FXML
  public void newGame() {

  }

  private void setMode(GameMode mode) {
    this.mode = mode;
    this.notifyDependencies();
  }

  private void notifyDependencies() {
    this.logo.changeMode(this.mode);
    this.gameBoard.setMode(this.mode);
  }
}
