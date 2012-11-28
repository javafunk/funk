/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.predicates;

import org.javafunk.funk.functors.Predicate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TruePredicateTest {

    @Test
    public void shouldReturnTrueForNull() {
        // Given
        Predicate<Object> truePredicate = new TruePredicate<Object>();

        // When
        Boolean result = truePredicate.evaluate(null);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnTrueForObject() {
        // Given
        Predicate<Object> truePredicate = new TruePredicate<Object>();

        // When
        Boolean result = truePredicate.evaluate(new Object());

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldBeEqualIfOtherPredicateIsAlsoATruePredicate() throws Exception {
        // Given
        Predicate<Object> firstPredicate = new TruePredicate<Object>();
        Predicate<Object> secondPredicate = new TruePredicate<Object>();

        // When
        boolean equal = firstPredicate.equals(secondPredicate);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldBeEqualIfOtherPredicateIsATruePredicateButTypesAreDifferent() throws Exception {
        // Given
        Predicate<String> firstPredicate = new TruePredicate<String>();
        Predicate<Integer> secondPredicate = new TruePredicate<Integer>();

        // When
        boolean equal = firstPredicate.equals(secondPredicate);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldNotBeEqualIfOtherPredicateIsNotATruePredicate() throws Exception {
        // Given
        Predicate<Object> firstPredicate = new TruePredicate<Object>();
        Predicate<Object> secondPredicate = new FalsePredicate<Object>();

        // When
        boolean equal = firstPredicate.equals(secondPredicate);

        // Then
        assertThat(equal, is(false));
    }
}
