package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Equivalence;
import org.javafunk.funk.functors.predicates.BinaryPredicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.functors.adapters.EquivalenceBinaryPredicateAdapter.equivalenceBinaryPredicate;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EquivalenceBinaryPredicateAdapterTest {
    @Mock Equivalence<? super String> equivalence;

    @Test
    public void shouldDelegateCallToSuppliedAction() throws Exception {
        // Given
        Boolean expected = false;
        String firstInput = "some string";
        String secondInput = "some other string";
        BinaryPredicate<String, String> adapter = equivalenceBinaryPredicate(equivalence);
        given(equivalence.equal(firstInput, secondInput)).willReturn(expected);

        // When
        Boolean actual = adapter.evaluate(firstInput, secondInput);

        // Then
        verify(equivalence).equal(firstInput, secondInput);
        assertThat(actual, is(expected));
    }
}
