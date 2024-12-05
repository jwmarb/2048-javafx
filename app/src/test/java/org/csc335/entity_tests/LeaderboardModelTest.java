package org.csc335.entity_tests;

import org.csc335.entity.LeaderboardModel;
import org.junit.Test;

public class LeaderboardModelTest {
    LeaderboardModel model = new LeaderboardModel();
    

    @Test
    public void writeNewPlayerScoreTest (){
        model.writeNewPlayerScore(5);
        System.out.println(model.toString());
        model.writeNewPlayerScore(6);
        System.out.println(model.toString());
        // for trevor
    }
}
