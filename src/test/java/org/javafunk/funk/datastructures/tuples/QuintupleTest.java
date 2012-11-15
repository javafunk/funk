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
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;
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

    @Test
    public void shouldBeMappableUsingMapperOnFirstPosition() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        Mapper<Integer, String> mapper = new Mapper<Integer, String>() {
            @Override public String map(Integer input) {
                return String.valueOf(input);
            }
        };
        Quintuple<String, String, Boolean, Double, Long> expected = tuple("5", "Five", true, 3.6, 2L);

        // When
        Quintuple<String, String, Boolean, Double, Long> actual = quintuple.mapFirst(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFirst() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        Mapper<Integer, String> mapper = null;

        // When
        quintuple.mapFirst(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFirstPosition() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        UnaryFunction<Integer, String> function = new UnaryFunction<Integer, String>() {
            @Override public String call(Integer input) {
                return String.valueOf(input);
            }
        };
        Quintuple<String, String, Boolean, Double, Long> expected = tuple("5", "Five", true, 3.6, 2L);

        // When
        Quintuple<String, String, Boolean, Double, Long> actual = quintuple.mapFirst(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFirst() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        UnaryFunction<Integer, String> function = null;

        // When
        quintuple.mapFirst(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSecondPosition() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        Mapper<String, Integer> mapper = new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return input.length();
            }
        };
        Quintuple<Integer, Integer, Boolean, Double, Long> expected = tuple(5, 4, true, 3.6, 2L);

        // When
        Quintuple<Integer, Integer, Boolean, Double, Long> actual = quintuple.mapSecond(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSecond() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        Mapper<String, Integer> mapper = null;

        // When
        quintuple.mapSecond(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSecondPosition() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        UnaryFunction<String, Integer> function = new UnaryFunction<String, Integer>() {
            @Override public Integer call(String input) {
                return input.length();
            }
        };
        Quintuple<Integer, Integer, Boolean, Double, Long> expected = tuple(5, 4, true, 3.6, 2L);

        // When
        Quintuple<Integer, Integer, Boolean, Double, Long> actual = quintuple.mapSecond(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSecond() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        UnaryFunction<String, Integer> function = null;

        // When
        quintuple.mapSecond(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnThirdPosition() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        Mapper<Boolean, String> mapper = new Mapper<Boolean, String>() {
            @Override public String map(Boolean input) {
                return input.toString();
            }
        };
        Quintuple<Integer, String, String, Double, Long> expected = tuple(5, "Five", "true", 3.6, 2L);

        // When
        Quintuple<Integer, String, String, Double, Long> actual = quintuple.mapThird(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapThird() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        Mapper<Boolean, String> mapper = null;

        // When
        quintuple.mapThird(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnThirdPosition() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        UnaryFunction<Boolean, String> function = new UnaryFunction<Boolean, String>() {
            @Override public String call(Boolean input) {
                return input.toString();
            }
        };
        Quintuple<Integer, String, String, Double, Long> expected = tuple(5, "Five", "true", 3.6, 2L);

        // When
        Quintuple<Integer, String, String, Double, Long> actual = quintuple.mapThird(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapThird() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        UnaryFunction<Boolean, String> function = null;

        // When
        quintuple.mapThird(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnFourthPosition() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        Mapper<Double, String> mapper = new Mapper<Double, String>() {
            @Override public String map(Double input) {
                return input.toString();
            }
        };
        Quintuple<Integer, String, Boolean, String, Long> expected = tuple(5, "Five", true, "3.6", 2L);

        // When
        Quintuple<Integer, String, Boolean, String, Long> actual = quintuple.mapFourth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFourth() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        Mapper<Double, String> mapper = null;

        // When
        quintuple.mapFourth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFourthPosition() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        UnaryFunction<Double, String> function = new UnaryFunction<Double, String>() {
            @Override public String call(Double input) {
                return input.toString();
            }
        };
        Quintuple<Integer, String, Boolean, String, Long> expected = tuple(5, "Five", true, "3.6", 2L);

        // When
        Quintuple<Integer, String, Boolean, String, Long> actual = quintuple.mapFourth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFourth() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        UnaryFunction<Double, String> function = null;

        // When
        quintuple.mapFourth(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnFifthPosition() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        Mapper<Long, String> mapper = new Mapper<Long, String>() {
            @Override public String map(Long input) {
                return input.toString();
            }
        };
        Quintuple<Integer, String, Boolean, Double, String> expected = tuple(5, "Five", true, 3.6, "2");

        // When
        Quintuple<Integer, String, Boolean, Double, String> actual = quintuple.mapFifth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFifth() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        Mapper<Long, String> mapper = null;

        // When
        quintuple.mapFifth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFifthPosition() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        UnaryFunction<Long, String> function = new UnaryFunction<Long, String>() {
            @Override public String call(Long input) {
                return input.toString();
            }
        };
        Quintuple<Integer, String, Boolean, Double, String> expected = tuple(5, "Five", true, 3.6, "2");

        // When
        Quintuple<Integer, String, Boolean, Double, String> actual = quintuple.mapFifth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFifth() throws Exception {
        // Given
        Quintuple<Integer, String, Boolean, Double, Long> quintuple = tuple(5, "Five", true, 3.6, 2L);
        UnaryFunction<Long, String> function = null;

        // When
        quintuple.mapFifth(function);

        // Then a NullPointerException is thrown
    }
}
