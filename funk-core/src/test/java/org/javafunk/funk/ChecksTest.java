package org.javafunk.funk;

import org.junit.Test;

import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterable;
import static org.javafunk.funk.Literals.iterableWith;

public class ChecksTest {
    @Test
    public void shouldReturnTheIterableIfItIsNotEmpty() throws Exception {
        // Given
        Iterable<String> input = iterableWith("one", "two");

        // When
        Iterable<String> output = Checks.returnOrThrowIfEmpty(input, new RuntimeException("Oops"));

        // Then
        assertThat(output, is(input));
    }

    @Test
    public void shouldThrowTheSuppliedExceptionIfEmpty() throws Exception {
        // Given
        Iterable<String> input = iterable();

        try {
            // When
            Checks.returnOrThrowIfEmpty(input, new IllegalArgumentException("Oops"));
            fail("Expected the supplied exception to be thrown.");
        } catch (Throwable exception) {
            // Then
            assertThat(exception, instanceOf(IllegalArgumentException.class));
            assertThat(exception.getMessage(), is("Oops"));
        }
    }

    @Test
    public void shouldReturnIfContainsNoNullsCheckIsSuppliedEmptyIterable() throws Exception {
        // Given
        Iterable<Integer> input = iterable();

        // When
        Iterable<Integer> output = Checks.returnOrThrowIfContainsNull(input);

        // Then
        assertThat(output, is(input));
    }
}
