package org.csc335.entity;

import java.util.ArrayList;

public class Leaderboard {
    
    // instance variables
    private ArrayList<Player> players;

    public Leaderboard() {
        players = new ArrayList<>();
    }

    public ArrayList<Player> load() {
        ArrayList<Player> top5 = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            top5.set(i, players.get(i));
        }

        return top5;
    }

    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
        selectionSort();
    }

    private void selectionSort() {
        for (int i = players.size()-1; i >= 0; i--) {
            int maxIndex = i;
            for (int j = i-1; j >= 0; j--) {
                Player a = players.get(maxIndex);
                Player b = players.get(j);

                if (b.getScore() > a.getScore()) {
                    maxIndex = j;
                }
            }

            swap(players, i, maxIndex);
        }
    }

    private void swap(ArrayList<Player> list, int index1, int index2) {
        Player temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }
}
