package org.csc335.controllers;

import java.time.Duration;

import org.csc335.entity.GameMode;
import org.csc335.interfaces.DialogActionCallback;
import org.csc335.interfaces.DialogActionListener;
import org.csc335.interfaces.DrawerMenuActionListener;
import org.csc335.interfaces.DrawerOptionListener;
import org.csc335.interfaces.GameBoardListener;
import org.csc335.interfaces.GameListener;
import org.csc335.interfaces.MoveCounterListener;
import org.csc335.interfaces.TimerListener;
import org.csc335.models.GameModel;
import org.csc335.navigation.Navigation;
import org.csc335.util.EZLoader;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Represents the main game panel for the 2048 game. This class extends
 * StackPane and serves as the container for the game's UI components, including
 * the game grid and score display. It manages the game's state, handles user
 * input, and updates the UI accordingly.
 */
public final class Game extends StackPane {

  @FXML
  private GameBoard gameBoard;

  @FXML
  private Scoreboard scoreboard;

  @FXML
  private GameLogo logo;

  @FXML
  private BorderPane bp;

  private GameModel model;

  public Game() {
    super();
    EZLoader.load(this, Game.class);

    this.model = new GameModel();
    this.initListeners();
    this.model.setGameMode(GameMode.TRADITIONAL);
  }

  /**
   * Initializes listeners for the game components, including the game model,
   * logo, and game board. Sets up listeners to handle changes in game mode, menu
   * actions, and game board events.
   *
   * @pre The game model, logo, and game board are properly initialized.
   * @post Listeners are added to the game model, logo, and game board to handle
   *       various game events.
   */
  private void initListeners() {
    this.model.addListener(new GameListener() {
      @Override
      public void gameModeChanged(GameMode newMode) {
        // Reset the game state before starting a new mode.
        Game.this.resetGame();

        // Play the theme music for the current game mode.
        newMode.playTheme();

        // Inform the logo about the change in game mode.
        Game.this.logo.gameModeChanged(newMode);

        // Switch behavior based on the current game mode.
        switch (newMode) {
          case GameMode.TIME_TRIAL:
            // Create a timer for time trial mode.
            Timer timer = new Timer();

            // Set the timer at the bottom of the game board.
            Game.this.bp.setBottom(timer);

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
            Game.this.bp.setBottom(moveCounter);

            // Add a listener to handle running out of moves.
            moveCounter.addMoveCounterListener(new MoveCounterListener() {
              @Override
              public void userMoved(int movesLeft) {
                // End the game once there are no more moves left.
                if (movesLeft == 0) {
                  Game.this.gameOver();
                }
              }
            });
            break;
          default:
            // Clear any previously set bottom component if the mode is unrecognized.
            Game.this.bp.setBottom(null);
            break;
        }
      }
    });

    this.logo.addMenuActionListener(new DrawerMenuActionListener() {
      @Override
      public void menuClick() {
        // Retrieve the DrawerMenu from the left property of the BorderPane.
        DrawerMenu drawerMenu = (DrawerMenu) Game.this.bp.leftProperty().get();

        // If the DrawerMenu is not initialized, create a new one and set it up.
        if (drawerMenu == null) {
          // Initialize the DrawerMenu with the current game mode from the model.
          drawerMenu = new DrawerMenu(Game.this.model.getGameMode());

          // Display the DrawerMenu.
          drawerMenu.show();

          // Set the DrawerMenu as the left node of the BorderPane.
          Game.this.bp.setLeft(drawerMenu);

          // Add an option listener to handle menu selections and visibility changes.
          drawerMenu.addOptionListener(new DrawerOptionListener() {
            // Update the game mode in the model when a new option is selected.
            public void selectOption(GameMode selected) {
              Game.this.model.setGameMode(selected);
            }

            // Remove the DrawerMenu from the BorderPane when it becomes hidden.
            public void becameHidden() {
              Game.this.bp.setLeft(null);
            }
          });
        } else {
          // Hide the DrawerMenu if it is already initialized.
          drawerMenu.hide();
        }
      }

    });

    this.gameBoard.addGameBoardListener(new GameBoardListener() {
      @Override
      public void tileMoved() {
        // Handle actions based on the current game mode.
        switch (Game.this.model.getGameMode()) {
          // For time trial mode, start the timer if it is not already active.
          case GameMode.TIME_TRIAL:
            Timer timer = (Timer) Game.this.bp.getBottom();
            if (!timer.isTimerActive()) {
              timer.startTimer();
            }
            break;
          // For move limit mode, update the move counter with the total number of moves
          // made on the game board.
          case GameMode.MOVE_LIMIT:
            MoveCounter counter = (MoveCounter) Game.this.bp.getBottom();
            counter.totalMovesMade(Game.this.gameBoard.getMoves());
            break;
          // Default case for any unrecognized game mode.
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

  /**
   * Handles the game over scenario by displaying a GameOverDialog to the user.
   * The dialog shows the user's final score and the number of moves made. It
   * provides options to play again, quit the game, or view the leaderboard. Based
   * on the user's selection, the method either resets the game, exits the
   * application, or navigates to the leaderboard screen.
   *
   * @pre The game has ended, and the final score and number of moves are
   *      available.
   * @post The GameOverDialog is displayed, and the user's choice is processed.
   *       Depending on the user's choice, the game may be reset, the application
   *       may exit, or the leaderboard may be shown.
   */
  private void gameOver() {
    // Define constants for dialog action indices for better readability
    final int PLAY_AGAIN = 0;
    final int QUIT_GAME = 1;
    final int VIEW_LEADERBOARD = 2;

    // Retrieve the bottom node from the BorderPane to check if it's a Timer
    Node bottomNode = this.bp.getBottom();

    // If the bottom node is a Timer, stop it to clean up resources
    if (bottomNode instanceof Timer) {
      ((Timer) bottomNode).stopTimer();
    }

    // Create a GameOverDialog instance with the current score and moves
    Dialog dialog = new GameOverDialog(this.scoreboard.getScore(), this.gameBoard.getMoves());

    // Add an action listener to handle user interactions with the dialog
    dialog.addDialogActionListener(this.createDialogActionListener(dialog, new DialogActionCallback() {
      @Override
      public void dialogAction(int childIdx) {
        // Handle the action based on the index of the pressed button
        switch (childIdx) {
          case PLAY_AGAIN:
            // Reset the game state and hide the dialog
            Game.this.resetGame();
            dialog.hide();
            break;
          case QUIT_GAME:
            // Exit the application
            System.exit(0);
            break;
          case VIEW_LEADERBOARD:
            // Navigate to the Leaderboard view
            Navigation.navigate(new Leaderboard());
            break;
        }
      }
    }));

    // Add the current score to the leaderboard
    Leaderboard.addLeaderboardScore(Game.this.scoreboard.getScore());

    // Display the GameOverDialog
    dialog.show();

    // Add the dialog to the game's children for rendering
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
    final int START_NEW_GAME = 0; // Constant representing the action to start a new game
    final int CANCEL = 1; // Constant representing the action to cancel the dialog
    Dialog newGameDialog = new NewGameDialog(); // Create a new game dialog instance
    newGameDialog.addDialogActionListener(this.createDialogActionListener(newGameDialog, new DialogActionCallback() {
      public void dialogAction(int childIdx) {
        switch (childIdx) {
          case START_NEW_GAME: // User chose to start a new game
            Game.this.resetGame(); // Reset the current game state
            newGameDialog.hide(); // Hide the dialog after starting the game
            break;
          case CANCEL: // User chose to cancel the dialog
            newGameDialog.hide(); // Hide the dialog without taking any action
            break;
        }
      }
    }));
    newGameDialog.show(); // Display the new game dialog to the user

    this.getChildren().add(newGameDialog); // Add the dialog to the game's child components
  }
}
