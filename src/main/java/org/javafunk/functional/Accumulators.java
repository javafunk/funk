package org.javafunk.functional;

import org.javafunk.functional.functors.Reducer;

public class Accumulators {
    private Accumulators() {}

    public static Reducer<Integer, Integer> integerAdditionAccumulator() {
        return new Reducer<Integer, Integer>() {
            public Integer accumulate(Integer accumulator, Integer element) {
                return accumulator + element;
            }
        };
    }

    public static Reducer<Long, Long> longAdditionAccumulator() {
        return new Reducer<Long, Long>() {
            public Long accumulate(Long accumulator, Long element) {
                return accumulator + element;
            }
        };
    }

    public static Reducer<Double, Double> doubleAdditionAccumulator() {
        return new Reducer<Double, Double>() {
            public Double accumulate(Double accumulator, Double element) {
                return accumulator + element;
            }
        };
    }

    public static Reducer<Float, Float> floatAdditionAccumulator() {
        return new Reducer<Float, Float>() {
            public Float accumulate(Float accumulator, Float element) {
                return accumulator + element;
            }
        };
    }

    public static Reducer<Integer, Integer> integerMultiplicationAccumulator() {
        return new Reducer<Integer, Integer>() {
            public Integer accumulate(Integer accumulator, Integer element) {
                return accumulator * element;
            }
        };
    }

    public static Reducer<Long, Long> longMultiplicationAccumulator() {
        return new Reducer<Long, Long>() {
            public Long accumulate(Long accumulator, Long element) {
                return accumulator * element;
            }
        };
    }

    public static Reducer<Double, Double> doubleMultiplicationAccumulator() {
        return new Reducer<Double, Double>() {
            public Double accumulate(Double accumulator, Double element) {
                return accumulator * element;
            }
        };
    }

    public static Reducer<Float, Float> floatMultiplicationAccumulator() {
        return new Reducer<Float, Float>() {
            public Float accumulate(Float accumulator, Float element) {
                return accumulator * element;
            }
        };
    }
}
