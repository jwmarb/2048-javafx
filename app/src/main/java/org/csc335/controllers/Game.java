package org.csc335.controllers;

import org.csc335.entity.GameMode;
import org.csc335.entity.Player;
import org.csc335.listeners.DialogActionListener;
import org.csc335.listeners.DrawerMenuActionListener;
import org.csc335.listeners.DrawerOptionListener;
import org.csc335.listeners.GameBoardListener;
import org.csc335.navigation.Navigation;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Game extends StackPane {

  @FXML
  private GameBoard gameBoard;

  @FXML
  private Scoreboard scoreboard;

  @FXML
  private GameLogo logo;

  @FXML
  private BorderPane bp;

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
        DrawerMenu drawerMenu = (DrawerMenu) Game.this.bp.leftProperty().get();
        if (drawerMenu == null) {
          drawerMenu = new DrawerMenu();
          drawerMenu.show();
          Game.this.bp.setLeft(drawerMenu);
          drawerMenu.addOptionListener(new DrawerOptionListener() {
            public void selectOption(DrawerOption selected) {
              Game.this.setMode(selected.getMode());
            }

            public void becameHidden() {
              Game.this.bp.setLeft(null);
            }
          });
        } else {
          drawerMenu.hide();
        }
      }

    });

    this.gameBoard.addGameBoardListener(new GameBoardListener() {
      @Override
      public void scoreChanged(int diff) {
        Game.this.scoreboard.addScore(diff);
      }

      @Override
      public void gameOver() {
        Dialog dialog = new Dialog("Game Over!",
            String.format("You scored %d points in %d moves", Game.this.scoreboard.getScore(),
                Game.this.gameBoard.getMoves()),
            "Play Again",
            "View Leaderboard");
        dialog.addDialogActionListener(new DialogActionListener() {

          @Override
          public void dialogHidden() {
            Game.this.getChildren().remove(dialog);
          }

          @Override
          public void primaryAction() {
            Game.this.resetGame();
            dialog.hide();
          }

          @Override
          public void secondaryAction() {
            Navigation.navigate(new Leaderboard().setTop5("leaderboard.txt", new Player("DummyPlayer123", scoreboard.getScore()))); // testing the leaderboard stuff
          }

        });

        dialog.show();

        Game.this.getChildren().add(dialog);
      }

    });
  }

  /**
   * Resets the game
   * 
   * This resets the current score (not the highest recorded score!) and the tile
   * layout.
   * 
   * @post The state of the game is returned to how it was at the start
   */
  private void resetGame() {
    // TODO: Reset the game here!
    this.gameBoard.reset();
    this.scoreboard.reset();
  }

  /**
   * Invoked when the user presses "New game"
   */
  @FXML
  public void newGame() {
    Dialog dialog = new Dialog("New Game", "Are you sure you want to start a new game? All progress will be lost.",
        "Start New Game", "Cancel");

    dialog.addDialogActionListener(new DialogActionListener() {

      @Override
      public void dialogHidden() {
        Game.this.getChildren().remove(dialog);
      }

      @Override
      public void primaryAction() {
        Game.this.resetGame();
        dialog.hide();
      }

      @Override
      public void secondaryAction() {
        dialog.hide();
      }

    });

    dialog.show();

    this.getChildren().add(dialog);
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
