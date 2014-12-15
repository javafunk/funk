package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;

public class Floats {
    public static Mapper<Double, Float> fromDoubleToFloat() {
        return new Mapper<Double, Float>() {
            @Override public Float map(Double input) {
                return input.floatValue();
            }
        };
    }
}
