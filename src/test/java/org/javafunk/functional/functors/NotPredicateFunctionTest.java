package org.javafunk.functional.functors;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotPredicateFunctionTest {
    @Test
    public void shouldReturnTrueIfTheSuppliedPredicateReturnsFalse() {
        // Given
        Predicate<String> delegatePredicate = mock(Predicate.class);
        when(delegatePredicate.evaluate(any(String.class))).thenReturn(false);

        NotPredicate<String> notPredicate = new NotPredicate<String>(delegatePredicate);

        // When
        Boolean result = notPredicate.evaluate("some string");

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfTheSuppliedPredicateReturnsTrue() {
        // Given
        Predicate<String> delegatePredicate = mock(Predicate.class);
        when(delegatePredicate.evaluate(any(String.class))).thenReturn(true);

        NotPredicate<String> notPredicate = new NotPredicate<String>(delegatePredicate);

        // When
        Boolean result = notPredicate.evaluate("some string");

        // Then
        assertThat(result, is(false));
    }
}
