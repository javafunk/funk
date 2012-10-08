/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.builders.MultisetBuilder.multisetBuilder;
import static org.junit.Assert.fail;

public class MultisetBuilderTest {
    @Test
    public void shouldAllowElementsToBeAddedToTheMultisetWithWith() throws Exception {
        // Given
        MultisetBuilder<String> multisetBuilder = multisetBuilder();
        Multiset<String> expected = HashMultiset.create(
                iterableWith("first", "second", "third", "fourth", "fifth", "sixth"));

        // When
        Multiset<String> actual = multisetBuilder
                .with("first", "second", "third")
                .with("fourth", "fifth", "sixth")
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheMultisetWithWith() throws Exception {
        // Given
        Multiset<Integer> expectedMultiset = HashMultiset.create(iterableWith(5, 10, 15, 20, 25, 30));
        Integer[] firstElementArray = new Integer[]{5, 10, 15};
        Integer[] secondElementArray = new Integer[]{20, 25, 30};

        // When
        Multiset<Integer> actualMultiset = multisetBuilder(Integer.class)
                .with(firstElementArray)
                .with(secondElementArray)
                .build();

        // Then
        assertThat(actualMultiset, is(expectedMultiset));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheMultisetWithWith() throws Exception {
        // Given
        MultisetBuilder<Integer> multisetBuilder = multisetBuilder();
        Multiset<Integer> expectedMultiset = HashMultiset.create(iterableWith(1, 2, 3, 4, 5, 6));
        Iterable<Integer> firstInputIterable = asList(1, 2, 3);
        Iterable<Integer> secondInputIterable = asList(4, 5, 6);

        // When
        Multiset<Integer> actualMultiset = multisetBuilder
                .with(firstInputIterable)
                .with(secondInputIterable)
                .build();

        // Then
        assertThat(actualMultiset, is(expectedMultiset));
    }

    @Test
    public void shouldAllowElementsToBeAddedToTheMultisetWithAnd() {
        // Given
        MultisetBuilder<Integer> multisetBuilder = multisetBuilder();
        Multiset<Integer> expectedMultiset = HashMultiset.create(iterableWith(5, 10, 15, 20, 25, 30, 35, 40, 45));

        // When
        Multiset<Integer> actualMultiset = multisetBuilder
                .with(5, 10, 15)
                .and(20, 25, 30)
                .and(35, 40, 45)
                .build();

        // Then
        assertThat(actualMultiset, is(expectedMultiset));
    }

    @Test
    public void shouldAllowArraysOfElementsToBeAddedToTheMultisetWithAnd() throws Exception {
        // Given
        MultisetBuilder<Integer> multisetBuilder = multisetBuilder();
        Multiset<Integer> expectedMultiset = HashMultiset.create(iterableWith(5, 10, 15, 20, 25, 30));
        Integer[] elementArray = new Integer[]{20, 25, 30};

        // When
        Multiset<Integer> actualMultiset = multisetBuilder
                .with(5, 10, 15)
                .and(elementArray)
                .build();

        // Then
        assertThat(actualMultiset, is(expectedMultiset));
    }

    @Test
    public void shouldAllowIterablesOfElementsToBeAddedToTheMultisetWithAnd() throws Exception {
        // Given
        MultisetBuilder<Integer> multisetBuilder = multisetBuilder();
        Multiset<Integer> expectedMultiset = HashMultiset.create(iterableWith(1, 2, 3, 4, 5, 6));
        Iterable<Integer> someOtherElements = asList(4, 5, 6);

        // When
        Multiset<Integer> actualMultiset = multisetBuilder
                .with(1, 2, 3)
                .and(someOtherElements)
                .build();

        // Then
        assertThat(actualMultiset, is(expectedMultiset));
    }

    @Test
    public void shouldAddElementsCorrectlyWhenElementTypeIsIterable() throws Exception {
        // Given
        MultisetBuilder<Iterable<Integer>> multisetBuilder = multisetBuilder();
        Iterable<Integer> firstElement = iterableWith(1, 2);
        Iterable<Integer> secondElement = iterableWith(3, 4, 5);
        Iterable<Integer> thirdElement = iterableWith(6, 7, 8);
        Iterable<Integer> fourthElement = iterableWith(9, 10);

        Iterable<Iterable<Integer>> iterableOfElements = iterableWith(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Iterable<Integer>[] arrayOfElements = new Iterable[]{fourthElement};

        Multiset<Iterable<Integer>> expected = HashMultiset.create();
        expected.add(firstElement);
        expected.add(secondElement);
        expected.add(thirdElement);
        expected.add(fourthElement);

        // When
        Multiset<Iterable<Integer>> actual = multisetBuilder
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
        MultisetBuilder<Integer[]> multisetBuilder = multisetBuilder();
        Integer[] firstElement = new Integer[]{1, 2};
        Integer[] secondElement = new Integer[]{3, 4, 5};
        Integer[] thirdElement = new Integer[]{6, 7, 8};
        Integer[] fourthElement = new Integer[]{9, 10};

        Iterable<Integer[]> iterableOfElements = iterableWith(secondElement, thirdElement);
        @SuppressWarnings("unchecked") Integer[][] arrayOfElements = new Integer[][]{fourthElement};

        Multiset<Integer[]> expected = HashMultiset.create();
        expected.add(firstElement);
        expected.add(secondElement);
        expected.add(thirdElement);
        expected.add(fourthElement);

        // When
        Multiset<Integer[]> actual = multisetBuilder
                .with(firstElement)
                .and(iterableOfElements)
                .and(arrayOfElements)
                .build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldBuildAMultisetOfTheSpecifiedImplementation() throws Exception {
        // Given
        MultisetBuilder<Integer> multisetBuilder = multisetBuilderWith(1, 2, 3);

        // When
        Multiset<Integer> actual = multisetBuilder.build(NoArgsConstructorMultiset.class);

        // Then
        assertThat(actual instanceof NoArgsConstructorMultiset, is(true));
    }

    @Test
    public void shouldThrowAnIllegalArgumentExceptionIfTheSpecifiedImplementationDoesNotHaveAnAccessibleConstructor() throws Exception {
        // Given
        MultisetBuilder<Integer> multisetBuilder = multisetBuilderWith(1, 2, 3);

        try {
            // When
            multisetBuilder.build(PrivateAccessConstructorMultiset.class);
            fail("Expected an IllegalArgumentException but got nothing.");
        } catch (IllegalArgumentException exception) {
            // Then
            assertThat(exception.getMessage(),
                    containsString(
                            "Could not instantiate instance of type PrivateAccessConstructorMultiset. " +
                                    "Does it have a public no argument constructor?"));
        }
    }

    @Test
    public void shouldThrowAnIllegalArgumentExceptionIfTheSpecifiedImplementationDoesNotHaveANoArgsConstructor() throws Exception {
        // Given
        MultisetBuilder<Integer> multisetBuilder = multisetBuilderWith(1, 2, 3);

        try {
            // When
            multisetBuilder.build(SomeArgsConstructorMultiset.class);
            fail("Expected an IllegalArgumentException but got nothing.");
        } catch (IllegalArgumentException exception) {
            // Then
            assertThat(exception.getMessage(),
                    containsString(
                            "Could not instantiate instance of type SomeArgsConstructorMultiset. " +
                                    "Does it have a public no argument constructor?"));
        }
    }

    @Test
    public void shouldPassAccumulatedElementsToTheSuppliedBuilderFunctionAndReturnTheResult() throws Exception {
        // Given
        MultisetBuilder<Integer> multisetBuilder = multisetBuilderWith(1, 2, 3);
        Multiset<Integer> expected = multisetWith(1, 2, 3);

        // When
        Multiset<Integer> actual = multisetBuilder.build(new UnaryFunction<Iterable<Integer>, Multiset<Integer>>() {
            @Override public Multiset<Integer> call(Iterable<Integer> elements) {
                return ConcurrentHashMultiset.create(elements);
            }
        });

        // Then
        assertThat(actual instanceof ConcurrentHashMultiset, is(true));
        assertThat(actual, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfFunctionSuppliedToBuildIsNull() throws Exception {
        // Given
        MultisetBuilder<Integer> multisetBuilder = multisetBuilderWith(1, 2, 3);
        UnaryFunction<Iterable<Integer>, Multiset<Integer>> function = null;

        // When
        multisetBuilder.build(function);

        // Then a NullPointerException is thrown.
    }

    public static class NoArgsConstructorMultiset<E> extends StubMultiset<E> {
        public NoArgsConstructorMultiset() { }
    }

    public static class PrivateAccessConstructorMultiset<E> extends StubMultiset<E> {
        private PrivateAccessConstructorMultiset() { }
    }

    public static class SomeArgsConstructorMultiset<E> extends StubMultiset<E> {
        private SomeArgsConstructorMultiset(Object first, Object second) { }
    }

    public static class StubMultiset<E> implements Multiset<E> {
        @Override public int count(Object element) { return 0; }
        @Override public int add(E element, int occurrences) { return 0; }
        @Override public int remove(Object element, int occurrences) { return 0; }
        @Override public int setCount(E element, int count) { return 0; }
        @Override public boolean setCount(E element, int oldCount, int newCount) { return false; }
        @Override public Set<E> elementSet() { return null; }
        @Override public Set<Entry<E>> entrySet() { return null; }
        @Override public Iterator<E> iterator() { return null; }
        @Override public Object[] toArray() { return new Object[0]; }
        @Override public <T> T[] toArray(T[] ts) { return null; }
        @Override public int size() { return 0; }
        @Override public boolean isEmpty() { return false; }
        @Override public boolean contains(Object element) { return false; }
        @Override public boolean containsAll(Collection<?> elements) { return false; }
        @Override public boolean addAll(Collection<? extends E> es) { return false; }
        @Override public boolean add(E element) { return false; }
        @Override public boolean remove(Object element) { return false; }
        @Override public boolean removeAll(Collection<?> c) { return false; }
        @Override public boolean retainAll(Collection<?> c) { return false; }
        @Override public void clear() { }
    }
}
