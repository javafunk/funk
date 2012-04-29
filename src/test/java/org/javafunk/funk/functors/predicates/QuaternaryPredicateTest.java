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
public class QuaternaryPredicateTest {
    @Mock QuaternaryPredicate<String, Integer, Long, Double> predicate;

    @Test
    public void shouldDelegateCallToEvaluate() throws Exception {
        // Given
        String firstInput = "hello";
        Integer secondInput = 15;
        Long thirdInput = 9L;
        Double fourthInput = 4.3;
        Boolean expected = false;
        given(predicate
                .call(firstInput, secondInput, thirdInput,
                        fourthInput))
                .willCallRealMethod();
        given(predicate
                .evaluate(firstInput, secondInput, thirdInput,
                        fourthInput))
                .willReturn(expected);

        // When
        Boolean actual = predicate
                .call(firstInput, secondInput, thirdInput,
                        fourthInput);

        // Then
        verify(predicate)
                .evaluate(firstInput, secondInput, thirdInput,
                        fourthInput);
        assertThat(actual, is(expected));
    }
}
