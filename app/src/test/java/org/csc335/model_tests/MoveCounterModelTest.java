package org.csc335.model_tests;

import org.csc335.interfaces.MoveCounterListener;
import org.csc335.models.MoveCounterModel;
import org.junit.jupiter.api.Test;

public class MoveCounterModelTest {

    @Test
    public void testMovesMade() {
        MoveCounterModel counter = new MoveCounterModel();

        for (int moves = 0; moves <= MoveCounterModel.TOTAL_MOVES; moves++) {
            counter.setMovesMade(moves);
            assert ((MoveCounterModel.TOTAL_MOVES - moves) == counter.getRemainingMoves());
        }
    }

    @Test
    public void testReset() {
        MoveCounterModel counter = new MoveCounterModel();

        for (int moves = 0; moves <= MoveCounterModel.TOTAL_MOVES; moves++) {
            counter.setMovesMade(moves);
            assert ((MoveCounterModel.TOTAL_MOVES - moves) == counter.getRemainingMoves());

            counter.reset();
            assert(MoveCounterModel.TOTAL_MOVES == counter.getRemainingMoves());
        }
    }

    @Test
    public void testNoMovesLeft() {
        MoveCounterModel counter = new MoveCounterModel();

        for (int moves = 0; moves < MoveCounterModel.TOTAL_MOVES; moves++) {
            counter.setMovesMade(moves);
            assert(!counter.hasNoMoreMoves());
        }

        counter.setMovesMade(MoveCounterModel.TOTAL_MOVES);
        assert(counter.hasNoMoreMoves());
    }

    private int reportedValue = 0;
    @Test
    public void testListening() {
        MoveCounterModel counter = new MoveCounterModel();
       
        MoveCounterListener listener = new MoveCounterListener() {
            @Override
            public void userMoved(int movesLeft) {
              reportedValue = counter.getRemainingMoves();
            }
        };

        counter.addListener(listener);

        for (int moves = 0; moves < MoveCounterModel.TOTAL_MOVES; moves++) {
            counter.setMovesMade(moves);
            assert(reportedValue == (MoveCounterModel.TOTAL_MOVES - moves));
        }
    }
}