/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.javafunk.funk.Iterators.emptyIterator;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChainedIteratorTest {
    @Test
    public void shouldReturnElementsFromEachIteratorInOrder() throws Exception {
        // Given
        Iterator<Integer> firstIterator = iterableWith(1, 2).iterator();
        Iterator<Integer> secondIterator = iterableWith(3, 4).iterator();
        Iterator<Integer> thirdIterator = iterableWith(5, 6).iterator();

        // When
        Iterator<Integer> chainedIterator = new ChainedIterator<Integer>(firstIterator, secondIterator, thirdIterator);

        // Then
        assertThat(chainedIterator.next(), is(1));
        assertThat(chainedIterator.next(), is(2));
        assertThat(chainedIterator.next(), is(3));
        assertThat(chainedIterator.next(), is(4));
        assertThat(chainedIterator.next(), is(5));
        assertThat(chainedIterator.next(), is(6));
    }

    @Test
    public void shouldAllowEmptyIteratorsToBeSupplied() throws Exception {
        // Given
        Iterator<Integer> firstIterator = emptyIterator();
        Iterator<Integer> secondIterator = iterableWith(1, 2, 3).iterator();
        Iterator<Integer> thirdIterator = emptyIterator();
        Iterator<Integer> fourthIterator = iterableWith(4).iterator();

        // When
        Iterator<Integer> chainedIterator = new ChainedIterator<Integer>(
                firstIterator, secondIterator, thirdIterator, fourthIterator);

        // Then
        assertThat(chainedIterator.next(), is(1));
        assertThat(chainedIterator.next(), is(2));
        assertThat(chainedIterator.next(), is(3));
        assertThat(chainedIterator.next(), is(4));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionWhenAllIteratorsAreExhausted() throws Exception {
        // Given
        Iterator<Integer> firstIterator = iterableWith(1, 2).iterator();
        Iterator<Integer> secondIterator = emptyIterator();
        Iterator<Integer> thirdIterator = iterableWith(3).iterator();
        Iterator<Integer> fourthIterator = emptyIterator();

        // When
        Iterator<Integer> chainedIterator = new ChainedIterator<Integer>(
                firstIterator, secondIterator, thirdIterator, fourthIterator);

        chainedIterator.next();
        chainedIterator.next();
        chainedIterator.next();
        chainedIterator.next();

        // Then a NoSuchElementException should be thrown
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() throws Exception {
        // Given
        Iterator<Integer> firstIterator = iterableWith(1).iterator();
        Iterator<Integer> secondIterator = iterableWith(2).iterator();

        // When
        Iterator<Integer> chainedIterator = new ChainedIterator<Integer>(firstIterator, secondIterator);

        // Then
        assertThat(chainedIterator.hasNext(), is(true));
        assertThat(chainedIterator.hasNext(), is(true));
        assertThat(chainedIterator.next(), is(1));
        assertThat(chainedIterator.hasNext(), is(true));
        assertThat(chainedIterator.hasNext(), is(true));
        assertThat(chainedIterator.hasNext(), is(true));
        assertThat(chainedIterator.next(), is(2));
        assertThat(chainedIterator.hasNext(), is(false));
        assertThat(chainedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldRemoveFromTheUnderlyingIterator() throws Exception {
        // Given
        List<Integer> firstList = listBuilderWith(1, 2).build(ArrayList.class);
        List<Integer> secondList = listBuilderWith(3, 4).build(ArrayList.class);
        Iterator<Integer> firstIterator = firstList.iterator();
        Iterator<Integer> secondIterator = secondList.iterator();

        // When
        Iterator<Integer> chainedIterator = new ChainedIterator<Integer>(firstIterator, secondIterator);

        chainedIterator.next();
        chainedIterator.next();
        chainedIterator.remove();
        chainedIterator.next();
        chainedIterator.remove();
        chainedIterator.next();

        // Then
        List<Integer> expectedFirstList = listWith(1);
        List<Integer> expectedSecondList = listWith(4);

        assertThat(firstList, hasOnlyItemsInOrder(expectedFirstList));
        assertThat(secondList, hasOnlyItemsInOrder(expectedSecondList));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledBeforeNext() throws Exception {
        // Given
        Iterator<Integer> firstIterator = listBuilderWith(1).build(ArrayList.class).iterator();
        Iterator<Integer> secondIterator = listBuilderWith(2).build(ArrayList.class).iterator();

        // When
        Iterator<Integer> chainedIterator = new ChainedIterator<Integer>(firstIterator, secondIterator);

        chainedIterator.hasNext();
        chainedIterator.remove();

        // Then an IllegalStateException is thrown
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledMoreThanOnceInARow() throws Exception {
        // Given
        Iterator<Integer> firstIterator = listBuilderWith(1).build(ArrayList.class).iterator();
        Iterator<Integer> secondIterator = listBuilderWith(2).build(ArrayList.class).iterator();

        // When
        Iterator<Integer> chainedIterator = new ChainedIterator<Integer>(firstIterator, secondIterator);

        chainedIterator.next();
        chainedIterator.remove();
        chainedIterator.remove();

        // Then an IllegalStateException is thrown
    }

    @Test
    public void shouldAllowNullValuesInTheIterator() throws Exception {
        // Given
        Iterator<Integer> firstIterator = iterableWith(1, null).iterator();
        Iterator<Integer> secondIterator = iterableWith(2).iterator();

        // When
        Iterator<Integer> chainedIterator = new ChainedIterator<Integer>(firstIterator, secondIterator);

        // Then
        assertThat(chainedIterator.next(), is(1));
        assertThat(chainedIterator.next(), is(nullValue()));
        assertThat(chainedIterator.next(), is(2));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldIncludeTheCurrentIteratorInTheToString() throws Exception {
        // Given
        Iterator<Integer> firstIterator = (Iterator<Integer>) mock(Iterator.class);
        Iterator<Integer> secondIterator = iteratorWith(2);

        when(firstIterator.toString()).thenReturn("first iterator toString");

        Iterator<Integer> chainedIterator = new ChainedIterator<Integer>(firstIterator, secondIterator);

        // When
        String toString = chainedIterator.toString();

        // Then
        assertThat(toString, containsString("first iterator toString"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfIteratorSuppliedAtConstructionTimeIsNull() throws Exception {
        // Given
        Iterator<? extends Iterator<Integer>> input = null;

        // When
        new ChainedIterator<Integer>(input);

        // Then a NullPointerException is thrown
    }
}
