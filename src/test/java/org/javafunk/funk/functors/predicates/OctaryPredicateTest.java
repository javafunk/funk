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
public class OctaryPredicateTest {
    @Mock OctaryPredicate<String, Integer, Long, Double, Float, Boolean, Character, Class> predicate;

    @Test
    public void shouldDelegateCallToEvaluate() throws Exception {
        // Given
        String firstInput = "hello";
        Integer secondInput = 15;
        Long thirdInput = 9L;
        Double fourthInput = 4.3;
        Float fifthInput = 3.2F;
        Boolean sixthInput = false;
        Character seventhInput = 'a';
        Class<Object> eighthInput = Object.class;
        Boolean expected = false;
        given(predicate
                .call(firstInput, secondInput, thirdInput,
                        fourthInput, fifthInput, sixthInput,
                        seventhInput, eighthInput))
                .willCallRealMethod();
        given(predicate
                .evaluate(firstInput, secondInput, thirdInput,
                        fourthInput, fifthInput, sixthInput,
                        seventhInput, eighthInput))
                .willReturn(expected);

        // When
        Boolean actual = predicate
                .call(firstInput, secondInput, thirdInput,
                        fourthInput, fifthInput, sixthInput,
                        seventhInput, eighthInput);

        // Then
        verify(predicate)
                .evaluate(firstInput, secondInput, thirdInput,
                        fourthInput, fifthInput, sixthInput,
                        seventhInput, eighthInput);
        assertThat(actual, is(expected));
    }
}
