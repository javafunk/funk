package org.javafunk.funk.functors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ReducerTest {
    @Mock Reducer<String, Integer> reducer;

    @Test
    public void shouldDelegateCallToReduce() throws Exception {
        // Given
        String input = "string";
        Integer accumulator = 0;
        Integer expected = 6;
        given(reducer.call(accumulator, input)).willCallRealMethod();
        given(reducer.accumulate(accumulator, input)).willReturn(expected);

        // When
        Integer actual = reducer.call(accumulator, input);

        // Then
        verify(reducer).accumulate(accumulator, input);
        assertThat(actual, is(expected));
    }
}
