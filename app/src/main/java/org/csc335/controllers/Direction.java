package org.csc335.controllers;

public enum Direction {
    // values
    RIGHT, LEFT, UP, DOWN;

    // converting string to enum
    public static Direction fromVal(String key) {
        switch(key) {
            case "W":
            case "Up":
                return UP;
            case "S":
            case "Down":
                return DOWN;
            case "A":
            case "Left":
                return LEFT;
            case "D":
            case "Right":
                return RIGHT;
            default:
                return null;
        }
    }
}