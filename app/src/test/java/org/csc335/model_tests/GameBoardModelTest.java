package org.csc335.model_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.csc335.entity.Direction;
import org.csc335.entity.TileValue;
import org.csc335.models.GameBoardModel;
import org.csc335.models.LeaderboardModel;
import org.csc335.models.TileModel;
import org.junit.jupiter.api.Test;

public class GameBoardModelTest {

  @Test
  public void testReset() {

  }

  @Test
  public void testAddNewTiles() {
    GameBoardModel game = new GameBoardModel(4);

    for (int i = 0; i < 14; i++) {

    }

  }

  @Test
  public void testGameFunction() {
    GameBoardModel game = new GameBoardModel(4);
    TileModel[][] actualBoard = forceGameStartState(game);
    printDebugHelper(game);

    game.reset();
    printDebugHelper(game);
  }

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
    GameBoardModel model = new GameBoardModel(4);

    try {
      Method method = GameBoardModel.class.getDeclaredMethod("shift", Direction.class);
      method.setAccessible(true);

      // testing the shifts

      for (Direction direction : Direction.values()) {
        assert (boolean) method.invoke(model, direction) == true;
        System.out.println("success");
      }

      // System.out.println("cool")

      // System.out.println((boolean) method.invoke(model, Direction.fromVal("abc")));
      assert (boolean) method.invoke(model, Direction.fromVal("abc")) == false;

      /*
       * assertTrue((boolean)method.invoke(model, Direction.RIGHT));
       * assertTrue((boolean)method.invoke(model, Direction.LEFT));
       * assertTrue((boolean)method.invoke(model, Direction.UP));
       * assertTrue((boolean)method.invoke(model, Direction.DOWN));
       * assertFalse((boolean)method.invoke(model, Direction.valueOf("abc")));
       */
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void gameEndTest() {
    GameBoardModel model = new GameBoardModel(4);

    try {
        Method method1 = GameBoardModel.class.getDeclaredMethod("testGameEndMethod");
        Field field = GameBoardModel.class.getDeclaredField("board");
        field.setAccessible(true);
        method1.setAccessible(true);

        TileModel[][] tiles = (TileModel[][])field.get(field);
        tiles[0][0].setValue(TileValue.T1024);
        tiles[1][0].setValue(TileValue.T64);
        tiles[0][1].setValue(TileValue.T64);
        tiles[1][1].setValue(TileValue.T32);


        tiles[3][3].setValue(TileValue.T2048);
        tiles[3][2].setValue(TileValue.T2);
        tiles[2][3].setValue(TileValue.T2);
        tiles[2][2].setValue(TileValue.T8);


        tiles[0][3].setValue(TileValue.T128);
        tiles[0][2].setValue(TileValue.T4);
        tiles[1][3].setValue(TileValue.T4);
        tiles[1][2].setValue(TileValue.T128);

        tiles[3][0].setValue(TileValue.T32);
        tiles[3][1].setValue(TileValue.T16);
        tiles[2][0].setValue(TileValue.T16);
        tiles[2][1].setValue(TileValue.T1024);

        assert (boolean)method1.invoke(model) == true;

        

    } catch (Exception e) {
        System.out.println(e);
    }
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
