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

public class FalsePredicateTest {

    @Test
    public void shouldReturnFalseForNull() {
        // Given
        Predicate<Object> falsePredicate = new FalsePredicate<Object>();

        // When
        Boolean result = falsePredicate.evaluate(null);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnFalseForObject() {
        // Given
        Predicate<Object> falsePredicate = new FalsePredicate<Object>();

        // When
        Boolean result = falsePredicate.evaluate(new Object());

        // Then
        assertThat(result, is(false));
    }
}
