package org.javafunk.funk.iterators;

import org.javafunk.funk.functors.procedures.UnaryProcedure;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.iteratorWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    @SuppressWarnings("unchecked")
    public void shouldIncludeIteratorInToStringRepresentation() throws Exception {
        // Given
        Iterator<String> input = (Iterator<String>) mock(Iterator.class);
        UnaryProcedure<String> action = new UnaryProcedure<String>() {
            @Override public void execute(String value) {
                System.out.println(value);
            }
        };

        when(input.toString()).thenReturn("the-iterator");

        EachIterator<String> iterator = new EachIterator<String>(input, action);

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("the-iterator"));
    }

    @Test
    public void shouldIncludeProcedureInToStringRepresentation() throws Exception {
        // Given
        Iterator<String> input = iteratorWith("1", "2", "3");
        UnaryProcedure<String> action = new UnaryProcedure<String>() {
            @Override public void execute(String value) {
                System.out.println(value);
            }

            @Override public String toString() {
                return "the-procedure";
            }
        };

        EachIterator<String> iterator = new EachIterator<String>(input, action);

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("the-procedure"));
    }
}
