/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.Fifth;
import org.javafunk.funk.behaviours.ordinals.First;
import org.javafunk.funk.behaviours.ordinals.Fourth;
import org.javafunk.funk.behaviours.ordinals.Second;
import org.javafunk.funk.behaviours.ordinals.Seventh;
import org.javafunk.funk.behaviours.ordinals.Sixth;
import org.javafunk.funk.behaviours.ordinals.Third;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.testclasses.Colour;
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.collectionBuilderOf;
import static org.javafunk.funk.Literals.iterableBuilderOf;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.datastructures.tuples.Septuple.septuple;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Name.name;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class SeptupleTest {
    @Test
    public void shouldConstructASeptupleWithTheSpecifiedValues() throws Exception {
        // Given
        String first = "5";
        Integer second = 4;
        Long third = 5L;
        Character fourth = 'a';
        Boolean fifth = true;
        Double sixth = 3.6;
        Name seventh = name("Anna");
        Septuple<String, Integer, Long, Character, Boolean, Double, Name> expected =
                new Septuple<String, Integer, Long, Character, Boolean, Double, Name>(
                        first, second, third, fourth, fifth, sixth, seventh);

        // When
        Septuple<String, Integer, Long, Character, Boolean, Double, Name> actual =
                septuple(first, second, third, fourth, fifth, sixth, seventh);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Integer first = septuple.getFirst();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        String second = septuple.getSecond();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Boolean third = septuple.getThird();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldReturnTheFourthObject() {
        // Given
        Fourth<Double> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Double fourth = septuple.getFourth();

        // Then
        assertThat(fourth, is(3.6));
    }

    @Test
    public void shouldReturnTheFifthObject() {
        // Given
        Fifth<Long> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Long fifth = septuple.getFifth();

        // Then
        assertThat(fifth, is(23L));
    }

    @Test
    public void shouldReturnTheSixthObject() {
        // Given
        Sixth<Name> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Name sixth = septuple.getSixth();

        // Then
        assertThat(sixth, is(name("fred")));
    }

    @Test
    public void shouldReturnTheSeventhObject() {
        // Given
        Seventh<Colour> septuple = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Colour seventh = septuple.getSeventh();

        // Then
        assertThat(seventh, is(colour("blue")));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstSecondThirdAndFourthInToString() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        String stringRepresentation = septuple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true, 3.6, 23, Name[value=fred], Colour[value=blue])"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondThirdFourthFifthSixthAndSeventh() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(10, "Five", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Ten", true, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", false, 3.6, 23L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);


        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFourth() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", true, 4.2, 23L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFifth() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", true, 3.6, 36L, name("fred"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSixth() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", true, 3.6, 23L, name("james"), colour("blue"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSeventh() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple1 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple2 = tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("red"));

        // When
        Boolean isEqual = septuple1.equals(septuple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfNotASeptuple() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Iterable<Object> iterable = iterableBuilderOf(Object.class)
                .with(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"))
                .build();

        // When
        boolean firstDirection = septuple.equals(iterable);
        boolean secondDirection = iterable.equals(septuple);

        // Then
        assertThat(firstDirection, is(false));
        assertThat(secondDirection, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"));
        Collection<Object> expected = collectionBuilderOf(Object.class)
                .with(5, "Five", true, 3.6, 23L, name("fred"), colour("blue"))
                .build();

        // When
        Collection<Object> actual = materialize(septuple);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldBeMappableUsingMapperOnFirstPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Integer, String> mapper = new Mapper<Integer, String>() {
            @Override public String map(Integer input) {
                return String.valueOf(input);
            }
        };
        Septuple<String, String, Boolean, Double, Long, Name, Colour> expected =
                tuple("5", "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));

        // When
        Septuple<String, String, Boolean, Double, Long, Name, Colour> actual = septuple.mapFirst(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFirst() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Integer, String> mapper = null;

        // When
        septuple.mapFirst(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFirstPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Integer, String> function = new UnaryFunction<Integer, String>() {
            @Override public String call(Integer input) {
                return String.valueOf(input);
            }
        };
        Septuple<String, String, Boolean, Double, Long, Name, Colour> expected =
                tuple("5", "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));

        // When
        Septuple<String, String, Boolean, Double, Long, Name, Colour> actual = septuple.mapFirst(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFirst() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Integer, String> function = null;

        // When
        septuple.mapFirst(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSecondPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<String, Integer> mapper = new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return input.length();
            }
        };
        Septuple<Integer, Integer, Boolean, Double, Long, Name, Colour> expected =
                tuple(5, 4, true, 3.6, 2L, name("Ellen"), colour("Green"));

        // When
        Septuple<Integer, Integer, Boolean, Double, Long, Name, Colour> actual = septuple.mapSecond(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSecond() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<String, Integer> mapper = null;

        // When
        septuple.mapSecond(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSecondPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<String, Integer> function = new UnaryFunction<String, Integer>() {
            @Override public Integer call(String input) {
                return input.length();
            }
        };
        Septuple<Integer, Integer, Boolean, Double, Long, Name, Colour> expected =
                tuple(5, 4, true, 3.6, 2L, name("Ellen"), colour("Green"));

        // When
        Septuple<Integer, Integer, Boolean, Double, Long, Name, Colour> actual = septuple.mapSecond(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSecond() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<String, Integer> function = null;

        // When
        septuple.mapSecond(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnThirdPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Boolean, String> mapper = new Mapper<Boolean, String>() {
            @Override public String map(Boolean input) {
                return input.toString();
            }
        };
        Septuple<Integer, String, String, Double, Long, Name, Colour> expected =
                tuple(5, "Five", "true", 3.6, 2L, name("Ellen"), colour("Green"));

        // When
        Septuple<Integer, String, String, Double, Long, Name, Colour> actual = septuple.mapThird(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapThird() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Boolean, String> mapper = null;

        // When
        septuple.mapThird(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnThirdPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Boolean, String> function = new UnaryFunction<Boolean, String>() {
            @Override public String call(Boolean input) {
                return input.toString();
            }
        };
        Septuple<Integer, String, String, Double, Long, Name, Colour> expected =
                tuple(5, "Five", "true", 3.6, 2L, name("Ellen"), colour("Green"));

        // When
        Septuple<Integer, String, String, Double, Long, Name, Colour> actual = septuple.mapThird(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapThird() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Boolean, String> function = null;

        // When
        septuple.mapThird(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnFourthPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Double, String> mapper = new Mapper<Double, String>() {
            @Override public String map(Double input) {
                return input.toString();
            }
        };
        Septuple<Integer, String, Boolean, String, Long, Name, Colour> expected = tuple(5, "Five", true, "3.6", 2L, name("Ellen"), colour("Green"));

        // When
        Septuple<Integer, String, Boolean, String, Long, Name, Colour> actual = septuple.mapFourth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFourth() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Double, String> mapper = null;

        // When
        septuple.mapFourth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFourthPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Double, String> function = new UnaryFunction<Double, String>() {
            @Override public String call(Double input) {
                return input.toString();
            }
        };
        Septuple<Integer, String, Boolean, String, Long, Name, Colour> expected = tuple(5, "Five", true, "3.6", 2L, name("Ellen"), colour("Green"));

        // When
        Septuple<Integer, String, Boolean, String, Long, Name, Colour> actual = septuple.mapFourth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFourth() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Double, String> function = null;

        // When
        septuple.mapFourth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnFifthPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Long, String> mapper = new Mapper<Long, String>() {
            @Override public String map(Long input) {
                return input.toString();
            }
        };
        Septuple<Integer, String, Boolean, Double, String, Name, Colour> expected = tuple(5, "Five", true, 3.6, "2", name("Ellen"), colour("Green"));

        // When
        Septuple<Integer, String, Boolean, Double, String, Name, Colour> actual = septuple.mapFifth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFifth() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Long, String> mapper = null;

        // When
        septuple.mapFifth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFifthPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Long, String> function = new UnaryFunction<Long, String>() {
            @Override public String call(Long input) {
                return input.toString();
            }
        };
        Septuple<Integer, String, Boolean, Double, String, Name, Colour> expected = tuple(5, "Five", true, 3.6, "2", name("Ellen"), colour("Green"));

        // When
        Septuple<Integer, String, Boolean, Double, String, Name, Colour> actual = septuple.mapFifth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFifth() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Long, String> function = null;

        // When
        septuple.mapFifth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSixthPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Name, String> mapper = new Mapper<Name, String>() {
            @Override public String map(Name input) {
                return input.getValue();
            }
        };
        Septuple<Integer, String, Boolean, Double, Long, String, Colour> expected = tuple(5, "Five", true, 3.6, 2L, "Ellen", colour("Green"));

        // When
        Septuple<Integer, String, Boolean, Double, Long, String, Colour> actual = septuple.mapSixth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSixth() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Name, String> mapper = null;

        // When
        septuple.mapSixth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSixthPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Name, String> function = new UnaryFunction<Name, String>() {
            @Override public String call(Name input) {
                return input.getValue();
            }
        };
        Septuple<Integer, String, Boolean, Double, Long, String, Colour> expected = tuple(5, "Five", true, 3.6, 2L, "Ellen", colour("Green"));

        // When
        Septuple<Integer, String, Boolean, Double, Long, String, Colour> actual = septuple.mapSixth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSixth() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Name, String> function = null;

        // When
        septuple.mapSixth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSeventhPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Colour, String> mapper = new Mapper<Colour, String>() {
            @Override public String map(Colour input) {
                return input.getValue();
            }
        };
        Septuple<Integer, String, Boolean, Double, Long, Name, String> expected = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), "Green");

        // When
        Septuple<Integer, String, Boolean, Double, Long, Name, String> actual = septuple.mapSeventh(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSeventh() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        Mapper<Colour, String> mapper = null;

        // When
        septuple.mapSeventh(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSeventhPosition() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Colour, String> function = new UnaryFunction<Colour, String>() {
            @Override public String call(Colour input) {
                return input.getValue();
            }
        };
        Septuple<Integer, String, Boolean, Double, Long, Name, String> expected = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), "Green");

        // When
        Septuple<Integer, String, Boolean, Double, Long, Name, String> actual = septuple.mapSeventh(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSeventh() throws Exception {
        // Given
        Septuple<Integer, String, Boolean, Double, Long, Name, Colour> septuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"));
        UnaryFunction<Colour, String> function = null;

        // When
        septuple.mapSeventh(function);

        // Then a NullPointerException is thrown
    }
}
