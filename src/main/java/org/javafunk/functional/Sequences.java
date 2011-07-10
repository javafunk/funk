package org.javafunk.functional;

import org.javafunk.datastructures.IntegerRange;

public class Sequences {
    private Sequences() {}

    private enum Direction {
        ASCENDING(+1), DESCENDING(-1);

        private int multiplier;

        Direction(int multiplier) {
            this.multiplier = multiplier;
        }

        int getMultiplier() {
            return multiplier;
        }
    }

    public static Direction increasing() {
        return Direction.ASCENDING;
    }

    public static Direction decreasing() {
        return Direction.DESCENDING;
    }

    public static Iterable<Integer> integers(Direction direction) {
        return new IntegerRange(0, null, direction.getMultiplier());
    }

    public static Iterable<Integer> integersFrom(int startPoint, Direction direction) {
        return new IntegerRange(startPoint, null, direction.getMultiplier());
    }
}
