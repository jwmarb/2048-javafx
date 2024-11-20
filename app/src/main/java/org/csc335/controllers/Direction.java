package org.csc335.controllers;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
    
    // values
    RIGHT, LEFT, UP, DOWN;

    // converting string to enum
    public static Direction fromVal(String direction) {
        switch(direction) {
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