package org.csc335.controllers;

import java.time.Duration;

import org.csc335.entity.GameMode;
import org.csc335.interfaces.DialogActionCallback;
import org.csc335.interfaces.DialogActionListener;
import org.csc335.interfaces.DrawerMenuActionListener;
import org.csc335.interfaces.DrawerOptionListener;
import org.csc335.interfaces.GameBoardListener;
import org.csc335.interfaces.MoveCounterListener;
import org.csc335.interfaces.TimerListener;
import org.csc335.navigation.Navigation;
import org.csc335.util.EZLoader;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public final class Game extends StackPane {

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
    EZLoader.load(this, Game.class);

    this.setMode(GameMode.TRADITIONAL);

    this.logo.addMenuActionListener(new DrawerMenuActionListener() {
      @Override
      public void menuClick() {
        DrawerMenu drawerMenu = (DrawerMenu) Game.this.bp.leftProperty().get();
        if (drawerMenu == null) {
          drawerMenu = new DrawerMenu(Game.this.mode);
          drawerMenu.show();
          Game.this.bp.setLeft(drawerMenu);
          drawerMenu.addOptionListener(new DrawerOptionListener() {
            public void selectOption(GameMode selected) {
              Game.this.setMode(selected);
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

    Dialog dialog = new GameOverDialog(this.scoreboard.getScore(), this.gameBoard.getMoves());

    dialog.addDialogActionListener(this.createDialogActionListener(dialog, new DialogActionCallback() {
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
    }));

    Leaderboard.addLeaderboardScore(Game.this.scoreboard.getScore());
    dialog.show();

    Game.this.getChildren().add(dialog);
  }

  /**
   * Resets the game state to its initial configuration.
   *
   * This method performs the following actions:
   * - Resets the current score (not the highest recorded score) to zero.
   * - Resets the tile layout to its initial state.
   * - Stops and resets the timer if present, returning it to zero.
   * - Resets the move counter if present, returning it to zero.
   *
   * @post The game state is returned to how it was at the start, including the
   *       game board, scoreboard, timer, and move counter.
   */
  private void resetGame() {
    // Reset the game board to its initial state.
    this.gameBoard.reset();

    // Reset the scoreboard to its initial state.
    this.scoreboard.reset();

    // Retrieve the bottom child node from the border pane (bp).
    Node bottomChild = this.bp.getBottom();

    // Check if the bottom child node is an instance of Timer.
    if (bottomChild instanceof Timer) {
      // Cast the node to Timer and stop the timer.
      ((Timer) bottomChild).stopTimer();
      // Reset the timer to its initial state.
      ((Timer) bottomChild).resetTimer();
    }
    // Check if the bottom child node is an instance of MoveCounter.
    else if (bottomChild instanceof MoveCounter) {
      // Reset the move counter to its initial state.
      ((MoveCounter) bottomChild).reset();
    }
  }

  /**
   * Creates a new {@link DialogActionListener} for managing the events related to
   * a dialog's lifecycle. The listener disables keystroke recording when the
   * dialog is shown, enables it when the dialog is hidden, and forwards any
   * dialog actions to the provided {@link DialogActionCallback}.
   *
   * @post A new {@link DialogActionListener} is created and returned.
   *
   * @param dialog         The dialog for which the action listener is being
   *                       created.
   * @param onDialogAction The callback to be invoked when a dialog action occurs.
   * 
   * @return A new {@link DialogActionListener} instance configured to manage the
   *         specified dialog.
   */
  private DialogActionListener createDialogActionListener(Dialog dialog, DialogActionCallback onDialogAction) {
    return new DialogActionListener() {

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
        onDialogAction.dialogAction(childIdx);
      }
    };
  }

  /**
   * Invoked when the user presses the "New game" button. This method displays a
   * dialog to confirm the start of a new game. If the user confirms, it resets
   * the current game state.
   * 
   * @pre The user has clicked the "New game" button.
   * @post If the user confirms starting a new game, the current game state is
   *       reset.
   *       The dialog is displayed to the user, and it is hidden after the user
   *       makes a choice.
   */
  @FXML
  public void newGame() {
    final int START_NEW_GAME = 0;
    final int CANCEL = 1;
    Dialog newGameDialog = new NewGameDialog();
    newGameDialog.addDialogActionListener(this.createDialogActionListener(newGameDialog, new DialogActionCallback() {
      public void dialogAction(int childIdx) {
        switch (childIdx) {
          case START_NEW_GAME:
            Game.this.resetGame();
            newGameDialog.hide();
            break;
          case CANCEL:
            newGameDialog.hide();
            break;
        }
      }
    }));
    newGameDialog.show();

    this.getChildren().add(newGameDialog);
  }

  /**
   * Sets the current game mode and notifies all dependent components of the
   * change.
   *
   * @post The game mode of the game is updated and all dependencies are notified.
   * @param mode The new game mode to be set.
   */
  private void setMode(GameMode mode) {
    this.mode = mode;
    this.notifyDependencies();
  }

  /**
   * Notifies all dependent components of a change in the game mode. This method
   * resets the game state, plays the theme music for the current game mode,
   * informs the logo, and sets up the necessary listeners based on the current
   * mode.
   *
   * @pre The game mode is set and this.mode is a valid GameMode. The game board
   *      panel (bp) and mode-specific components (Timer, MoveCounter) are
   *      properly initialized.
   * @post The game state is reset, theme music plays, the logo is updated, and
   *       mode-specific listeners are attached. The game board's bottom component
   *       is set to the timer or move counter based on the game mode, or cleared
   *       if the mode is unrecognized.
   */
  private void notifyDependencies() {
    // Reset the game state before starting a new mode.
    this.resetGame();

    // Play the theme music for the current game mode.
    this.mode.playTheme();

    // Inform the logo about the change in game mode.
    this.logo.gameModeChanged(this.mode);

    // Switch behavior based on the current game mode.
    switch (this.mode) {
      case GameMode.TIME_TRIAL:
        // Create a timer for time trial mode.
        Timer timer = new Timer();

        // Set the timer at the bottom of the game board.
        this.bp.setBottom(timer);

        // Add a listener to handle the end of the timer.
        timer.addTimerListener(new TimerListener() {

          @Override
          public void timerChanged(Duration timeLeft) {
            if (timeLeft.isZero()) {
              Game.this.gameOver();
            }
          }

        });
        break;
      case GameMode.MOVE_LIMIT:
        // Create a move counter for move limit mode.
        MoveCounter moveCounter = new MoveCounter();

        // Set the move counter at the bottom of the game board.
        this.bp.setBottom(moveCounter);

        // Add a listener to handle running out of moves.
        moveCounter.addMoveCounterListener(new MoveCounterListener() {
          @Override
          public void noMoreMovesLeft() {
            // End the game once there are no more moves left.
            Game.this.gameOver();
          }
        });
        break;
      default:
        // Clear any previously set bottom component if the mode is unrecognized.
        this.bp.setBottom(null);
        break;
    }
  }
}
