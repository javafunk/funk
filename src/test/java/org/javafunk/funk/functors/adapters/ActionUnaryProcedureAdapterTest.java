package org.javafunk.funk.functors.adapters;

import org.javafunk.funk.functors.Action;
import org.javafunk.funk.functors.procedures.UnaryProcedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.javafunk.funk.functors.adapters.ActionUnaryProcedureAdapter.actionUnaryProcedure;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ActionUnaryProcedureAdapterTest {
    @Mock Action<String> action;

    @Test
    public void shouldDelegateCallToSuppliedAction() throws Exception {
        // Given
        String target = "some string";
        UnaryProcedure<String> adapter = actionUnaryProcedure(action);

        // When
        adapter.execute(target);

        // Then
        verify(action).on(target);
    }
}
