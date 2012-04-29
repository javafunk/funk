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
import org.javafunk.funk.datastructures.tuples.Triple;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.asList;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.matchers.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertThat;

public class LazilyZipEnumerateTest {
    @Test
    public void shouldEnumerateSequence() {
        // Given
        Iterable<String> iterable = iterableWith("A", "B", "C");
        Collection<Pair<Integer, String>> expected = collectionWith(tuple(0, "A"), tuple(1, "B"), tuple(2, "C"));

        // When
        Iterable<Pair<Integer, String>> actual = Lazily.enumerate(iterable);

        // Then
        assertThat(asList(actual), hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedEnumeratedIterable() throws Exception {
        // Given
        Iterable<String> input = iterableWith("A", "B", "C");

        // When
        Iterable<Pair<Integer, String>> iterable = Lazily.enumerate(input);
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
    public void shouldZipTwoIterables() {
        // Given
        Iterable<String> iterable1 = iterableWith("A", "B", "C");
        Iterable<Integer> iterable2 = iterableWith(1, 2, 3);

        Collection<Pair<String, Integer>> expected = collectionWith(tuple("A", 1), tuple("B", 2), tuple("C", 3));

        // When
        Collection<Pair<String, Integer>> actual = asList(Lazily.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipTwoIterablesWithLongerFirstIterable() {
        // Given
        Iterable<String> iterable1 = iterableWith("A", "B", "C", "D");
        Iterable<Integer> iterable2 = iterableWith(1, 2, 3);
        Collection<Pair<String, Integer>> expected = collectionWith(tuple("A", 1), tuple("B", 2), tuple("C", 3));

        // When
        Collection<Pair<String, Integer>> actual = asList(Lazily.zip(iterable1, iterable2));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldZipTwoIterablesWithShorterFirstIterable() {
        // Given
        Iterable<String> iterable1 = iterableWith("A", "B", "C");
        Iterable<Integer> iterable2 = iterableWith(1, 2, 3, 4);
        Collection<Pair<String, Integer>> expected = collectionWith(tuple("A", 1), tuple("B", 2), tuple("C", 3));

        // When
        Iterable<Pair<String, Integer>> actual = Lazily.zip(iterable1, iterable2);

        // Then
        assertThat(asList(actual), hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldReturnDistinctIteratorsEachTimeIteratorIsCalledOnTheReturnedTwoZippedIterable() throws Exception {
        // Given
        Iterable<String> inputIterable1 = iterableWith("A", "B", "C");
        Iterable<Integer> inputIterable2 = iterableWith(1, 2, 3);

        // When
        Iterable<Pair<String, Integer>> outputIterable = Lazily.zip(inputIterable1, inputIterable2);
        Iterator<Pair<String, Integer>> firstIterator = outputIterable.iterator();
        Iterator<Pair<String, Integer>> secondIterator = outputIterable.iterator();

        // Then
        assertThat(secondIterator.next(), is(tuple("A", 1)));
        assertThat(secondIterator.next(), is(tuple("B", 2)));
        assertThat(firstIterator.next(), is(tuple("A", 1)));
        assertThat(secondIterator.next(), is(tuple("C", 3)));
        assertThat(firstIterator.next(), is(tuple("B", 2)));
    }

    @Test
    public void shouldZipThreeIterables() throws Exception {
        // Given
        Iterable<String> iterable1 = iterableWith("A", "B", "C");
        Iterable<Integer> iterable2 = iterableWith(1, 2, 3);
        Iterable<Boolean> iterable3 = iterableWith(true, false, true);

        Collection<Triple<String, Integer, Boolean>> expected = collectionWith(tuple("A", 1, true), tuple("B", 2, false), tuple("C", 3, true));

        // When
        Collection<Triple<String, Integer, Boolean>> actual = materialize(Lazily.zip(iterable1, iterable2, iterable3));

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }
}
