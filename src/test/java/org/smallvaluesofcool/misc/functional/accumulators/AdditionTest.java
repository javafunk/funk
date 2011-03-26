package org.smallvaluesofcool.misc.functional.accumulators;

import org.junit.Test;
import org.smallvaluesofcool.misc.functional.functors.ReduceFunction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.smallvaluesofcool.misc.functional.accumulators.Addition.*;

public class AdditionTest {
    @Test
    public void shouldReturnAnAccumulatorThatAddsTheSuppliedIntegerInputToTheSuppliedAccumulatorValue() throws Exception {
        // Given
        ReduceFunction<Integer, Integer> integerAdditionAccumulator = integerAdditionAccumulator();

        // When
        Integer result = integerAdditionAccumulator.accumulate(10, 5);

        // Then
        assertThat(result, is(15));
    }

    @Test
    public void shouldReturnAnAccumulatorThatAddsTheSuppliedLongInputToTheSuppliedAccumulatorValue() throws Exception {
        // Given
        ReduceFunction<Long, Long> longAdditionAccumulator = longAdditionAccumulator();

        // When
        Long result = longAdditionAccumulator.accumulate(10L, 5L);

        // Then
        assertThat(result, is(15L));
    }

    @Test
    public void shouldReturnAnAccumulatorThatAddsTheSuppliedFloatInputToTheSuppliedAccumulatorValue() throws Exception {
        // Given
        ReduceFunction<Float, Float> floatAdditionAccumulator = floatAdditionAccumulator();

        // When
        Float result = floatAdditionAccumulator.accumulate(10.7F, 4.6F);

        // Then
        assertThat(result.doubleValue(), is(closeTo(15.3, 0.01)));
    }

    @Test
    public void shouldReturnAnAccumulatorThatAddsTheSuppliedDoubleInputToTheSuppliedAccumulatorValue() throws Exception {
        // Given
        ReduceFunction<Double, Double> doubleAdditionAccumulator = doubleAdditionAccumulator();

        // When
        Double result = doubleAdditionAccumulator.accumulate(21.4D, 6.7D);

        // Then
        assertThat(result.doubleValue(), is(closeTo(28.1, 0.01)));
    }
}
