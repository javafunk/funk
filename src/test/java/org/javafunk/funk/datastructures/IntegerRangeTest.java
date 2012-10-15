/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class IntegerRangeTest {
    @Test
    public void shouldReturnTheSpecifiedStart() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 10, 1);

        // When
        Integer start = range.getStart();

        // Then
        assertThat(start, is(0));
    }

    @Test
    public void shouldReturnTheSpecifiedEnd() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 10, 1);

        // When
        Integer end = range.getEnd();

        // Then
        assertThat(end, is(10));
    }

    @Test
    public void shouldDefaultToAStepSizeOfOne() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 10);

        // When
        Integer step = range.getStep();

        // Then
        assertThat(step, is(1));
    }

    @Test
    public void shouldDefaultToAStepSizeOfOneIfNullIsSupplied() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 10, null);

        // When
        Integer step = range.getStep();

        // Then
        assertThat(step, is(1));
    }

    @Test
    public void shouldDefaultToAStartOfZeroIfNullIsSupplied() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(null, 10, 1);

        // When
        Integer start = range.getStart();

        // Then
        assertThat(start, is(0));
    }

    @Test
    public void shouldDefaultToAnEndOfIntegerMaxIfTheStepSizeIsPositive() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, null, 1);

        // When
        Integer end = range.getEnd();

        // Then
        assertThat(end, is(Integer.MAX_VALUE));
    }

    @Test
    public void shouldDefaultToAnEndOfIntegerMinIfTheStepSizeIsNegative() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, null, -1);

        // When
        Integer end = range.getEnd();

        // Then
        assertThat(end, is(Integer.MIN_VALUE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfZeroIsSpecifiedForStepSize() throws Exception {
        // Given
        new IntegerRange(0, 10, 0);

        // Then an IllegalArgumentException is thrown
    }

    @Test
    public void shouldIterateOverAllIntegersInTheSpecifiedRangeWhenAStepSizeOfOneIsSupplied() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 10, 1);
        Collection<Integer> expectedRange = collectionWith(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        // When
        Collection<Integer> materializedRange = materialize(range);

        // Then
        assertThat(materializedRange, hasOnlyItemsInOrder(expectedRange));
    }

    @Test
    public void shouldIterateOverAllIntegersInTheSpecifiedRangeSpacedByTheSpecifiedStepSize() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 10, 2);
        Collection<Integer> expectedRange = collectionWith(0, 2, 4, 6, 8);

        // When
        Collection<Integer> materializedRange = materialize(range);

        // Then
        assertThat(materializedRange, hasOnlyItemsInOrder(expectedRange));
    }

    @Test
    public void shouldIterateInReverseIfTheStepSizeIsNegativeAndTheStartIsGreaterThanTheEnd() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(10, 0, -2);
        Collection<Integer> expectedRange = collectionWith(10, 8, 6, 4, 2);

        // When
        Collection<Integer> materializedRange = materialize(range);

        // Then
        assertThat(materializedRange, hasOnlyItemsInOrder(expectedRange));
    }

    @Test
    public void shouldIterateOverAnEmptyIteratorIfTheStepSizeIsPositiveAndTheEndIsLessThanTheStart() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(10, 5, 1);

        // When
        Collection<Integer> materializedRange = materialize(range);

        // Then
        assertThat(materializedRange, is(Matchers.<Integer>empty()));
    }

    @Test
    public void shouldIterateOverAnEmptyIteratorIfTheStepSizeIsNegativeAndTheEndIsGreaterThatTheStart() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(5, 10, -1);

        // When
        Collection<Integer> materializedRange = materialize(range);

        // Then
        assertThat(materializedRange, is(Matchers.<Integer>empty()));
    }

    @Test
    public void shouldIteratePositivelyOverANegativeRange() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(-20, -10, 2);
        Collection<Integer> expectedRange = collectionWith(-20, -18, -16, -14, -12);

        // When
        Collection<Integer> materializedRange = materialize(range);

        // Then
        assertThat(materializedRange, hasOnlyItemsInOrder(expectedRange));
    }

    @Test
    public void shouldIterateNegativelyOverANegativeRange() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(-10, -20, -2);
        Collection<Integer> expectedRange = collectionWith(-10, -12, -14, -16, -18);

        // When
        Collection<Integer> materializedRange = materialize(range);

        // Then
        assertThat(materializedRange, hasOnlyItemsInOrder(expectedRange));
    }

    @Test
    public void shouldIterateOverAnEmptyIteratorIfTheStepSizeIsPositiveAndTheStartIsLessNegativeThanTheEnd() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(-10, -20, 2);

        // When
        Collection<Integer> materializedRange = materialize(range);

        // Then
        assertThat(materializedRange, is(Matchers.<Integer>empty()));
    }

    @Test
    public void shouldIterateOverAnEmptyIteratorIfTheStepSizeIsNegativeAndTheEndIsLessNegativeThanTheStart() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(-20, -10, -2);

        // When
        Collection<Integer> materializedRange = materialize(range);

        // Then
        assertThat(materializedRange, is(Matchers.<Integer>empty()));
    }

    @Test
    public void shouldReturnTrueIfTheRangeContainsTheSpecifiedInteger() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 10, 3);

        // When
        Boolean contained = range.contains(6);

        // Then
        assertThat(contained, is(true));
    }

    @Test
    public void shouldReturnFalseIfTheRangeDoesNotContainTheSpecifiedInteger() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 10, 3);

        // When
        Boolean contained = range.contains(20);

        // Then
        assertThat(contained, is(false));
    }

    @Test
    public void shouldReturnFalseIfTheSpecifiedIntegerIsBetweenStartAndEndButDoesNotLieOnAStepPoint() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 10, 3);

        // When
        Boolean contained = range.contains(4);

        // Then
        assertThat(contained, is(false));
    }

    @Test
    public void shouldReturnFalseIfAnObjectOtherThanAnIntegerIsSupplied() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 10, 3);

        // When
        Boolean contained = range.contains("hello");

        // Then
        assertThat(contained, is(false));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowUnsupportedOperationExceptionIfTryingToRemoveFromTheReturnedIterator() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 10);
        Iterator<Integer> iterator = range.iterator();
        iterator.next();

        // When
        iterator.remove();

        // Then an UnsupportedOperationException is thrown.
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesOnTheReturnedIteratorWithoutProgressingThroughTheRange() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 3);

        // When
        Iterator<Integer> iterator = range.iterator();

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(0));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(false));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutHavingCalledHasNext() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 3);

        // When
        Iterator<Integer> iterator = range.iterator();

        // Then
        assertThat(iterator.next(), is(0));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionIfTheRangeIsExhausted() throws Exception {
        // Given
        Range<Integer> range = new IntegerRange(0, 2);

        // When
        Iterator<Integer> iterator = range.iterator();
        iterator.next();
        iterator.next();
        iterator.next();

        // Then a NoSuchElementException is thrown.
    }
}
