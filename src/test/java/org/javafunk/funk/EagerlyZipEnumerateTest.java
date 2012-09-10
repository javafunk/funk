/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Pair;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInAnyOrder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class EagerlyZipEnumerateTest {
    @Test
    public void shouldZipIterables() {
        // Given
        Iterable<Integer> iterable1 = iterableWith(1, 2, 3);
        Iterable<String> iterable2 = iterableWith("First", "Second", "Third");
        Collection<Pair<Integer, String>> expectedOutput = collectionWith(
                tuple(1, "First"),
                tuple(2, "Second"),
                tuple(3, "Third"));

        // When
        Collection<Pair<Integer, String>> actualOutput = Eagerly.zip(iterable1, iterable2);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }

    @Test
    public void shouldEnumerateIterable() {
        // Given
        Iterable<String> iterable = iterableWith("a", "b", "c");
        Collection<Pair<Integer, String>> expectedOutput = collectionWith(
                tuple(0, "a"),
                tuple(1, "b"),
                tuple(2, "c"));

        // When
        Collection<Pair<Integer, String>> actualOutput = Eagerly.enumerate(iterable);

        // Then
        assertThat(actualOutput, hasOnlyItemsInOrder(expectedOutput));
    }
}
