package org.javafunk.funk;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class EagerlyReverseTest {

    @Test
    public void shouldReverseTheIterable() throws Exception {
        // Given
        Iterable<String> iterable = iterableWith("a", "b", "c");

        // When
        Iterable<String> actual = Eagerly.reverse(iterable);

        // Then
        assertThat(actual, hasOnlyItemsInOrder("c","b","a"));
    }
}
