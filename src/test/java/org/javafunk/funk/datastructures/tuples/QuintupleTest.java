/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.*;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.collectionBuilderOf;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class QuintupleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> quintuple = tuple(5, "Five", true, 3.6, 23L);

        // When
        Integer first = quintuple.getFirst();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> quintuple = tuple(5, "Five", true, 3.6, 23L);

        // When
        String second = quintuple.getSecond();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> quintuple = tuple(5, "Five", true, 3.6, 23L);

        // When
        Boolean third = quintuple.getThird();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldReturnTheFourthObject() {
        // Given
        Fourth<Double> quintuple = tuple(5, "Five", true, 3.6, 23L);

        // When
        Double fourth = quintuple.getFourth();

        // Then
        assertThat(fourth, is(3.6));
    }

    @Test
    public void shouldReturnTheFifthObject() {
        // Given
        Fifth<Long> quintuple = tuple(5, "Five", true, 3.6, 23L);

        // When
        Long fifth = quintuple.getFifth();

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

    @Test
    public void shouldBeIterable() {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 23L);
        Collection<Object> expected = collectionBuilderOf(Object.class)
                .with(5, "Five", true, 3.6, 23L)
                .build();

        // When
        Collection<Object> actual = materialize(quintuple);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }
}
