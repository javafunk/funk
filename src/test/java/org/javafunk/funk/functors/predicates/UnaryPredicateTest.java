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
public class UnaryPredicateTest {
    @Mock UnaryPredicate<String> predicate;

    @Test
    public void shouldDelegateCallToEvaluate() throws Exception {
        // Given
        String firstInput = "hello";
        Boolean expected = false;
        given(predicate.call(firstInput)).willCallRealMethod();
        given(predicate.evaluate(firstInput)).willReturn(expected);

        // When
        Boolean actual = predicate.call(firstInput);

        // Then
        verify(predicate).evaluate(firstInput);
        assertThat(actual, is(expected));
    }
}
