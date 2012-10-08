/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Predicate;
import org.javafunk.funk.predicates.FalsePredicate;
import org.javafunk.funk.predicates.NotPredicate;
import org.javafunk.funk.predicates.TruePredicate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Predicates.*;

public class PredicatesTest {
    @Test
    public void shouldReturnNotPredicateOverSuppliedPredicate() throws Exception {
        // Given
        Predicate<String> predicate = new FalsePredicate<String>();
        Predicate<String> expected = new NotPredicate<String>(predicate);

        // When
        Predicate<String> actual = not(predicate);

        // Then
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfPredicateSuppliedtoNotIsNull() throws Exception {
        // Given
        Predicate<String> predicate = null;

        // When
        Predicates.not(predicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldReturnTruePredicate() throws Exception {
        // Given
        Predicate<String> expected = new TruePredicate<String>();

        // When
        Predicate<String> actual = alwaysTrue();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnFalsePredicate() throws Exception {
        // Given
        Predicate<String> expected = new FalsePredicate<String>();

        // When
        Predicate<String> actual = alwaysFalse();

        // Then
        assertThat(actual, is(expected));
    }
}
