package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;

public class Doubles {
    public static Mapper<Integer, Double> fromIntegerToDouble() {
        return new Mapper<Integer, Double>() {
            @Override public Double map(Integer input) {
                return input.doubleValue();
            }
        };
    }
}
