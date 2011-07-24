package org.javafunk.funk.predicates;

import org.javafunk.funk.functors.Predicate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotPredicateFunctionTest {
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
}
