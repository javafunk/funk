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
public class QuinaryPredicateTest {
    @Mock QuinaryPredicate<String, Integer, Long, Double, Float> predicate;

    @Test
    public void shouldDelegateCallToEvaluate() throws Exception {
        // Given
        String firstInput = "hello";
        Integer secondInput = 15;
        Long thirdInput = 9L;
        Double fourthInput = 4.3;
        Float fifthInput = 3.2F;
        Boolean expected = false;
        given(predicate
                .call(firstInput, secondInput, thirdInput,
                        fourthInput, fifthInput))
                .willCallRealMethod();
        given(predicate
                .evaluate(firstInput, secondInput, thirdInput,
                        fourthInput, fifthInput))
                .willReturn(expected);

        // When
        Boolean actual = predicate
                .call(firstInput, secondInput, thirdInput,
                        fourthInput, fifthInput);

        // Then
        verify(predicate)
                .evaluate(firstInput, secondInput, thirdInput,
                        fourthInput, fifthInput);
        assertThat(actual, is(expected));
    }
}
