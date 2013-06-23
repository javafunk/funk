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
import org.javafunk.funk.testclasses.NoNoArgsConstructorList;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.builders.ListBuilder.listBuilder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.fail;

public class ListBuilderTest {
    @Test
    public void shouldAllowElementsToBeAddedToTheListWithWith() throws Exception {
        // Given
        ListBuilder<String> listBuilder = listBuilder();
        List<String> expected = new ArrayList<String>(
                asList("first", "second", "third", "fourth", "fifth", "sixth"));

        // When
        List<String> actual = listBuilder
                .with("first", "second", "third")
                .with("fourth", "fifth", "sixth")
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheListWithWith() throws Exception {
        // Given
        List<Integer> expectedList = asList(5, 10, 15, 20, 25, 30);
        Integer[] firstElementArray = new Integer[]{5, 10, 15};
        Integer[] secondElementArray = new Integer[]{20, 25, 30};

        // When
        List<Integer> actualList = listBuilder(Integer.class)
                .with(firstElementArray)
                .with(secondElementArray)
                .build();

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheListWithWith() throws Exception {
        // Given
        ListBuilder<Integer> listBuilder = listBuilder();
        List<Integer> expectedList = asList(1, 2, 3, 4, 5, 6);
        Iterable<Integer> firstInputIterable = asList(1, 2, 3);
        Iterable<Integer> secondInputIterable = asList(4, 5, 6);

        // When
        List<Integer> actualList = listBuilder
                .with(firstInputIterable)
                .with(secondInputIterable)
                .build();

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldAllowElementsToBeAddedToTheListWithAnd() {
        // Given
        ListBuilder<Integer> listBuilder = listBuilder();
        List<Integer> expectedList = asList(5, 10, 15, 20, 25, 30, 35, 40, 45);

        // When
        List<Integer> actualList = listBuilder
                .with(5, 10, 15)
                .and(20, 25, 30)
                .and(35, 40, 45)
                .build();

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheListWithAnd() throws Exception {
        // Given
        ListBuilder<Integer> listBuilder = listBuilder();
        List<Integer> expectedList = asList(5, 10, 15, 20, 25, 30);
        Integer[] elementArray = new Integer[]{20, 25, 30};

        // When
        List<Integer> actualList = listBuilder
                .with(5, 10, 15)
                .and(elementArray)
                .build();

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheListWithAnd() throws Exception {
        // Given
        ListBuilder<Integer> listBuilder = listBuilder();
        List<Integer> expectedList = asList(1, 2, 3, 4, 5, 6);
        Iterable<Integer> someOtherElements = asList(4, 5, 6);

        // When
        List<Integer> actualList = listBuilder
                .with(1, 2, 3)
                .and(someOtherElements)
                .build();

        // Then
        assertThat(actualList, is(expectedList));
    }

    @Test
    public void shouldAddElementsCorrectlyWhenElementTypeIsIterable() throws Exception {
        // Given
        ListBuilder<Iterable<Integer>> listBuilder = listBuilder();
        Iterable<Integer> firstElement = iterableWith(1, 2);
        Iterable<Integer> secondElement = iterableWith(3, 4, 5);
        Iterable<Integer> thirdElement = iterableWith(6, 7, 8);
        Iterable<Integer> fourthElement = iterableWith(9, 10);

        Iterable<Iterable<Integer>> iterableOfElements = iterableWith(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Iterable<Integer>[] arrayOfElements = new Iterable[]{fourthElement};

        List<Iterable<Integer>> expected = new ArrayList<Iterable<Integer>>();
        expected.add(firstElement);
        expected.add(secondElement);
        expected.add(thirdElement);
        expected.add(fourthElement);

        // When
        List<Iterable<Integer>> actual = listBuilder
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
        ListBuilder<Integer[]> listBuilder = listBuilder();
        Integer[] firstElement = new Integer[]{1, 2};
        Integer[] secondElement = new Integer[]{3, 4, 5};
        Integer[] thirdElement = new Integer[]{6, 7, 8};
        Integer[] fourthElement = new Integer[]{9, 10};

        Iterable<Integer[]> iterableOfElements = iterableWith(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Integer[][] arrayOfElements = new Integer[][]{fourthElement};

        List<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(firstElement);
        expected.add(secondElement);
        expected.add(thirdElement);
        expected.add(fourthElement);

        // When
        List<Integer[]> actual = listBuilder
                .with(firstElement)
                .and(iterableOfElements)
                .and(arrayOfElements)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldBuildAListOfTheSpecifiedImplementation() throws Exception {
        // Given
        List<Integer> expected = listWith(1, 2, 3);
        ListBuilder<Integer> listBuilder = listBuilderWith(1, 2, 3);

        // When
        List<Integer> actual = listBuilder.build(LinkedList.class);

        // Then
        assertThat(actual instanceof LinkedList, is(true));
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAddElementsToAListOfTheSpecifiedImplementationInTheOrderTheyWereAddedToTheBuilder() throws Exception {
        // Given
        List<Integer> expected = new ArrayList<Integer>(listWith(8, 7, 6, 5, 4));
        ListBuilder<Integer> listBuilder = listBuilderWith(8, 7)
                .and(6, 5)
                .and(4);

        // When
        List<Integer> actual = listBuilder.build(ArrayList.class);

        // Then
        assertThat(actual, hasOnlyItemsInOrder(expected));
    }

    @Test
    public void shouldThrowAnIllegalArgumentExceptionIfTheSpecifiedImplementationDoesNotHaveAnAccessibleConstructor() throws Exception {
        // Given
        ListBuilder<Integer> listBuilder = listBuilderWith(1, 2, 3);

        try {
            // When
            listBuilder.build(ImmutableList.class);
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
        ListBuilder<Integer> listBuilder = listBuilderWith(1, 2, 3);

        try {
            // When
            listBuilder.build(NoNoArgsConstructorList.class);
            fail("Expected an IllegalArgumentException but got nothing.");
        } catch (IllegalArgumentException exception) {
            // Then
            assertThat(exception.getMessage(),
                    containsString(
                            "Could not instantiate instance of type NoNoArgsConstructorList. " +
                                    "Does it have a public no argument constructor?"));
        }
    }

    @Test
    public void shouldPassAccumulatedElementsToTheSuppliedBuilderFunctionAndReturnTheResult() throws Exception {
        // Given
        ListBuilder<Integer> listBuilder = listBuilderWith(1, 2, 3);
        List<Integer> expected = listWith(1, 2, 3);

        // When
        List<Integer> actual = listBuilder.build(new UnaryFunction<Iterable<Integer>, List<Integer>>() {
            @Override public List<Integer> call(Iterable<Integer> elements) {
                return ImmutableList.copyOf(elements);
            }
        });

        // Then
        assertThat(actual instanceof ImmutableList, is(true));
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldPassElementsToTheSuppliedBuilderFunctionInTheOrderPassedToTheBuilder() throws Exception {
        // Given
        ListBuilder<Integer> listBuilder = listBuilderWith(1, 2, 3)
                .and(4, 5, 6);
        final List<Integer> expectedElements = listWith(1, 2, 3, 4, 5, 6);

        // When
        listBuilder.build(new UnaryFunction<Iterable<Integer>, List<Integer>>() {
            @Override public List<Integer> call(Iterable<Integer> elements) {
                List<Integer> actualElements = listFrom(elements);

                // Then
                assertThat(actualElements, hasOnlyItemsInOrder(expectedElements));

                return actualElements;
            }
        });
    }

    @Test
    public void shouldAllowConcreteTypeOfListToBeReturnedWhenBuildingUsingBuilderFunction() throws Exception {
        // Given
        ListBuilder<Integer> listBuilder = listBuilderWith(1, 2, 3)
                .and(4, 5, 6);
        Stack<Integer> expectedStack = new Stack<Integer>();
        expectedStack.addAll(listWith(1, 2, 3, 4, 5, 6));

        // When
        Stack<Integer> actualStack = listBuilder.build(new UnaryFunction<Iterable<Integer>, Stack<Integer>>() {
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
        ListBuilder<Integer> listBuilder = listBuilderWith(1, 2, 3);
        UnaryFunction<Iterable<Integer>, List<Integer>> function = null;

        // When
        listBuilder.build(function);

        // Then a NullPointerException is thrown.
    }
}
