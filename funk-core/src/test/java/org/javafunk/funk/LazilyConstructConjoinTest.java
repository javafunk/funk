/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertThat;

public class LazilyConstructConjoinTest {
    @Test
    public void shouldConstructIterableWithElementAsFirstAndSuppliedIterableAsRest() {
        // Given
        String element = "first";
        Iterable<String> others = iterableWith("second", "third");

        // When
        Iterable<String> actual = Lazily.construct(element, others);

        // Then
        assertThat(actual, hasOnlyItemsInOrder("first", "second", "third"));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIteratorsForConstruct() throws Exception {
        // Given
        Integer element = 1;
        Iterable<Integer> others = iterableWith(2, 3);

        // When
        Iterable<Integer> iterable = Lazily.construct(element, others);
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(1));
        assertThat(iterator1.next(), is(2));
        assertThat(iterator2.next(), is(1));
        assertThat(iterator1.next(), is(3));
        assertThat(iterator2.next(), is(2));
        assertThat(iterator2.next(), is(3));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullElementSuppliedToConstruct() throws Exception {
        // Given
        Integer element = null;
        Iterable<Integer> input = iterableWith(2, 3);

        // When
        Lazily.construct(element, input);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullIterableSuppliedToConstruct() throws Exception {
        // Given
        Integer element = 1;
        Iterable<Integer> input = null;

        // When
        Lazily.construct(element, input);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldConjoinElementOntoIterableDefinedToBeTheLastElementInTheResultingIterable() {
        // Given
        Iterable<String> others = iterableWith("first", "second");
        String element = "third";

        // When
        Iterable<String> actual = Lazily.conjoin(others, element);

        // Then
        assertThat(actual, hasOnlyItemsInOrder("first", "second", "third"));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIteratorsForConjoin() throws Exception {
        // Given
        Iterable<Integer> others = iterableWith(1, 2);
        Integer element = 3;

        // When
        Iterable<Integer> iterable = Lazily.conjoin(others, element);
        Iterator<Integer> iterator1 = iterable.iterator();
        Iterator<Integer> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is(1));
        assertThat(iterator1.next(), is(2));
        assertThat(iterator2.next(), is(1));
        assertThat(iterator1.next(), is(3));
        assertThat(iterator2.next(), is(2));
        assertThat(iterator2.next(), is(3));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullElementSuppliedToConjoin() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2);
        Integer element = null;

        // When
        Lazily.conjoin(input, element);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullIterableSuppliedToConjoin() throws Exception {
        // Given
        Iterable<Integer> input = null;
        Integer element = 1;

        // When
        Lazily.conjoin(input, element);

        // Then a NullPointerException is thrown.
    }
}
