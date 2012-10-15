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
import org.javafunk.funk.testclasses.Age;
import org.javafunk.funk.testclasses.Colour;
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.collectionBuilderOf;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.testclasses.Age.age;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Name.name;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class OctupleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> octuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        Integer first = octuple.getFirst();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> octuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        String second = octuple.getSecond();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> octuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        Boolean third = octuple.getThird();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldReturnTheFourthObject() {
        // Given
        Fourth<Double> octuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        Double fourth = octuple.getFourth();

        // Then
        assertThat(fourth, is(3.6));
    }

    @Test
    public void shouldReturnTheFifthObject() {
        // Given
        Fifth<Long> octuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        Long fifth = octuple.getFifth();

        // Then
        assertThat(fifth, is(23L));
    }

    @Test
    public void shouldReturnTheSixthObject() {
        // Given
        Sixth<Name> octuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        Name sixth = octuple.getSixth();

        // Then
        assertThat(sixth, is(name("fred")));
    }

    @Test
    public void shouldReturnTheSeventhObject() {
        // Given
        Seventh<Colour> octuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        Colour seventh = octuple.getSeventh();

        // Then
        assertThat(seventh, is(colour("blue")));
    }

    @Test
    public void shouldReturnTheEighthObject() {
        // Given
        Eighth<Age> octuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        Age eighth = octuple.getEighth();

        // Then
        assertThat(eighth, is(age(25)));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstSecondThirdAndFourthInToString() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        String stringRepresentation = octuple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true, 3.6, 23, Name[value=fred], Colour[value=blue], Age[value=25])"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondThirdFourthFifthSixthSeventhAndEighth() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        Boolean isEqual = octuple1.equals(octuple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple2 =
                tuple(10, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        Boolean isEqual = octuple1.equals(octuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple2 =
                tuple(5, "Ten", true, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        Boolean isEqual = octuple1.equals(octuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple2 =
                tuple(5, "Five", false, 3.6, 23L, name("fred"), colour("blue"), age(25));

        // When
        Boolean isEqual = octuple1.equals(octuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFourth() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple2 =
                tuple(5, "Five", true, 4.2, 23L, name("fred"), colour("blue"), age(25));

        // When
        Boolean isEqual = octuple1.equals(octuple2);


        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFifth() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple2 =
                tuple(5, "Five", true, 3.6, 43L, name("fred"), colour("blue"), age(25));

        // When
        Boolean isEqual = octuple1.equals(octuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSixth() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("james"), colour("blue"), age(25));

        // When
        Boolean isEqual = octuple1.equals(octuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSeventh() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("red"), age(25));

        // When
        Boolean isEqual = octuple1.equals(octuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentEighth() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(50));

        // When
        Boolean isEqual = octuple1.equals(octuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25));
        Collection<Object> expected = collectionBuilderOf(Object.class)
                .with(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25))
                .build();

        // When
        Collection<Object> actual = materialize(octuple);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }
}
