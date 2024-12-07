package org.csc335.entity_tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.csc335.entity.TileValue;
import org.junit.jupiter.api.Test;

public class TileValueTest {
    
    @Test
    public void nextAndValueTest() {
        for (int i = 2; i < 2048; i*=2) {
            Optional<TileValue> tV1 = TileValue.fromString( i + "");
            Optional<TileValue> tV2 = TileValue.fromString(i * 2  + "");
            System.out.println(String.format("%d --> %d", tV1.get().value(), tV2.get().value()));
            assertEquals(tV2.get(), tV1.get().next());
        }
    }

    @Test
    public void fromStringTest() {
        // regular case
        assertEquals(128, TileValue.fromString("128").get().value());
        // when argument passed in is null
        assertEquals(Optional.empty(), TileValue.fromString(null));
        
        // when argument passed in is invalid
        for (int i = -1; i < 4100; i++) {
            if (i > 1 && i < 4096) {
                assertFalse(TileValue.fromString(i + "").isEmpty());
            } else {
                System.out.println(TileValue.fromString(i + ""));
                assertTrue(TileValue.fromString(i + "").isEmpty());
            }
        }

        assertEquals(Optional.empty(), TileValue.fromString("1"));
        assertEquals(Optional.empty(), TileValue.fromString("a"));
    }

}
