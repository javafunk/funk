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

import java.util.Iterator;

import static org.hamcrest.Matchers.*;
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

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullIterablePassedToCycle() throws Exception {
        // Given
        Iterable<String> input = null;

        // When
        Lazily.cycle(input);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldCycleTheSuppliedIterableTheSpecifiedNumberOfTimes() throws Exception {
        // Given
        Iterable<String> input = iterableWith("Black", "White");

        // When
        Iterable<String> output = Lazily.cycle(input, 2);
        Iterator<String> cyclingIterator = output.iterator();

        // Then
        assertThat(cyclingIterator.hasNext(), is(true));
        assertThat(cyclingIterator.next(), is("Black"));
        assertThat(cyclingIterator.hasNext(), is(true));
        assertThat(cyclingIterator.next(), is("White"));
        assertThat(cyclingIterator.hasNext(), is(true));
        assertThat(cyclingIterator.next(), is("Black"));
        assertThat(cyclingIterator.hasNext(), is(true));
        assertThat(cyclingIterator.next(), is("White"));
        assertThat(cyclingIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnCycleIterableWithCountReturningDifferentIterators() throws Exception {
        // Given
        Iterable<String> input = iterableWith("Black", "White");

        // When
        Iterable<String> iterable = Lazily.cycle(input, 2);
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

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullIterablePassedToCycleWithCount() throws Exception {
        // Given
        Iterable<String> input = null;

        // When
        Lazily.cycle(input, 3);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldRepeatTheSuppliedObjectInfinitely() {
        // Given
        String input = "Red";

        // When
        Iterable<String> output = Lazily.repeat(input);
        Iterator<String> repeatingIterator = output.iterator();

        // Then
        for(int i = 0; i < 20; i++) {
            assertThat(repeatingIterator.next(), is("Red"));
        }
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnRepeatElementReturningDifferentIterators() throws Exception {
        // Given
        String input = "Red";

        // When
        Iterable<String> iterable = Lazily.repeat(input);
        Iterator<String> iterator1 = iterable.iterator();
        Iterator<String> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is("Red"));
        assertThat(iterator1.next(), is("Red"));
        assertThat(iterator2.next(), is("Red"));
        assertThat(iterator1.next(), is("Red"));
        assertThat(iterator2.next(), is("Red"));
        assertThat(iterator1.next(), is("Red"));
        assertThat(iterator1, is(not(sameInstance(iterator2))));
    }

    @Test
    public void shouldRepeatNullValue() throws Exception {
        // Given
        String input = null;

        // When
        Iterable<String> output = Lazily.repeat(input);
        Iterator<String> repeatingIterator = output.iterator();

        // Then
        for(int i = 0; i < 20; i++) {
            assertThat(repeatingIterator.next(), is(nullValue()));
        }
    }

    @Test
    public void shouldRepeatTheSuppliedObjectTheSpecifiedNumberOfTimes() throws Exception {
        // Given
        String input = "Black";

        // When
        Iterable<String> output = Lazily.repeat(input, 3);
        Iterator<String> repeatingIterator = output.iterator();

        // Then
        assertThat(repeatingIterator.hasNext(), is(true));
        assertThat(repeatingIterator.next(), is("Black"));
        assertThat(repeatingIterator.hasNext(), is(true));
        assertThat(repeatingIterator.next(), is("Black"));
        assertThat(repeatingIterator.hasNext(), is(true));
        assertThat(repeatingIterator.next(), is("Black"));
        assertThat(repeatingIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesOnRepeatWithCountReturningDifferentIterators() throws Exception {
        // Given
        Integer input = 5;

        // When
        Iterable<Integer> iterable = Lazily.repeat(input, 3);
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(5));
        assertThat(iterator1.next(), is(5));
        assertThat(iterator2.next(), is(5));
        assertThat(iterator1.next(), is(5));
        assertThat(iterator2.next(), is(5));
        assertThat(iterator2.next(), is(5));
    }

    @Test
    public void shouldRepeatNullValueTheSpecificNumberOfTimes() throws Exception {
        // Given
        Integer input = null;

        // When
        Iterable<Integer> output = Lazily.repeat(input, 3);
        Iterator<Integer> repeatingIterator = output.iterator();

        // Then
        assertThat(repeatingIterator.hasNext(), is(true));
        assertThat(repeatingIterator.next(), is(nullValue()));
        assertThat(repeatingIterator.hasNext(), is(true));
        assertThat(repeatingIterator.next(), is(nullValue()));
        assertThat(repeatingIterator.hasNext(), is(true));
        assertThat(repeatingIterator.next(), is(nullValue()));
        assertThat(repeatingIterator.hasNext(), is(false));
    }
}
