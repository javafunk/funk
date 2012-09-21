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
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.collectionBuilderOf;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.testclasses.Name.name;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInAnyOrder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class SextupleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Integer first = sextuple.getFirst();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        String second = sextuple.getSecond();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Boolean third = sextuple.getThird();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldReturnTheFourthObject() {
        // Given
        Fourth<Double> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Double fourth = sextuple.getFourth();

        // Then
        assertThat(fourth, is(3.6));
    }

    @Test
    public void shouldReturnTheFifthObject() {
        // Given
        Fifth<Long> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Long fifth = sextuple.getFifth();

        // Then
        assertThat(fifth, is(23L));
    }

    @Test
    public void shouldReturnTheSixthObject() {
        // Given
        Sixth<Name> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Name sixth = sextuple.getSixth();

        // Then
        assertThat(sixth, is(name("fred")));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstSecondThirdAndFourthInToString() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        String stringRepresentation = sextuple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true, 3.6, 23, Name[value=fred])"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondThirdFourthFifthAndSixth() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Five", true, 3.6, 23L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(10, "Five", true, 3.6, 23L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Ten", true, 3.6, 23L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Five", false, 3.6, 23L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFourth() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Five", true, 4.2, 23L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFifth() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Five", true, 3.6, 36L, name("fred"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSixth() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple2 = tuple(5, "Five", true, 3.6, 23L, name("james"));

        // When
        Boolean isEqual = sextuple1.equals(sextuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Collection<Object> expected = collectionBuilderOf(Object.class)
                .with(5, "Five", true, 3.6, 23L, name("fred"))
                .build();

        // When
        Collection<Object> actual = materialize(sextuple);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }
}
