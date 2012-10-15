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
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.collectionBuilderOf;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class TripleTest {
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
}
