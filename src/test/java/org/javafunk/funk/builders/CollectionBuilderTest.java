/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Collection;
import java.util.Stack;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.collectionBuilderWith;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.builders.CollectionBuilder.collectionBuilder;

public class CollectionBuilderTest {
    @Test
    public void shouldAllowElementsToBeAddedToTheCollectionWithWith() throws Exception {
        // Given
        CollectionBuilder<String> collectionBuilder = collectionBuilder();
        Collection<String> expected = asList("first", "second", "third", "fourth", "fifth", "sixth");

        // When
        Collection<String> actual = collectionBuilder
                .with("first", "second", "third")
                .with("fourth", "fifth", "sixth")
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheCollectionWithWith() throws Exception {
        // Given
        Collection<Integer> expected = asList(5, 10, 15, 20, 25, 30);
        Integer[] firstElementArray = new Integer[]{5, 10, 15};
        Integer[] secondElementArray = new Integer[]{20, 25, 30};

        // When
        Collection<Integer> actual = collectionBuilder(Integer.class)
                .with(firstElementArray)
                .with(secondElementArray)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheCollectionWithWith() throws Exception {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilder();
        Collection<Integer> expected = asList(1, 2, 3, 4, 5, 6);
        Iterable<Integer> firstInputIterable = asList(1, 2, 3);
        Iterable<Integer> secondInputIterable = asList(4, 5, 6);

        // When
        Collection<Integer> actual = collectionBuilder
                .with(firstInputIterable)
                .with(secondInputIterable)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowElementsToBeAddedToTheCollectionWithAnd() {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilder();
        Collection<Integer> expected = asList(5, 10, 15, 20, 25, 30, 35, 40, 45);

        // When
        Collection<Integer> actual = collectionBuilder
                .with(5, 10, 15)
                .and(20, 25, 30)
                .and(35, 40, 45)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheCollectionWithAnd() throws Exception {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilder();
        Collection<Integer> expected = asList(5, 10, 15, 20, 25, 30);
        Integer[] elementArray = new Integer[]{20, 25, 30};

        // When
        Collection<Integer> actual = collectionBuilder
                .with(5, 10, 15)
                .and(elementArray)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheCollectionWithAnd() throws Exception {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilder();
        Collection<Integer> expected = asList(1, 2, 3, 4, 5, 6);
        Iterable<Integer> someOtherElements = asList(4, 5, 6);

        // When
        Collection<Integer> actual = collectionBuilder
                .with(1, 2, 3)
                .and(someOtherElements)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAddElementsCorrectlyWhenElementTypeIsIterable() throws Exception {
        // Given
        CollectionBuilder<Iterable<Integer>> collectionBuilder = collectionBuilder();
        Iterable<Integer> firstElement = asList(1, 2);
        Iterable<Integer> secondElement = asList(3, 4, 5);
        Iterable<Integer> thirdElement = asList(6, 7, 8);
        Iterable<Integer> fourthElement = asList(9, 10);

        @SuppressWarnings("unchecked") Iterable<Iterable<Integer>> iterableOfElements = asList(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Iterable<Integer>[] arrayOfElements = new Iterable[]{fourthElement};
        @SuppressWarnings("unchecked") Collection<Iterable<Integer>> expected = asList(firstElement, secondElement, thirdElement, fourthElement);

        // When
        Collection<Iterable<Integer>> actual = collectionBuilder
                .with(firstElement)
                .and(iterableOfElements)
                .and(arrayOfElements)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAddElementsCorrectlyWhenElementTypeIsArray() throws Exception {
        // Given
        CollectionBuilder<Integer[]> collectionBuilder = collectionBuilder();
        Integer[] firstElement = new Integer[]{1, 2};
        Integer[] secondElement = new Integer[]{3, 4, 5};
        Integer[] thirdElement = new Integer[]{6, 7, 8};
        Integer[] fourthElement = new Integer[]{9, 10};

        Iterable<Integer[]> iterableOfElements = asList(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Integer[][] arrayOfElements = new Integer[][]{fourthElement};
        Collection<Integer[]> expected = asList(firstElement, secondElement, thirdElement, fourthElement);

        // When
        Collection<Integer[]> actual = collectionBuilder
                .with(firstElement)
                .and(iterableOfElements)
                .and(arrayOfElements)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldBuildACollectionOfTheSpecifiedImplementation() throws Exception {
        // Given
        Collection<Integer> expected = new TreeSet<Integer>(collectionWith(1, 2, 3));
        CollectionBuilder<Integer> collectionBuilder = collectionBuilderWith(1, 2, 3);

        // When
        Collection<Integer> actual = collectionBuilder.build(TreeSet.class);

        // Then
        assertThat(actual instanceof TreeSet, is(true));
        assertThat(actual, is(expected));
    }

    @Test(expected = IllegalAccessException.class)
    public void shouldThrowAnIllegalAccessExceptionIfTheSpecifiedImplementationDoesNotHaveAnAccessibleConstructor() throws Exception {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilderWith(1, 2, 3);

        // When
        collectionBuilder.build(ImmutableList.class);

        // Then an InstantiationException is thrown
    }

    @Test(expected = InstantiationException.class)
    public void shouldThrowAnInstantiationExceptionIfTheSpecifiedImplementationDoesNotHaveANoArgsConstructor() throws Exception {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilderWith(1, 2, 3);

        // When
        collectionBuilder.build(NoNoArgsConstructorStack.class);

        // Then an InstantiationException is thrown
    }

    private static class NoNoArgsConstructorStack<E> extends Stack<E> {
        public NoNoArgsConstructorStack(Throwable argument) {
            throw new UnsupportedOperationException("should never throw", argument);
        }
    }
}
