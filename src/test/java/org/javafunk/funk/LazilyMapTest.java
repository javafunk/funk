/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.javafunk.funk.Iterables.asList;
import static org.javafunk.funk.Literals.listWith;
import static org.junit.Assert.assertThat;

public class LazilyMapTest {
    @Test
    public void shouldMapIterableUsingCustomMapFunction() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3);

        // When
        Iterable<String> actual = Lazily.map(input, new Mapper<Integer, String>() {
            public String map(Integer input) {
                return String.valueOf(input);
            }
        });

        // Then
        assertThat(asList(actual), hasItems("1", "2", "3"));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2, 3);

        // When
        Iterable<String> iterable = Lazily.map(input, new Mapper<Integer, String>() {
            public String map(Integer input) {
                return String.valueOf(input);
            }
        });

        Iterator<String> iterator1 = iterable.iterator();
        Iterator<String> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is("1"));
        assertThat(iterator1.next(), is("2"));
        assertThat(iterator2.next(), is("1"));
        assertThat(iterator1.next(), is("3"));
        assertThat(iterator2.next(), is("2"));
        assertThat(iterator2.next(), is("3"));
    }
}
