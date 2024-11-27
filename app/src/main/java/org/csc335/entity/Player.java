package org.csc335.entity;

public class Player {
    
    // instance variables
    private String name;
    private int score;

    // constrcutor(s)
    public Player(String name) {
        this.name = name;
    }

    // accessors and mutators
    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public String getName() {
        return this.name;
    }
}
