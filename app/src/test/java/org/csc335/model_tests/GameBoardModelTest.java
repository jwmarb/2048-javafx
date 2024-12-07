package org.csc335.model_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.csc335.entity.Direction;
import org.csc335.entity.TileValue;
import org.csc335.models.GameBoardModel;
import org.csc335.models.TileModel;
import org.junit.jupiter.api.Test;

public class GameBoardModelTest {

  @Test
  public void testBlankTiles() {
    GameBoardModel game = new GameBoardModel(4); // should be 14 blank tiles

    updateBlankTiles(game);

    List<TileModel> emptyTiles = getEmptyTileList(game);

    assert (emptyTiles.size() == 14);

    for (int i = 13; i >= 0; i--) {
      updateBlankTiles(game);
      generateRandomValues(game);
      assertEquals(i, emptyTiles.size() - 1);
    }
  }

  @Test
  public void shiftTest() {
    GameBoardModel game = new GameBoardModel(4);
    forceGameStartState(game);
    TileModel[][] actualBoard = forceGameStartState(game);

    
    for (Direction d : Direction.values()) {
      int rowStart = (d == Direction.UP) ? 1: 0;
      int rowEnd = (d == Direction.DOWN) ? actualBoard.length - 1: actualBoard.length;
      int colStart = (d == Direction.LEFT) ? 1: 0;
      int colEnd = (d == Direction.RIGHT) ? actualBoard.length - 1: actualBoard.length;

      for (int r = rowStart; r < rowEnd; r++) {
        for (int c = colStart; c < colEnd; c++) {
          actualBoard[r][c].setValue(TileValue.T2);
          assert (shiftOnly(game, d));
          forceGameStartState(game);
        }
      }
    }
  }

  @Test
  public void testGameEndLose() {
    GameBoardModel game = new GameBoardModel(4);
    TileModel[][] actualBoard = forceGameStartState(game);

    gameAlmostOver(actualBoard);
    // 1024 512  1024 512
    // 512  1024 512  1024
    // 1024 512  1024 512
    // 256  128  64
    actualBoard[3][2].setValue(TileValue.T64);

    shift(game, Direction.LEFT);
    assert (actualBoard[3][3].isBlank());

    shift(game, Direction.RIGHT);
    assert (!actualBoard[3][0].isBlank());

    assert (isGameOver(game));
  }

  @Test
  public void testGameEndWin() {
    GameBoardModel game = new GameBoardModel(4);
    TileModel[][] actualBoard = forceGameStartState(game);

    gameAlmostOver(actualBoard);
    // 1024 512  1024 512
    // 512  1024 512  1024
    // 1024 512  1024 512
    // 256  128  1024
    actualBoard[3][2].setValue(TileValue.T1024);

    shift(game, Direction.UP);

    assert (actualBoard[2][2].getNumericValue() == 2048);
  }

  @Test
  public void testKeyStrokeRecording() {
    GameBoardModel game = new GameBoardModel(4);

    assert(game.shouldRecordKeystrokes());

    game.disableKeystrokeRecording();
    assert(!game.shouldRecordKeystrokes());
    
    game.enableKeystrokeRecording();
    assert(game.shouldRecordKeystrokes());
  }

  private void gameAlmostOver(TileModel[][] actualBoard) {
    actualBoard[0][0].setValue(TileValue.T1024);
    actualBoard[0][1].setValue(TileValue.T512);
    actualBoard[0][2].setValue(TileValue.T1024);
    actualBoard[0][3].setValue(TileValue.T512);
    // 1024 512 1024 512

    actualBoard[1][0].setValue(TileValue.T512);
    actualBoard[1][1].setValue(TileValue.T1024);
    actualBoard[1][2].setValue(TileValue.T512);
    actualBoard[1][3].setValue(TileValue.T1024);
    // 512 1024 512 1024

    actualBoard[2][0].setValue(TileValue.T1024);
    actualBoard[2][1].setValue(TileValue.T512);
    actualBoard[2][2].setValue(TileValue.T1024);
    actualBoard[2][3].setValue(TileValue.T512);
    // 1024 512 1024 512

    actualBoard[3][0].setValue(TileValue.T256);
    actualBoard[3][1].setValue(TileValue.T128);
    // 256 128
  }

  private boolean shiftOnly(GameBoardModel game, Direction d) {
    try {
      Method method = GameBoardModel.class.getDeclaredMethod("shift", Direction.class);
      method.setAccessible(true);
      return (boolean) method.invoke(game, d);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private void shift(GameBoardModel game, Direction d) {
    try {
      Method method = GameBoardModel.class.getDeclaredMethod("handleDirection", Direction.class);
      method.setAccessible(true);
      method.invoke(game, d);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean isGameOver(GameBoardModel game) {
    try {
      Method method = GameBoardModel.class.getDeclaredMethod("testGameEndMethod");
      method.setAccessible(true);
      return (boolean) method.invoke(game);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  private List<TileModel> getEmptyTileList(GameBoardModel game) {
    List<TileModel> emptyTiles = null;
    try {
      Field field = GameBoardModel.class.getDeclaredField("emptyTiles");
      field.setAccessible(true);
      emptyTiles = (List<TileModel>) field.get(game);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return emptyTiles;
  }

  private void updateBlankTiles(GameBoardModel game) {
    try {
      Method method = GameBoardModel.class.getDeclaredMethod("updateBlankTiles");
      method.setAccessible(true);
      method.invoke(game);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void generateRandomValues(GameBoardModel game) {
    try {
      Method method = GameBoardModel.class.getDeclaredMethod("generateRandomValues");
      method.setAccessible(true);
      method.invoke(game);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private TileModel[][] forceGameStartState(GameBoardModel game) {
    TileModel[][] gameBoard = null;
    try {
      Field field = GameBoardModel.class.getDeclaredField("board");
      field.setAccessible(true);
      gameBoard = (TileModel[][]) field.get(game);
    } catch (Exception e) {
      e.printStackTrace();
    }
    for (int i = 0; i < gameBoard.length; ++i) {
      for (int j = 0; j < gameBoard[i].length; ++j) {
        gameBoard[i][j].makeBlank();
      }
    }

    return gameBoard;
  }

  private void printDebugHelper(GameBoardModel game) {
    try {
      Method method = GameBoardModel.class.getDeclaredMethod("printBoard");
      method.setAccessible(true);
      method.invoke(game);
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(game.getMoves());
  }
}