// my delete later


package org.csc335.controller_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.csc335.controllers.DrawerOption;
import org.csc335.entity.GameMode;
import org.junit.Test;

public class DrawerOptionTest {
    DrawerOption option = new DrawerOption("abc", "123", "do re mi", GameMode.TRADITIONAL);

    @Test
    public void isSelectedTest() {
        assertFalse(option.isSelected());
        option.select();
        assertTrue(option.isSelected());
        option.deselect();
        assertFalse(option.isSelected());
    }
}
