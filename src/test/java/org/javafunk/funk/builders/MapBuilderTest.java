/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.builders.MapBuilder.mapBuilder;
import static org.junit.Assert.fail;

public class MapBuilderTest {
    @Test
    public void shouldAllowMapEntryInstancesToBeAddedToTheMapWithWith() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("first", 1);
        expected.put("second", 2);
        expected.put("third", 3);

        // When
        Map<String, Integer> actual = mapBuilder
                .with(mapEntryFor("first", 1), mapEntryFor("second", 2))
                .with(mapEntryFor("third", 3))
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowTuplesToBeAddedToTheMapWithWith() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("first", 1);
        expected.put("second", 2);
        expected.put("third", 3);

        // When
        Map<String, Integer> actual = mapBuilder
                .withPair(tuple("first", 1))
                .withPairs(tuple("second", 2), tuple("third", 3))
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowKeysAndValuesToBeAddedToTheMapWithWith() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("first", 1);
        expected.put("second", 2);
        expected.put("third", 3);

        // When
        Map<String, Integer> actual = mapBuilder
                .withKeyValuePairs("first", 1, "second", 2)
                .withKeyValuePair("third", 3)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowArraysOfMapEntryInstancesToBeAddedToTheMapWithWith() throws Exception {
        // Given
        Map<String, Integer> expectedMap = new HashMap<String, Integer>();
        expectedMap.put("five", 5);
        expectedMap.put("ten", 10);
        expectedMap.put("fifteen", 15);
        expectedMap.put("twenty", 20);
        @SuppressWarnings("unchecked") Map.Entry<String, Integer>[] firstMapEntryArray = new Map.Entry[]{
                mapEntryFor("five", 5),
                mapEntryFor("ten", 10)
        };
        @SuppressWarnings("unchecked") Map.Entry<String, Integer>[] secondMapEntryArray = new Map.Entry[]{
                mapEntryFor("fifteen", 15),
                mapEntryFor("twenty", 20)
        };

        // When
        Map<String, Integer> actualMap = mapBuilder(String.class, Integer.class)
                .with(firstMapEntryArray)
                .with(secondMapEntryArray)
                .build();

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldAllowArraysOfTuplesToBeAddedToTheMapWithWith() throws Exception {
        // Given
        Map<String, Integer> expectedMap = new HashMap<String, Integer>();
        expectedMap.put("five", 5);
        expectedMap.put("ten", 10);
        expectedMap.put("fifteen", 15);
        expectedMap.put("twenty", 20);
        @SuppressWarnings("unchecked") Pair<String, Integer>[] firstTupleArray = new Pair[]{
                tuple("five", 5),
                tuple("ten", 10)
        };
        @SuppressWarnings("unchecked") Pair<String, Integer>[] secondTupleArray = new Pair[]{
                tuple("fifteen", 15),
                tuple("twenty", 20)
        };

        // When
        Map<String, Integer> actualMap = mapBuilder(String.class, Integer.class)
                .withPairs(firstTupleArray)
                .withPairs(secondTupleArray)
                .build();

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldAllowIterablesOfMapEntryInstancesToBeAddedToTheMapWithWith() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expectedMap = new HashMap<String, Integer>();
        expectedMap.put("one", 1);
        expectedMap.put("two", 2);
        expectedMap.put("three", 3);
        expectedMap.put("four", 4);
        Iterable<Map.Entry<String, Integer>> firstInputIterable = listWith(mapEntryFor("one", 1), mapEntryFor("two", 2));
        Iterable<Map.Entry<String, Integer>> secondInputIterable = listWith(mapEntryFor("three", 3), mapEntryFor("four", 4));

        // When
        Map<String, Integer> actualMap = mapBuilder
                .with(firstInputIterable)
                .with(secondInputIterable)
                .build();

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldAllowIterablesOfTuplesToBeAddedToTheMapWithWith() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expectedMap = new HashMap<String, Integer>();
        expectedMap.put("one", 1);
        expectedMap.put("two", 2);
        expectedMap.put("three", 3);
        expectedMap.put("four", 4);
        Iterable<Pair<String, Integer>> firstInputIterable = listWith(tuple("one", 1), tuple("two", 2));
        Iterable<Pair<String, Integer>> secondInputIterable = listWith(tuple("three", 3), tuple("four", 4));

        // When
        Map<String, Integer> actualMap = mapBuilder
                .withPairs(firstInputIterable)
                .withPairs(secondInputIterable)
                .build();

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldAllowMapEntryInstancesToBeAddedToTheMapWithAnd() {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expectedMap = new HashMap<String, Integer>();
        expectedMap.put("five", 5);
        expectedMap.put("ten", 10);
        expectedMap.put("fifteen", 15);
        expectedMap.put("twenty", 20);

        // When
        Map<String, Integer> actualMap = mapBuilder
                .with(mapEntryFor("five", 5))
                .and(mapEntryFor("ten", 10), mapEntryFor("fifteen", 15))
                .and(mapEntryFor("twenty", 20))
                .build();

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldAllowTuplesToBeAddedToTheMapWithAnd() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("zeroth", 0);
        expected.put("first", 1);
        expected.put("second", 2);
        expected.put("third", 3);

        // When
        Map<String, Integer> actual = mapBuilder
                .withKeyValuePair("zeroth", 0)
                .andPair(tuple("first", 1))
                .andPairs(tuple("second", 2), tuple("third", 3))
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowKeysAndValuesToBeAddedToTheMapWithAnd() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("zeroth", 0);
        expected.put("first", 1);
        expected.put("second", 2);
        expected.put("third", 3);

        // When
        Map<String, Integer> actual = mapBuilder
                .withKeyValuePair("zeroth", 0)
                .andKeyValuePairs("first", 1, "second", 2)
                .andKeyValuePair("third", 3)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowArraysOfMapEntryInstancesToBeAddedToTheMapWithAnd() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expectedMap = new HashMap<String, Integer>();
        expectedMap.put("five", 5);
        expectedMap.put("ten", 10);
        expectedMap.put("fifteen", 15);
        expectedMap.put("twenty", 20);

        @SuppressWarnings("unchecked") Map.Entry<String, Integer>[] mapEntryArray = new Map.Entry[]{
                mapEntryFor("fifteen", 15),
                mapEntryFor("twenty", 20)
        };

        // When
        Map<String, Integer> actualMap = mapBuilder
                .with(mapEntryFor("five", 5), mapEntryFor("ten", 10))
                .and(mapEntryArray)
                .build();

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldAllowArraysOfTuplesToBeAddedToTheMapWithAnd() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expectedMap = new HashMap<String, Integer>();
        expectedMap.put("five", 5);
        expectedMap.put("ten", 10);
        expectedMap.put("fifteen", 15);
        expectedMap.put("twenty", 20);

        @SuppressWarnings("unchecked") Pair<String, Integer>[] tupleArray = new Pair[]{
                tuple("fifteen", 15),
                tuple("twenty", 20)
        };

        // When
        Map<String, Integer> actualMap = mapBuilder
                .with(mapEntryFor("five", 5), mapEntryFor("ten", 10))
                .andPairs(tupleArray)
                .build();

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldAllowIterablesOfMapEntryInstancesToBeAddedToTheMapWithAnd() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expectedMap = new HashMap<String, Integer>();
        expectedMap.put("first", 1);
        expectedMap.put("second", 2);
        expectedMap.put("third", 3);
        expectedMap.put("fourth", 4);

        Iterable<Map.Entry<String, Integer>> someOtherElements = listWith(mapEntryFor("third", 3), mapEntryFor("fourth", 4));

        // When
        Map<String, Integer> actualMap = mapBuilder
                .with(mapEntryFor("first", 1), mapEntryFor("second", 2))
                .and(someOtherElements)
                .build();

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldAllowIterablesOfTuplesToBeAddedToTheMapWithAnd() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilder();
        Map<String, Integer> expectedMap = new HashMap<String, Integer>();
        expectedMap.put("first", 1);
        expectedMap.put("second", 2);
        expectedMap.put("third", 3);
        expectedMap.put("fourth", 4);

        Iterable<Pair<String, Integer>> iterableOfTuples = listWith(tuple("third", 3), tuple("fourth", 4));

        // When
        Map<String, Integer> actualMap = mapBuilder
                .with(mapEntryFor("first", 1), mapEntryFor("second", 2))
                .andPairs(iterableOfTuples)
                .build();

        // Then
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    public void shouldBuildAMapOfTheSpecifiedImplementation() throws Exception {
        // Given
        Map<String, Integer> expected = mapWith("first", 1);
        MapBuilder<String, Integer> mapBuilder = mapBuilderWith("first", 1);

        // When
        Map<String, Integer> actual = mapBuilder.build(TreeMap.class);

        // Then
        assertThat(actual instanceof TreeMap, is(true));
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldThrowAnIllegalArgumentExceptionIfTheSpecifiedImplementationDoesNotHaveAnAccessibleConstructor() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilderWith("first", 1);

        try {
            // When
            mapBuilder.build(ImmutableMap.class);
            fail("Expected an IllegalArgumentException but got nothing.");
        } catch (IllegalArgumentException exception) {
            // Then
            assertThat(exception.getMessage(),
                    containsString(
                            "Could not instantiate instance of type ImmutableMap. " +
                                    "Does it have a public no argument constructor?"));
        }
    }

    @Test
    public void shouldThrowAnIllegalArgumentExceptionIfTheSpecifiedImplementationDoesNotHaveANoArgsConstructor() throws Exception {
        // Given
        MapBuilder<String, Integer> mapBuilder = mapBuilderWith("first", 1);

        try {
            // When
            mapBuilder.build(NoNoArgsConstructorMap.class);
            fail("Expected an IllegalArgumentException but got nothing.");
        } catch (IllegalArgumentException exception) {
            // Then
            assertThat(exception.getMessage(),
                    containsString(
                            "Could not instantiate instance of type NoNoArgsConstructorMap. " +
                                    "Does it have a public no argument constructor?"));
        }
    }

    private static class NoNoArgsConstructorMap<K, V> extends HashMap<K, V> {
        public NoNoArgsConstructorMap(Throwable argument) {
            throw new UnsupportedOperationException("should never throw", argument);
        }
    }
}
