package org.csc335.entity_tests;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.csc335.controllers.DrawerOption;
import org.csc335.entity.GameMode;
import org.junit.jupiter.api.Test;

public class GameModeTest {
    
    GameMode gMode1 = GameMode.MOVE_LIMIT;
    GameMode gMode2 = GameMode.TIME_TRIAL;
    GameMode gMode3 = GameMode.TRADITIONAL;


    @Test
    public void toCSSSuffixTest() {
        assertEquals("move-limit", gMode1.toCSSSuffix());
        assertEquals("time-trial", gMode2.toCSSSuffix());
        assertEquals("traditional", gMode3.toCSSSuffix());
    }

    /* 
    @Test
    public void testCreateDrawerOption() {
        DrawerOption option = gMode1.createDrawerOption();
        assertEquals(GameMode.MOVE_LIMIT, option.getMode());

        option = gMode2.createDrawerOption();
        assertEquals(GameMode.TIME_TRIAL, option.getMode());

        option = gMode3.createDrawerOption();
        assertEquals(GameMode.TRADITIONAL, option.getMode());
    }
        */

}
