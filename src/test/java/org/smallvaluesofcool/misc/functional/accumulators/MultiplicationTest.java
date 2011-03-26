package org.smallvaluesofcool.misc.functional.accumulators;

import org.junit.Test;
import org.smallvaluesofcool.misc.functional.functors.ReduceFunction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.smallvaluesofcool.misc.functional.accumulators.Addition.integerAdditionAccumulator;
import static org.smallvaluesofcool.misc.functional.accumulators.Multiplication.*;

public class MultiplicationTest {
    @Test
    public void shouldReturnAnAccumulatorThatMultipliesTheSuppliedAccumulatorValueByTheSuppliedIntegerInput() throws Exception {
        // Given
        ReduceFunction<Integer, Integer> integerMultiplicationAccumulator = integerMultiplicationAccumulator();

        // When
        Integer result = integerMultiplicationAccumulator.accumulate(10, 5);

        // Then
        assertThat(result, is(50));
    }

    @Test
    public void shouldReturnAnAccumulatorThatMultipliesTheSuppliedAccumulatorValueByTheSuppliedLongInput() throws Exception {
        // Given
        ReduceFunction<Long, Long> longMultiplicationAccumulator = longMultiplicationAccumulator();

        // When
        Long result = longMultiplicationAccumulator.accumulate(10L, 5L);

        // Then
        assertThat(result, is(50L));
    }

    @Test
    public void shouldReturnAnAccumulatorThatMultipliesTheSuppliedAccumulatorValueByTheSuppliedFloatInput() throws Exception {
        // Given
        ReduceFunction<Float, Float> floatMultiplicationAccumulator = floatMultiplicationAccumulator();

        // When
        Float result = floatMultiplicationAccumulator.accumulate(2.5F, 3.2F);

        // Then
        assertThat(result.doubleValue(), is(closeTo(8.0F, 0.01)));
    }

    @Test
    public void shouldReturnAnAccumulatorThatMultipliesTheSuppliedAccumulatorValueByTheSuppliedDoubleInput() throws Exception {
        // Given
        ReduceFunction<Double, Double> doubleMultiplicationAccumulator = doubleMultiplicationAccumulator();

        // When
        Double result = doubleMultiplicationAccumulator.accumulate(2.5D, 3.7D);

        // Then
        assertThat(result.doubleValue(), is(closeTo(9.25D, 0.01)));
    }
}
