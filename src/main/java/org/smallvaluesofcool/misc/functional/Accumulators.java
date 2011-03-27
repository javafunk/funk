package org.smallvaluesofcool.misc.functional;

import org.smallvaluesofcool.misc.functional.functors.ReduceFunction;

public class Accumulators {
    public static ReduceFunction<Integer, Integer> integerAdditionAccumulator() {
        return new ReduceFunction<Integer, Integer>() {
            public Integer accumulate(Integer accumulator, Integer element) {
                return accumulator + element;
            }
        };
    }

    public static ReduceFunction<Long, Long> longAdditionAccumulator() {
        return new ReduceFunction<Long, Long>() {
            public Long accumulate(Long accumulator, Long element) {
                return accumulator + element;
            }
        };
    }

    public static ReduceFunction<Double, Double> doubleAdditionAccumulator() {
        return new ReduceFunction<Double, Double>() {
            public Double accumulate(Double accumulator, Double element) {
                return accumulator + element;
            }
        };
    }

    public static ReduceFunction<Float, Float> floatAdditionAccumulator() {
        return new ReduceFunction<Float, Float>() {
            public Float accumulate(Float accumulator, Float element) {
                return accumulator + element;
            }
        };
    }

    public static ReduceFunction<Integer, Integer> integerMultiplicationAccumulator() {
        return new ReduceFunction<Integer, Integer>() {
            public Integer accumulate(Integer accumulator, Integer element) {
                return accumulator * element;
            }
        };
    }

    public static ReduceFunction<Long, Long> longMultiplicationAccumulator() {
        return new ReduceFunction<Long, Long>() {
            public Long accumulate(Long accumulator, Long element) {
                return accumulator * element;
            }
        };
    }

    public static ReduceFunction<Double, Double> doubleMultiplicationAccumulator() {
        return new ReduceFunction<Double, Double>() {
            public Double accumulate(Double accumulator, Double element) {
                return accumulator * element;
            }
        };
    }

    public static ReduceFunction<Float, Float> floatMultiplicationAccumulator() {
        return new ReduceFunction<Float, Float>() {
            public Float accumulate(Float accumulator, Float element) {
                return accumulator * element;
            }
        };
    }
}
