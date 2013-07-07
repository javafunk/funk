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
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertThat;

public class LazilySliceTest {
    @Test
    public void shouldTakeElementsFromTheIterableBetweenTheSpecifiedStartAndStopInStepsOfTheSpecifiedSize() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> expectedOutput = collectionWith(3, 5, 7);

        // When
        Collection<Integer> actualOutput = materialize(Lazily.slice(input, 2, 7, 2));

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldBeInclusiveOfTheStartIndexAndExclusiveOfTheStopIndex() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> expectedOutput = collectionWith(3, 4, 5, 6, 7);

        // When
        Collection<Integer> actualOutput = materialize(Lazily.slice(input, 2, 7, 1));

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // When
        Iterable<Integer> iterable = Lazily.slice(input, 2, 7, 2);
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(3));
        assertThat(iterator1.next(), is(5));
        assertThat(iterator2.next(), is(3));
        assertThat(iterator1.next(), is(7));
        assertThat(iterator2.next(), is(5));
        assertThat(iterator2.next(), is(7));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullIterablePassedToSlice() throws Exception {
        // Given
        Iterable<Integer> input = null;

        // When
        Lazily.slice(input, 2, 7, 1);

        // Then a NullPointerException is thrown
    }
}
