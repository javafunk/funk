package org.smallvaluesofcool.misc.functional;

import org.junit.Test;
import org.smallvaluesofcool.misc.datastructures.TwoTuple;
import org.smallvaluesofcool.misc.functional.functors.DoFunction;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.Literals.twoTuple;

public class EagerSliceTest {
    @Test
    public void shouldSliceTheSuppliedIterableAsSpecifiedByStartStopAndStep() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(8, 6, 4, 2);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, 2, 9, 2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDefaultToZeroForStartIfNullSupplied() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(10, 8, 6);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, null, 6, 2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheEndOfTheIterableForStopIfNullSupplied() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(6, 4, 2);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, 4, null, 2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDefaultToAStepSizeOfOneIfNullSupplied() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e");
        Collection<String> expectedOutput = listWith("b", "c", "d");

        // When
        Collection<String> actualOutput = Eager.slice(input, 1, 4, null);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnACollectionEqualToTheInputIterableIfAllIndicesAreNull() throws Exception {
        // Given
        Iterable<Integer> input = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = listWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, null, null, null);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfZeroSuppliedForStep() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e");

        // When
        Eager.slice(input, 1, 4, 0);

        // Then an IllegalArgumentException should be thrown
    }

    @Test
    public void shouldHaveADefaultStepSizeOfOne() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e");
        Collection<String> expectedOutput = listWith("b", "c", "d");

        // When
        Collection<String> actualOutput = Eager.slice(input, 1, 4);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    // Different behaviour to python
    @Test
    public void shouldWorkForwardFromTheEndOfTheIterableIfANegativeStartIsSupplied() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = listWith(8, 9, 10, 1, 2, 3);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, -3, 3);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    // Different behaviour to python
    @Test
    public void shouldWorkBackwardsIfStartIsLessThanStopAndStepIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = listWith(3, 2, 1, 10, 9, 8);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, 2, 6, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldCountBackFromTheEndOfTheIterableIfANegativeStopIsSupplied() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = listWith(3, 4, 5, 6, 7, 8);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, 2, -2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldWorkBackwardsIfStartIsGreaterThanStopAndStepIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = listWith(8, 6, 4, 2);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, 7, 0, -2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldAllowNegativeStartWhenWorkingBackwardsWithANegativeStep() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = listWith(9, 8, 7, 6, 5, 4);

        // When
        Collection<Integer> actualOutput = Eager.slice(input, -2, 2, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyListIfStartIsNegativelyOutOfRange() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eager.slice(input, -15, 2, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyListIfStopIsNegativelyOutOfRange() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collection<Integer> expectedOutput = Collections.emptyList();

        // When
        Collection<Integer> actualOutput = Eager.slice(input, 2, 20, 1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }
}
