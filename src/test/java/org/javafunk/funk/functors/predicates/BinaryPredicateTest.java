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
public class BinaryPredicateTest {
    @Mock BinaryPredicate<String, Integer> predicate;

    @Test
    public void shouldDelegateCallToEvaluate() throws Exception {
        // Given
        String firstInput = "hello";
        Integer secondInput = 15;
        Boolean expected = false;
        given(predicate.call(firstInput, secondInput)).willCallRealMethod();
        given(predicate.evaluate(firstInput, secondInput)).willReturn(expected);

        // When
        Boolean actual = predicate.call(firstInput, secondInput);

        // Then
        verify(predicate).evaluate(firstInput, secondInput);
        assertThat(actual, is(expected));
    }
}
