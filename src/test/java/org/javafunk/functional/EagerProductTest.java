package org.javafunk.functional;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.listWith;

public class EagerProductTest {
    @Test
    public void shouldCalculateTheProductOfTheSuppliedIntegers() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5);

        // When
        Integer result = Eager.product(input);

        // Then
        assertThat(result, is(120));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedLongs() throws Exception {
        // Given
        Iterable<Long> input = listWith(1L, 2L, 3L, 4L, 5L);

        // When
        Long result = Eager.product(input);

        // Then
        assertThat(result, is(120L));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedFloats() throws Exception {
        // Given
        Iterable<Float> input = listWith(1.1F, 1.2F, 1.3F, 1.4F, 1.5F);

        // When
        Float result = Eager.product(input);

        // Then
        assertThat(result.doubleValue(), is(closeTo(3.6036F, 0.0001)));
    }
    
    @Test
    public void shouldCalculateTheProductOfTheSuppliedDoubles() throws Exception {
        // Given
        Iterable<Double> input = listWith(1.1D, 1.2D, 1.3D, 1.4D, 1.5D);

        // When
        Double result = Eager.product(input);

        // Then
        assertThat(result.doubleValue(), is(closeTo(3.6036F, 0.0001)));
    }
}
