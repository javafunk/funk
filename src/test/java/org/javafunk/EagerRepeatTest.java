package org.javafunk;

import org.javafunk.Eager;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.listWith;

public class EagerRepeatTest {
    @Test
    public void shouldRepeatTheElementsOfTheSpecifiedIterableInOrderTheSpecifiedNumberOfTimes() throws Exception {
        // Given
        Iterable<Integer> input = listWith(1, 2);
        Collection<Integer> expectedOutput = listWith(1, 2, 1, 2, 1, 2);

        // When
        Collection<Integer> actualOutput = Eager.repeat(input, 3);

        // Then
        assertThat(actualOutput, is(expectedOutput));
    }
}
