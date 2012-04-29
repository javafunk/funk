package org.javafunk.funk.functors.predicates;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NullaryPredicateTest {
    @Mock NullaryPredicate predicate;

    @Test
    public void shouldDelegateCallToEvaluate() throws Exception {
        // Given
        Boolean expected = false;
        given(predicate.call()).willCallRealMethod();
        given(predicate.evaluate()).willReturn(expected);

        // When
        Boolean actual = predicate.call();

        // Then
        verify(predicate).evaluate();
        assertThat(actual, is(expected));
    }
}
