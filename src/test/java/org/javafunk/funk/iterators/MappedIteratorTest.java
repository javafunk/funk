/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.javafunk.funk.functors.Mapper;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;

public class MappedIteratorTest {
    @Test
    public void shouldHaveNextAsLongAsDelegateHasNext() {
        // Given
        Iterator<Integer> delegateIterator = iterableWith(1, 2, 3).iterator();

        // When
        MappedIterator<Integer, String> iterator = new MappedIterator<Integer, String>(
                delegateIterator, stringValueMapFunction());

        // Then
        assertThat(iterator.hasNext(), is(true));
        iterator.next();
        assertThat(iterator.hasNext(), is(true));
        iterator.next();
        assertThat(iterator.hasNext(), is(true));
        iterator.next();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldMapTheValuesOfTheDelegateIteratorUsingTheSuppliedMapFunction() {
        // Given
        Iterator<Integer> delegateIterator = iterableWith(1, 2, 3).iterator();

        // When
        MappedIterator<Integer, String> iterator = new MappedIterator<Integer, String>(
                delegateIterator, stringValueMapFunction());

        // Then
        assertThat(iterator.next(), is("1"));
        assertThat(iterator.next(), is("2"));
        assertThat(iterator.next(), is("3"));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionIfNoMoreElements() {
        // Given
        Iterator<Integer> delegateIterator = iterableWith(1, 2, 3).iterator();

        // When
        MappedIterator<Integer, String> iterator = new MappedIterator<Integer, String>(
                delegateIterator, stringValueMapFunction());
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldRemoveTheElementFromTheUnderlyingIterator() {
        // Given
        Collection<Integer> initialElements = collectionWith(1, 2, 3);
        Collection<Integer> expectedElements = collectionWith(3);
        Iterator<Integer> delegateIterator = initialElements.iterator();

        // When
        MappedIterator<Integer, String> iterator = new MappedIterator<Integer, String>(
                delegateIterator, stringValueMapFunction());

        iterator.next();
        iterator.remove();
        iterator.next();
        iterator.remove();

        // Then
        assertThat(initialElements, is(expectedElements));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledBeforeNext() throws Exception {
        // Given
        Iterator<Integer> delegateIterator = iterableWith(1, 2, 3).iterator();

        // When
        MappedIterator<Integer, String> iterator = new MappedIterator<Integer, String>(
                delegateIterator, stringValueMapFunction());

        iterator.hasNext();
        iterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledMoreThanOnceInARow() throws Exception {
        // Given
        Iterator<Integer> delegateIterator = iterableWith(1, 2, 3).iterator();

        // When
        MappedIterator<Integer, String> iterator = new MappedIterator<Integer, String>(
                delegateIterator, stringValueMapFunction());

        iterator.next();
        iterator.remove();
        iterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test
    public void shouldAllowNullValuesInTheIterator() throws Exception {
        // Given
        Iterator<Integer> delegateIterator = iterableWith(1, null, 2).iterator();

        // When
        MappedIterator<Integer, Integer> iterator = new MappedIterator<Integer, Integer>(delegateIterator,
                new Mapper<Integer, Integer>() {
                    @Override
                    public Integer map(Integer input) {
                        return input == null ? null : input * 2;
                    }
                });

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(nullValue()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(4));
        assertThat(iterator.hasNext(), is(false));
    }

    private Mapper<Integer, String> stringValueMapFunction() {
        return new Mapper<Integer, String>() {
            public String map(Integer input) {
                return String.valueOf(input);
            }
        };
    }
}
