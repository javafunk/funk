/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.javafunk.funk.Eagerly;
import org.javafunk.funk.functors.Action;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.javafunk.funk.Literals.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CyclicIteratorTest {
    @Test
    public void shouldCycleThroughTheSuppliedIteratorInfinitely() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
    }

    @Test
    public void shouldCycleThroughTheSuppliedIteratorTheSpecifiedNumberOfTimes() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator(), 2);

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfTheSpecifiedNumberOfTimesToRepeatIsNegative() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2);

        // When
        new CyclicIterator<Integer>(input.iterator(), -5);

        // Then an IllegalArgumentException is be thrown
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfIteratorSuppliedAtConstructionTimeIsNull() throws Exception {
        // Given
        Iterator<Integer> input = null;

        // When
        new CyclicIterator<Integer>(input, 3);

        // Then a NullPointerException is thrown
    }

    @Test
    public void shouldReturnFalseForHasNextIfANumberOfRepeatsOfZeroIsSpecified() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator(), 0);

        // Then
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfNextIsCalledAndANumberOfRepeatsOfZeroIsSpecified() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2);
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator(), 0);

        // When
        iterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldReturnFalseForHasNextIfAnEmptyIteratorIsSupplied() throws Exception {
        // Given
        Iterable<Integer> input = iterableOf(Integer.class);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());

        // Then
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfNextIsCalledAndAnEmptyIteratorIsSupplied() throws Exception {
        // Given
        Iterable<Integer> input = iterableOf(Integer.class);
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());

        // When
        iterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutCallingHasNextFirst() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());

        // Then
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(3));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(3));
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowAnUnsupportedOperationExceptionWhenTryingToRemoveElements() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2);

        // When
        CyclicIterator<Integer> iterator = new CyclicIterator<Integer>(input.iterator());
        iterator.next();
        iterator.remove();

        // Then an UnsupportedOperationException should be thrown
    }

    @Test
    public void shouldAllowNullValuesInTheInputIterator() throws Exception {
        // Given
        Iterator<String> input = iterableWith("a", "b", null).iterator();

        // When
        Iterator<String> iterator = new CyclicIterator<String>(input);

        // Then
        assertThat(iterator.next(), is("a"));
        assertThat(iterator.next(), is("b"));
        assertThat(iterator.next(), is(nullValue()));
    }

    @Test
    public void shouldHaveNumberOfRepeatsAsInfiniteInTheToStringRepresentationIfNoneSupplied() throws Exception {
        // Given
        Iterator<String> input = iteratorWith("1", "2", "3");

        Iterator<String> iterator = new CyclicIterator<String>(input);

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("numberOfTimesToRepeat=infinite"));
    }

    @Test
    public void shouldIncludeNumberOfRepeatsInTheToStringRepresentation() throws Exception {
        // Given
        Iterator<String> input = iteratorWith("1", "2", "3");
        Integer numberOfTimesToRepeat = 5;

        Iterator<String> iterator = new CyclicIterator<String>(input, numberOfTimesToRepeat);

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("numberOfTimesToRepeat=5"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldIncludeTheIteratorInTheToStringRepresentation() throws Exception {
        // Given
        Iterator<String> input = (Iterator<String>) mock(Iterator.class);

        when(input.toString()).thenReturn("the-iterator");

        Iterator<String> iterator = new CyclicIterator<String>(input);

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("the-iterator"));
    }

    @Test
    public void shouldIncludeTheNumberOfRepeatsCompletedInTheToStringRepresentation() throws Exception {
        // Given
        Iterator<String> input = iteratorWith("1", "2", "3");
        Integer numberOfTimesToRepeat = 5;

        final Iterator<String> iterator = new CyclicIterator<String>(input, numberOfTimesToRepeat);

        Eagerly.times(10, new Action<Integer>() {
            @Override public void on(Integer input) {
                iterator.next();
            }
        });

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("numberOfRepeatsCompleted=3"));
    }

    @Test
    public void shouldIncludeIndexInCurrentRepeatInTheToStringRepresentation() throws Exception {
        // Given
        Iterator<String> input = iteratorWith("1", "2", "3");
        Integer numberOfTimesToRepeat = 5;

        final Iterator<String> iterator = new CyclicIterator<String>(input, numberOfTimesToRepeat);

        Eagerly.times(10, new Action<Integer>() {
            @Override public void on(Integer input) {
                iterator.next();
            }
        });

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("indexInCurrentRepeat=1"));
    }
}
