/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.javafunk.funk.datastructures.tuples.TwoTuple;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.Literals.tuple;

public class ZippedIteratorTest {
    @Test
    public void shouldZipTheSuppliedIterators() throws Exception {
        // Given
        Iterator<String> firstIterator = listWith("A", "B").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        // Then
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(tuple("A", 1)));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(tuple("B", 2)));
        assertThat(zippedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldOnlyHaveAsManyElementsAsTheSecondIteratorIfTheFirstIsLonger() {
        // Given
        Iterator<String> firstIterator = listWith("A", "B", "C", "D").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        // Then
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(tuple("A", 1)));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(tuple("B", 2)));
        assertThat(zippedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldOnlyHaveAsManyElementsAsTheFirstIteratorIfTheSecondIsLonger() {
        // Given
        Iterator<String> firstIterator = listWith("A", "B", "C").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2, 3, 4, 5).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        // Then
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(tuple("A", 1)));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(tuple("B", 2)));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(tuple("C", 3)));
        assertThat(zippedIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutCallingHasNext() throws Exception {
        // Given
        Iterator<String> firstIterator = listWith("A", "B").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        // Then
        assertThat(zippedIterator.next(), is(tuple("A", 1)));
        assertThat(zippedIterator.next(), is(tuple("B", 2)));
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() throws Exception {
        // Given
        Iterator<String> firstIterator = listWith("A", "B").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        // Then
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(tuple("A", 1)));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.hasNext(), is(true));
        assertThat(zippedIterator.next(), is(tuple("B", 2)));
        assertThat(zippedIterator.hasNext(), is(false));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowAnUnsupportedOperationExceptionWhenTryingToRemoveElements() throws Exception {
        // Given
        Iterator<String> firstIterator = listWith("A", "B").iterator();
        Iterator<Integer> secondIterator = listWith(1, 2).iterator();

        // When
        Iterator<TwoTuple<String, Integer>> zippedIterator = new ZippedIterator<String, Integer>(firstIterator, secondIterator);

        zippedIterator.next();
        zippedIterator.remove();

        // Then an UnsupportedOperationException is thrown
    }
}
