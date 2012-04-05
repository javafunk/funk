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
import org.javafunk.funk.functors.Indexer;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.Literals.tuple;

public class LazilyIndexTest {
    @Test
    public void shouldReturnTwoTuplesWithTheIndexFirstAndTheElementSecond() throws Exception {
        // Given
        Iterable<String> input = listWith("apple", "pear", "lemon");

        // When
        Iterable<Pair<Integer, String>> outputIterable = Lazily.index(input, new Indexer<String, Integer>() {
            public Integer index(String item) {
                return item.length();
            }
        });
        Iterator<Pair<Integer,String>> outputIterator = outputIterable.iterator();

        // Then
        assertThat(outputIterator.hasNext(), is(true));
        assertThat(outputIterator.next(), is(tuple(5, "apple")));
        assertThat(outputIterator.hasNext(), is(true));
        assertThat(outputIterator.next(), is(tuple(4, "pear")));
        assertThat(outputIterator.hasNext(), is(true));
        assertThat(outputIterator.next(), is(tuple(5, "lemon")));
        assertThat(outputIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<String> input = listWith("apple", "pear", "lemon");

        // When
        Iterable<Pair<Integer, String>> iterable = Lazily.index(input, new Indexer<String, Integer>() {
            public Integer index(String item) {
                return item.length();
            }
        });

        Iterator<Pair<Integer, String>> iterator1 = iterable.iterator();
        Iterator<Pair<Integer, String>> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(tuple(5, "apple")));
        assertThat(iterator1.next(), is(tuple(4, "pear")));
        assertThat(iterator2.next(), is(tuple(5, "apple")));
        assertThat(iterator1.next(), is(tuple(5, "lemon")));
        assertThat(iterator2.next(), is(tuple(4, "pear")));
        assertThat(iterator2.next(), is(tuple(5, "lemon")));
    }
}
