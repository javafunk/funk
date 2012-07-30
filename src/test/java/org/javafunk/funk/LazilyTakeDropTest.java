/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.hamcrest.Matchers;
import org.javafunk.funk.functors.Predicate;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.*;
import static org.junit.Assert.assertThat;

public class LazilyTakeDropTest {
    @Test
    public void shouldReturnAnIterableContainingTheSpecifiedNumberOfElements() {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Collection<Integer> expectedOutput = collectionWith(1, 1, 2, 3, 5);

        // When
        Collection<Integer> actualOutput = materialize(Lazily.take(fibonaccis, 5));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnAnEmptyIterableIfTheNumberOfElementsToTakeIsZero() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Integer numberToTake = 0;

        // When
        Collection<Integer> actualOutput = materialize(Lazily.take(fibonaccis, numberToTake));

        // Then
        assertThat(actualOutput, is(Matchers.<Integer>empty()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheNumberOfElementsToTakeIsLessThanZero() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Integer numberToTake = -5;

        // When
        Lazily.take(fibonaccis, numberToTake);

        // Then an IllegalArgumentException is thrown.
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedTakeIterable() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);

        // When
        Iterable<Integer> iterable = Lazily.take(fibonaccis, 5);
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(1));
        assertThat(iterator1.next(), is(1));
        assertThat(iterator1.next(), is(2));
        assertThat(iterator2.next(), is(1));
        assertThat(iterator2.next(), is(1));
        assertThat(iterator1.next(), is(3));
        assertThat(iterator2.next(), is(2));
    }

    @Test
    public void shouldReturnAnIterableWithTheFirstNElementsDropped() {
        // Given
        List<Integer> tenFibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Collection<Integer> expectedOutput = collectionWith(8, 13, 21, 34, 55);

        // When
        Collection<Integer> actualOutput = materialize(Lazily.drop(tenFibonaccis, 5));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnTheSuppliedIterableIfTheNumberOfElementsToDropIsZero() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Collection<Integer> expectedOutput = collectionWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Integer numberToTake = 0;

        // When
        Collection<Integer> actualOutput = materialize(Lazily.drop(fibonaccis, numberToTake));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheNumberOfElementsToDropIsLessThanZero() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Integer numberToDrop = -5;

        // When
        Lazily.drop(fibonaccis, numberToDrop);

        // Then an IllegalArgumentException is thrown.
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedDropIterable() throws Exception {
        // Given
        List<Integer> fibonaccis = listWith(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);

        // When
        Iterable<Integer> iterable = Lazily.drop(fibonaccis, 5);
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(8));
        assertThat(iterator1.next(), is(13));
        assertThat(iterator1.next(), is(21));
        assertThat(iterator2.next(), is(8));
        assertThat(iterator2.next(), is(13));
        assertThat(iterator1.next(), is(34));
        assertThat(iterator2.next(), is(21));
    }

    @Test
    public void shouldTakeElementsFromTheIterableWhileTheSuppliedPredicateIsTrue() {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 6, 7, 8);
        Collection<Integer> expectedOutput = collectionWith(1, 2, 3, 4);

        // When
        Collection<Integer> actualOutput = materialize(Lazily.takeWhile(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedTakeWhileIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5, 6, 7, 8);

        // When
        Iterable<Integer> iterable = Lazily.takeWhile(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        });
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(1));
        assertThat(iterator1.next(), is(2));
        assertThat(iterator2.next(), is(1));
        assertThat(iterator1.next(), is(3));
        assertThat(iterator2.next(), is(2));
        assertThat(iterator1.next(), is(4));
    }

    @Test
    public void shouldTakeElementsFromTheIterableUntilTheSuppliedPredicateIsTrue() {
        // Given
        Iterable<Integer> input = iterableWith(8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(8, 7, 6, 5);

        // When
        Collection<Integer> actualOutput = materialize(Lazily.takeUntil(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedTakeUntilIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Iterable<Integer> iterable = Lazily.takeUntil(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        });
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(8));
        assertThat(iterator1.next(), is(7));
        assertThat(iterator2.next(), is(8));
        assertThat(iterator1.next(), is(6));
        assertThat(iterator2.next(), is(7));
        assertThat(iterator1.next(), is(5));
    }

    @Test
    public void shouldDropElementsFromTheIterableWhileTheSuppliedPredicateIsTrue() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(4, 3, 2, 1);

        // When
        Collection<Integer> actualOutput = materialize(Lazily.dropWhile(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input > 4;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedDropWhileIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Iterable<Integer> iterable = Lazily.dropWhile(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input > 4;
            }
        });
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(4));
        assertThat(iterator1.next(), is(3));
        assertThat(iterator2.next(), is(4));
        assertThat(iterator1.next(), is(2));
        assertThat(iterator2.next(), is(3));
        assertThat(iterator1.next(), is(1));
    }

    @Test
    public void shouldDropElementsFromTheIterableUntilTheSuppliedPredicateIsTrue() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(8, 7, 6, 5, 4, 3, 2, 1);
        Collection<Integer> expectedOutput = collectionWith(4, 3, 2, 1);

        // When
        Collection<Integer> actualOutput = materialize(Lazily.dropUntil(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        }));

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedDropUntilIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(8, 7, 6, 5, 4, 3, 2, 1);

        // When
        Iterable<Integer> iterable = Lazily.dropUntil(input, new Predicate<Integer>() {
            public boolean evaluate(Integer input) {
                return input < 5;
            }
        });
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(4));
        assertThat(iterator1.next(), is(3));
        assertThat(iterator2.next(), is(4));
        assertThat(iterator1.next(), is(2));
        assertThat(iterator2.next(), is(3));
        assertThat(iterator1.next(), is(1));
    }
}
