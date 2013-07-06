/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.junit.Test;

import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertThat;

public class LazilyRestTest {
    @Test
    public void shouldReturnTheRestOfTheIterable(){
        // Given
        Iterable<String> iterable = iterableWith("a", "b", "c", "d");
        Iterable<String> expectedRest = iterableWith("b", "c", "d");

        // When
        Iterable<String> rest = Lazily.rest(iterable);
        
        // Then
        assertThat(materialize(rest), hasOnlyItemsInOrder(expectedRest));
    }

    @Test
    public void shouldReturnEmptyIterableForAnIterableWithOneElement(){
        // Given
        Iterable<String> iterable = iterableWith("a");
        Iterable<String> expectedRest = Iterables.empty();

        // When
        Iterable<String> rest = Lazily.rest(iterable);

        // Then
        assertThat(materialize(rest), hasOnlyItemsInOrder(expectedRest));
    }

    @Test
    public void shouldReturnEmptyIterableForAnEmptyIterable(){
        // Given
        Iterable<String> iterable = Iterables.empty();
        Iterable<String> expectedRest = Iterables.empty();

        // When
        Iterable<String> rest = Lazily.rest(iterable);

        // Then
        assertThat(materialize(rest), hasOnlyItemsInOrder(expectedRest));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfAnEmptyIterableIsPassedToRest() throws Exception {
        // Given
        Iterable<String> iterable = null;

        // When
        Lazily.rest(iterable);

        // Then a NullPointerException is thrown
    }
}
