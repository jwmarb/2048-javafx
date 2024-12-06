package org.csc335.model_tests;

import org.csc335.entity.GameMode;
import org.csc335.models.DrawerMenuModel;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.Assert.*;

public class DrawerMenuModelTest {

    @Test
    public void testSelection() {
        DrawerMenuModel drawer = new DrawerMenuModel();

        for (GameMode g : GameMode.values()) {
            drawer.setSelected(g);
            assert (drawer.getSelected().get() == g);
        }
    }

    @Test
    public void testSelectionOrdinal() {
        DrawerMenuModel drawer = new DrawerMenuModel();

        assert(drawer.getSelectedOrdinal().isEmpty());

        for (GameMode g : GameMode.values()) {
            drawer.setSelected(g);
            assert (drawer.getSelectedOrdinal().get() == g.ordinal());
        }
    }

    
}