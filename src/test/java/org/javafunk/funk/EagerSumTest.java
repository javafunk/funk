package org.javafunk.funk;

import org.javafunk.funk.Eager;
import org.javafunk.funk.Eager;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.listWith;

public class EagerSumTest {
    @Test
    public void shouldSumTheSuppliedIntegers() {
        // Given
        List<Integer> inputs = listWith(1, 2, 3);

        // When
        Integer sum = Eager.sum(inputs);

        // Then
        assertThat(sum, is(6));
    }

    @Test
    public void shouldSumTheSuppliedLongs() {
        // Given
        List<Long> inputs = listWith(1L, 2L, 3L);

        // When
        Long sum = Eager.sum(inputs);

        // Then
        assertThat(sum, is(6L));
    }

    @Test
    public void shouldSumTheSuppliedDoubles() {
        // Given
        List<Double> inputs = listWith(1.6D, 2.2D, 3.5D);

        // When
        Double sum = Eager.sum(inputs);

        // Then
        assertThat(sum, is(closeTo(7.3D, 0.01D)));
    }

    @Test
    public void shouldSumTheSuppliedFloats() {
        // Given
        List<Float> inputs = listWith(1.6F, 2.2F, 3.5F);

        // When
        Float sum = Eager.sum(inputs);

        // Then
        assertThat(sum.doubleValue(), is(closeTo(7.3D, 0.01)));
    }
}
