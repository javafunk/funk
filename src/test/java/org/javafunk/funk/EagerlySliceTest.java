/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class EagerlySliceTest {
    @Test
    public void shouldDefaultToZeroForStartIfNullSupplied() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("a", "c", "e");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, null, 6, 2);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheEndOfTheIterableForStopIfNullSupplied() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("e", "g", "i", "k");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 4, null, 2);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldDefaultToAStepSizeOfOneIfNullSupplied() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e");
        Collection<String> expectedOutput = collectionWith("b", "c", "d");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 1, 4, null);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnACollectionEqualToTheInputIterableIfAllIndicesAreNull() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, null, null, null);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfZeroSuppliedForStep() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e");

        // When
        Eagerly.slice(input, 1, 4, 0);

        // Then an IllegalArgumentException should be thrown
    }

    @Test
    public void shouldDefaultToAStepSizeOfOne() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e");
        Collection<String> expectedOutput = collectionWith("b", "c", "d");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 1, 4);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnACollectionIncludingTheElementAtStartExcludingTheElementAtEndWithAllElementsAtStepsOfTheSpecifiedSize() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("c", "e", "g", "i");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 2, 9, 2);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldResolveFromTheEndOfTheIterableIfANegativeStopIsSupplied() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("c", "d", "e", "f", "g", "h", "i");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 2, -2, 1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldResolveFromTheEndOfTheIterableIfANegativeStartIsSupplied() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("e", "f", "g", "h", "i");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, -7, 9, 1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldIterateForwardsThroughTheIterableIfResolvedStartIsLessThanResolvedStopAndAPositiveStepIsSupplied() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("e", "f", "g", "h", "i");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, -7, -2, 1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldIterateInReverseThroughTheIterableIfStartIsGreaterThanStopAndANegativeStepIsSupplied() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("h", "f", "d");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 7, 1, -2);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldIterateInReverseThroughTheIterableIfResolvedStartIsGreaterThanStopAndANegativeStepIsSupplied() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("j", "i", "h", "g", "f", "e", "d");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, -2, 2, -1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldIterateInReverseThroughTheIterableIfStartIsGreaterThanResolvedStopAndANegativeStepIsSupplied() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("j", "i", "h", "g", "f", "e");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 9, -8, -1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldIterateInReverseThroughTheIterableIfResolvedStartIsGreaterThanResolvedStopAndANegativeStepIsSupplied() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("i", "h", "g", "f", "e");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, -3, -8, -1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfResolvedStartIsGreaterThanResolvedStopAndStepSizeIsPositive() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eagerly.slice(input, -3, 3, 1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfResolvedStartIsLessThanResolvedStopAndStepSizeIsNegative() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 2, -2, -1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheStartOfTheIterableIfSuppliedStartIsNegativelyOutOfRange() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("a", "b");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, -15, 2, 1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheStartOfTheIterableIfSuppliedEndIsNegativelyOutOfRange() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("c", "b", "a");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 2, -15, -1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheEndOfTheIterableIfSuppliedEndIsPositivelyOutOfRange() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("g", "h", "i", "j", "k");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 6, 20, 1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldDefaultToTheEndOfTheIterableIfSuppliedStartIsPositivelyOutOfRange() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = collectionWith("k", "j", "i", "h");

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 20, 6, -1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfSuppliedStartIsPostivelyOutOfRangeAndStepSizeIsPositive() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 20, 6, 1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfSuppliedEndIsNegativelyOutOfRangeAndStepSizeIsPositive() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 6, -15, 1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfSuppliedStartIsNegativelyOutOfRangeAndStepSizeIsNegative() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eagerly.slice(input, -15, 6, -1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfSuppliedEndIsPositivelyOutOfRangeAndStepSizeIsNegative() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        Collection<String> expectedOutput = Collections.emptyList();

        // When
        Collection<String> actualOutput = Eagerly.slice(input, 6, 20, -1);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldReturnAllElementsWhenIterableHasASingleElementAndCompleteSliceSpecified() throws Exception {
        // Given
        Iterable<String> input = iterableWith("one item");
        Collection<String> expectedCompleteSlice = collectionWith("one item");

        // When
        Collection<String> actualCompleteSlice = Eagerly.slice(input, 0, null);

        // Then
        assertThat(actualCompleteSlice, hasOnlyItemsInOrder(expectedCompleteSlice));
    }

    @Test
    public void shouldReturnNoElementsWhenIterableHasASingleElementAndForwardSubSliceSpecified() throws Exception {
        // Given
        Iterable<String> input = iterableWith("one item");
        Collection<String> expectedFirstToEndSlice = Collections.emptyList();

        // When
        Collection<String> actualFirstToEndSlice = Eagerly.slice(input, 1, null);

        // Then
        assertThat(actualFirstToEndSlice, hasOnlyItemsInOrder(expectedFirstToEndSlice));
    }

    @Test
    public void shouldReturnAllElementsWhenIterableHasASingleElementAndReverseSubSliceSpecified() throws Exception {
        // Given
        Iterable<String> input = iterableWith("one item");
        Collection<String> expectedFirstToEndSlice = Collections.emptyList();

        // When
        Collection<String> actualFirstToEndSlice = Eagerly.slice(input, -1, 0, -1);

        // Then
        assertThat(actualFirstToEndSlice, hasOnlyItemsInOrder(expectedFirstToEndSlice));
    }

    @Test
    public void shouldReturnEmptyCollectionWhenIterableHasNoElementsAndCompleteSliceIsSpecified() throws Exception {
        // Given
        Iterable<String> input = Collections.emptyList();
        Collection<String> expectedCompleteSlice = Collections.emptyList();

        // When
        Collection<String> actualCompleteSlice = Eagerly.slice(input, 0, null);

        // Then
        assertThat(actualCompleteSlice, hasOnlyItemsInOrder(expectedCompleteSlice));
    }

    @Test
    public void shouldReturnEmptyCollectionWhenIterableHasNoElementsAndForwardSubSliceIsSpecified() throws Exception {
        // Given
        Iterable<String> input = Collections.emptyList();
        Collection<String> expectedCompleteSlice = Collections.emptyList();

        // When
        Collection<String> actualCompleteSlice = Eagerly.slice(input, 5, null);

        // Then
        assertThat(actualCompleteSlice, hasOnlyItemsInOrder(expectedCompleteSlice));
    }

    @Test
    public void shouldReturnEmptyCollectionWhenIterableHasNoElementsAndReverseSubSliceIsSpecified() throws Exception {
        // Given
        Iterable<String> input = Collections.emptyList();
        Collection<String> expectedCompleteSlice = Collections.emptyList();

        // When
        Collection<String> actualCompleteSlice = Eagerly.slice(input, 5, 0, -1);

        // Then
        assertThat(actualCompleteSlice, hasOnlyItemsInOrder(expectedCompleteSlice));
    }
}
