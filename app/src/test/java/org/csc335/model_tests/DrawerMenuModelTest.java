package org.csc335.model_tests;

import org.csc335.interfaces.DrawerOptionListener;
import org.csc335.javafx_entity.GameMode;
import org.csc335.models.DrawerMenuModel;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleBooleanProperty;
import java.lang.reflect.Field;

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

    @Test 
    public void testHiddenAndVisible() {
        DrawerMenuModel drawer = new DrawerMenuModel();

        drawer.setHidden();
        try {
            Field field = DrawerMenuModel.class.getDeclaredField("isHidden");
            field.setAccessible(true);
            assert (((SimpleBooleanProperty)(field.get(drawer))).getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

        drawer.setVisible();
        try {
            Field field = DrawerMenuModel.class.getDeclaredField("isHidden");
            field.setAccessible(true);
            assert (!((SimpleBooleanProperty)(field.get(drawer))).getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private GameMode mode = null;
    private boolean becameHiddenFlag = false;
    @Test
    public void testListening() {
        DrawerMenuModel drawer = new DrawerMenuModel();

        drawer.addListener(new DrawerOptionListener() {
            public void selectOption(GameMode selected) {
                mode = selected;
            }

            public void becameHidden() {
                becameHiddenFlag = true;
            }
        });

        assert (!becameHiddenFlag);
        drawer.setHidden();
        assert (becameHiddenFlag);

        for (GameMode g : GameMode.values()) {
            drawer.setSelected(g);
            assert (mode == g);
        }
    }
}