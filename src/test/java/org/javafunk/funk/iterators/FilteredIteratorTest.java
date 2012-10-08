/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.javafunk.funk.functors.Predicate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.javafunk.funk.Literals.collectionBuilderWith;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.junit.Assert.fail;

public class FilteredIteratorTest {
    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() {
        // Given
        Iterable<String> iterable = iterableWith("one", "two", "three", "four", "five");
        Predicate<String> predicate = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        };

        // When
        FilteredIterator<String> iterator = new FilteredIterator<String>(iterable.iterator(), predicate);

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("one"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("two"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("four"));
        assertThat(iterator.hasNext(), is(false));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutHavingCalledHasNext() {
        // Given
        Iterable<String> iterable = iterableWith("one", "two", "three", "four", "five");
        Predicate<String> predicate = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        };

        // When
        FilteredIterator<String> iterator = new FilteredIterator<String>(iterable.iterator(), predicate);

        // Then
        assertThat(iterator.next(), is("one"));
        assertThat(iterator.next(), is("two"));
        assertThat(iterator.next(), is("four"));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfDoesntHaveNext() {
        // Given
        Iterable<String> iterable = iterableWith("one", "two", "three", "four", "five");
        Predicate<String> predicate = new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        };

        // When
        FilteredIterator<String> iterator = new FilteredIterator<String>(iterable.iterator(), predicate);
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();

        // Then a NoSuchElementException is thrown
    }

    @Test
    public void shouldRemoveTheElementFromTheUnderlyingIterator() {
        // Given
        Collection<String> actualList = collectionBuilderWith("one", "two", "three", "four", "five").build(ArrayList.class);
        Collection<String> expectedList = collectionBuilderWith("three", "four", "five").build(ArrayList.class);
        Iterator<String> iterator = actualList.iterator();

        // When
        FilteredIterator<String> filteredIterator = new FilteredIterator<String>(iterator, new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        });

        filteredIterator.next();
        filteredIterator.remove();
        filteredIterator.next();
        filteredIterator.remove();

        // Then
        assertThat(expectedList, is(actualList));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfNextHasNotBeenCalledBeforeRemove() throws Exception {
        // Given
        Iterator<String> iterator = iterableWith("one", "two", "three").iterator();

        // When
        FilteredIterator<String> filteredIterator = new FilteredIterator<String>(iterator, new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        });

        filteredIterator.hasNext();
        filteredIterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnIllegalStateExceptionIfRemoveIsCalledMoreThanOnceInARow() throws Exception {
        // Given
        Iterator<String> iterator = collectionBuilderWith("one", "two", "three").build(ArrayList.class).iterator();

        // When
        FilteredIterator<String> filteredIterator = new FilteredIterator<String>(iterator, new Predicate<String>() {
            public boolean evaluate(String item) {
                return item.contains("o");
            }
        });

        filteredIterator.next();
        filteredIterator.remove();
        filteredIterator.remove();

        // Then an IllegalStateException should be thrown
    }

    @Test
    public void shouldNotRemoveAnElementThatDoesNotMatchTheSuppliedPredicate() throws Exception {
        // Given
        Collection<String> initialElements = collectionBuilderWith("one", "three").build(ArrayList.class);
        Collection<String> expectedElements = collectionBuilderWith("three").build(ArrayList.class);

        // When
        FilteredIterator<String> filteredIterator = new FilteredIterator<String>(initialElements.iterator(),
                new Predicate<String>() {
                    public boolean evaluate(String item) {
                        return item.contains("o");
                    }
                });

        // Then
        assertThat(filteredIterator.next(), is("one"));
        filteredIterator.remove();
        assertThat(filteredIterator.hasNext(), is(false));

        try {
            filteredIterator.remove();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException exception) {
            // continue
        }

        assertThat(initialElements, is(expectedElements));
    }

    @Test
    public void shouldNotRemoveAnElementThatDoesNotMatchTheSuppliedPredicateEvenIfNextCalled() throws Exception {
        // Given
        Collection<String> initialElements = collectionBuilderWith("one", "three").build(ArrayList.class);
        Collection<String> expectedElements = collectionBuilderWith("three").build(ArrayList.class);

        // When
        FilteredIterator<String> filteredIterator = new FilteredIterator<String>(initialElements.iterator(),
                new Predicate<String>() {
                    public boolean evaluate(String item) {
                        return item.contains("o");
                    }
                });

        // Then
        assertThat(filteredIterator.next(), is("one"));
        filteredIterator.remove();

        try {
            filteredIterator.next();
            fail("Expected a NoSuchElementException");
        } catch (NoSuchElementException exception) {
            // continue
        }

        try {
            filteredIterator.remove();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException exception) {
            // continue
        }

        assertThat(initialElements, is(expectedElements));
    }

    @Test
    public void shouldAllowNullValuesInTheIterator() throws Exception {
        // Given
        Iterator<Integer> delegateIterator = iterableWith(1, null, 10, 5).iterator();

        // When
        FilteredIterator<Integer> iterator = new FilteredIterator<Integer>(delegateIterator, new Predicate<Integer>(){
            public boolean evaluate(Integer item) {
                return item == null || item % 10 != 0;
            }
        });

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(nullValue()));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(5));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfPredicateSuppliedToConstructorIsNull() throws Exception {
        // Given
        Iterator<Integer> input = iterableWith(1, 2, 3).iterator();
        Predicate<Integer> predicate = null;

        // When
        new FilteredIterator<Integer>(input, predicate);

        // Then a NullPointerException is thrown.
    }
}
