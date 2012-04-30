package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Reducer;
import org.javafunk.funk.functors.functions.BinaryFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.functors.adapters.ReducerBinaryFunctionAdapter.reducerBinaryFunction;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ReducerBinaryFunctionAdapterTest {
    @Mock Reducer<Integer, String> reducer;

    @Test
    public void shouldDelegateCallToSuppliedAction() throws Exception {
        // Given
        String accumulator = "some string";
        Integer element = 3;
        String expected = "some string three";
        BinaryFunction<String, Integer, String> adapter = reducerBinaryFunction(reducer);
        given(reducer.accumulate(accumulator, element)).willReturn(expected);

        // When
        String actual = adapter.call(accumulator, element);

        // Then
        assertThat(actual, is(expected));
    }
}
