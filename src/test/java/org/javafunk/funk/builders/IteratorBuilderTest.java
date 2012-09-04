/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import org.javafunk.funk.Iterators;
import org.junit.Test;

import java.util.Iterator;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.builders.IteratorBuilder.iteratorBuilder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class IteratorBuilderTest {
    @Test
    public void shouldAllowElementsToBeAddedToTheIteratorWithWith() throws Exception {
        // Given
        IteratorBuilder<String> iteratorBuilder = iteratorBuilder();
        Iterator<String> expected = iterableWith("first", "second", "third", "fourth", "fifth", "sixth").iterator();

        // When
        Iterator<String> actual = iteratorBuilder
                .with("first", "second", "third")
                .with("fourth", "fifth", "sixth")
                .build();

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheIteratorWithWith() throws Exception {
        // Given
        Iterator<Integer> expected = iterableWith(5, 10, 15, 20, 25, 30).iterator();
        Integer[] firstElementArray = new Integer[]{5, 10, 15};
        Integer[] secondElementArray = new Integer[]{20, 25, 30};

        // When
        Iterator<Integer> actual = iteratorBuilder(Integer.class)
                .with(firstElementArray)
                .with(secondElementArray)
                .build();

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test
    public void shouldAllowIteratorsOfElementsToBeAddedToTheIteratorWithWith() throws Exception {
        // Given
        IteratorBuilder<Integer> iteratorBuilder = iteratorBuilder();
        Iterator<Integer> expected = iterableWith(1, 2, 3, 4, 5, 6).iterator();
        Iterable<Integer> firstInputIterable = iterableWith(1, 2, 3);
        Iterable<Integer> secondInputIterable = iterableWith(4, 5, 6);

        // When
        Iterator<Integer> actual = iteratorBuilder
                .with(firstInputIterable)
                .with(secondInputIterable)
                .build();

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test
    public void shouldAllowElementsToBeAddedToTheIteratorWithAnd() {
        // Given
        IteratorBuilder<Integer> iteratorBuilder = iteratorBuilder();
        Iterator<Integer> expected = iterableWith(5, 10, 15, 20, 25, 30, 35, 40, 45).iterator();

        // When
        Iterator<Integer> actual = iteratorBuilder
                .with(5, 10, 15)
                .and(20, 25, 30)
                .and(35, 40, 45)
                .build();

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheIteratorWithAnd() throws Exception {
        // Given
        IteratorBuilder<Integer> iteratorBuilder = iteratorBuilder();
        Iterator<Integer> expected = iterableWith(5, 10, 15, 20, 25, 30).iterator();
        Integer[] elementArray = new Integer[]{20, 25, 30};

        // When
        Iterator<Integer> actual = iteratorBuilder
                .with(5, 10, 15)
                .and(elementArray)
                .build();

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheIteratorWithAnd() throws Exception {
        // Given
        IteratorBuilder<Integer> iteratorBuilder = iteratorBuilder();
        Iterator<Integer> expected = iterableWith(1, 2, 3, 4, 5, 6).iterator();
        Iterable<Integer> someOtherElements = iterableWith(4, 5, 6);

        // When
        Iterator<Integer> actual = iteratorBuilder
                .with(1, 2, 3)
                .and(someOtherElements)
                .build();

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test
    public void shouldAddElementsCorrectlyWhenElementTypeIsIterable() throws Exception {
        // Given
        IteratorBuilder<Iterable<Integer>> iteratorBuilder = iteratorBuilder();
        Iterable<Integer> firstElement = iterableWith(1, 2);
        Iterable<Integer> secondElement = iterableWith(3, 4, 5);
        Iterable<Integer> thirdElement = iterableWith(6, 7, 8);
        Iterable<Integer> fourthElement = iterableWith(9, 10);

        @SuppressWarnings("unchecked") Iterable<Iterable<Integer>> iterableOfElements = asList(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Iterable<Integer>[] arrayOfElements = new Iterable[]{fourthElement};
        @SuppressWarnings("unchecked") Iterator<Iterable<Integer>> expected = iterableWith(firstElement, secondElement, thirdElement, fourthElement).iterator();

        // When
        Iterator<Iterable<Integer>> actual = iteratorBuilder
                .with(firstElement)
                .and(iterableOfElements)
                .and(arrayOfElements)
                .build();

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }

    @Test
    public void shouldAddElementsCorrectlyWhenElementTypeIsArray() throws Exception {
        // Given
        IteratorBuilder<Integer[]> IteratorBuilder = iteratorBuilder();
        Integer[] firstElement = new Integer[]{1, 2};
        Integer[] secondElement = new Integer[]{3, 4, 5};
        Integer[] thirdElement = new Integer[]{6, 7, 8};
        Integer[] fourthElement = new Integer[]{9, 10};

        Iterable<Integer[]> iterableOfElements = iterableWith(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Integer[][] arrayOfElements = new Integer[][]{fourthElement};
        Iterator<Integer[]> expected = iterableWith(firstElement, secondElement, thirdElement, fourthElement).iterator();

        // When
        Iterator<Integer[]> actual = IteratorBuilder
                .with(firstElement)
                .and(iterableOfElements)
                .and(arrayOfElements)
                .build();

        // Then
        assertThat(Iterators.asList(actual), hasOnlyItemsInOrder(Iterators.asList(expected)));
    }
}
