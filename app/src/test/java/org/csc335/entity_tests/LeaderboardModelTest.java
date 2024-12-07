package org.csc335.entity_tests;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.csc335.entity.LeaderboardModel;
import org.junit.jupiter.api.Test;

public class LeaderboardModelTest {
    private Path leaderboardPath;
    
    @Test
    public void fileCreateTest() {
        // delete any leaderboard.txt file in or under current working directory
        deleteLeaderboard();
        assert (findLeaderboard() == null);

        // creating a leaderboard should generate new file now that none exist
        new LeaderboardModel();
        assert (findLeaderboard() != null);
    }

    @Test
    public void writeNewPlayerScoreTest (){
        deleteLeaderboard();
        LeaderboardModel model = new LeaderboardModel();

        model.writeNewPlayerScore(5);
        assert (
            model.toString().equals(
                """
                1.    5 points
                2.    0 points
                3.    0 points
                4.    0 points
                5.    0 points
                6.    0 points
                7.    0 points
                8.    0 points
                9.    0 points
                10.    0 points
                """
            )
        );

        model.writeNewPlayerScore(6);
        assert (
            model.toString().equals(
                """
                1.    6 points
                2.    5 points
                3.    0 points
                4.    0 points
                5.    0 points
                6.    0 points
                7.    0 points
                8.    0 points
                9.    0 points
                10.    0 points
                """
            )
        );

        model.writeNewPlayerScore(150);
        model.writeNewPlayerScore(150);
        model.writeNewPlayerScore(1500);
        model.writeNewPlayerScore(15000);
        model.writeNewPlayerScore(3);
        model.writeNewPlayerScore(25);
        model.writeNewPlayerScore(8);
        model.writeNewPlayerScore(7);
        assert (
            model.toString().equals(
                """
                1.    15000 points
                2.    1500 points
                3.    150 points
                4.    150 points
                5.    25 points
                6.    8 points
                7.    7 points
                8.    6 points
                9.    5 points
                10.    3 points
                """
            )
        );
    }

    @Test
    public void writeNewPlayerScoreTestWhenFileFull (){
        deleteLeaderboard();
        LeaderboardModel model = new LeaderboardModel();

        for (int i = 1; i < 11; i++) {
            model.writeNewPlayerScore(i);
        }
        assert (
            model.toString().equals(
                """
                1.    10 points
                2.    9 points
                3.    8 points
                4.    7 points
                5.    6 points
                6.    5 points
                7.    4 points
                8.    3 points
                9.    2 points
                10.    1 points
                """
            )
        );

        model.writeNewPlayerScore(3);
        assert (
            model.toString().equals(
                """
                1.    10 points
                2.    9 points
                3.    8 points
                4.    7 points
                5.    6 points
                6.    5 points
                7.    4 points
                8.    3 points
                9.    3 points
                10.    2 points
                """
            )
        );

        model.writeNewPlayerScore(0);
        assert (
            model.toString().equals(
                """
                1.    10 points
                2.    9 points
                3.    8 points
                4.    7 points
                5.    6 points
                6.    5 points
                7.    4 points
                8.    3 points
                9.    3 points
                10.    2 points
                """
            )
        );
    }

    @Test
    public void testWithExistingFile() {
        deleteLeaderboard();
        File leaderboardFile = findLeaderboard();
        if (leaderboardFile == null) {
            leaderboardFile = Paths.get("leaderboard.txt").toFile();
        }

        LeaderboardModel model = new LeaderboardModel();

        try {
            Field field = LeaderboardModel.class.getDeclaredField("leaderboardPath");
            field.setAccessible(true);

            assert (field.get(model) != null);

            field.set(model, null);

            assert (field.get(model) == null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            Method method = LeaderboardModel.class.getDeclaredMethod("getLeaderboard");
            method.setAccessible(true);
            method.invoke(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Field field = LeaderboardModel.class.getDeclaredField("leaderboardPath");
            field.setAccessible(true);
            assert (field.get(model) != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddScores() {
        deleteLeaderboard();
        LeaderboardModel model = new LeaderboardModel();

        model.writeNewPlayerScore(3);
        assert (
            model.toString().equals(
                """
                1.    3 points
                2.    0 points
                3.    0 points
                4.    0 points
                5.    0 points
                6.    0 points
                7.    0 points
                8.    0 points
                9.    0 points
                10.    0 points
                """
            )
        );
        
        try {
            Method method = LeaderboardModel.class.getDeclaredMethod("addScores");
            method.setAccessible(true);
            method.invoke(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert (
            model.toString().equals(
                """
                1.    3 points
                2.    3 points
                3.    0 points
                4.    0 points
                5.    0 points
                6.    0 points
                7.    0 points
                8.    0 points
                9.    0 points
                10.    0 points
                """
            )
        );
    }

    private File findLeaderboard() {
        leaderboardPath = null; // reset leaderboardPath
        try (Stream<Path> walkStream = Files.walk(Paths.get("."))) {
            walkStream.filter(p -> p.toFile().isFile()).forEach(f -> {
                if (f.toString().endsWith("leaderboard.txt")) {
                    leaderboardPath = f;
                }
            });
        } catch (Exception e) {
            System.out.println("Error reading files...");
        }

        if (leaderboardPath == null) {
            return null;
        }
        return leaderboardPath.toFile();
    }

    private void deleteLeaderboard() {
        File leaderboard = findLeaderboard();
        while (leaderboard != null) {
            if (leaderboard.exists()) {
                leaderboard.delete();
            }
            leaderboard = findLeaderboard();
        }
    }
}