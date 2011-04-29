package org.javafunk.functional;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.listWith;

public class EagerSliceTest {
    @Test
    public void shouldDefaultToZeroForStartIfNullSupplied() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("a", "c", "e");

        // When
        Collection<String> actualOutput = Eager.slice(input, null, 6, 2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheEndOfTheIterableForStopIfNullSupplied() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("e", "g", "i", "k");

        // When
        Collection<String> actualOutput = Eager.slice(input, 4, null, 2);

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
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");

        // When
        Collection<String> actualOutput = Eager.slice(input, null, null, null);

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
    public void shouldDefaultToAStepSizeOfOne() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e");
        Collection<String> expectedOutput = listWith("b", "c", "d");

        // When
        Collection<String> actualOutput = Eager.slice(input, 1, 4);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnACollectionIncludingTheElementAtStartExcludingTheElementAtEndWithAllElementsAtStepsOfTheSpecifiedSize() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("c", "e", "g", "i");

        // When
        Collection<String> actualOutput = Eager.slice(input, 2, 9, 2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldResolveFromTheEndOfTheIterableIfANegativeStopIsSupplied() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("c", "d", "e", "f", "g", "h", "i");

        // When
        Collection<String> actualOutput = Eager.slice(input, 2, -2, 1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldResolveFromTheEndOfTheIterableIfANegativeStartIsSupplied() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("e", "f", "g", "h", "i");

        // When
        Collection<String> actualOutput = Eager.slice(input, -7, 9, 1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldIterateForwardsThroughTheIterableIfResolvedStartIsLessThanResolvedStopAndAPositiveStepIsSupplied() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("e", "f", "g", "h", "i");

        // When
        Collection<String> actualOutput = Eager.slice(input, -7, -2, 1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldIterateInReverseThroughTheIterableIfStartIsGreaterThanStopAndANegativeStepIsSupplied() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("h", "f", "d");

        // When
        Collection<String> actualOutput = Eager.slice(input, 7, 1, -2);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldIterateInReverseThroughTheIterableIfResolvedStartIsGreaterThanStopAndANegativeStepIsSupplied() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("j", "i", "h", "g", "f", "e", "d");

        // When
        Collection<String> actualOutput = Eager.slice(input, -2, 2, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldIterateInReverseThroughTheIterableIfStartIsGreaterThanResolvedStopAndANegativeStepIsSupplied() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("j", "i", "h", "g", "f", "e");

        // When
        Collection<String> actualOutput = Eager.slice(input, 9, -8, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldIterateInReverseThroughTheIterableIfResolvedStartIsGreaterThanResolvedStopAndANegativeStepIsSupplied() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("i", "h", "g", "f", "e");

        // When
        Collection<String> actualOutput = Eager.slice(input, -3, -8, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfResolvedStartIsGreaterThanResolvedStopAndStepSizeIsPositive() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eager.slice(input, -3, 3, 1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfResolvedStartIsLessThanResolvedStopAndStepSizeIsNegative() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eager.slice(input, 2, -2, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheStartOfTheIterableIfSuppliedStartIsNegativelyOutOfRange() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("a", "b");

        // When
        Collection<String> actualOutput = Eager.slice(input, -15, 2, 1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheStartOfTheIterableIfSuppliedEndIsNegativelyOutOfRange() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("c", "b", "a");

        // When
        Collection<String> actualOutput = Eager.slice(input, 2, -15, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheEndOfTheIterableIfSuppliedEndIsPositivelyOutOfRange() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("g", "h", "i", "j", "k");

        // When
        Collection<String> actualOutput = Eager.slice(input, 6, 20, 1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheEndOfTheIterableIfSuppliedStartIsPositivelyOutOfRange() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = listWith("k", "j", "i", "h");

        // When
        Collection<String> actualOutput = Eager.slice(input, 20, 6, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfSuppliedStartIsPostivelyOutOfRangeAndStepSizeIsPositive() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eager.slice(input, 20, 6, 1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfSuppliedEndIsNegativelyOutOfRangeAndStepSizeIsPositive() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eager.slice(input, 6, -15, 1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfSuppliedStartIsNegativelyOutOfRangeAndStepSizeIsNegative() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eager.slice(input, -15, 6, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfSuppliedEndIsPositivelyOutOfRangeAndStepSizeIsNegative() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eager.slice(input, 6, 20, -1);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }
}
