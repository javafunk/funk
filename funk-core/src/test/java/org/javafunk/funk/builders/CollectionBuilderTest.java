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
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.builders.CollectionBuilder.collectionBuilder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInAnyOrder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.fail;

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
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
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
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
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
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
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
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
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
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
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
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
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
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
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
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
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
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
    }

    @Test
    public void shouldAddElementsToACollectionOfTheSpecifiedImplementationInTheOrderTheyWereAddedToTheBuilder() throws Exception {
        // Given
        Collection<Integer> expected = new ArrayList<Integer>(listWith(8, 7, 6, 5, 4));
        CollectionBuilder<Integer> collectionBuilder = collectionBuilderWith(8, 7)
                .and(6, 5)
                .and(4);

        // When
        Collection<Integer> actual = collectionBuilder.build(ArrayList.class);

        // Then
        List<Integer> expectedElements = listFrom(expected);
        List<Integer> actualElements = listFrom(actual);
        assertThat(actualElements, hasOnlyItemsInOrder(expectedElements));
    }

    @Test
    public void shouldThrowAnIllegalArgumentExceptionIfTheSpecifiedImplementationDoesNotHaveAnAccessibleConstructor() throws Exception {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilderWith(1, 2, 3);

        try {
            // When
            collectionBuilder.build(ImmutableList.class);
            fail("Expected an IllegalArgumentException but got nothing.");
        } catch (IllegalArgumentException exception) {
            // Then
            assertThat(exception.getMessage(),
                    containsString(
                            "Could not instantiate instance of type ImmutableList. " +
                                    "Does it have a public no argument constructor?"));
        }
    }

    @Test
    public void shouldThrowAnIllegalArgumentExceptionIfTheSpecifiedImplementationDoesNotHaveANoArgsConstructor() throws Exception {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilderWith(1, 2, 3);

        try {
            // When
            collectionBuilder.build(NoNoArgsConstructorStack.class);
            fail("Expected an IllegalArgumentException but got nothing.");
        } catch (IllegalArgumentException exception) {
            // Then
            assertThat(exception.getMessage(),
                    containsString(
                            "Could not instantiate instance of type NoNoArgsConstructorStack. " +
                                    "Does it have a public no argument constructor?"));
        }
    }

    @Test
    public void shouldPassAccumulatedElementsToTheSuppliedBuilderFunctionAndReturnTheResult() throws Exception {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilderWith(1, 2, 3);
        Collection<Integer> expected = collectionWith(1, 2, 3);

        // When
        Collection<Integer> actual = collectionBuilder.build(new UnaryFunction<Iterable<Integer>, Collection<Integer>>() {
            @Override public Collection<Integer> call(Iterable<Integer> elements) {
                Stack<Integer> collection = new Stack<Integer>();
                for (Integer element : elements) {
                    collection.push(element);
                }
                return collection;
            }
        });

        // Then
        assertThat(actual instanceof Stack, is(true));
        assertThat(actual, hasOnlyItemsInAnyOrder(expected));
    }

    @Test
    public void shouldPassElementsToTheSuppliedBuilderFunctionInTheOrderPassedToTheBuilder() throws Exception {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilderWith(1, 2, 3)
                .and(4, 5, 6);
        final List<Integer> expectedElements = listWith(1, 2, 3, 4, 5, 6);

        // When
        collectionBuilder.build(new UnaryFunction<Iterable<Integer>, Collection<Integer>>() {
            @Override public Collection<Integer> call(Iterable<Integer> elements) {
                List<Integer> actualElements = listFrom(elements);

                // Then
                assertThat(actualElements, hasOnlyItemsInOrder(expectedElements));

                return actualElements;
            }
        });
    }

    @Test
    public void shouldAllowConcreteTypeOfCollectionToBeReturnedWhenBuildingUsingBuilderFunction() throws Exception {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilderWith(1, 2, 3)
                .and(4, 5, 6);
        Stack<Integer> expectedStack = new Stack<Integer>();
        expectedStack.addAll(listWith(1, 2, 3, 4, 5, 6));

        // When
        Stack<Integer> actualStack = collectionBuilder.build(new UnaryFunction<Iterable<Integer>, Stack<Integer>>() {
            @Override public Stack<Integer> call(Iterable<Integer> elements) {
                Stack<Integer> stack = new Stack<Integer>();
                for (Integer element : elements) {
                    stack.push(element);
                }
                return stack;
            }
        });

        // Then
        assertThat(actualStack, is(expectedStack));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfUnaryFunctionSuppliedToBuildIsNull() throws Exception {
        // Given
        CollectionBuilder<Integer> collectionBuilder = collectionBuilderWith(1, 2, 3);
        UnaryFunction<Iterable<Integer>, Collection<Integer>> unaryFunction = null;

        // When
        collectionBuilder.build(unaryFunction);

        // Then a NullPointerException is thrown.
    }

    private static class NoNoArgsConstructorStack<E> extends Stack<E> {
        public NoNoArgsConstructorStack(Throwable argument) {
            throw new UnsupportedOperationException("should never throw", argument);
        }
    }
}
