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
