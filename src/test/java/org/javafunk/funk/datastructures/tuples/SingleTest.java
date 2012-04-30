/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.datastructures.tuples;

import org.javafunk.funk.behaviours.ordinals.First;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.listOf;
import static org.javafunk.funk.Literals.tuple;

public class SingleTest {
    @Test
    public void shouldReturnTheFirstObject() {
        // Given
        First<Integer> single = tuple(5);

        // When
        Integer first = single.first();

        //Then
        assertThat(first, is(5));
    }

    @Test
    public void shouldUseTheStringValueOfTheFirstInToString() {
        // Given
        First<Integer> single = tuple(5);

        // When
        String stringRepresentation = single.toString();

        // Then
        assertThat(stringRepresentation, is("(5)"));
    }

    @Test
    public void shouldBeEqualIfHasSameFirst() {
        // Given
        Single<Integer> single1 = tuple(5);
        Single<Integer> single2 = tuple(5);

        // When
        Boolean isEqual = single1.equals(single2);

        // Then
        assertThat(isEqual, is(true));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentFirst() {
        // Given
        Single<Integer> single1 = tuple(5);
        Single<Integer> single2 = tuple(10);

        // When
        Boolean isEqual = single1.equals(single2);

        // Then
        assertThat(isEqual, is(false));
    }

    @Test
    public void shouldBeIterable() {
        // Given
        Single<Integer> single1 = tuple(5);
        List<Object> expected = listOf(Object.class).with(5);

        // When
        Boolean isEqual = materialize(single1).equals(expected);

        // Then
        assertThat(isEqual, is(true));
    }
}
