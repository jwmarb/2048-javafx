package org.csc335.entity;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.BufferedWriter;

public class LeaderboardModel {
    
    // instance variables
    private ArrayList<Player> players;

    private String top5;

    public LeaderboardModel() {
        players = new ArrayList<>();
    }

    public static void main(String[] args) {
        Player a = new Player("adam");
        Player b = new Player("joseph");
        Player c = new Player("josh");
        Player d = new Player("trevor");
        Player e = new Player("nate");
        Player f = new Player("cameron");

        LeaderboardModel lb = new LeaderboardModel();

        b.setScore(10);
        c.setScore(20);
        d.setScore(5);
        e.setScore(5);
        // f.setScore(0);


        lb.addPlayer(a);
        lb.addPlayer(b);
        lb.addPlayer(c);
        lb.addPlayer(d);
        lb.addPlayer(e);
        lb.addPlayer(f);
        System.out.println(lb.players);
        System.out.println("The top five scores:");
        System.out.println(lb.load());

        LeaderboardModel lb1 = new LeaderboardModel();
        // lb1.addPlayers("C:\\Users\\shehe\\CSC-335\\2048-javafx\\app\\src\\main\\java\\org\\csc335\\leaderboard.txt");
        lb1.addPlayers("app/src/main/java/org/csc335/leaderboard.txt");
        System.out.println(lb1.players);
    }

    public String load() {
        // ArrayList<Player> top5 = new ArrayList<>();
        this.top5 = "";
        int count = 5;
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (count > 0) {
                this.top5 += String.format("%d. %s scored %d points.\n", i+1, player.getName(), player.getScore());
                count--;
            }
        }

        return top5;
    }

    // helper method
    public void addPlayer(Player newPlayer) {
        if (players.size() == 0) {
            players.add(newPlayer);
        } else {
            for (int i = 0; i < this.players.size(); i++) {
                // System.out.println(players.get(i));
                if (players.get(i).getScore() < newPlayer.getScore()) {
                    players.add(i, newPlayer);
                    break;
                }
                if (i == this.players.size()-1) {
                    players.add(newPlayer);
                    break;
                }
            }
        }
    }

    // adds players from leaderboard file
    public void addPlayers(String fileName) {
        File file = new File(fileName);
        Scanner fileReader = null;

        try {
            fileReader = new Scanner(file);

            while (fileReader.hasNext()) {
                String[] playerInfo = fileReader.nextLine().split(":");
                Player player = new Player(playerInfo[0], Integer.parseInt(playerInfo[1]));
                addPlayer(player);
            }

            fileReader.close();

        } catch(Exception e) {
            System.out.println("File does not exist");
            System.out.println(file.getAbsolutePath());
        }
    }

    //write in file
    public void writeNewPlayerScore(String fileName, Player newPlayer) {
        try {
            
            // get the saved data
            addPlayers(fileName);
            System.out.println(players);
            addPlayer(newPlayer);
            System.out.println(players);

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName));

            for (Player player : players) {
                fileWriter.write(String.format("%s:%d\n", player.getName(), player.getScore()));   
            }

            fileWriter.close();
        } catch(Exception e) {
            System.out.println("File does not exit.");
        }
    }
}
