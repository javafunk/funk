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
import org.javafunk.funk.functors.predicates.UnaryPredicate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Predicates.alwaysFalse;
import static org.javafunk.funk.Predicates.alwaysTrue;
import static org.javafunk.funk.Predicates.not;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotPredicateTest {
    @Test
    @SuppressWarnings("unchecked")
    public void shouldReturnTrueIfTheSuppliedPredicateReturnsFalse() {
        // Given
        Predicate<String> delegatePredicate = (Predicate<String>) mock(Predicate.class);
        when(delegatePredicate.evaluate(any(String.class))).thenReturn(false);

        NotPredicate<String> notPredicate = new NotPredicate<String>(delegatePredicate);

        // When
        Boolean result = notPredicate.evaluate("some string");

        // Then
        assertThat(result, is(true));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReturnFalseIfTheSuppliedPredicateReturnsTrue() {
        // Given
        Predicate<String> delegatePredicate = (Predicate<String>) mock(Predicate.class);
        when(delegatePredicate.evaluate(any(String.class))).thenReturn(true);

        NotPredicate<String> notPredicate = new NotPredicate<String>(delegatePredicate);

        // When
        Boolean result = notPredicate.evaluate("some string");

        // Then
        assertThat(result, is(false));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfUnaryPredicateSuppliedToConstructorIsNull() throws Exception {
        // Given
        UnaryPredicate<String> predicate = null;

        // When
        new NotPredicate<String>(predicate);

        // Then a NullPointerException is thrown.
    }

@Test
    public void shouldBeEqualIfOtherIsANotPredicateAndPredicateSuppliedAtInitialisationIsEqual() throws Exception {
        // Given
        Predicate<Object> first = not(alwaysTrue());
        Predicate<Object> second = not(alwaysTrue());

        // When
        boolean equal = first.equals(second);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldNotBeEqualIfOtherIsANotPredicateButDelegatePredicateSuppliedAtInitialisationIsDifferent() throws Exception {
        // Given
        Predicate<Object> first = not(alwaysFalse());
        Predicate<Object> second = not(alwaysTrue());

        // When
        boolean equal = first.equals(second);

        // Then
        assertThat(equal, is(false));
    }

    @Test
    public void shouldNotBeEqualIfOtherIsNotANotPredicate() throws Exception {
        // Given
        Predicate<Object> first = not(alwaysTrue());
        Predicate<Object> second = alwaysFalse();

        // When
        boolean equal = first.equals(second);

        // Then
        assertThat(equal, is(false));
    }
}
