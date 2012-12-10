/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Eighth;
import org.javafunk.funk.behaviours.ordinals.Fifth;
import org.javafunk.funk.behaviours.ordinals.First;
import org.javafunk.funk.behaviours.ordinals.Fourth;
import org.javafunk.funk.behaviours.ordinals.Ninth;
import org.javafunk.funk.behaviours.ordinals.Second;
import org.javafunk.funk.behaviours.ordinals.Seventh;
import org.javafunk.funk.behaviours.ordinals.Sixth;
import org.javafunk.funk.behaviours.ordinals.Third;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.testclasses.Age;
import org.javafunk.funk.testclasses.Colour;
import org.javafunk.funk.testclasses.Location;
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.collectionBuilderOf;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.datastructures.tuples.Nonuple.nonuple;
import static org.javafunk.funk.testclasses.Age.age;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Location.location;
import static org.javafunk.funk.testclasses.Name.name;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class NonupleTest {
    @Test
    public void shouldConstructANonupleWithTheSpecifiedValues() throws Exception {
        // Given
        String first = "5";
        Integer second = 4;
        Long third = 5L;
        Character fourth = 'a';
        Boolean fifth = true;
        Double sixth = 3.6;
        Name seventh = name("Anna");
        Age eighth = age(20);
        Colour ninth = colour("Red");
        Nonuple<String, Integer, Long, Character, Boolean, Double, Name, Age, Colour> expected =
                new Nonuple<String, Integer, Long, Character, Boolean, Double, Name, Age, Colour>(
                        first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);

        // When
        Nonuple<String, Integer, Long, Character, Boolean, Double, Name, Age, Colour> actual =
                nonuple(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Integer first = nonuple.getFirst();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        String second = nonuple.getSecond();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean third = nonuple.getThird();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldReturnTheFourthObject() {
        // Given
        Fourth<Double> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Double fourth = nonuple.getFourth();

        // Then
        assertThat(fourth, is(3.6));
    }

    @Test
    public void shouldReturnTheFifthObject() {
        // Given
        Fifth<Long> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Long fifth = nonuple.getFifth();

        // Then
        assertThat(fifth, is(23L));
    }

    @Test
    public void shouldReturnTheSixthObject() {
        // Given
        Sixth<Name> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Name sixth = nonuple.getSixth();

        // Then
        assertThat(sixth, is(name("fred")));
    }

    @Test
    public void shouldReturnTheSeventhObject() {
        // Given
        Seventh<Colour> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Colour seventh = nonuple.getSeventh();

        // Then
        assertThat(seventh, is(colour("blue")));
    }

    @Test
    public void shouldReturnTheEighthObject() {
        // Given
        Eighth<Age> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Age eighth = nonuple.getEighth();

        // Then
        assertThat(eighth, is(age(25)));
    }

    @Test
    public void shouldReturnTheNinthObject() {
        // Given
        Ninth<Location> nonuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Location ninth = nonuple.getNinth();

        // Then
        assertThat(ninth, is(location("USA")));
    }

    @Test
    public void shouldUseTheStringValueOfAllElementsInToString() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        String stringRepresentation = nonuple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true, 3.6, 23, Name[value=fred], Colour[value=blue], Age[value=25], Location[value=USA])"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondThirdFourthFifthSixthSeventhEighthAndNinth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(10, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Ten", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", false, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFourth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 5.4, 23L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFifth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 46L, name("fred"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSixth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("george"), colour("blue"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSeventh() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("red"), age(25), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentEighth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(45), location("USA"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentNinth() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple1 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple2 =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("UK"));

        // When
        Boolean isEqual = nonuple1.equals(nonuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"));
        Collection<Object> expected = collectionBuilderOf(Object.class)
                .with(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"), age(25), location("USA"))
                .build();

        // When
        Collection<Object> actual = materialize(nonuple);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldBeMappableUsingMapperOnFirstPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Integer, String> mapper = new Mapper<Integer, String>() {
            @Override public String map(Integer input) {
                return String.valueOf(input);
            }
        };
        Nonuple<String, String, Boolean, Double, Long, Name, Colour, Age, Location> expected =
                tuple("5", "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));

        // When
        Nonuple<String, String, Boolean, Double, Long, Name, Colour, Age, Location> actual = nonuple.mapFirst(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFirst() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Integer, String> mapper = null;

        // When
        nonuple.mapFirst(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFirstPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Integer, String> function = new UnaryFunction<Integer, String>() {
            @Override public String call(Integer input) {
                return String.valueOf(input);
            }
        };
        Nonuple<String, String, Boolean, Double, Long, Name, Colour, Age, Location> expected =
                tuple("5", "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));

        // When
        Nonuple<String, String, Boolean, Double, Long, Name, Colour, Age, Location> actual = nonuple.mapFirst(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFirst() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Integer, String> function = null;

        // When
        nonuple.mapFirst(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSecondPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<String, Integer> mapper = new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return input.length();
            }
        };
        Nonuple<Integer, Integer, Boolean, Double, Long, Name, Colour, Age, Location> expected =
                tuple(5, 4, true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));

        // When
        Nonuple<Integer, Integer, Boolean, Double, Long, Name, Colour, Age, Location> actual = nonuple.mapSecond(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSecond() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<String, Integer> mapper = null;

        // When
        nonuple.mapSecond(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSecondPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<String, Integer> function = new UnaryFunction<String, Integer>() {
            @Override public Integer call(String input) {
                return input.length();
            }
        };
        Nonuple<Integer, Integer, Boolean, Double, Long, Name, Colour, Age, Location> expected =
                tuple(5, 4, true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));

        // When
        Nonuple<Integer, Integer, Boolean, Double, Long, Name, Colour, Age, Location> actual = nonuple.mapSecond(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSecond() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<String, Integer> function = null;

        // When
        nonuple.mapSecond(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnThirdPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Boolean, String> mapper = new Mapper<Boolean, String>() {
            @Override public String map(Boolean input) {
                return input.toString();
            }
        };
        Nonuple<Integer, String, String, Double, Long, Name, Colour, Age, Location> expected =
                tuple(5, "Five", "true", 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));

        // When
        Nonuple<Integer, String, String, Double, Long, Name, Colour, Age, Location> actual = nonuple.mapThird(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapThird() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Boolean, String> mapper = null;

        // When
        nonuple.mapThird(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnThirdPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Boolean, String> function = new UnaryFunction<Boolean, String>() {
            @Override public String call(Boolean input) {
                return input.toString();
            }
        };
        Nonuple<Integer, String, String, Double, Long, Name, Colour, Age, Location> expected =
                tuple(5, "Five", "true", 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));

        // When
        Nonuple<Integer, String, String, Double, Long, Name, Colour, Age, Location> actual = nonuple.mapThird(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapThird() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Boolean, String> function = null;

        // When
        nonuple.mapThird(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnFourthPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Double, String> mapper = new Mapper<Double, String>() {
            @Override public String map(Double input) {
                return input.toString();
            }
        };
        Nonuple<Integer, String, Boolean, String, Long, Name, Colour, Age, Location> expected =
                tuple(5, "Five", true, "3.6", 2L, name("Ellen"), colour("Green"), age(20), location("France"));

        // When
        Nonuple<Integer, String, Boolean, String, Long, Name, Colour, Age, Location> actual = nonuple.mapFourth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFourth() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Double, String> mapper = null;

        // When
        nonuple.mapFourth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFourthPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Double, String> function = new UnaryFunction<Double, String>() {
            @Override public String call(Double input) {
                return input.toString();
            }
        };
        Nonuple<Integer, String, Boolean, String, Long, Name, Colour, Age, Location> expected =
                tuple(5, "Five", true, "3.6", 2L, name("Ellen"), colour("Green"), age(20), location("France"));

        // When
        Nonuple<Integer, String, Boolean, String, Long, Name, Colour, Age, Location> actual = nonuple.mapFourth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFourth() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Double, String> function = null;

        // When
        nonuple.mapFourth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnFifthPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Long, String> mapper = new Mapper<Long, String>() {
            @Override public String map(Long input) {
                return input.toString();
            }
        };
        Nonuple<Integer, String, Boolean, Double, String, Name, Colour, Age, Location> expected =
                tuple(5, "Five", true, 3.6, "2", name("Ellen"), colour("Green"), age(20), location("France"));

        // When
        Nonuple<Integer, String, Boolean, Double, String, Name, Colour, Age, Location> actual = nonuple.mapFifth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFifth() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Long, String> mapper = null;

        // When
        nonuple.mapFifth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFifthPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Long, String> function = new UnaryFunction<Long, String>() {
            @Override public String call(Long input) {
                return input.toString();
            }
        };
        Nonuple<Integer, String, Boolean, Double, String, Name, Colour, Age, Location> expected =
                tuple(5, "Five", true, 3.6, "2", name("Ellen"), colour("Green"), age(20), location("France"));

        // When
        Nonuple<Integer, String, Boolean, Double, String, Name, Colour, Age, Location> actual = nonuple.mapFifth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFifth() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Long, String> function = null;

        // When
        nonuple.mapFifth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSixthPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Name, String> mapper = new Mapper<Name, String>() {
            @Override public String map(Name input) {
                return input.getValue();
            }
        };
        Nonuple<Integer, String, Boolean, Double, Long, String, Colour, Age, Location> expected =
                tuple(5, "Five", true, 3.6, 2L, "Ellen", colour("Green"), age(20), location("France"));

        // When
        Nonuple<Integer, String, Boolean, Double, Long, String, Colour, Age, Location> actual = nonuple.mapSixth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSixth() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Name, String> mapper = null;

        // When
        nonuple.mapSixth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSixthPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Name, String> function = new UnaryFunction<Name, String>() {
            @Override public String call(Name input) {
                return input.getValue();
            }
        };
        Nonuple<Integer, String, Boolean, Double, Long, String, Colour, Age, Location> expected =
                tuple(5, "Five", true, 3.6, 2L, "Ellen", colour("Green"), age(20), location("France"));

        // When
        Nonuple<Integer, String, Boolean, Double, Long, String, Colour, Age, Location> actual = nonuple.mapSixth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSixth() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Name, String> function = null;

        // When
        nonuple.mapSixth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSeventhPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Colour, String> mapper = new Mapper<Colour, String>() {
            @Override public String map(Colour input) {
                return input.getValue();
            }
        };
        Nonuple<Integer, String, Boolean, Double, Long, Name, String, Age, Location> expected =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), "Green", age(20), location("France"));

        // When
        Nonuple<Integer, String, Boolean, Double, Long, Name, String, Age, Location> actual = nonuple.mapSeventh(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSeventh() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Colour, String> mapper = null;

        // When
        nonuple.mapSeventh(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSeventhPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Colour, String> function = new UnaryFunction<Colour, String>() {
            @Override public String call(Colour input) {
                return input.getValue();
            }
        };
        Nonuple<Integer, String, Boolean, Double, Long, Name, String, Age, Location> expected =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), "Green", age(20), location("France"));

        // When
        Nonuple<Integer, String, Boolean, Double, Long, Name, String, Age, Location> actual = nonuple.mapSeventh(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSeventh() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Colour, String> function = null;

        // When
        nonuple.mapSeventh(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnEighthPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Age, Integer> mapper = new Mapper<Age, Integer>() {
            @Override public Integer map(Age input) {
                return input.getValue();
            }
        };
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Integer, Location> expected =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), 20, location("France"));

        // When
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Integer, Location> actual = nonuple.mapEighth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapEighth() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Age, Integer> mapper = null;

        // When
        nonuple.mapEighth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnEighthPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Age, Integer> function = new UnaryFunction<Age, Integer>() {
            @Override public Integer call(Age input) {
                return input.getValue();
            }
        };
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Integer, Location> expected =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), 20, location("France"));

        // When
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Integer, Location> actual = nonuple.mapEighth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapEighth() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Age, Integer> function = null;

        // When
        nonuple.mapEighth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnNinthPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Location, String> mapper = new Mapper<Location, String>() {
            @Override public String map(Location input) {
                return input.getValue();
            }
        };
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, String> expected =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), "France");

        // When
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, String> actual = nonuple.mapNinth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapNinth() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        Mapper<Location, String> mapper = null;

        // When
        nonuple.mapNinth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnNinthPosition() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Location, String> function = new UnaryFunction<Location, String>() {
            @Override public String call(Location input) {
                return input.getValue();
            }
        };
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, String> expected =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), "France");

        // When
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, String> actual = nonuple.mapNinth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapNinth() throws Exception {
        // Given
        Nonuple<Integer, String, Boolean, Double, Long, Name, Colour, Age, Location> nonuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20), location("France"));
        UnaryFunction<Location, String> function = null;

        // When
        nonuple.mapNinth(function);

        // Then a NullPointerException is thrown
    }
}
