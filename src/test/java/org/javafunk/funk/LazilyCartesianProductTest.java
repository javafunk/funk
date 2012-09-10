/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.annotations.ToDo;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.datastructures.tuples.Quadruple;
import org.javafunk.funk.datastructures.tuples.Triple;
import org.javafunk.funk.functors.Action;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Eagerly.times;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Lazily.cartesianProduct;
import static org.javafunk.funk.Lazily.cycle;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInAnyOrder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class LazilyCartesianProductTest {
    @Test
    public void shouldReturnTheCartesianProductOfTheSuppliedIterablesAsAnIterableOfTuples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2, 3);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Collection<Pair<Integer, String>> expectedCartesianProduct = collectionWith(
                tuple(1, "a"), tuple(1, "b"), tuple(1, "c"),
                tuple(2, "a"), tuple(2, "b"), tuple(2, "c"),
                tuple(3, "a"), tuple(3, "b"), tuple(3, "c"));

        // When
        Collection<Pair<Integer, String>> actualCartesianProduct = materialize(Lazily.cartesianProduct(input1, input2));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInOrder(expectedCartesianProduct));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReturnTheCartesianProductOfThreeSuppliedIterablesAsAnIterableOfTriples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Iterable<Long> input3 = iterableWith(1L);

        Collection<Triple<Integer, String, Long>> expectedCartesianProduct = collectionWith(
                tuple(1, "a", 1L), tuple(2, "a", 1L),
                tuple(1, "b", 1L), tuple(2, "b", 1L),
                tuple(1, "c", 1L), tuple(2, "c", 1L));

        // When
        Collection<Triple<Integer, String, Long>> actualCartesianProduct = materialize(Lazily.cartesianProduct(input1, input2, input3));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReturnTheCartesianProductOfFourSuppliedIterablesAsAnIterableOfQuadruples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Iterable<Long> input3 = iterableWith(1L, 2L, 3L);
        Iterable<String> input4 = iterableWith("hi", "bye");

        Collection<Quadruple<Integer, String, Long, String>> expectedCartesianProduct = collectionWith(
                tuple(1, "a", 1L, "hi"), tuple(1, "a", 1L, "bye"),
                tuple(1, "b", 1L, "hi"), tuple(1, "b", 1L, "bye"),
                tuple(1, "c", 1L, "hi"), tuple(1, "c", 1L, "bye"),
                tuple(2, "a", 1L, "hi"), tuple(2, "a", 1L, "bye"),
                tuple(2, "b", 1L, "hi"), tuple(2, "b", 1L, "bye"),
                tuple(2, "c", 1L, "hi"), tuple(2, "c", 1L, "bye"),
                tuple(1, "a", 2L, "hi"), tuple(1, "a", 2L, "bye"),
                tuple(1, "b", 2L, "hi"), tuple(1, "b", 2L, "bye"),
                tuple(1, "c", 2L, "hi"), tuple(1, "c", 2L, "bye"),
                tuple(2, "a", 2L, "hi"), tuple(2, "a", 2L, "bye"),
                tuple(2, "b", 2L, "hi"), tuple(2, "b", 2L, "bye"),
                tuple(2, "c", 2L, "hi"), tuple(2, "c", 2L, "bye"),
                tuple(1, "a", 3L, "hi"), tuple(1, "a", 3L, "bye"),
                tuple(1, "b", 3L, "hi"), tuple(1, "b", 3L, "bye"),
                tuple(1, "c", 3L, "hi"), tuple(1, "c", 3L, "bye"),
                tuple(2, "a", 3L, "hi"), tuple(2, "a", 3L, "bye"),
                tuple(2, "b", 3L, "hi"), tuple(2, "b", 3L, "bye"),
                tuple(2, "c", 3L, "hi"), tuple(2, "c", 3L, "bye")
        );

        // When
        Collection<Quadruple<Integer, String, Long, String>> actualCartesianProduct = materialize(Lazily.cartesianProduct(input1, input2, input3, input4));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    @SuppressWarnings("unchecked")
    @ToDo(raisedBy = "Toby",
            date     = "2012-04-29",
            message  = "Reinstate type safety.")
    public void shouldReturnTheCartesianProductOfNSuppliedIterablesAsAnIterableOfIterables() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Iterable<Long> input3 = iterableWith(1L, 2L, 3L);

        Collection expectedCartesianProduct = Literals.collectionWith(
                iterableWith(1, "a", 1L), iterableWith(2, "a", 1L),
                iterableWith(1, "b", 1L), iterableWith(2, "b", 1L),
                iterableWith(1, "c", 1L), iterableWith(2, "c", 1L),
                iterableWith(1, "a", 2L), iterableWith(2, "a", 2L),
                iterableWith(1, "b", 2L), iterableWith(2, "b", 2L),
                iterableWith(1, "c", 2L), iterableWith(2, "c", 2L),
                iterableWith(1, "a", 3L), iterableWith(2, "a", 3L),
                iterableWith(1, "b", 3L), iterableWith(2, "b", 3L),
                iterableWith(1, "c", 3L), iterableWith(2, "c", 3L)
        );

        // When
        Collection actualCartesianProduct = materialize(Lazily.cartesianProduct(iterableWith(input1, input2, input3)));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2, 3);
        Iterable<String> input2 = iterableWith("a", "b", "c");

        // When
        Iterable<Pair<Integer, String>> iterable = Lazily.cartesianProduct(input1, input2);

        Iterator<Pair<Integer, String>> iterator1 = iterable.iterator();
        Iterator<Pair<Integer, String>> iterator2 = iterable.iterator();

        // Then
        Assert.assertThat(iterator1.next(), is(tuple(1, "a")));
        Assert.assertThat(iterator1.next(), is(tuple(1, "b")));
        Assert.assertThat(iterator2.next(), is(tuple(1, "a")));
        Assert.assertThat(iterator2.next(), is(tuple(1, "b")));
        Assert.assertThat(iterator1.next(), is(tuple(1, "c")));
        Assert.assertThat(iterator2.next(), is(tuple(1, "c")));
        Assert.assertThat(iterator2.next(), is(tuple(2, "a")));
    }

    @Test
    public void shouldAllowSecondIterableToBeLongerThanFirst() throws Exception {
        // Given
        Iterable<Long> firstIterable = iterableWith(90L, 80L);
        Iterable<String> secondIterable = iterableWith("a", "b", "c", "d");
        Collection<Pair<Long, String>> expectedCartesianProduct = collectionWith(
                tuple(90L, "a"), tuple(90L, "b"), tuple(90L, "c"), tuple(90L, "d"),
                tuple(80L, "a"), tuple(80L, "b"), tuple(80L, "c"), tuple(80L, "d"));

        // When
        Iterable<Pair<Long, String>> actualCartesianProduct = cartesianProduct(firstIterable, secondIterable);

        // Then
        assertThat(materialize(actualCartesianProduct), hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    @ToDo(raisedBy = "Toby",
          date     = "2012-04-28",
          message  = "There must be a better way to test this...")
    public void shouldAllowInfiniteIterableInEitherSlot() throws Exception {
        // Given
        Iterable<Integer> firstIterable = cycle(iterableWith(1, 2, 3));
        Iterable<String> secondIterable = cycle(iterableWith("a", "b", "c"));

        // When
        final Iterator<Pair<Integer, String>> iterator = cartesianProduct(firstIterable, secondIterable).iterator();

        // Then
        times(1000000, new Action<Integer>() {
            public void on(Integer input) {
                assertThat(iterator.hasNext(), is(true));
                assertThat(iterator.next(), is(tuple(1, "a")));
                assertThat(iterator.hasNext(), is(true));
                assertThat(iterator.next(), is(tuple(1, "b")));
                assertThat(iterator.hasNext(), is(true));
                assertThat(iterator.next(), is(tuple(1, "c")));
            }
        });
    }

    @Test
    public void shouldAllowNextToBeCalledWithoutCallingHasNext() throws Exception {
        // Given
        Iterable<String> firstIterable = iterableWith("a", "b", "c");
        Iterable<Integer> secondIterable = iterableWith(1, 2, 3);

        // When
        Iterator<Pair<String, Integer>> iterator = cartesianProduct(firstIterable, secondIterable).iterator();

        // Then
        assertThat(iterator.next(), is(tuple("a", 1)));
        assertThat(iterator.next(), is(tuple("a", 2)));
        assertThat(iterator.next(), is(tuple("a", 3)));
        assertThat(iterator.next(), is(tuple("b", 1)));
    }

    @Test
    public void shouldAllowHasNextToBeCalledMultipleTimesWithoutProgressingTheIterator() throws Exception {
        // Given
        Iterable<String> firstIterable = iterableWith("a", "b", "c");
        Iterable<Integer> secondIterable = iterableWith(1, 2);

        // When
        Iterator<Pair<String, Integer>> iterator = cartesianProduct(firstIterable, secondIterable).iterator();

        // Then
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(tuple("a", 1)));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(tuple("a", 2)));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(tuple("b", 1)));
        assertThat(iterator.hasNext(), is(true));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowAnUnsupportedOperationExceptionWhenTryingToRemoveElements() throws Exception {
        // Given
        Iterable<String> firstIterable = iterableWith("a", "b");
        Iterable<Integer> secondIterable = iterableWith(1, 2, 3);

        // When
        Iterator<Pair<String, Integer>> iterator = cartesianProduct(firstIterable, secondIterable).iterator();

        iterator.next();
        iterator.remove();

        // Then an UnsupportedOperationException is thrown
    }
}
