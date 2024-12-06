package org.csc335.entity_tests;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.csc335.entity.TileValue;
import org.junit.Test;

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
        
    }

}
