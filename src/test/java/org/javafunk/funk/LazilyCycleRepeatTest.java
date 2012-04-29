/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterableWith;
import static org.junit.Assert.assertThat;

public class LazilyCycleRepeatTest {
    @Test
    public void shouldCycleTheSuppliedIterableInfinitely() {
        // Given
        Iterable<String> input = iterableWith("Red", "Green", "Blue");

        // When
        Iterable<String> output = Lazily.cycle(input);
        Iterator<String> cyclicIterator = output.iterator();

        // Then
        for(int i = 0; i < 20; i++) {
            assertThat(cyclicIterator.next(), is("Red"));
            assertThat(cyclicIterator.next(), is("Green"));
            assertThat(cyclicIterator.next(), is("Blue"));
        }
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnCycleIterableReturningDifferentIterators() throws Exception {
        // Given
        Iterable<String> input = iterableWith("Red", "Green", "Blue");

        // When
        Iterable<String> iterable = Lazily.cycle(input);
        Iterator<String> iterator1 = iterable.iterator();
        Iterator<String> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is("Red"));
        assertThat(iterator1.next(), is("Green"));
        assertThat(iterator2.next(), is("Red"));
        assertThat(iterator1.next(), is("Blue"));
        assertThat(iterator2.next(), is("Green"));
        assertThat(iterator1.next(), is("Red"));
    }

    @Test
    public void shouldRepeatTheSuppliedIterableTheSpecifiedNumberOfTimes() throws Exception {
        // Given
        Iterable<String> input = iterableWith("Black", "White");

        // When
        Iterable<String> output = Lazily.repeat(input, 2);
        Iterator<String> repeatedIterator = output.iterator();

        // Then
        assertThat(repeatedIterator.hasNext(), is(true));
        assertThat(repeatedIterator.next(), is("Black"));
        assertThat(repeatedIterator.hasNext(), is(true));
        assertThat(repeatedIterator.next(), is("White"));
        assertThat(repeatedIterator.hasNext(), is(true));
        assertThat(repeatedIterator.next(), is("Black"));
        assertThat(repeatedIterator.hasNext(), is(true));
        assertThat(repeatedIterator.next(), is("White"));
        assertThat(repeatedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnRepeatIterableReturningDifferentIterators() throws Exception {
        // Given
        Iterable<String> input = iterableWith("Black", "White");

        // When
        Iterable<String> iterable = Lazily.repeat(input, 2);
        Iterator<String> iterator1 = iterable.iterator();
        Iterator<String> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is("Black"));
        assertThat(iterator1.next(), is("White"));
        assertThat(iterator2.next(), is("Black"));
        assertThat(iterator1.next(), is("Black"));
        assertThat(iterator2.next(), is("White"));
        assertThat(iterator2.next(), is("Black"));
    }
}
