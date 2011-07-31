/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.IntegerRange;

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
