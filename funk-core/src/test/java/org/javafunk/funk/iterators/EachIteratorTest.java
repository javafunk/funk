package org.javafunk.funk.iterators;

import org.javafunk.funk.functors.procedures.UnaryProcedure;
import org.junit.Test;

import java.util.Iterator;

import static org.javafunk.funk.Literals.iterableWith;

public class EachIteratorTest {
    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfUnaryProcedureSuppliedToConstructorIsNull() throws Exception {
        // Given
        Iterator<String> input = iterableWith("a", "b", "c").iterator();
        UnaryProcedure<String> action = null;

        // When
        new EachIterator<String>(input, action);

        // Then a NullPointerException is thrown.
    }
}
