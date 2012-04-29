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
public class EquivalenceTest {
    @Mock Equivalence<String> equivalence;

    @Test
    public void shouldDelegateEvaluateToEqual() throws Exception {
        // Given
        String first = "some string";
        String second = "some other string";
        Boolean expected = true;
        given(equivalence.evaluate(first, second)).willCallRealMethod();
        given(equivalence.equal(first, second)).willReturn(expected);

        // When
        Boolean actual = equivalence.evaluate(first, second);

        // Then
        verify(equivalence).equal(first, second);
        assertThat(actual, is(expected));
    }
}
