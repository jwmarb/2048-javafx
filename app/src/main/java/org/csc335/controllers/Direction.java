package org.csc335.controllers;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
    
    // values
    RIGHT, LEFT, UP, DOWN;

    // look up
    private static final Map<String, Direction> lookup = new HashMap<>();
    static {
        lookup.put("Right", RIGHT);
        lookup.put("Left", LEFT);
        lookup.put("Up", UP);
        lookup.put("Down", DOWN);
    }

    // converting string to enum
    public static Direction fromVal(String direction) {
        return lookup.getOrDefault(direction, null);
    }

}
