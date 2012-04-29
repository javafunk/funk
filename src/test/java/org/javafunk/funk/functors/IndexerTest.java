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
public class IndexerTest {
    @Mock Indexer<String, Integer> indexer;

    @Test
    public void shouldDelegateCallToIndex() throws Exception {
        // Given
        String input = "string";
        Integer expected = 6;
        given(indexer.call(input)).willCallRealMethod();
        given(indexer.index(input)).willReturn(expected);

        // When
        Integer actual = indexer.call(input);

        // Then
        verify(indexer).index(input);
        assertThat(actual, is(expected));
    }
}
