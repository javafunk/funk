/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.javafunk.funk.Lazily;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.listWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class BatchedIteratorTest {
    @Test
    public void shouldReturnElementsOfTheIterableInBatchesOfTheSpecifiedSize() {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d");
        Iterator<String> expectedFirstBatch = listWith("a", "b").iterator();
        Iterator<String> expectedSecondBatch = listWith("c", "d").iterator();

        // When
        Iterator<Iterable<String>> returnedIterator = Lazily.batch(input, 2).iterator();
        Iterator<String> actualFirstBatch = returnedIterator.next().iterator();
        Iterator<String> actualSecondBatch = returnedIterator.next().iterator();

        // Then
        assertThat(actualFirstBatch.next(), is(expectedFirstBatch.next()));
        assertThat(actualFirstBatch.next(), is(expectedFirstBatch.next()));
        assertThat(actualSecondBatch.next(), is(expectedSecondBatch.next()));
        assertThat(actualSecondBatch.next(), is(expectedSecondBatch.next()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheSuppliedBatchSizeIsZero() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d");
        int batchSize = 0;

        // When
        Lazily.batch(input, batchSize);

        // Then an IllegalArgumentException is thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheSuppliedBatchSizeIsLessThanZero() throws Exception {
        // Given
        Iterable<String> input = listWith("a", "b", "c", "d");
        int batchSize = -3;

        // When
        Lazily.batch(input, batchSize);

        // Then an IllegalArgumentException is thrown
    }

    @Test
    public void shouldAllowLaterIterablesToBeTraversedBeforeEarlierOnes() {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4);

        // When
        Iterator<Iterable<Integer>> returnedIterator = Lazily.batch(input, 2).iterator();
        Iterator<Integer> firstBatchIterator = returnedIterator.next().iterator();
        Iterator<Integer> secondBatchIterator = returnedIterator.next().iterator();

        // Then
        assertThat(secondBatchIterator.next(), is(3));
        assertThat(firstBatchIterator.next(), is(1));
        assertThat(secondBatchIterator.next(), is(4));
        assertThat(firstBatchIterator.next(), is(2));
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesOnEachIterableWithoutIncrementingTheIterator() {
        // Given
        Iterable<Integer> input = listWith(1, 2);

        // When
        Iterator<Iterable<Integer>> returnedIterator = Lazily.batch(input, 1).iterator();

        // Then
        assertThat(returnedIterator.hasNext(), is(true));
        assertThat(returnedIterator.hasNext(), is(true));
        Iterator<Integer> firstBatchIterator = returnedIterator.next().iterator();
        assertThat(returnedIterator.hasNext(), is(true));
        assertThat(returnedIterator.hasNext(), is(true));
        Iterator<Integer> secondBatchIterator = returnedIterator.next().iterator();
        assertThat(returnedIterator.hasNext(), is(false));
        assertThat(returnedIterator.hasNext(), is(false));

        assertThat(firstBatchIterator.hasNext(), is(true));
        assertThat(firstBatchIterator.hasNext(), is(true));
        firstBatchIterator.next();
        assertThat(firstBatchIterator.hasNext(), is(false));
        assertThat(firstBatchIterator.hasNext(), is(false));

        assertThat(secondBatchIterator.hasNext(), is(true));
        assertThat(secondBatchIterator.hasNext(), is(true));
        secondBatchIterator.next();
        assertThat(secondBatchIterator.hasNext(), is(false));
        assertThat(secondBatchIterator.hasNext(), is(false));
    }

    @Test
    public void shouldLeaveLastIterableShortIfBatchSizeDoesNotDivideNumberOfItems() {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4);
        Collection<Integer> expectedFirstBatch = listWith(1, 2, 3);
        Collection<Integer> expectedSecondBatch = listWith(4);

        // When
        Iterator<Iterable<Integer>> returnedIterator = Lazily.batch(input, 3).iterator();
        Collection<Integer> actualFirstBatch = materialize(returnedIterator.next());
        Collection<Integer> actualSecondBatch = materialize(returnedIterator.next());

        // Then
        assertThat(actualFirstBatch, is(expectedFirstBatch));
        assertThat(actualSecondBatch, is(expectedSecondBatch));
    }

    @Test
    public void shouldThrowNoSuchElementExceptionOnceEachIterableHasBeenDepleted() {
        // Given
        Iterable<String> input = listWith("a", "b");

        // When
        Iterator<Iterable<String>> returnedIterator = Lazily.batch(input, 1).iterator();
        Iterator<String> firstBatchIterator = returnedIterator.next().iterator();
        Iterator<String> secondBatchIterator = returnedIterator.next().iterator();

        firstBatchIterator.next();
        secondBatchIterator.next();

        // Then
        try {
            returnedIterator.next();
            fail("Expected parent iterator to throw a NoSuchElementException.");
        } catch (NoSuchElementException exception) {
        }

        try {
            firstBatchIterator.next();
            fail("Expected first child iterator to throw a NoSuchElementException.");
        } catch (NoSuchElementException exception) {
        }

        try {
            secondBatchIterator.next();
            fail("Expected second child iterator to throw a NoSuchElementException.");
        } catch (NoSuchElementException exception) {
        }
    }

    @Test
    public void shouldNotSupportRemovingElementsFromAnyReturnedIterables() {
        // Given
        Iterable<String> input = listWith("a", "b");

        // When
        Iterator<Iterable<String>> returnedIterator = Lazily.batch(input, 1).iterator();
        Iterator<String> firstBatchIterator = returnedIterator.next().iterator();
        Iterator<String> secondBatchIterator = returnedIterator.next().iterator();

        // Then
        try {
            returnedIterator.remove();
            fail("Expected parent iterator to throw an UnsupportedOperationException for remove.");
        } catch (UnsupportedOperationException exception) {
        }

        try {
            firstBatchIterator.remove();
            fail("Expected first child iterator to throw an UnsupportedOperationException for remove.");
        } catch (UnsupportedOperationException exception) {
        }

        try {
            secondBatchIterator.remove();
            fail("Expected second child iterator to throw an UnsupportedOperationException for remove.");
        } catch (UnsupportedOperationException exception) {
        }
    }

    @Test
    public void shouldAllowNullValuesInTheInputIterator() throws Exception {
        // Given
        Iterable<String> input = listWith("a", null, null, "d");
        Iterator<String> expectedFirstBatch = listWith("a", null).iterator();
        Iterator<String> expectedSecondBatch = listWith(null, "d").iterator();

        // When
        Iterator<Iterable<String>> returnedIterator = Lazily.batch(input, 2).iterator();
        Iterator<String> actualFirstBatch = returnedIterator.next().iterator();
        Iterator<String> actualSecondBatch = returnedIterator.next().iterator();

        // Then
        assertThat(actualFirstBatch.next(), is(expectedFirstBatch.next()));
        assertThat(actualFirstBatch.next(), is(expectedFirstBatch.next()));
        assertThat(actualSecondBatch.next(), is(expectedSecondBatch.next()));
        assertThat(actualSecondBatch.next(), is(expectedSecondBatch.next()));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnTheReturnedIterablesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3, 4, 5, 6);
        Iterator<Iterable<Integer>> batchIterator = new BatchedIterator<Integer>(input.iterator(), 3);

        // When
        Iterable<Integer> firstBatchIterable = batchIterator.next();
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
