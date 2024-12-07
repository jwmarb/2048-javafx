package org.csc335.model_tests;

import org.csc335.interfaces.ScoreboardListener;
import org.csc335.models.ScoreboardModel;
import org.junit.jupiter.api.Test;

public class ScoreboardModelTest {

    @Test
    public void testScore() {
        ScoreboardModel model = new ScoreboardModel();

        assert (model.getScore() == 0);

        model.addScore(100);

        assert (model.getScore() == 100);

        model.reset();

        assert (model.getScore() == 0);

    }

    private int bestScore = 0;
    private int currnetScore = 0;

    @Test
    public void testAddListener() {
        ScoreboardModel model = new ScoreboardModel();

        ScoreboardListener listener = new ScoreboardListener() {
            public void scoreChanged(int current, int best) {
                bestScore = best;
                currnetScore = current;
            }

        };

        model.addListener(listener);

        model.addScore(40);

        assert (bestScore == 40);
        assert (currnetScore == 40);

    }
}
