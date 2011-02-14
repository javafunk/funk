package org.smallvaluesofcool.misc.functional.functors;

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
        PredicateFunction<String> delegatePredicate = mock(PredicateFunction.class);
        when(delegatePredicate.matches(any(String.class))).thenReturn(false);

        NotPredicateFunction<String> notPredicate = new NotPredicateFunction<String>(delegatePredicate);

        // When
        Boolean result = notPredicate.matches("some string");

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfTheSuppliedPredicateReturnsTrue() {
        // Given
        PredicateFunction<String> delegatePredicate = mock(PredicateFunction.class);
        when(delegatePredicate.matches(any(String.class))).thenReturn(true);

        NotPredicateFunction<String> notPredicate = new NotPredicateFunction<String>(delegatePredicate);

        // When
        Boolean result = notPredicate.matches("some string");

        // Then
        assertThat(result, is(false));
    }
}
