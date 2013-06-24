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
import org.javafunk.funk.behaviours.ordinals.Sixth;
import org.javafunk.funk.behaviours.ordinals.Third;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.testclasses.Name;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.collectionBuilderOf;
import static org.javafunk.funk.Literals.iterableBuilderOf;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.datastructures.tuples.Sextuple.sextuple;
import static org.javafunk.funk.testclasses.Name.name;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class SextupleTest {
    @Test
    public void shouldConstructASextupleWithTheSpecifiedValues() throws Exception {
        // Given
        String first = "5";
        Integer second = 4;
        Long third = 5L;
        Character fourth = 'a';
        Boolean fifth = true;
        Double sixth = 3.6;
        Sextuple<String, Integer, Long, Character, Boolean, Double> expected =
                new Sextuple<String, Integer, Long, Character, Boolean, Double>(first, second, third, fourth, fifth, sixth);

        // When
        Sextuple<String, Integer, Long, Character, Boolean, Double> actual =
                sextuple(first, second, third, fourth, fifth, sixth);

        // Then
        assertThat(actual, is(expected));
    }

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
    public void shouldNotBeEqualIfNotASextuple() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple =
                tuple(5, "Five", true, 3.6, 23L, name("fred"));
        Iterable<Object> iterable = iterableBuilderOf(Object.class)
                .with(5, "Five", true, 3.6, 23L, name("fred"))
                .build();

        // When
        boolean firstDirection = sextuple.equals(iterable);
        boolean secondDirection = iterable.equals(sextuple);

        // Then
        assertThat(firstDirection, is(false));
        assertThat(secondDirection, is(false));
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

    @Test
    public void shouldBeMappableUsingMapperOnFirstPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<Integer, String> mapper = new Mapper<Integer, String>() {
            @Override public String map(Integer input) {
                return String.valueOf(input);
            }
        };
        Sextuple<String, String, Boolean, Double, Long, Name> expected = tuple("5", "Five", true, 3.6, 2L, name("Ellen"));

        // When
        Sextuple<String, String, Boolean, Double, Long, Name> actual = sextuple.mapFirst(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFirst() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<Integer, String> mapper = null;

        // When
        sextuple.mapFirst(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFirstPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<Integer, String> function = new UnaryFunction<Integer, String>() {
            @Override public String call(Integer input) {
                return String.valueOf(input);
            }
        };
        Sextuple<String, String, Boolean, Double, Long, Name> expected = tuple("5", "Five", true, 3.6, 2L, name("Ellen"));

        // When
        Sextuple<String, String, Boolean, Double, Long, Name> actual = sextuple.mapFirst(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFirst() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<Integer, String> function = null;

        // When
        sextuple.mapFirst(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSecondPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<String, Integer> mapper = new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return input.length();
            }
        };
        Sextuple<Integer, Integer, Boolean, Double, Long, Name> expected = tuple(5, 4, true, 3.6, 2L, name("Ellen"));

        // When
        Sextuple<Integer, Integer, Boolean, Double, Long, Name> actual = sextuple.mapSecond(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSecond() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<String, Integer> mapper = null;

        // When
        sextuple.mapSecond(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSecondPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<String, Integer> function = new UnaryFunction<String, Integer>() {
            @Override public Integer call(String input) {
                return input.length();
            }
        };
        Sextuple<Integer, Integer, Boolean, Double, Long, Name> expected = tuple(5, 4, true, 3.6, 2L, name("Ellen"));

        // When
        Sextuple<Integer, Integer, Boolean, Double, Long, Name> actual = sextuple.mapSecond(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSecond() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<String, Integer> function = null;

        // When
        sextuple.mapSecond(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnThirdPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<Boolean, String> mapper = new Mapper<Boolean, String>() {
            @Override public String map(Boolean input) {
                return input.toString();
            }
        };
        Sextuple<Integer, String, String, Double, Long, Name> expected = tuple(5, "Five", "true", 3.6, 2L, name("Ellen"));

        // When
        Sextuple<Integer, String, String, Double, Long, Name> actual = sextuple.mapThird(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapThird() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<Boolean, String> mapper = null;

        // When
        sextuple.mapThird(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnThirdPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<Boolean, String> function = new UnaryFunction<Boolean, String>() {
            @Override public String call(Boolean input) {
                return input.toString();
            }
        };
        Sextuple<Integer, String, String, Double, Long, Name> expected = tuple(5, "Five", "true", 3.6, 2L, name("Ellen"));

        // When
        Sextuple<Integer, String, String, Double, Long, Name> actual = sextuple.mapThird(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapThird() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<Boolean, String> function = null;

        // When
        sextuple.mapThird(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnFourthPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<Double, String> mapper = new Mapper<Double, String>() {
            @Override public String map(Double input) {
                return input.toString();
            }
        };
        Sextuple<Integer, String, Boolean, String, Long, Name> expected = tuple(5, "Five", true, "3.6", 2L, name("Ellen"));

        // When
        Sextuple<Integer, String, Boolean, String, Long, Name> actual = sextuple.mapFourth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFourth() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<Double, String> mapper = null;

        // When
        sextuple.mapFourth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFourthPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<Double, String> function = new UnaryFunction<Double, String>() {
            @Override public String call(Double input) {
                return input.toString();
            }
        };
        Sextuple<Integer, String, Boolean, String, Long, Name> expected = tuple(5, "Five", true, "3.6", 2L, name("Ellen"));

        // When
        Sextuple<Integer, String, Boolean, String, Long, Name> actual = sextuple.mapFourth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFourth() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<Double, String> function = null;

        // When
        sextuple.mapFourth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnFifthPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<Long, String> mapper = new Mapper<Long, String>() {
            @Override public String map(Long input) {
                return input.toString();
            }
        };
        Sextuple<Integer, String, Boolean, Double, String, Name> expected = tuple(5, "Five", true, 3.6, "2", name("Ellen"));

        // When
        Sextuple<Integer, String, Boolean, Double, String, Name> actual = sextuple.mapFifth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFifth() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<Long, String> mapper = null;

        // When
        sextuple.mapFifth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFifthPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<Long, String> function = new UnaryFunction<Long, String>() {
            @Override public String call(Long input) {
                return input.toString();
            }
        };
        Sextuple<Integer, String, Boolean, Double, String, Name> expected = tuple(5, "Five", true, 3.6, "2", name("Ellen"));

        // When
        Sextuple<Integer, String, Boolean, Double, String, Name> actual = sextuple.mapFifth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFifth() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<Long, String> function = null;

        // When
        sextuple.mapFifth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSixthPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<Name, String> mapper = new Mapper<Name, String>() {
            @Override public String map(Name input) {
                return input.getValue();
            }
        };
        Sextuple<Integer, String, Boolean, Double, Long, String> expected = tuple(5, "Five", true, 3.6, 2L, "Ellen");

        // When
        Sextuple<Integer, String, Boolean, Double, Long, String> actual = sextuple.mapSixth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSixth() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        Mapper<Name, String> mapper = null;

        // When
        sextuple.mapSixth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSixthPosition() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<Name, String> function = new UnaryFunction<Name, String>() {
            @Override public String call(Name input) {
                return input.getValue();
            }
        };
        Sextuple<Integer, String, Boolean, Double, Long, String> expected = tuple(5, "Five", true, 3.6, 2L, "Ellen");

        // When
        Sextuple<Integer, String, Boolean, Double, Long, String> actual = sextuple.mapSixth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSixth() throws Exception {
        // Given
        Sextuple<Integer, String, Boolean, Double, Long, Name> sextuple = tuple(5, "Five", true, 3.6, 2L, name("Ellen"));
        UnaryFunction<Name, String> function = null;

        // When
        sextuple.mapSixth(function);

        // Then a NullPointerException is thrown
    }
}
