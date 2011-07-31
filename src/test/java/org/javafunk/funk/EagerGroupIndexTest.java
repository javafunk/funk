/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.TwoTuple;
import org.javafunk.funk.functors.Indexer;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;

public class EagerGroupIndexTest {
    @Test
    public void shouldGroupTheElementsOfTheIterableUsingTheSpecifiedIndexer() throws Exception {
        // Given
        Iterable<String> input = listWith("apple", "pear", "lemon", "apricot", "orange", "papaya", "banana");

        Collection<String> fourLetterFruits = listWith("pear");
        Collection<String> fiveLetterFruits = listWith("apple", "lemon");
        Collection<String> sixLetterFruits = listWith("orange", "papaya", "banana");
        Collection<String> sevenLetterFruits = listWith("apricot");
        
        Map<Integer, Collection<String>> expectedOutput =
                mapWith(4, fourLetterFruits)
                   .and(5, fiveLetterFruits)
                   .and(6, sixLetterFruits)
                   .and(7, sevenLetterFruits);

        // When
        Map<Integer, Collection<String>> actualOutput = Eager.group(input, new Indexer<String, Integer>() {
            public Integer index(String string) {
                return string.length();
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }

    @Test
    public void shouldReturnATwoTupleWithTheIndexFirstAndTheElementSecond() throws Exception {
        // Given
        Iterable<String> input = listWith("apple", "pear", "lemon", "apricot", "orange", "papaya", "banana");
        Collection<TwoTuple<Integer, String>> expectedOutput = listWith(
                tuple(5, "apple"),
                tuple(4, "pear"),
                tuple(5, "lemon"),
                tuple(7, "apricot"),
                tuple(6, "orange"),
                tuple(6, "papaya"),
                tuple(6, "banana"));

        // When
        Collection<TwoTuple<Integer, String>> actualOutput = Eager.index(input, new Indexer<String, Integer>() {
            public Integer index(String item) {
                return item.length();
            }
        });

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }
}
