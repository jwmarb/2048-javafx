package org.csc335.model_tests;

import org.csc335.entity.PressableVariant;
import org.csc335.models.PressableModel;
import org.junit.Test;

public class PressableModelTest {

    @Test
    public void testVariant() {
        PressableModel pressable = new PressableModel();

        for (PressableVariant p : PressableVariant.values()) {
            pressable.setVariant(p);

            assert(pressable.getVariant() == p);
            assert(pressable.variantProperty().get() == p);
        }
    }

    @Test
    public void testGrow() {
        PressableModel pressable = new PressableModel();

        for (int i = 0; i < 5; i++) {
            boolean grow = i % 2 == 0;
            pressable.setGrow(grow);

            assert (grow == pressable.getGrow());
            assert (grow == pressable.growProperty().get());
        }
    }
}