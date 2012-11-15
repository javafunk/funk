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
import org.javafunk.funk.behaviours.ordinals.Second;
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

public class PairTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> pair = tuple(5, "Five");

        // When
        Integer first = pair.getFirst();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> pair = tuple(5, "Five");

        // When
        String second = pair.getSecond();

        // Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstAndSecondInToString() {
        // Given
        Pair<Integer, String> pair = tuple(5, "Five");

        // When
        String stringRepresentation = pair.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five)"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstAndSecond() {
        // Given
        Pair<Integer, String> pair1 = tuple(5, "Five");
        Pair<Integer, String> pair2 = tuple(5, "Five");

        // When
        Boolean isEqual = pair1.equals(pair2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Pair<Integer, String> pair1 = tuple(5, "Five");
        Pair<Integer, String> pair2 = tuple(10, "Five");

        // When
        Boolean isEqual = pair1.equals(pair2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Pair<Integer, String> pair1 = tuple(5, "Five");
        Pair<Integer, String> pair2 = tuple(5, "Ten");

        // When
        Boolean isEqual = pair1.equals(pair2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirstAndSecond() {
        // Given
        Pair<Integer, String> pair1 = tuple(5, "Five");
        Pair<Integer, String> pair2 = tuple(10, "Ten");

        // When
        Boolean isEqual = pair1.equals(pair2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Pair<Integer, String> pair = tuple(5, "Five");
        Collection<Object> expected = collectionBuilderOf(Object.class)
                .with(5, "Five")
                .build();

        // When
        Collection<Object> actual = materialize(pair);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldBeMappableUsingMapperOnFirstPosition() throws Exception {
        // Given
        Pair<Integer, String> pair = tuple(5, "Five");
        Mapper<Integer, String> mapper = new Mapper<Integer, String>() {
            @Override public String map(Integer input) {
                return String.valueOf(input);
            }
        };
        Pair<String, String> expected = tuple("5", "Five");

        // When
        Pair<String, String> actual = pair.mapFirst(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFirst() throws Exception {
        // Given
        Pair<Integer, String> pair = tuple(5, "Five");
        Mapper<Integer, String> mapper = null;

        // When
        pair.mapFirst(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFirstPosition() throws Exception {
        // Given
        Pair<Integer, String> pair = tuple(5, "Five");
        UnaryFunction<Integer, String> function = new UnaryFunction<Integer, String>() {
            @Override public String call(Integer input) {
                return String.valueOf(input);
            }
        };
        Pair<String, String> expected = tuple("5", "Five");

        // When
        Pair<String, String> actual = pair.mapFirst(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFirst() throws Exception {
        // Given
        Pair<Integer, String> pair = tuple(5, "Five");
        UnaryFunction<Integer, String> function = null;

        // When
        pair.mapFirst(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSecondPosition() throws Exception {
        // Given
        Pair<Integer, String> pair = tuple(5, "Five");
        Mapper<String, Integer> mapper = new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return input.length();
            }
        };
        Pair<Integer, Integer> expected = tuple(5, 4);

        // When
        Pair<Integer, Integer> actual = pair.mapSecond(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSecond() throws Exception {
        // Given
        Pair<Integer, String> pair = tuple(5, "Five");
        Mapper<String, Integer> mapper = null;

        // When
        pair.mapSecond(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSecondPosition() throws Exception {
        // Given
        Pair<Integer, String> pair = tuple(5, "Five");
        UnaryFunction<String, Integer> function = new UnaryFunction<String, Integer>() {
            @Override public Integer call(String input) {
                return input.length();
            }
        };
        Pair<Integer, Integer> expected = tuple(5, 4);

        // When
        Pair<Integer, Integer> actual = pair.mapSecond(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSecond() throws Exception {
        // Given
        Pair<Integer, String> pair = tuple(5, "Five");
        UnaryFunction<String, Integer> function = null;

        // When
        pair.mapSecond(function);

        // Then a NullPointerException is thrown
    }
}
