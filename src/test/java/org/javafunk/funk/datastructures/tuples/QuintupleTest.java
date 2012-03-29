/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.functors.ordinals.*;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.tuple;

public class QuintupleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> quintuple = tuple(5, "Five", true, 3.6, 23L);

        // When
        Integer first = quintuple.first();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> quintuple = tuple(5, "Five", true, 3.6, 23L);

        // When
        String second = quintuple.second();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> quintuple = tuple(5, "Five", true, 3.6, 23L);

        // When
        Boolean third = quintuple.third();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldReturnTheFourthObject() {
        // Given
        Fourth<Double> quintuple = tuple(5, "Five", true, 3.6, 23L);

        // When
        Double fourth = quintuple.fourth();

        // Then
        assertThat(fourth, is(3.6));
    }

    @Test
    public void shouldReturnTheFifthObject() {
        // Given
        Fifth<Long> quintuple = tuple(5, "Five", true, 3.6, 23L);

        // When
        Long fifth = quintuple.fifth();

        // Then
        assertThat(fifth, is(23L));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstSecondThirdAndFourthInToString() {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 23L);

        // When
        String stringRepresentation = quintuple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true, 3.6, 23)"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondThirdFourthAndFifth() {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple1 = tuple(5, "Five", true, 3.6, 23L);
        Quintuple<Integer, String, Boolean, Double, Long> quintuple2 = tuple(5, "Five", true, 3.6, 23L);

        // When
        Boolean isEqual = quintuple1.equals(quintuple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple1 = tuple(5, "Five", true, 3.6, 23L);
        Quintuple<Integer, String, Boolean, Double, Long> quintuple2 = tuple(10, "Five", true, 3.6, 23L);

        // When
        Boolean isEqual = quintuple1.equals(quintuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple1 = tuple(5, "Five", true, 3.6, 23L);
        Quintuple<Integer, String, Boolean, Double, Long> quintuple2 = tuple(10, "Ten", true, 3.6, 23L);

        // When
        Boolean isEqual = quintuple1.equals(quintuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple1 = tuple(5, "Five", true, 3.6, 23L);
        Quintuple<Integer, String, Boolean, Double, Long> quintuple2 = tuple(10, "Five", false, 3.6, 23L);

        // When
        Boolean isEqual = quintuple1.equals(quintuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFourth() {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple1 = tuple(5, "Five", true, 3.6, 23L);
        Quintuple<Integer, String, Boolean, Double, Long> quintuple2 = tuple(10, "Five", true, 4.2, 23L);

        // When
        Boolean isEqual = quintuple1.equals(quintuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFifth() {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple1 = tuple(5, "Five", true, 3.6, 23L);
        Quintuple<Integer, String, Boolean, Double, Long> quintuple2 = tuple(10, "Five", true, 4.2, 36L);

        // When
        Boolean isEqual = quintuple1.equals(quintuple2);

        // Then
        assertThat(isEqual, is(false));
    }
}
