/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.First;
import org.javafunk.funk.behaviours.ordinals.Fourth;
import org.javafunk.funk.behaviours.ordinals.Second;
import org.javafunk.funk.behaviours.ordinals.Third;
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

public class QuadrupleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> quadruple = tuple(5, "Five", true, 3.6);

        // When
        Integer first = quadruple.getFirst();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> quadruple = tuple(5, "Five", true, 3.6);

        // When
        String second = quadruple.getSecond();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> quadruple = tuple(5, "Five", true, 3.6);

        // When
        Boolean third = quadruple.getThird();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldReturnTheFourthObject() {
        // Given
        Fourth<Double> quadruple = tuple(5, "Five", true, 3.6);

        // When
        Double fourth = quadruple.getFourth();

        //Then
        assertThat(fourth, is(3.6));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstSecondThirdAndFourthInToString() {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);

        // When
        String stringRepresentation = quadruple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true, 3.6)"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondThirdAndFourth() {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple1 = tuple(5, "Five", true, 3.6);
        Quadruple<Integer, String, Boolean, Double> quadruple2 = tuple(5, "Five", true, 3.6);

        // When
        Boolean isEqual = quadruple1.equals(quadruple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple1 = tuple(5, "Five", true, 3.6);
        Quadruple<Integer, String, Boolean, Double> quadruple2 = tuple(10, "Five", true, 3.6);

        // When
        Boolean isEqual = quadruple1.equals(quadruple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple1 = tuple(5, "Five", true, 3.6);
        Quadruple<Integer, String, Boolean, Double> quadruple2 = tuple(5, "Ten", true, 3.6);

        // When
        Boolean isEqual = quadruple1.equals(quadruple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple1 = tuple(5, "Five", true, 3.6);
        Quadruple<Integer, String, Boolean, Double> quadruple2 = tuple(5, "Five", false, 3.6);

        // When
        Boolean isEqual = quadruple1.equals(quadruple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFourth() {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple1 = tuple(5, "Five", true, 3.6);
        Quadruple<Integer, String, Boolean, Double> quadruple2 = tuple(5, "Five", true, 4.2);

        // When
        Boolean isEqual = quadruple1.equals(quadruple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        Collection<Object> expected = collectionBuilderOf(Object.class)
                .with(5, "Five", true, 3.6)
                .build();

        // When
        Collection<Object> actual = materialize(quadruple);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldBeMappableUsingMapperOnFirstPosition() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        Mapper<Integer, String> mapper = new Mapper<Integer, String>() {
            @Override public String map(Integer input) {
                return String.valueOf(input);
            }
        };
        Quadruple<String, String, Boolean, Double> expected = tuple("5", "Five", true, 3.6);

        // When
        Quadruple<String, String, Boolean, Double> actual = quadruple.mapFirst(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFirst() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        Mapper<Integer, String> mapper = null;

        // When
        quadruple.mapFirst(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFirstPosition() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        UnaryFunction<Integer, String> function = new UnaryFunction<Integer, String>() {
            @Override public String call(Integer input) {
                return String.valueOf(input);
            }
        };
        Quadruple<String, String, Boolean, Double> expected = tuple("5", "Five", true, 3.6);

        // When
        Quadruple<String, String, Boolean, Double> actual = quadruple.mapFirst(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFirst() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        UnaryFunction<Integer, String> function = null;

        // When
        quadruple.mapFirst(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSecondPosition() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        Mapper<String, Integer> mapper = new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return input.length();
            }
        };
        Quadruple<Integer, Integer, Boolean, Double> expected = tuple(5, 4, true, 3.6);

        // When
        Quadruple<Integer, Integer, Boolean, Double> actual = quadruple.mapSecond(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSecond() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        Mapper<String, Integer> mapper = null;

        // When
        quadruple.mapSecond(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSecondPosition() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        UnaryFunction<String, Integer> function = new UnaryFunction<String, Integer>() {
            @Override public Integer call(String input) {
                return input.length();
            }
        };
        Quadruple<Integer, Integer, Boolean, Double> expected = tuple(5, 4, true, 3.6);

        // When
        Quadruple<Integer, Integer, Boolean, Double> actual = quadruple.mapSecond(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSecond() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        UnaryFunction<String, Integer> function = null;

        // When
        quadruple.mapSecond(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnThirdPosition() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        Mapper<Boolean, String> mapper = new Mapper<Boolean, String>() {
            @Override public String map(Boolean input) {
                return input.toString();
            }
        };
        Quadruple<Integer, String, String, Double> expected = tuple(5, "Five", "true", 3.6);

        // When
        Quadruple<Integer, String, String, Double> actual = quadruple.mapThird(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapThird() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        Mapper<Boolean, String> mapper = null;

        // When
        quadruple.mapThird(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnThirdPosition() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        UnaryFunction<Boolean, String> function = new UnaryFunction<Boolean, String>() {
            @Override public String call(Boolean input) {
                return input.toString();
            }
        };
        Quadruple<Integer, String, String, Double> expected = tuple(5, "Five", "true", 3.6);

        // When
        Quadruple<Integer, String, String, Double> actual = quadruple.mapThird(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapThird() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        UnaryFunction<Boolean, String> function = null;

        // When
        quadruple.mapThird(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnFourthPosition() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        Mapper<Double, String> mapper = new Mapper<Double, String>() {
            @Override public String map(Double input) {
                return input.toString();
            }
        };
        Quadruple<Integer, String, Boolean, String> expected = tuple(5, "Five", true, "3.6");

        // When
        Quadruple<Integer, String, Boolean, String> actual = quadruple.mapFourth(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFourth() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        Mapper<Double, String> mapper = null;

        // When
        quadruple.mapFourth(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFourthPosition() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        UnaryFunction<Double, String> function = new UnaryFunction<Double, String>() {
            @Override public String call(Double input) {
                return input.toString();
            }
        };
        Quadruple<Integer, String, Boolean, String> expected = tuple(5, "Five", true, "3.6");

        // When
        Quadruple<Integer, String, Boolean, String> actual = quadruple.mapFourth(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFourth() throws Exception {
        // Given
        Quadruple<Integer, String, Boolean, Double> quadruple = tuple(5, "Five", true, 3.6);
        UnaryFunction<Double, String> function = null;

        // When
        quadruple.mapFourth(function);

        // Then a NullPointerException is thrown
    }
}
