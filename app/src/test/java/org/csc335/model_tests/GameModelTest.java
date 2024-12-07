package org.csc335.model_tests;

import java.lang.reflect.Method;

import org.csc335.interfaces.GameListener;
import org.csc335.javafx_entity.GameMode;
import org.csc335.models.GameModel;
import org.junit.jupiter.api.Test;

public class GameModelTest {

    @Test
    public void testGameMode() {
        GameModel model = new GameModel();

        model.setGameMode(GameMode.TRADITIONAL);

        assert (model.getGameMode() == GameMode.TRADITIONAL);

        model.setGameMode(GameMode.TIME_TRIAL);

        assert (model.getGameMode() == GameMode.TIME_TRIAL);
    }

    GameMode mode = GameMode.TRADITIONAL;

    @Test
    public void testNotifyDependenciesAndAddListener() {
        GameModel model = new GameModel();

        GameListener listener = new GameListener() {
            public void gameModeChanged(GameMode newMode) {
                mode = newMode;
            }
        };

        model.addListener(listener);
        model.setGameMode(GameMode.TIME_TRIAL);

        try {
            Method method = GameModel.class.getDeclaredMethod("notifyDependencies");
            method.setAccessible(true);
            method.invoke(model);

            assert (mode == GameMode.TIME_TRIAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
