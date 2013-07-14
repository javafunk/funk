/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.javafunk.funk.UnaryFunctions;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.javafunk.funk.Literals.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        Collection<Integer> initialElements = collectionBuilderWith(1, 2, 3).build(ArrayList.class);
        Collection<Integer> expectedElements = collectionBuilderWith(3).build(ArrayList.class);
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
        Iterator<Integer> delegateIterator = collectionBuilderWith(1, 2, 3).build(ArrayList.class).iterator();

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
        Iterator<Integer> delegateIterator = collectionBuilderWith(1, 2, 3).build(ArrayList.class).iterator();

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
        MappedIterator<Integer, Integer> iterator = new MappedIterator<Integer, Integer>(
                delegateIterator,
                new UnaryFunction<Integer, Integer>() {
                    @Override
                    public Integer call(Integer input) {
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

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfUnaryFunctionSuppliedToConstructorIsNull() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(1, 2, 3).iterator();
        UnaryFunction<Integer, String> mapper = null;

        // When
        new MappedIterator<Integer, String>(input, mapper);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfIteratorSuppliedAtConstructionTimeIsNull() throws Exception {
        // Given
        Iterator<Integer> input = null;
        UnaryFunction<Integer, Integer> mapper = UnaryFunctions.identity();

        // When
        new MappedIterator<Integer, Integer>(input, mapper);

        // Then a NullPointerException is thrown
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldIncludeIteratorInToStringRepresentation() throws Exception {
        // Given
        Iterator<Integer> input = (Iterator<Integer>) mock(Iterator.class);
        UnaryFunction<Integer, Integer> mapper = UnaryFunctions.identity();

        when(input.toString()).thenReturn("the-iterator");

        MappedIterator<Integer, Integer> iterator =
                new MappedIterator<Integer, Integer>(input, mapper);

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("the-iterator"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldIncludeMappingFunctionInToStringRepresentation() throws Exception {
        // Given
        Iterator<Integer> input = iteratorWith(1, 2 ,3);
        UnaryFunction<Integer, Integer> mapper =
                (UnaryFunction<Integer, Integer>) mock(UnaryFunction.class);

        when(mapper.toString()).thenReturn("the-mapping-function");

        MappedIterator<Integer, Integer> iterator =
                new MappedIterator<Integer, Integer>(input, mapper);

        // When
        String toString = iterator.toString();

        // Then
        assertThat(toString, containsString("the-mapping-function"));
    }

    private UnaryFunction<Integer, String> stringValueMapFunction() {
        return new UnaryFunction<Integer, String>() {
            public String call(Integer input) {
                return String.valueOf(input);
            }
        };
    }
}
