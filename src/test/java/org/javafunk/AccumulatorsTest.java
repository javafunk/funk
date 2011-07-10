package org.javafunk;

import org.javafunk.Accumulators;
import org.javafunk.functors.Reducer;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public class AccumulatorsTest {
    @Test
    public void shouldReturnAnAccumulatorThatAddsTheSuppliedIntegerInputToTheSuppliedAccumulatorValue() throws Exception {
        // Given
        Reducer<Integer, Integer> integerAdditionAccumulator = Accumulators.integerAdditionAccumulator();

        // When
        Integer result = integerAdditionAccumulator.accumulate(10, 5);

        // Then
        assertThat(result, is(15));
    }

    @Test
    public void shouldReturnAnAccumulatorThatAddsTheSuppliedLongInputToTheSuppliedAccumulatorValue() throws Exception {
        // Given
        Reducer<Long, Long> longAdditionAccumulator = Accumulators.longAdditionAccumulator();

        // When
        Long result = longAdditionAccumulator.accumulate(10L, 5L);

        // Then
        assertThat(result, is(15L));
    }

    @Test
    public void shouldReturnAnAccumulatorThatAddsTheSuppliedFloatInputToTheSuppliedAccumulatorValue() throws Exception {
        // Given
        Reducer<Float, Float> floatAdditionAccumulator = Accumulators.floatAdditionAccumulator();

        // When
        Float result = floatAdditionAccumulator.accumulate(10.7F, 4.6F);

        // Then
        assertThat(result.doubleValue(), is(closeTo(15.3, 0.01)));
    }

    @Test
    public void shouldReturnAnAccumulatorThatAddsTheSuppliedDoubleInputToTheSuppliedAccumulatorValue() throws Exception {
        // Given
        Reducer<Double, Double> doubleAdditionAccumulator = Accumulators.doubleAdditionAccumulator();

        // When
        Double result = doubleAdditionAccumulator.accumulate(21.4D, 6.7D);

        // Then
        assertThat(result.doubleValue(), is(closeTo(28.1, 0.01)));
    }

    @Test
    public void shouldReturnAnAccumulatorThatMultipliesTheSuppliedAccumulatorValueByTheSuppliedIntegerInput() throws Exception {
        // Given
        Reducer<Integer, Integer> integerMultiplicationAccumulator = Accumulators.integerMultiplicationAccumulator();

        // When
        Integer result = integerMultiplicationAccumulator.accumulate(10, 5);

        // Then
        assertThat(result, is(50));
    }

    @Test
    public void shouldReturnAnAccumulatorThatMultipliesTheSuppliedAccumulatorValueByTheSuppliedLongInput() throws Exception {
        // Given
        Reducer<Long, Long> longMultiplicationAccumulator = Accumulators.longMultiplicationAccumulator();

        // When
        Long result = longMultiplicationAccumulator.accumulate(10L, 5L);

        // Then
        assertThat(result, is(50L));
    }

    @Test
    public void shouldReturnAnAccumulatorThatMultipliesTheSuppliedAccumulatorValueByTheSuppliedFloatInput() throws Exception {
        // Given
        Reducer<Float, Float> floatMultiplicationAccumulator = Accumulators.floatMultiplicationAccumulator();

        // When
        Float result = floatMultiplicationAccumulator.accumulate(2.5F, 3.2F);

        // Then
        assertThat(result.doubleValue(), is(closeTo(8.0F, 0.01)));
    }

    @Test
    public void shouldReturnAnAccumulatorThatMultipliesTheSuppliedAccumulatorValueByTheSuppliedDoubleInput() throws Exception {
        // Given
        Reducer<Double, Double> doubleMultiplicationAccumulator = Accumulators.doubleMultiplicationAccumulator();

        // When
        Double result = doubleMultiplicationAccumulator.accumulate(2.5D, 3.7D);

        // Then
        assertThat(result.doubleValue(), is(closeTo(9.25D, 0.01)));
    }
}
