package org.csc335.model_tests;

import org.csc335.entity.TileValue;
import org.csc335.models.TileModel;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TileModelTest {

    @Test
    public void testSetAndGetNonOptional() {
        TileModel tile = new TileModel();

        assert (tile.getNumericValue() == 0);

        for (TileValue t : TileValue.values()) {
            tile.setValue(t);
            assert (tile.getNumericValue() == t.value());
        }
    }

    @Test
    public void testSetAndGetWithOptional() {
        TileModel tile = new TileModel();

        assert (tile.getValue().isEmpty());

        for (TileValue t : TileValue.values()) {
            tile.setValue(Optional.of(t));
            assert (tile.getValue().equals(Optional.of(t)));
        }
    }

    @Test
    public void testNextValue() {
        TileModel tile = new TileModel();

        assert (tile.getNextValue().isEmpty());

        TileValue[] values = TileValue.values();

        for (int i = 0; i < values.length - 1; i++) {
            tile.setValue(values[i]);
            assert (Optional.of(values[i + 1]).equals(tile.getNextValue()));
        }
    }

    @Test
    public void testBlank() {
        TileModel tile = new TileModel();

        assert (tile.isBlank());

        for (TileValue t : TileValue.values()) {
            tile.setValue(Optional.of(t));
            assert (!tile.isBlank());
            tile.makeBlank();
            assert (tile.isBlank());
        }
    }

    @Test
    public void testRandom() {
        TileModel tile = new TileModel();
        int t4num = 0;
        int t2num = 0;

        for (int i = 0; i < Integer.MAX_VALUE / 4; i++) {
            tile.random();
            if (tile.getNumericValue() == 2) {
                t2num++;
            } else {
                t4num++;
            }
        }

        // inspect output, should be somewhat closish to 75% 2's, 25% 4's
        System.out.println("Number of 2 tiles randomly generated: " + t2num);
        System.out.println("Number of 4 tiles randomly generated: " + t4num);
    }

    @Test
    public void testEquality() {
        for (TileValue t : TileValue.values()) {
            TileModel t1 = new TileModel();
            TileModel t2 = new TileModel();
            t1.setValue(t);
            t2.setValue(t);
            assert (t1.equals(t2));
        }

        TileModel tile1 = new TileModel();
        assert (!tile1.equals(null));
        assert (!tile1.equals(new String("")));

        TileModel tile2 = new TileModel();

        assert (tile1.equals(tile2));
        assert (tile2.equals(tile1));

        tile1.setValue(TileValue.T2);
        assert (!tile1.equals(tile2));
        assert (!tile2.equals(tile1));

    }
}
