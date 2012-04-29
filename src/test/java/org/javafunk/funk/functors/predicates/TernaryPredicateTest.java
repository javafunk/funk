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
public class TernaryPredicateTest {
    @Mock TernaryPredicate<String, Integer, Long> predicate;

    @Test
    public void shouldDelegateCallToEvaluate() throws Exception {
        // Given
        String firstInput = "hello";
        Integer secondInput = 15;
        Long thirdInput = 9L;
        Boolean expected = false;
        given(predicate.call(firstInput, secondInput, thirdInput)).willCallRealMethod();
        given(predicate.evaluate(firstInput, secondInput, thirdInput)).willReturn(expected);

        // When
        Boolean actual = predicate.call(firstInput, secondInput, thirdInput);

        // Then
        verify(predicate).evaluate(firstInput, secondInput, thirdInput);
        assertThat(actual, is(expected));
    }
}
