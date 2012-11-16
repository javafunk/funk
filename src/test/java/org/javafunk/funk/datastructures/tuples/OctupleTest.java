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
import org.javafunk.funk.behaviours.ordinals.Second;
import org.javafunk.funk.behaviours.ordinals.Seventh;
import org.javafunk.funk.behaviours.ordinals.Sixth;
import org.javafunk.funk.behaviours.ordinals.Third;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;
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
import static org.javafunk.funk.datastructures.tuples.Octuple.octuple;
import static org.javafunk.funk.testclasses.Age.age;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Name.name;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class OctupleTest {
    @Test
    public void shouldConstructAOctupleWithTheSpecifiedValues() throws Exception {
        // Given
        String first = "5";
        Integer second = 4;
        Long third = 5L;
        Character fourth = 'a';
        Boolean fifth = true;
        Double sixth = 3.6;
        Name seventh = name("Anna");
        Age eighth = age(20);
        Octuple<String, Integer, Long, Character, Boolean, Double, Name, Age> expected =
                new Octuple<String, Integer, Long, Character, Boolean, Double, Name, Age>(
                        first, second, third, fourth, fifth, sixth, seventh, eighth);

        // When
        Octuple<String, Integer, Long, Character, Boolean, Double, Name, Age> actual =
                octuple(first, second, third, fourth, fifth, sixth, seventh, eighth);

        // Then
        assertThat(actual, is(expected));
    }

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

    @Test
    public void shouldBeMappableUsingMapperOnFirstPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Integer, String> mapper = new Mapper<Integer, String>() {
            @Override public String map(Integer input) {
                return String.valueOf(input);
            }
        };
        Octuple<String, String, Boolean, Double, Long, Name, Colour, Age> expected =
                tuple("5", "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));

        // When
        Octuple<String, String, Boolean, Double, Long, Name, Colour, Age> actual = octuple.mapFirst(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFirst() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Integer, String> mapper = null;

        // When
        octuple.mapFirst(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFirstPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Integer, String> function = new UnaryFunction<Integer, String>() {
            @Override public String call(Integer input) {
                return String.valueOf(input);
            }
        };
        Octuple<String, String, Boolean, Double, Long, Name, Colour, Age> expected =
                tuple("5", "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));

        // When
        Octuple<String, String, Boolean, Double, Long, Name, Colour, Age> actual = octuple.mapFirst(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFirst() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Integer, String> function = null;

        // When
        octuple.mapFirst(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSecondPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<String, Integer> mapper = new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return input.length();
            }
        };
        Octuple<Integer, Integer, Boolean, Double, Long, Name, Colour, Age> expected =
                tuple(5, 4, true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));

        // When
        Octuple<Integer, Integer, Boolean, Double, Long, Name, Colour, Age> actual = octuple.mapSecond(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSecond() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<String, Integer> mapper = null;

        // When
        octuple.mapSecond(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSecondPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<String, Integer> function = new UnaryFunction<String, Integer>() {
            @Override public Integer call(String input) {
                return input.length();
            }
        };
        Octuple<Integer, Integer, Boolean, Double, Long, Name, Colour, Age> expected =
                tuple(5, 4, true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));

        // When
        Octuple<Integer, Integer, Boolean, Double, Long, Name, Colour, Age> actual = octuple.mapSecond(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSecond() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<String, Integer> function = null;

        // When
        octuple.mapSecond(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnThirdPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Boolean, String> mapper = new Mapper<Boolean, String>() {
            @Override public String map(Boolean input) {
                return input.toString();
            }
        };
        Octuple<Integer, String, String, Double, Long, Name, Colour, Age> expected =
                tuple(5, "Five", "true", 3.6, 2L, name("Ellen"), colour("Green"), age(20));

        // When
        Octuple<Integer, String, String, Double, Long, Name, Colour, Age> actual = octuple.mapThird(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapThird() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Boolean, String> mapper = null;

        // When
        octuple.mapThird(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnThirdPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Boolean, String> function = new UnaryFunction<Boolean, String>() {
            @Override public String call(Boolean input) {
                return input.toString();
            }
        };
        Octuple<Integer, String, String, Double, Long, Name, Colour, Age> expected =
                tuple(5, "Five", "true", 3.6, 2L, name("Ellen"), colour("Green"), age(20));

        // When
        Octuple<Integer, String, String, Double, Long, Name, Colour, Age> actual = octuple.mapThird(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapThird() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Boolean, String> function = null;

        // When
        octuple.mapThird(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnFourthPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Double, String> mapper = new Mapper<Double, String>() {
            @Override public String map(Double input) {
                return input.toString();
            }
        };
        Octuple<Integer, String, Boolean, String, Long, Name, Colour, Age> expected =
                tuple(5, "Five", true, "3.6", 2L, name("Ellen"), colour("Green"), age(20));

        // When
        Octuple<Integer, String, Boolean, String, Long, Name, Colour, Age> actual = octuple.mapFourth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFourth() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Double, String> mapper = null;

        // When
        octuple.mapFourth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFourthPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Double, String> function = new UnaryFunction<Double, String>() {
            @Override public String call(Double input) {
                return input.toString();
            }
        };
        Octuple<Integer, String, Boolean, String, Long, Name, Colour, Age> expected =
                tuple(5, "Five", true, "3.6", 2L, name("Ellen"), colour("Green"), age(20));

        // When
        Octuple<Integer, String, Boolean, String, Long, Name, Colour, Age> actual = octuple.mapFourth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFourth() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Double, String> function = null;

        // When
        octuple.mapFourth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnFifthPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Long, String> mapper = new Mapper<Long, String>() {
            @Override public String map(Long input) {
                return input.toString();
            }
        };
        Octuple<Integer, String, Boolean, Double, String, Name, Colour, Age> expected =
                tuple(5, "Five", true, 3.6, "2", name("Ellen"), colour("Green"), age(20));

        // When
        Octuple<Integer, String, Boolean, Double, String, Name, Colour, Age> actual = octuple.mapFifth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFifth() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Long, String> mapper = null;

        // When
        octuple.mapFifth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFifthPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Long, String> function = new UnaryFunction<Long, String>() {
            @Override public String call(Long input) {
                return input.toString();
            }
        };
        Octuple<Integer, String, Boolean, Double, String, Name, Colour, Age> expected =
                tuple(5, "Five", true, 3.6, "2", name("Ellen"), colour("Green"), age(20));

        // When
        Octuple<Integer, String, Boolean, Double, String, Name, Colour, Age> actual = octuple.mapFifth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFifth() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Long, String> function = null;

        // When
        octuple.mapFifth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSixthPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Name, String> mapper = new Mapper<Name, String>() {
            @Override public String map(Name input) {
                return input.getValue();
            }
        };
        Octuple<Integer, String, Boolean, Double, Long, String, Colour, Age> expected =
                tuple(5, "Five", true, 3.6, 2L, "Ellen", colour("Green"), age(20));

        // When
        Octuple<Integer, String, Boolean, Double, Long, String, Colour, Age> actual = octuple.mapSixth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSixth() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Name, String> mapper = null;

        // When
        octuple.mapSixth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSixthPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Name, String> function = new UnaryFunction<Name, String>() {
            @Override public String call(Name input) {
                return input.getValue();
            }
        };
        Octuple<Integer, String, Boolean, Double, Long, String, Colour, Age> expected =
                tuple(5, "Five", true, 3.6, 2L, "Ellen", colour("Green"), age(20));

        // When
        Octuple<Integer, String, Boolean, Double, Long, String, Colour, Age> actual = octuple.mapSixth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSixth() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Name, String> function = null;

        // When
        octuple.mapSixth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSeventhPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Colour, String> mapper = new Mapper<Colour, String>() {
            @Override public String map(Colour input) {
                return input.getValue();
            }
        };
        Octuple<Integer, String, Boolean, Double, Long, Name, String, Age> expected =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), "Green", age(20));

        // When
        Octuple<Integer, String, Boolean, Double, Long, Name, String, Age> actual = octuple.mapSeventh(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSeventh() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Colour, String> mapper = null;

        // When
        octuple.mapSeventh(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSeventhPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Colour, String> function = new UnaryFunction<Colour, String>() {
            @Override public String call(Colour input) {
                return input.getValue();
            }
        };
        Octuple<Integer, String, Boolean, Double, Long, Name, String, Age> expected =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), "Green", age(20));

        // When
        Octuple<Integer, String, Boolean, Double, Long, Name, String, Age> actual = octuple.mapSeventh(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSeventh() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Colour, String> function = null;

        // When
        octuple.mapSeventh(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnEighthPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Age, Integer> mapper = new Mapper<Age, Integer>() {
            @Override public Integer map(Age input) {
                return input.getValue();
            }
        };
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Integer> expected =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), 20);

        // When
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Integer> actual = octuple.mapEighth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapEighth() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        Mapper<Age, Integer> mapper = null;

        // When
        octuple.mapEighth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnEighthPosition() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Age, Integer> function = new UnaryFunction<Age, Integer>() {
            @Override public Integer call(Age input) {
                return input.getValue();
            }
        };
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Integer> expected =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), 20);

        // When
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Integer> actual = octuple.mapEighth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapEighth() throws Exception {
        // Given
        Octuple<Integer, String, Boolean, Double, Long, Name, Colour, Age> octuple =
                tuple(5, "Five", true, 3.6, 2L, name("Ellen"), colour("Green"), age(20));
        UnaryFunction<Age, Integer> function = null;

        // When
        octuple.mapEighth(function);

        // Then a NullPointerException is thrown
    }
}
