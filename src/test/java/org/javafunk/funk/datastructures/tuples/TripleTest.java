/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.functors.ordinals.First;
import org.javafunk.funk.functors.ordinals.Second;
import org.javafunk.funk.functors.ordinals.Third;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.tuple;

public class TripleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> triple = tuple(5, "Five", true);

        // When
        Integer first = triple.first();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> triple = tuple(5, "Five", true);

        // When
        String second = triple.second();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> triple = tuple(5, "Five", true);

        // When
        Boolean third = triple.third();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstSecondAndThirdInToString() {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);

        // When
        String stringRepresentation = triple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true)"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondAndThird() {
        // Given
        Triple<Integer, String, Boolean> triple1 = tuple(5, "Five", true);
        Triple<Integer, String, Boolean> triple2 = tuple(5, "Five", true);

        // When
        Boolean isEqual = triple1.equals(triple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Triple<Integer, String, Boolean> triple1 = tuple(5, "Five", true);
        Triple<Integer, String, Boolean> triple2 = tuple(10, "Five", true);

        // When
        Boolean isEqual = triple1.equals(triple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Triple<Integer, String, Boolean> triple1 = tuple(5, "Five", true);
        Triple<Integer, String, Boolean> triple2 = tuple(5, "Ten", true);

        // When
        Boolean isEqual = triple1.equals(triple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        Triple<Integer, String, Boolean> triple1 = tuple(5, "Five", true);
        Triple<Integer, String, Boolean> triple2 = tuple(5, "Five", false);

        // When
        Boolean isEqual = triple1.equals(triple2);

        // Then
        assertThat(isEqual, is(false));
    }
}
