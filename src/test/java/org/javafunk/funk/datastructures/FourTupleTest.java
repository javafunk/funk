/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures;

import org.javafunk.funk.datastructures.tuples.FourTuple;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.tuple;

public class FourTupleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        FourTuple<Integer, String, Boolean, Double> tuple = tuple(5, "Five", true, 3.6);

        // When
        Integer first = tuple.first();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        FourTuple<Integer, String, Boolean, Double> tuple = tuple(5, "Five", true, 3.6);

        // When
        String second = tuple.second();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        FourTuple<Integer, String, Boolean, Double> tuple = tuple(5, "Five", true, 3.6);

        // When
        Boolean third = tuple.third();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldReturnTheFourthObject() {
        // Given
        FourTuple<Integer, String, Boolean, Double> tuple = tuple(5, "Five", true, 3.6);

        // When
        Double fourth = tuple.fourth();

        //Then
        assertThat(fourth, is(3.6));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstSecondThirdAndFourthInToString() {
        // Given
        FourTuple<Integer, String, Boolean, Double> tuple = tuple(5, "Five", true, 3.6);

        // When
        String stringRepresentation = tuple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true, 3.6)"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondThirdAndFourth() {
        // Given
        FourTuple<Integer, String, Boolean, Double> tuple1 = tuple(5, "Five", true, 3.6);
        FourTuple<Integer, String, Boolean, Double> tuple2 = tuple(5, "Five", true, 3.6);

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        FourTuple<Integer, String, Boolean, Double> tuple1 = tuple(5, "Five", true, 3.6);
        FourTuple<Integer, String, Boolean, Double> tuple2 = tuple(10, "Five", true, 3.6);

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        FourTuple<Integer, String, Boolean, Double> tuple1 = tuple(5, "Five", true, 3.6);
        FourTuple<Integer, String, Boolean, Double> tuple2 = tuple(5, "Ten", true, 3.6);

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        FourTuple<Integer, String, Boolean, Double> tuple1 = tuple(5, "Five", true, 3.6);
        FourTuple<Integer, String, Boolean, Double> tuple2 = tuple(5, "Five", false, 3.6);

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFourth() {
        // Given
        FourTuple<Integer, String, Boolean, Double> tuple1 = tuple(5, "Five", true, 3.6);
        FourTuple<Integer, String, Boolean, Double> tuple2 = tuple(5, "Five", true, 4.2);

        // When
        Boolean isEqual = tuple1.equals(tuple2);

        // Then
        assertThat(isEqual, is(false));
    }
}
