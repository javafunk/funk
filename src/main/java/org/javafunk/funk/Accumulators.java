/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Reducer;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Accumulators {
    private Accumulators() {}

    public static Reducer<Integer, Integer> integerAdditionAccumulator() {
        return new Reducer<Integer, Integer>() {
            @Override public Integer accumulate(Integer accumulator, Integer element) {
                return accumulator + element;
            }
        };
    }

    public static Reducer<Long, Long> longAdditionAccumulator() {
        return new Reducer<Long, Long>() {
            @Override public Long accumulate(Long accumulator, Long element) {
                return accumulator + element;
            }
        };
    }

    public static Reducer<Double, Double> doubleAdditionAccumulator() {
        return new Reducer<Double, Double>() {
            @Override public Double accumulate(Double accumulator, Double element) {
                return accumulator + element;
            }
        };
    }

    public static Reducer<Float, Float> floatAdditionAccumulator() {
        return new Reducer<Float, Float>() {
            @Override public Float accumulate(Float accumulator, Float element) {
                return accumulator + element;
            }
        };
    }

    public static Reducer<BigDecimal, BigDecimal> bigDecimalAdditionAccumulator() {
        return new Reducer<BigDecimal, BigDecimal>() {
            @Override public BigDecimal accumulate(BigDecimal accumulator, BigDecimal decimal) {
                return accumulator.add(decimal);
            }
        };
    }

    public static Reducer<BigInteger, BigInteger> bigIntegerAdditionAccumulator() {
        return new Reducer<BigInteger, BigInteger>() {
            @Override public BigInteger accumulate(BigInteger accumulator, BigInteger integer) {
                return accumulator.add(integer);
            }
        };
    }

    public static Reducer<Integer, Integer> integerMultiplicationAccumulator() {
        return new Reducer<Integer, Integer>() {
            @Override public Integer accumulate(Integer accumulator, Integer element) {
                return accumulator * element;
            }
        };
    }

    public static Reducer<Long, Long> longMultiplicationAccumulator() {
        return new Reducer<Long, Long>() {
            @Override public Long accumulate(Long accumulator, Long element) {
                return accumulator * element;
            }
        };
    }

    public static Reducer<BigInteger, BigInteger> bigIntegerMultiplicationAccumulator() {
        return new Reducer<BigInteger, BigInteger>() {
            @Override public BigInteger accumulate(BigInteger accumulator, BigInteger integer) {
                return accumulator.multiply(integer);
            }
        };
    }

    public static Reducer<Double, Double> doubleMultiplicationAccumulator() {
        return new Reducer<Double, Double>() {
            @Override public Double accumulate(Double accumulator, Double element) {
                return accumulator * element;
            }
        };
    }

    public static Reducer<Float, Float> floatMultiplicationAccumulator() {
        return new Reducer<Float, Float>() {
            @Override public Float accumulate(Float accumulator, Float element) {
                return accumulator * element;
            }
        };
    }

    public static Reducer<BigDecimal, BigDecimal> bigDecimalMultiplicationAccumulator() {
        return new Reducer<BigDecimal, BigDecimal>() {
            @Override public BigDecimal accumulate(BigDecimal accumulator, BigDecimal decimal) {
                return accumulator.multiply(decimal);
            }
        };
    }
}
