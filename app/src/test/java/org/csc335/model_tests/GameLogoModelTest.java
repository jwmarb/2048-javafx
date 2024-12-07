package org.csc335.model_tests;

import org.csc335.interfaces.DrawerMenuActionListener;
import org.csc335.models.GameLogoModel;
import org.junit.jupiter.api.Test;

public class GameLogoModelTest {

    private static int numClicks = -1;
    @Test
    public void testListening() {
        GameLogoModel gameLogo = new GameLogoModel();

        gameLogo.addMenuActionListener(new DrawerMenuActionListener() {
            @Override
            public void menuClick() {
                numClicks++;
            }
        });

        assert(numClicks == -1);

        for (int i = 0; i < Integer.MAX_VALUE/2; i++) {
            gameLogo.invokeOpenMenu();
            assert (numClicks == i);
        }
    }
}