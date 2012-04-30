package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Indexer;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.functors.adapters.IndexerUnaryFunctionAdapter.indexerUnaryFunction;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class IndexerUnaryFunctionAdapterTest {
    @Mock Indexer<String, Integer> indexer;

    @Test
    public void shouldDelegateCallToSuppliedAction() throws Exception {
        // Given
        String target = "some string";
        Integer expected = 11;
        UnaryFunction<String, Integer> adapter = indexerUnaryFunction(indexer);
        given(indexer.index(target)).willReturn(expected);

        // When
        Integer actual = adapter.call(target);

        // Then
        assertThat(actual, is(expected));
    }
}
