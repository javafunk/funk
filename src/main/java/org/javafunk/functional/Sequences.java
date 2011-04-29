package org.javafunk.functional;

import org.javafunk.datastructures.IntegerRange;

public class Sequences {
    public static Iterable<Integer> increasingIntegers() {
        return new IntegerRange(0, null, 1);
    }

    public static Iterable<Integer> increasingIntegersFrom(int startPoint) {
        return new IntegerRange(startPoint, null, 1);
    }

    public static Iterable<Integer> decreasingIntegers() {
        return new IntegerRange(0, null, -1);
    }

    public static Iterable<Integer> decreasingIntegersFrom(int startPoint) {
        return new IntegerRange(startPoint, null, -1);
    }
}
