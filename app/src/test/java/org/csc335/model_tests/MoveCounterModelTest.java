package org.csc335.model_tests;

import org.csc335.entity.LeaderboardModel;
import org.csc335.models.MoveCounterModel;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MoveCounterModelTest {

    

    @Test
    public void testMovesMade() {
        MoveCounterModel counter = new MoveCounterModel();

        for (int moves = 0; moves < 1001; moves++) {
            counter.setMovesMade(moves);

            assertEquals((MoveCounterModel.TOTAL_MOVES - moves) + "", counter.getRemainingMoves());
        }
    }
    
}
