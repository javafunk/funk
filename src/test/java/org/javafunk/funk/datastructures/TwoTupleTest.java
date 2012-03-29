/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures;

import org.javafunk.funk.datastructures.tuples.TwoTuple;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.tuple;

public class TwoTupleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        TwoTuple<Integer, String> tuple = tuple(5, "Five");

        // When
        Integer first = tuple.first();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        TwoTuple<Integer, String> tuple = tuple(5, "Five");

        // When
        String second = tuple.second();

        // Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstAndSecondInToString() {
        // Given
        TwoTuple<Integer, String> tuple = tuple(5, "Five");

        // When
        String stringRepresentation = tuple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five)"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstAndSecond() {
        // Given
        TwoTuple<Integer, String> tuple1 = tuple(5, "Five");
        TwoTuple<Integer, String> tuple2 = tuple(5, "Five");

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        TwoTuple<Integer, String> tuple1 = tuple(5, "Five");
        TwoTuple<Integer, String> tuple2 = tuple(10, "Five");

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        TwoTuple<Integer, String> tuple1 = tuple(5, "Five");
        TwoTuple<Integer, String> tuple2 = tuple(5, "Ten");

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirstAndSecond() {
        // Given
        TwoTuple<Integer, String> tuple1 = tuple(5, "Five");
        TwoTuple<Integer, String> tuple2 = tuple(10, "Ten");

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(false));
    }
}
