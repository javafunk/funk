/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.builders.SetBuilder.setBuilder;

public class SetBuilderTest {
    @Test
    public void shouldAllowElementsToBeAddedToTheSetWithWith() throws Exception {
        // Given
        SetBuilder<String> setBuilder = setBuilder();
        Set<String> expected = new HashSet<String>(
                asList("first", "second", "third", "fourth", "fifth", "sixth"));

        // When
        Set<String> actual = setBuilder
                .with("first", "second", "third")
                .with("fourth", "fifth", "sixth")
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheSetWithWith() throws Exception {
        // Given
        Set<Integer> expectedSet = new HashSet<Integer>(asList(5, 10, 15, 20, 25, 30));
        Integer[] firstElementArray = new Integer[]{5, 10, 15};
        Integer[] secondElementArray = new Integer[]{20, 25, 30};

        // When
        Set<Integer> actualSet = setBuilder(Integer.class)
                .with(firstElementArray)
                .with(secondElementArray)
                .build();

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheSetWithWith() throws Exception {
        // Given
        SetBuilder<Integer> setBuilder = setBuilder();
        Set<Integer> expectedSet = new HashSet<Integer>(asList(1, 2, 3, 4, 5, 6));
        Iterable<Integer> firstInputIterable = asList(1, 2, 3);
        Iterable<Integer> secondInputIterable = asList(4, 5, 6);

        // When
        Set<Integer> actualSet = setBuilder
                .with(firstInputIterable)
                .with(secondInputIterable)
                .build();

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldAllowElementsToBeAddedToTheSetWithAnd() {
        // Given
        SetBuilder<Integer> setBuilder = setBuilder();
        Set<Integer> expectedSet = new HashSet<Integer>(asList(5, 10, 15, 20, 25, 30, 35, 40, 45));

        // When
        Set<Integer> actualSet = setBuilder
                .with(5, 10, 15)
                .and(20, 25, 30)
                .and(35, 40, 45)
                .build();

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheSetWithAnd() throws Exception {
        // Given
        SetBuilder<Integer> setBuilder = setBuilder();
        Set<Integer> expectedSet = new HashSet<Integer>(asList(5, 10, 15, 20, 25, 30));
        Integer[] elementArray = new Integer[]{20, 25, 30};

        // When
        Set<Integer> actualSet = setBuilder
                .with(5, 10, 15)
                .and(elementArray)
                .build();

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheSetWithAnd() throws Exception {
        // Given
        SetBuilder<Integer> setBuilder = setBuilder();
        Set<Integer> expectedSet = new HashSet<Integer>(asList(1, 2, 3, 4, 5, 6));
        Iterable<Integer> someOtherElements = asList(4, 5, 6);

        // When
        Set<Integer> actualSet = setBuilder
                .with(1, 2, 3)
                .and(someOtherElements)
                .build();

        // Then
        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void shouldAddElementsCorrectlyWhenElementTypeIsIterable() throws Exception {
        // Given
        SetBuilder<Iterable<Integer>> setBuilder = setBuilder();
        Iterable<Integer> firstElement = iterableWith(1, 2);
        Iterable<Integer> secondElement = iterableWith(3, 4, 5);
        Iterable<Integer> thirdElement = iterableWith(6, 7, 8);
        Iterable<Integer> fourthElement = iterableWith(9, 10);

        Iterable<Iterable<Integer>> iterableOfElements = iterableWith(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Iterable<Integer>[] arrayOfElements = new Iterable[]{fourthElement};

        Set<Iterable<Integer>> expected = new HashSet<Iterable<Integer>>();
        expected.add(firstElement);
        expected.add(secondElement);
        expected.add(thirdElement);
        expected.add(fourthElement);

        // When
        Set<Iterable<Integer>> actual = setBuilder
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
        SetBuilder<Integer[]> setBuilder = setBuilder();
        Integer[] firstElement = new Integer[]{1, 2};
        Integer[] secondElement = new Integer[]{3, 4, 5};
        Integer[] thirdElement = new Integer[]{6, 7, 8};
        Integer[] fourthElement = new Integer[]{9, 10};

        Iterable<Integer[]> iterableOfElements = iterableWith(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Integer[][] arrayOfElements = new Integer[][]{fourthElement};

        Set<Integer[]> expected = new HashSet<Integer[]>();
        expected.add(firstElement);
        expected.add(secondElement);
        expected.add(thirdElement);
        expected.add(fourthElement);

        // When
        Set<Integer[]> actual = setBuilder
                .with(firstElement)
                .and(iterableOfElements)
                .and(arrayOfElements)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldBuildASetOfTheSpecifiedImplementation() throws Exception {
        // Given
        Set<Integer> expected = setWith(1, 2, 3);
        SetBuilder<Integer> setBuilder = setBuilderWith(1, 2, 3);

        // When
        Set<Integer> actual = setBuilder.build(TreeSet.class);

        // Then
        assertThat(actual instanceof TreeSet, is(true));
        assertThat(actual, is(expected));
    }

    @Test(expected = IllegalAccessException.class)
    public void shouldThrowAnIllegalAccessExceptionIfTheSpecifiedImplementationDoesNotHaveAnAccessibleConstructor() throws Exception {
        // Given
        SetBuilder<Integer> setBuilder = setBuilderWith(1, 2, 3);

        // When
        setBuilder.build(ImmutableSet.class);

        // Then an InstantiationException is thrown
    }

    @Test(expected = InstantiationException.class)
    public void shouldThrowAnInstantiationExceptionIfTheSpecifiedImplementationDoesNotHaveANoArgsConstructor() throws Exception {
        // Given
        SetBuilder<Integer> setBuilder = setBuilderWith(1, 2, 3);

        // When
        setBuilder.build(NoNoArgsConstructorSet.class);

        // Then an InstantiationException is thrown
    }

    private static class NoNoArgsConstructorSet<E> extends HashSet<E> {
        public NoNoArgsConstructorSet(Throwable argument) {
            throw new UnsupportedOperationException("should never throw", argument);
        }
    }
}
