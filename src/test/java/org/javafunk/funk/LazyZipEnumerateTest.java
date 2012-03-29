/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Pair;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.asList;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.matchers.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertThat;

public class LazyZipEnumerateTest {
    @Test
    public void shouldEnumerateSequence() {
        // Given
        Iterable<String> iterable = listWith("A", "B", "C");
        Collection<Pair<Integer, String>> expected = listWith(tuple(0, "A"), tuple(1, "B"), tuple(2, "C"));

        // When
        Iterable<Pair<Integer, String>> actual = Lazy.enumerate(iterable);

        // Then
        assertThat(asList(actual), hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedEnumeratedIterable() throws Exception {
        // Given
        Iterable<String> input = listWith("A", "B", "C");

        // When
        Iterable<Pair<Integer, String>> iterable = Lazy.enumerate(input);
        Iterator<Pair<Integer, String>> iterator1 = iterable.iterator();
        Iterator<Pair<Integer, String>> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator2.next(), is(tuple(0, "A")));
        assertThat(iterator2.next(), is(tuple(1, "B")));
        assertThat(iterator1.next(), is(tuple(0, "A")));
        assertThat(iterator2.next(), is(tuple(2, "C")));
        assertThat(iterator1.next(), is(tuple(1, "B")));
        assertThat(iterator1.next(), is(tuple(2, "C")));
    }

    @Test
    public void shouldZipIterables() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C");
        Iterable<Integer> iterable2 = listWith(1, 2, 3);

        Collection<Pair<String, Integer>> expected = listWith(tuple("A", 1), tuple("B", 2), tuple("C", 3));

        // When
        Collection<Pair<String, Integer>> actual = asList(Lazy.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipIterablesWithLongerFirstIterable() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C", "D");
        Iterable<Integer> iterable2 = listWith(1, 2, 3);
        Collection<Pair<String, Integer>> expected = listWith(tuple("A", 1), tuple("B", 2), tuple("C", 3));

        // When
        Collection<Pair<String, Integer>> actual = asList(Lazy.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipIterablesWithShorterFirstIterable() {
        // Given
        Iterable<String> iterable1 = listWith("A", "B", "C");
        Iterable<Integer> iterable2 = listWith(1, 2, 3, 4);
        Collection<Pair<String, Integer>> expected = listWith(tuple("A", 1), tuple("B", 2), tuple("C", 3));

        // When
        Iterable<Pair<String, Integer>> actual = Lazy.zip(iterable1, iterable2);

        // Then
        assertThat(asList(actual), hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedZippedIterable() throws Exception {
        // Given
        Iterable<String> inputIterable1 = listWith("A", "B", "C");
        Iterable<Integer> inputIterable2 = listWith(1, 2, 3);

        // When
        Iterable<Pair<String, Integer>> outputIterable = Lazy.zip(inputIterable1, inputIterable2);
        Iterator<Pair<String, Integer>> firstIterator = outputIterable.iterator();
        Iterator<Pair<String, Integer>> secondIterator = outputIterable.iterator();

        // Then
        assertThat(secondIterator.next(), is(tuple("A", 1)));
        assertThat(secondIterator.next(), is(tuple("B", 2)));
        assertThat(firstIterator.next(), is(tuple("A", 1)));
        assertThat(secondIterator.next(), is(tuple("C", 3)));
        assertThat(firstIterator.next(), is(tuple("B", 2)));
    }
}
