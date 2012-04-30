/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.First;
import org.javafunk.funk.behaviours.ordinals.Second;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.collectionBuilderOf;
import static org.javafunk.funk.Literals.tuple;

public class PairTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> pair = tuple(5, "Five");

        // When
        Integer first = pair.first();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> pair = tuple(5, "Five");

        // When
        String second = pair.second();

        // Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstAndSecondInToString() {
        // Given
        Pair<Integer, String> pair = tuple(5, "Five");

        // When
        String stringRepresentation = pair.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five)"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstAndSecond() {
        // Given
        Pair<Integer, String> pair1 = tuple(5, "Five");
        Pair<Integer, String> pair2 = tuple(5, "Five");

        // When
        Boolean isEqual = pair1.equals(pair2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Pair<Integer, String> pair1 = tuple(5, "Five");
        Pair<Integer, String> pair2 = tuple(10, "Five");

        // When
        Boolean isEqual = pair1.equals(pair2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Pair<Integer, String> pair1 = tuple(5, "Five");
        Pair<Integer, String> pair2 = tuple(5, "Ten");

        // When
        Boolean isEqual = pair1.equals(pair2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirstAndSecond() {
        // Given
        Pair<Integer, String> pair1 = tuple(5, "Five");
        Pair<Integer, String> pair2 = tuple(10, "Ten");

        // When
        Boolean isEqual = pair1.equals(pair2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Pair<Integer, String> pair1 = tuple(5, "Five");
        Collection<Object> expected = collectionBuilderOf(Object.class)
                .with(5, "Five")
                .build();

        // When
        Boolean isEqual = materialize(pair1).equals(expected);

        // Then
        assertThat(isEqual, is(true));
    }
}
