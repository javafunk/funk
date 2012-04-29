/*
 * Copyright (C) 2011 Funk committers.
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
import static org.junit.Assert.assertThat;

public class LazilyBatchTest {
    @Test
    public void shouldReturnElementsOfTheIterableInBatchesOfTheSpecifiedSize() {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> firstBatch = collectionWith(1, 2, 3);
        Collection<Integer> secondBatch = collectionWith(4, 5, 6);
        Collection<Integer> thirdBatch = collectionWith(7, 8, 9);

        // When
        Iterator<Iterable<Integer>> returnedIterator = Lazily.batch(input, 3).iterator();

        // Then
        assertThat(materialize(returnedIterator.next()), is(firstBatch));
        assertThat(materialize(returnedIterator.next()), is(secondBatch));
        assertThat(materialize(returnedIterator.next()), is(thirdBatch));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheSuppliedBatchSizeIsZero() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Integer batchSize = 0;

        // When
        Lazily.batch(input, batchSize);

        // Then an IllegalArgumentException is thrown.
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> firstBatch = collectionWith(1, 2, 3);
        Collection<Integer> secondBatch = collectionWith(4, 5, 6);
        Collection<Integer> thirdBatch = collectionWith(7, 8, 9);

        // When
        Iterable<Iterable<Integer>> iterable = Lazily.batch(input, 3);
        Iterator<Iterable<Integer>> iterator1 = iterable.iterator();
        Iterator<Iterable<Integer>> iterator2 = iterable.iterator();

        // Then
        assertThat(materialize(iterator1.next()), is(firstBatch));
        assertThat(materialize(iterator1.next()), is(secondBatch));
        assertThat(materialize(iterator2.next()), is(firstBatch));
        assertThat(materialize(iterator1.next()), is(thirdBatch));
        assertThat(materialize(iterator2.next()), is(secondBatch));
        assertThat(materialize(iterator2.next()), is(thirdBatch));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnABatchIterableReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 6);

        // When
        Iterable<Iterable<Integer>> batchIterator = Lazily.batch(input, 3);
        Iterable<Integer> firstBatchIterable = batchIterator.iterator().next();
        Iterator<Integer> firstBatchIterator1 = firstBatchIterable.iterator();
        Iterator<Integer> firstBatchIterator2 = firstBatchIterable.iterator();

        // Then
        assertThat(firstBatchIterator1.next(), is(1));
        assertThat(firstBatchIterator1.next(), is(2));
        assertThat(firstBatchIterator2.next(), is(1));
        assertThat(firstBatchIterator1.next(), is(3));
        assertThat(firstBatchIterator1.hasNext(), is(false));
        assertThat(firstBatchIterator2.next(), is(2));
        assertThat(firstBatchIterator2.next(), is(3));
        assertThat(firstBatchIterator2.hasNext(), is(false));
    }
}
