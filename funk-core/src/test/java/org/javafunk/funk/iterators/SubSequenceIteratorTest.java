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
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.javafunk.funk.Literals.collectionBuilderWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.junit.Assert.fail;

public class SubSequenceIteratorTest {
    @Test
    public void shouldTakeElementsFromTheIteratorBetweenTheSpecifiedStartAndStopWithTheSpecifiedStep() throws Exception {
        // Given
        Iterator<String> input = iterableWith("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k").iterator();

        // When
        Iterator<String> subSequenceIterator = new SubSequenceIterator<String>(input, 2, 9, 3);

        // Then
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is("c"));
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is("f"));
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is("i"));
        assertThat(subSequenceIterator.hasNext(), is(false));
    }

    @Test
    public void shouldReturnAnEmptyIteratorIfSuppliedStartIsEqualToSuppliedStop() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();
        Integer start = 2, stop = 2, step = 1;

        // When
        Iterator<Integer> iterator = new SubSequenceIterator<Integer>(input, start, stop, step);

        // Then
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldReturnAnEmptyIteratorIfSuppliedStartAndStopAreBothZero() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();
        Integer start = 0, stop = 0, step = 2;

        // When
        Iterator<Integer> iterator = new SubSequenceIterator<Integer>(input, start, stop, step);

        // Then
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfSuppliedStartIsLessThanZero() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();
        Integer start = -5, stop = 2, step = 2;

        // When
        new SubSequenceIterator<Integer>(input, start, stop, step);

        // Then an IllegalArgumentException should be thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfSuppliedStopIsLessThanZero() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();
        Integer start = 0, stop = -5, step = 2;

        // When
        new SubSequenceIterator<Integer>(input, start, stop, step);

        // Then an IllegalArgumentException should be thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfSuppliedStepIsZero() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();
        Integer start = 1, stop = 2, step = 0;

        // When
        new SubSequenceIterator<Integer>(input, start, stop, step);

        // Then an IllegalArgumentException should be thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfSuppliedStepIsLessThanZero() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();
        Integer start = 1, stop = 2, step = -3;

        // When
        new SubSequenceIterator<Integer>(input, start, stop, step);

        // Then an IllegalArgumentException should be thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionIfSuppliedStartIsGreaterThanSuppliedStop() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();
        Integer start = 6, stop = 4, step = 1;

        // When
        new SubSequenceIterator<Integer>(input, start, stop, step);

        // Then an IllegalArgumentException should be thrown
    }

    @Test
    public void shouldDefaultToZeroForStartIfNullSupplied() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4, 5, 6, 7, 8).iterator();
        Integer start = null, stop = 7, step = 2;

        // When
        SubSequenceIterator<Integer> iterator = new SubSequenceIterator<Integer>(input, start, stop, step);

        // Then
        assertThat(iterator.next(), is(0));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(4));
        assertThat(iterator.next(), is(6));
    }

    @Test
    public void shouldDefaultToTheEndOfTheIteratorForStopIfNullSupplied() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4, 5, 6, 7, 8).iterator();
        Integer start = 1, stop = null, step = 2;

        // When
        SubSequenceIterator<Integer> iterator = new SubSequenceIterator<Integer>(input, start, stop, step);

        // Then
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(3));
        assertThat(iterator.next(), is(5));
        assertThat(iterator.next(), is(7));

        try {
            iterator.next();
            fail("Expected a NoSuchElementException to be thrown");
        } catch (NoSuchElementException exception) {
            // continue
        }
    }

    @Test
    public void shouldDefaultToAStepOfOne() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();

        // When
        Iterator<Integer> subSequenceIterator = new SubSequenceIterator<Integer>(input, 1, 4);

        // Then
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is(1));
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is(2));
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is(3));
        assertThat(subSequenceIterator.hasNext(), is(false));
    }

    @Test
    public void shouldDefaultToAStepOfOneIfNullSupplied() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();

        // When
        Iterator<Integer> subSequenceIterator = new SubSequenceIterator<Integer>(input, 1, 4, null);

        // Then
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is(1));
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is(2));
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is(3));
        assertThat(subSequenceIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutCallingHasNext() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();

        // When
        Iterator<Integer> subSequenceIterator = new SubSequenceIterator<Integer>(input, 1, 4);

        // Then
        assertThat(subSequenceIterator.next(), is(1));
        assertThat(subSequenceIterator.next(), is(2));
        assertThat(subSequenceIterator.next(), is(3));
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4, 5, 6).iterator();

        // When
        Iterator<Integer> subSequenceIterator = new SubSequenceIterator<Integer>(input, 1, 6, 2);

        // Then
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is(1));
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is(3));
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.hasNext(), is(true));
        assertThat(subSequenceIterator.next(), is(5));
        assertThat(subSequenceIterator.hasNext(), is(false));
        assertThat(subSequenceIterator.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionWhenStopHasBeenReached() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();

        // When
        Iterator<Integer> subSequenceIterator = new SubSequenceIterator<Integer>(input, 1, 2);

        subSequenceIterator.next();
        subSequenceIterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowANoSuchElementExceptionIfTheUnderlyingIteratorIsExhausted() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(0, 1, 2, 3, 4).iterator();

        // When
        Iterator<Integer> subSequenceIterator = new SubSequenceIterator<Integer>(input, 3, 10);

        subSequenceIterator.next();
        subSequenceIterator.next();
        subSequenceIterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldAllowNullValuesInTheIterator() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, null, 3, 4);

        // When
        Iterator<Integer> iterator = new SubSequenceIterator<Integer>(input.iterator(), 1, 4);

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(nullValue()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(3));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldRemoveTheElementFromTheUnderlyingIterator() throws Exception {
        // Given
        Iterable<String> input = collectionBuilderWith("a", "aa", "aaa", "aaaa").build(ArrayList.class);
        Iterable<String> expectedOutput = collectionBuilderWith("a", "aaa", "aaaa").build(ArrayList.class);

        // When
        Iterator<String> iterator = new SubSequenceIterator<String>(input.iterator(), 1, 3);

        iterator.next();
        iterator.remove();

        // Then
        assertThat(input, is(expectedOutput));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledBeforeNext() throws Exception {
        // Given
        Iterable<String> input = iterableWith("a", "aa", "aaa", "aaaa");

        // When
        Iterator<String> iterator = new SubSequenceIterator<String>(input.iterator(), 1, 3);

        iterator.hasNext();
        iterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledMoreThanOnceInARow() throws Exception {
        // Given
        Iterable<String> input = collectionBuilderWith("a", "aa", "aaa", "aaaa").build(ArrayList.class);

        // When
        Iterator<String> iterator = new SubSequenceIterator<String>(input.iterator(), 1, 3);

        iterator.next();
        iterator.remove();
        iterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test
    public void shouldNotRemoveAnElementAfterStopHasBeenReached() throws Exception {
        // Given
        Collection<String> input = collectionBuilderWith("a", "aa", "aaa", "aaaa").build(ArrayList.class);
        Collection<String> expectedResult = collectionBuilderWith("a", "aaa", "aaaa").build(ArrayList.class);

        // When
        Iterator<String> iterator = new SubSequenceIterator<String>(input.iterator(), 1, 2);

        // Then
        assertThat(iterator.next(), is("aa"));
        iterator.remove();
        assertThat(iterator.hasNext(), is(false));

        try {
            iterator.remove();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException exception) {
            // continue
        }

        assertThat(input, is(expectedResult));
    }

    @Test
    public void shouldNotRemoveAnElementAfterStopHasBeenReachedEvenIfNextHasBeenCalled() throws Exception {
        // Given
        Collection<String> input = collectionBuilderWith("a", "aa", "aaa", "aaaa").build(ArrayList.class);
        Collection<String> expectedResult = collectionBuilderWith("a", "aaa", "aaaa").build(ArrayList.class);

        // When
        Iterator<String> iterator = new SubSequenceIterator<String>(input.iterator(), 1, 2);

        // Then
        assertThat(iterator.next(), is("aa"));
        iterator.remove();

        try {
            iterator.next();
            fail("Expected a NoSuchElementException");
        } catch (NoSuchElementException exception) {
            // continue
        }

        try {
            iterator.remove();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException exception) {
            // continue
        }

        assertThat(input, is(expectedResult));
    }
}
