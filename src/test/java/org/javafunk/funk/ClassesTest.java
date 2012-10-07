package org.javafunk.funk;

import org.javafunk.funk.functors.functions.UnaryFunction;
import org.junit.Test;

public class ClassesTest {
    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionSuppliedToUncheckedInstantiate() throws Exception {
        // Given
        Class<String> classToInstantiate = String.class;
        UnaryFunction<? super Exception, ? extends RuntimeException> function = null;

        // When
        Classes.uncheckedInstantiate(classToInstantiate, function);

        // Then a NullPointerException is thrown.
    }
}
