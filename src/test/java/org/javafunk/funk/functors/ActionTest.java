package org.javafunk.funk.functors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ActionTest {
    @Mock Action<String> action;

    @Test
    public void shouldDelegateExecuteToOn() throws Exception {
        // Given
        String target = "some string";
        doCallRealMethod().when(action).execute(target);

        // When
        action.execute(target);

        // Then
        verify(action).on(target);
    }
}
