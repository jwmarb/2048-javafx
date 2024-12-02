package org.csc335.controllers;

import org.csc335.entity.GameMode;
import org.csc335.listeners.DialogActionListener;
import org.csc335.listeners.DrawerMenuActionListener;
import org.csc335.listeners.DrawerOptionListener;
import org.csc335.listeners.GameBoardListener;
import org.csc335.listeners.MoveCounterListener;
import org.csc335.listeners.TimerListener;
import org.csc335.navigation.Navigation;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
      public void tileMoved() {
        switch (Game.this.mode) {
          case GameMode.TIME_TRIAL:
            Timer timer = (Timer) Game.this.bp.getBottom();
            if (!timer.isTimerActive()) {
              timer.startTimer();
            }
            break;
          case GameMode.MOVE_LIMIT:
            MoveCounter counter = (MoveCounter) Game.this.bp.getBottom();
            counter.totalMovesMade(Game.this.gameBoard.getMoves());
            break;
          default:
            break;
        }
      }

      @Override
      public void scoreChanged(int diff) {
        Game.this.scoreboard.addScore(diff);
      }

      @Override
      public void gameOver() {
        Game.this.gameOver();
      }

    });
  }

  private void gameOver() {
    final int PLAY_AGAIN = 0;
    final int QUIT_GAME = 1;
    final int VIEW_LEADERBOARD = 2;

    Node bottomNode = this.bp.getBottom();

    if (bottomNode instanceof Timer) {
      ((Timer) bottomNode).stopTimer();
    }

    Dialog dialog = new Dialog("Game Over!",
        String.format("You scored %d points in %d moves", Game.this.scoreboard.getScore(),
            Game.this.gameBoard.getMoves()));
    dialog.setActions(new Pressable("Play Again", Pressable.FILLED, true),
        new Pressable("Quit Game", Pressable.OUTLINED, true),
        new Pressable("View Leaderboard", Pressable.OUTLINED, true));

    Leaderboard.addLeaderboardScore(Game.this.scoreboard.getScore());

    dialog.addDialogActionListener(new DialogActionListener() {

      @Override
      public void dialogShown() {
        Game.this.gameBoard.disableKeystrokeRecording();
      }

      @Override
      public void dialogHidden() {
        Game.this.getChildren().remove(dialog);
        Game.this.gameBoard.enableKeystrokeRecording();
      }

      @Override
      public void dialogAction(int childIdx) {
        switch (childIdx) {
          case PLAY_AGAIN:
            Game.this.resetGame();
            dialog.hide();
            break;
          case QUIT_GAME:
            System.exit(0);
            break;
          case VIEW_LEADERBOARD:
            Navigation.navigate(new Leaderboard());
            break;
        }
      }
    });

    dialog.show();

    Game.this.getChildren().add(dialog);
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
    Node bottomChild = this.bp.getBottom();

    if (bottomChild instanceof Timer) {
      ((Timer) bottomChild).stopTimer();
      ((Timer) bottomChild).resetTimer();
    } else if (bottomChild instanceof MoveCounter) {
      ((MoveCounter) bottomChild).reset();
    }
  }

  /**
   * Invoked when the user presses "New game"
   */
  @FXML
  public void newGame() {
    final int START_NEW_GAME = 0;
    final int CANCEL = 1;
    Dialog dialog = new Dialog("New Game", "Are you sure you want to start a new game? All progress will be lost.");

    dialog.setActions(new Pressable("Start New Game", Pressable.FILLED, true),
        new Pressable("Cancel", Pressable.OUTLINED, true));

    dialog.addDialogActionListener(new DialogActionListener() {

      @Override
      public void dialogShown() {
        Game.this.gameBoard.disableKeystrokeRecording();
      }

      @Override
      public void dialogHidden() {
        Game.this.getChildren().remove(dialog);
        Game.this.gameBoard.enableKeystrokeRecording();
      }

      @Override
      public void dialogAction(int childIdx) {
        switch (childIdx) {
          case START_NEW_GAME:
            Game.this.resetGame();
            dialog.hide();
            break;
          case CANCEL:
            dialog.hide();
            break;
        }
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
    this.resetGame();
    this.logo.changeMode(this.mode);
    this.gameBoard.setMode(this.mode);

    switch (this.mode) {
      case GameMode.TIME_TRIAL:
        Timer timer = new Timer();
        this.bp.setBottom(timer);
        timer.addTimerListener(new TimerListener() {
          @Override
          public void timerFinished() {
            Game.this.gameOver();
          }

        });
        break;
      case GameMode.MOVE_LIMIT:
        MoveCounter moveCounter = new MoveCounter();
        this.bp.setBottom(moveCounter);

        moveCounter.addMoveCounterListener(new MoveCounterListener() {
          @Override
          public void noMoreMovesLeft() {
            Game.this.gameOver();
          }
        });
        break;
      default:
        this.bp.setBottom(null);
        break;
    }
  }
}
