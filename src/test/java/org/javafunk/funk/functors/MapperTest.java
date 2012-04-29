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
public class MapperTest {
    @Mock Mapper<String, Integer> mapper;

    @Test
    public void shouldDelegateCallToMap() throws Exception {
        // Given
        String input = "string";
        Integer expected = 6;
        given(mapper.call(input)).willCallRealMethod();
        given(mapper.map(input)).willReturn(expected);

        // When
        Integer actual = mapper.call(input);

        // Then
        verify(mapper).map(input);
        assertThat(actual, is(expected));
    }
}
