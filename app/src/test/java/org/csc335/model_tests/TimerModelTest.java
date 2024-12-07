package org.csc335.model_tests;

import org.csc335.entity.GameMode;
import org.csc335.interfaces.DrawerOptionListener;
import org.csc335.interfaces.TimerListener;
import org.csc335.models.DrawerMenuModel;
import org.csc335.models.TimerModel;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleBooleanProperty;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;

public class TimerModelTest {
    @Test
    public void testDecrementSecondAndGetTimeLeft() {
        TimerModel model = new TimerModel();

        assert (model.getTimeLeft().toMinutes() == 15);

        for (int i = 0; i < 60; ++i) {
            try {
                Method method = TimerModel.class.getDeclaredMethod("decrementSecond");
                method.setAccessible(true);
                method.invoke(model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        assert (model.getTimeLeft().toMinutes() == 14);

    }

    @Test
    public void testIsDone() {
        TimerModel model = new TimerModel();

        assert (model.isDone());

        try {
            Field field = TimerModel.class.getDeclaredField("isDone");
            field.setAccessible(true);
            field.set(model, false);
            assert (!model.isDone());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testReset() {
        TimerModel model = new TimerModel();

        try {
            Field field = TimerModel.class.getDeclaredField("duration");
            field.setAccessible(true);
            field.set(model, Duration.ofMinutes(10));
            assert (model.getTimeLeft().toMinutes() == 10);

            model.reset();

            assert (model.getTimeLeft().toMinutes() == 15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Duration time = Duration.ofMinutes(15);

    @Test
    public void testHandleDurationChangeAndAddListener() {
        TimerModel model = new TimerModel() {
            @Override
            public void stop() {

            }
        };

        TimerListener listener = new TimerListener() {
            public void timerChanged(Duration timeLeft) {
                time = timeLeft;
            }
        };
        model.addListener(listener);

        try {
            Field field = TimerModel.class.getDeclaredField("duration");
            field.setAccessible(true);
            field.set(model, Duration.ofMinutes(10));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Method method = TimerModel.class.getDeclaredMethod("handleDurationChange");
            method.setAccessible(true);
            method.invoke(model);
            assert (time.toMinutes() == 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Field field = TimerModel.class.getDeclaredField("duration");
            field.setAccessible(true);
            field.set(model, Duration.ofMinutes(0));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Method method = TimerModel.class.getDeclaredMethod("handleDurationChange");
            method.setAccessible(true);
            method.invoke(model);
            assert (time.toMinutes() == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
