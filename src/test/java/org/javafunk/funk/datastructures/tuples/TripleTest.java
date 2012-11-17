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
import static org.javafunk.funk.datastructures.tuples.Triple.triple;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class TripleTest {
    @Test
    public void shouldConstructATripleWithTheSpecifiedValues() throws Exception {
        // Given
        String first = "5";
        Integer second = 4;
        Long third = 5L;
        Triple<String, Integer, Long> expected = new Triple<String, Integer, Long>(first, second, third);

        // When
        Triple<String, Integer, Long> actual = triple(first, second, third);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> triple = tuple(5, "Five", true);

        // When
        Integer first = triple.getFirst();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldReturnTheSecondObject() {
        // Given
        Second<String> triple = tuple(5, "Five", true);

        // When
        String second = triple.getSecond();

        //Then
        assertThat(second, is("Five"));
    }

    @Test
    public void shouldReturnTheThirdObject() {
        // Given
        Third<Boolean> triple = tuple(5, "Five", true);

        // When
        Boolean third = triple.getThird();

        //Then
        assertThat(third, is(true));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstSecondAndThirdInToString() {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);

        // When
        String stringRepresentation = triple.toString();

        // Then
        assertThat(stringRepresentation, is("(5, Five, true)"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirstSecondAndThird() {
        // Given
        Triple<Integer, String, Boolean> triple1 = tuple(5, "Five", true);
        Triple<Integer, String, Boolean> triple2 = tuple(5, "Five", true);

        // When
        Boolean isEqual = triple1.equals(triple2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Triple<Integer, String, Boolean> triple1 = tuple(5, "Five", true);
        Triple<Integer, String, Boolean> triple2 = tuple(10, "Five", true);

        // When
        Boolean isEqual = triple1.equals(triple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentSecond() {
        // Given
        Triple<Integer, String, Boolean> triple1 = tuple(5, "Five", true);
        Triple<Integer, String, Boolean> triple2 = tuple(5, "Ten", true);

        // When
        Boolean isEqual = triple1.equals(triple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentThird() {
        // Given
        Triple<Integer, String, Boolean> triple1 = tuple(5, "Five", true);
        Triple<Integer, String, Boolean> triple2 = tuple(5, "Five", false);

        // When
        Boolean isEqual = triple1.equals(triple2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        Collection<Object> expected = collectionBuilderOf(Object.class)
                .with(5, "Five", true)
                .build();

        // When
        Collection<Object> actual = materialize(triple);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldBeMappableUsingMapperOnFirstPosition() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        Mapper<Integer, String> mapper = new Mapper<Integer, String>() {
            @Override public String map(Integer input) {
                return String.valueOf(input);
            }
        };
        Triple<String, String, Boolean> expected = tuple("5", "Five", true);

        // When
        Triple<String, String, Boolean> actual = triple.mapFirst(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapFirst() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        Mapper<Integer, String> mapper = null;

        // When
        triple.mapFirst(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnFirstPosition() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        UnaryFunction<Integer, String> function = new UnaryFunction<Integer, String>() {
            @Override public String call(Integer input) {
                return String.valueOf(input);
            }
        };
        Triple<String, String, Boolean> expected = tuple("5", "Five", true);

        // When
        Triple<String, String, Boolean> actual = triple.mapFirst(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapFirst() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        UnaryFunction<Integer, String> function = null;

        // When
        triple.mapFirst(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnSecondPosition() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        Mapper<String, Integer> mapper = new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return input.length();
            }
        };
        Triple<Integer, Integer, Boolean> expected = tuple(5, 4, true);

        // When
        Triple<Integer, Integer, Boolean> actual = triple.mapSecond(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapSecond() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        Mapper<String, Integer> mapper = null;

        // When
        triple.mapSecond(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnSecondPosition() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        UnaryFunction<String, Integer> function = new UnaryFunction<String, Integer>() {
            @Override public Integer call(String input) {
                return input.length();
            }
        };
        Triple<Integer, Integer, Boolean> expected = tuple(5, 4, true);

        // When
        Triple<Integer, Integer, Boolean> actual = triple.mapSecond(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapSecond() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        UnaryFunction<String, Integer> function = null;

        // When
        triple.mapSecond(function);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingMapperOnThirdPosition() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        Mapper<Boolean, String> mapper = new Mapper<Boolean, String>() {
            @Override public String map(Boolean input) {
                return input.toString();
            }
        };
        Triple<Integer, String, String> expected = tuple(5, "Five", "true");

        // When
        Triple<Integer, String, String> actual = triple.mapThird(mapper);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullMapperPassedToMapThird() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        Mapper<Boolean, String> mapper = null;

        // When
        triple.mapThird(mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldBeMappableUsingUnaryFunctionOnThirdPosition() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        UnaryFunction<Boolean, String> function = new UnaryFunction<Boolean, String>() {
            @Override public String call(Boolean input) {
                return input.toString();
            }
        };
        Triple<Integer, String, String> expected = tuple(5, "Five", "true");

        // When
        Triple<Integer, String, String> actual = triple.mapThird(function);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionPassedToMapThird() throws Exception {
        // Given
        Triple<Integer, String, Boolean> triple = tuple(5, "Five", true);
        UnaryFunction<Boolean, String> function = null;

        // When
        triple.mapThird(function);

        // Then a NullPointerException is thrown
    }
}
