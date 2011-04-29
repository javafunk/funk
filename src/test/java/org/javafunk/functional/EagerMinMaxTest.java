package org.javafunk.functional;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.Literals.listWith;

public class EagerMinMaxTest {
    @Test
    public void shouldReturnMinValue() throws Exception {
        // Given
        List<String> list = listWith("b", "a", "c");

        // When
        String actual = Eager.min(list);

        // Then
        assertThat(actual, is("a"));
    }

    @Test
    public void shouldReturnMaxValue() throws Exception {
        // Given
        List<Integer> list = listWith(3, 2, 6);

        // When
        Integer actual = Eager.max(list);

        // Then
        assertThat(actual, is(6));
    }
}
