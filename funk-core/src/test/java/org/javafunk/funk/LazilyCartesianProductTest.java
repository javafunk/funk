/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.hamcrest.Matchers;
import org.javafunk.funk.annotations.ToDo;
import org.javafunk.funk.datastructures.tuples.Nonuple;
import org.javafunk.funk.datastructures.tuples.Octuple;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.datastructures.tuples.Quadruple;
import org.javafunk.funk.datastructures.tuples.Quintuple;
import org.javafunk.funk.datastructures.tuples.Septuple;
import org.javafunk.funk.datastructures.tuples.Sextuple;
import org.javafunk.funk.datastructures.tuples.Triple;
import org.javafunk.funk.functors.Action;
import org.javafunk.funk.testclasses.Age;
import org.javafunk.funk.testclasses.Colour;
import org.javafunk.funk.testclasses.Name;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Eagerly.times;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Lazily.cartesianProduct;
import static org.javafunk.funk.Lazily.cycle;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterable;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.tuple;
import static org.javafunk.funk.testclasses.Age.age;
import static org.javafunk.funk.testclasses.Colour.colour;
import static org.javafunk.funk.testclasses.Name.name;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInAnyOrder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;

public class LazilyCartesianProductTest {
    @Test
    public void shouldReturnTheCartesianProductOfTwoIterablesAsAnIterableOfPairs() throws Exception {
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

    @Test
    @ToDo(raisedBy = "Toby",
            date = "2012-04-28",
            message = "There must be a better way to test this...")
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
    public void shouldReturnEmptyIterableIfAnyOfTheSuppliedIterablesAreEmpty() throws Exception {
        // Given
        Iterable<Integer> firstIterable = iterableWith(1, 2, 3);
        Iterable<String> secondIterable = iterable();

        // When
        Iterable<Pair<Integer, String>> product = cartesianProduct(firstIterable, secondIterable);

        // Then
        assertThat(product, is(Matchers.<Pair<Integer, String>>emptyIterable()));
    }

    @Test
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
        Collection<Triple<Integer, String, Long>> actualCartesianProduct = materialize(Lazily.cartesianProduct(
                input1, input2, input3));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfFourSuppliedIterablesAsAnIterableOfQuadruples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Iterable<Long> input3 = iterableWith(1L, 2L, 3L);
        Iterable<String> input4 = iterableWith("hi", "bye");

        Collection<Quadruple<Integer, String, Long, String>> expectedCartesianProduct =
                Literals.<Quadruple<Integer, String, Long, String>>collectionBuilder()
                        .with(tuple(1, "a", 1L, "hi")).with(tuple(1, "a", 1L, "bye"))
                        .with(tuple(1, "b", 1L, "hi")).with(tuple(1, "b", 1L, "bye"))
                        .with(tuple(1, "c", 1L, "hi")).with(tuple(1, "c", 1L, "bye"))
                        .with(tuple(2, "a", 1L, "hi")).with(tuple(2, "a", 1L, "bye"))
                        .with(tuple(2, "b", 1L, "hi")).with(tuple(2, "b", 1L, "bye"))
                        .with(tuple(2, "c", 1L, "hi")).with(tuple(2, "c", 1L, "bye"))
                        .with(tuple(1, "a", 2L, "hi")).with(tuple(1, "a", 2L, "bye"))
                        .with(tuple(1, "b", 2L, "hi")).with(tuple(1, "b", 2L, "bye"))
                        .with(tuple(1, "c", 2L, "hi")).with(tuple(1, "c", 2L, "bye"))
                        .with(tuple(2, "a", 2L, "hi")).with(tuple(2, "a", 2L, "bye"))
                        .with(tuple(2, "b", 2L, "hi")).with(tuple(2, "b", 2L, "bye"))
                        .with(tuple(2, "c", 2L, "hi")).with(tuple(2, "c", 2L, "bye"))
                        .with(tuple(1, "a", 3L, "hi")).with(tuple(1, "a", 3L, "bye"))
                        .with(tuple(1, "b", 3L, "hi")).with(tuple(1, "b", 3L, "bye"))
                        .with(tuple(1, "c", 3L, "hi")).with(tuple(1, "c", 3L, "bye"))
                        .with(tuple(2, "a", 3L, "hi")).with(tuple(2, "a", 3L, "bye"))
                        .with(tuple(2, "b", 3L, "hi")).with(tuple(2, "b", 3L, "bye"))
                        .with(tuple(2, "c", 3L, "hi")).with(tuple(2, "c", 3L, "bye"))
                        .build();

        // When
        Collection<Quadruple<Integer, String, Long, String>> actualCartesianProduct = materialize(Lazily.cartesianProduct(
                input1, input2, input3, input4));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfFiveSuppliedIterablesAsAnIterableOfQuintuples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1);
        Iterable<String> input2 = iterableWith("a", "b");
        Iterable<Long> input3 = iterableWith(1L, 2L);
        Iterable<String> input4 = iterableWith("hi", "bye");
        Iterable<Boolean> input5 = iterableWith(true);

        Collection<Quintuple<Integer, String, Long, String, Boolean>> expectedCartesianProduct =
                Literals.<Quintuple<Integer, String, Long, String, Boolean>>collectionBuilder()
                        .with(tuple(1, "a", 1L, "hi", true)).with(tuple(1, "a", 1L, "bye", true))
                        .with(tuple(1, "b", 1L, "hi", true)).with(tuple(1, "b", 1L, "bye", true))
                        .with(tuple(1, "a", 2L, "hi", true)).with(tuple(1, "a", 2L, "bye", true))
                        .with(tuple(1, "b", 2L, "hi", true)).with(tuple(1, "b", 2L, "bye", true))
                        .build();

        // When
        Collection<Quintuple<Integer, String, Long, String, Boolean>> actualCartesianProduct =
                materialize(Lazily.cartesianProduct(
                        input1,
                        input2,
                        input3,
                        input4,
                        input5));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfSixSuppliedIterablesAsAnIterableOfSextuples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1);
        Iterable<String> input2 = iterableWith("a", "b");
        Iterable<Long> input3 = iterableWith(1L, 2L);
        Iterable<String> input4 = iterableWith("hi", "bye");
        Iterable<Boolean> input5 = iterableWith(true);
        Iterable<Double> input6 = iterableWith(1.1, 2.2);

        Collection<Sextuple<Integer, String, Long, String, Boolean, Double>> expectedCartesianProduct =
                Literals.<Sextuple<Integer, String, Long, String, Boolean, Double>>collectionBuilder()
                        .with(tuple(1, "a", 1L, "hi", true, 1.1)).with(tuple(1, "a", 1L, "bye", true, 1.1))
                        .with(tuple(1, "b", 1L, "hi", true, 1.1)).with(tuple(1, "b", 1L, "bye", true, 1.1))
                        .with(tuple(1, "a", 2L, "hi", true, 1.1)).with(tuple(1, "a", 2L, "bye", true, 1.1))
                        .with(tuple(1, "b", 2L, "hi", true, 1.1)).with(tuple(1, "b", 2L, "bye", true, 1.1))
                        .with(tuple(1, "a", 1L, "hi", true, 2.2)).with(tuple(1, "a", 1L, "bye", true, 2.2))
                        .with(tuple(1, "b", 1L, "hi", true, 2.2)).with(tuple(1, "b", 1L, "bye", true, 2.2))
                        .with(tuple(1, "a", 2L, "hi", true, 2.2)).with(tuple(1, "a", 2L, "bye", true, 2.2))
                        .with(tuple(1, "b", 2L, "hi", true, 2.2)).with(tuple(1, "b", 2L, "bye", true, 2.2))
                        .build();

        // When
        Collection<Sextuple<Integer, String, Long, String, Boolean, Double>> actualCartesianProduct =
                materialize(Lazily.cartesianProduct(
                        input1,
                        input2,
                        input3,
                        input4,
                        input5,
                        input6));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfSevenSuppliedIterablesAsAnIterableOfSeptuples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1);
        Iterable<String> input2 = iterableWith("a", "b");
        Iterable<Long> input3 = iterableWith(1L, 2L);
        Iterable<String> input4 = iterableWith("hi", "bye");
        Iterable<Boolean> input5 = iterableWith(true);
        Iterable<Double> input6 = iterableWith(1.1, 2.2);
        Iterable<Name> input7 = iterableWith(name("Adam"));

        Collection<Septuple<Integer, String, Long, String, Boolean, Double, Name>> expectedCartesianProduct =
                Literals.<Septuple<Integer, String, Long, String, Boolean, Double, Name>>collectionBuilder()
                        .with(tuple(1, "a", 1L, "hi", true, 1.1, name("Adam"))).with(tuple(1, "a", 1L, "bye", true, 1.1, name("Adam")))
                        .with(tuple(1, "b", 1L, "hi", true, 1.1, name("Adam"))).with(tuple(1, "b", 1L, "bye", true, 1.1, name("Adam")))
                        .with(tuple(1, "a", 2L, "hi", true, 1.1, name("Adam"))).with(tuple(1, "a", 2L, "bye", true, 1.1, name("Adam")))
                        .with(tuple(1, "b", 2L, "hi", true, 1.1, name("Adam"))).with(tuple(1, "b", 2L, "bye", true, 1.1, name("Adam")))
                        .with(tuple(1, "a", 1L, "hi", true, 2.2, name("Adam"))).with(tuple(1, "a", 1L, "bye", true, 2.2, name("Adam")))
                        .with(tuple(1, "b", 1L, "hi", true, 2.2, name("Adam"))).with(tuple(1, "b", 1L, "bye", true, 2.2, name("Adam")))
                        .with(tuple(1, "a", 2L, "hi", true, 2.2, name("Adam"))).with(tuple(1, "a", 2L, "bye", true, 2.2, name("Adam")))
                        .with(tuple(1, "b", 2L, "hi", true, 2.2, name("Adam"))).with(tuple(1, "b", 2L, "bye", true, 2.2, name("Adam")))
                        .build();

        // When
        Collection<Septuple<Integer, String, Long, String, Boolean, Double, Name>> actualCartesianProduct =
                materialize(Lazily.cartesianProduct(
                        input1,
                        input2,
                        input3,
                        input4,
                        input5,
                        input6,
                        input7));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfEightSuppliedIterablesAsAnIterableOfOctuples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1);
        Iterable<String> input2 = iterableWith("a", "b");
        Iterable<Long> input3 = iterableWith(1L, 2L);
        Iterable<String> input4 = iterableWith("hi", "bye");
        Iterable<Boolean> input5 = iterableWith(true);
        Iterable<Double> input6 = iterableWith(1.1, 2.2);
        Iterable<Name> input7 = iterableWith(name("Adam"));
        Iterable<Age> input8 = iterableWith(age(20), age(30));

        Collection<Octuple<Integer, String, Long, String, Boolean, Double, Name, Age>> expectedCartesianProduct =
                Literals.<Octuple<Integer, String, Long, String, Boolean, Double, Name, Age>>collectionBuilder()
                        .with(tuple(1, "a", 1L, "hi",  true, 1.1, name("Adam"), age(20)))
                        .with(tuple(1, "a", 1L, "bye", true, 1.1, name("Adam"), age(20)))
                        .with(tuple(1, "b", 1L, "hi",  true, 1.1, name("Adam"), age(20)))
                        .with(tuple(1, "b", 1L, "bye", true, 1.1, name("Adam"), age(20)))
                        .with(tuple(1, "a", 2L, "hi",  true, 1.1, name("Adam"), age(20)))
                        .with(tuple(1, "a", 2L, "bye", true, 1.1, name("Adam"), age(20)))
                        .with(tuple(1, "b", 2L, "hi",  true, 1.1, name("Adam"), age(20)))
                        .with(tuple(1, "b", 2L, "bye", true, 1.1, name("Adam"), age(20)))
                        .with(tuple(1, "a", 1L, "hi",  true, 2.2, name("Adam"), age(20)))
                        .with(tuple(1, "a", 1L, "bye", true, 2.2, name("Adam"), age(20)))
                        .with(tuple(1, "b", 1L, "hi",  true, 2.2, name("Adam"), age(20)))
                        .with(tuple(1, "b", 1L, "bye", true, 2.2, name("Adam"), age(20)))
                        .with(tuple(1, "a", 2L, "hi",  true, 2.2, name("Adam"), age(20)))
                        .with(tuple(1, "a", 2L, "bye", true, 2.2, name("Adam"), age(20)))
                        .with(tuple(1, "b", 2L, "hi",  true, 2.2, name("Adam"), age(20)))
                        .with(tuple(1, "b", 2L, "bye", true, 2.2, name("Adam"), age(20)))
                        .with(tuple(1, "a", 1L, "hi",  true, 1.1, name("Adam"), age(30)))
                        .with(tuple(1, "a", 1L, "bye", true, 1.1, name("Adam"), age(30)))
                        .with(tuple(1, "b", 1L, "hi",  true, 1.1, name("Adam"), age(30)))
                        .with(tuple(1, "b", 1L, "bye", true, 1.1, name("Adam"), age(30)))
                        .with(tuple(1, "a", 2L, "hi",  true, 1.1, name("Adam"), age(30)))
                        .with(tuple(1, "a", 2L, "bye", true, 1.1, name("Adam"), age(30)))
                        .with(tuple(1, "b", 2L, "hi",  true, 1.1, name("Adam"), age(30)))
                        .with(tuple(1, "b", 2L, "bye", true, 1.1, name("Adam"), age(30)))
                        .with(tuple(1, "a", 1L, "hi",  true, 2.2, name("Adam"), age(30)))
                        .with(tuple(1, "a", 1L, "bye", true, 2.2, name("Adam"), age(30)))
                        .with(tuple(1, "b", 1L, "hi",  true, 2.2, name("Adam"), age(30)))
                        .with(tuple(1, "b", 1L, "bye", true, 2.2, name("Adam"), age(30)))
                        .with(tuple(1, "a", 2L, "hi",  true, 2.2, name("Adam"), age(30)))
                        .with(tuple(1, "a", 2L, "bye", true, 2.2, name("Adam"), age(30)))
                        .with(tuple(1, "b", 2L, "hi",  true, 2.2, name("Adam"), age(30)))
                        .with(tuple(1, "b", 2L, "bye", true, 2.2, name("Adam"), age(30)))
                        .build();

        // When
        Collection<Octuple<Integer, String, Long, String, Boolean, Double, Name, Age>> actualCartesianProduct =
                materialize(Lazily.cartesianProduct(
                        input1,
                        input2,
                        input3,
                        input4,
                        input5,
                        input6,
                        input7,
                        input8));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfNineSuppliedIterablesAsAnIterableOfNonuples() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1);
        Iterable<String> input2 = iterableWith("a", "b");
        Iterable<Long> input3 = iterableWith(1L, 2L);
        Iterable<String> input4 = iterableWith("hi", "bye");
        Iterable<Boolean> input5 = iterableWith(true);
        Iterable<Double> input6 = iterableWith(1.1, 2.2);
        Iterable<Name> input7 = iterableWith(name("Adam"));
        Iterable<Age> input8 = iterableWith(age(20), age(30));
        Iterable<Colour> input9 = iterableWith(colour("Azul"));

        Collection<Nonuple<Integer, String, Long, String, Boolean, Double, Name, Age, Colour>> expectedCartesianProduct =
                Literals.<Nonuple<Integer, String, Long, String, Boolean, Double, Name, Age, Colour>>collectionBuilder()
                        .with(tuple(1, "a", 1L, "hi",  true, 1.1, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "a", 1L, "bye", true, 1.1, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "b", 1L, "hi",  true, 1.1, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "b", 1L, "bye", true, 1.1, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "a", 2L, "hi",  true, 1.1, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "a", 2L, "bye", true, 1.1, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "b", 2L, "hi",  true, 1.1, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "b", 2L, "bye", true, 1.1, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "a", 1L, "hi",  true, 2.2, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "a", 1L, "bye", true, 2.2, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "b", 1L, "hi",  true, 2.2, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "b", 1L, "bye", true, 2.2, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "a", 2L, "hi",  true, 2.2, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "a", 2L, "bye", true, 2.2, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "b", 2L, "hi",  true, 2.2, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "b", 2L, "bye", true, 2.2, name("Adam"), age(20), colour("Azul")))
                        .with(tuple(1, "a", 1L, "hi",  true, 1.1, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "a", 1L, "bye", true, 1.1, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "b", 1L, "hi",  true, 1.1, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "b", 1L, "bye", true, 1.1, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "a", 2L, "hi",  true, 1.1, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "a", 2L, "bye", true, 1.1, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "b", 2L, "hi",  true, 1.1, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "b", 2L, "bye", true, 1.1, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "a", 1L, "hi",  true, 2.2, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "a", 1L, "bye", true, 2.2, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "b", 1L, "hi",  true, 2.2, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "b", 1L, "bye", true, 2.2, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "a", 2L, "hi",  true, 2.2, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "a", 2L, "bye", true, 2.2, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "b", 2L, "hi",  true, 2.2, name("Adam"), age(30), colour("Azul")))
                        .with(tuple(1, "b", 2L, "bye", true, 2.2, name("Adam"), age(30), colour("Azul")))
                        .build();

        // When
        Collection<Nonuple<Integer, String, Long, String, Boolean, Double, Name, Age, Colour>> actualCartesianProduct =
                materialize(Lazily.cartesianProduct(
                        input1,
                        input2,
                        input3,
                        input4,
                        input5,
                        input6,
                        input7,
                        input8,
                        input9));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test
    public void shouldReturnTheCartesianProductOfNSuppliedIterablesAsAnIterableOfIterables() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Iterable<Long> input3 = iterableWith(1L, 2L, 3L);

        Collection<Iterable<?>> expectedCartesianProduct = Literals.<Iterable<?>>collectionBuilder()
                .with(iterableWith(1, "a", 1L)).with(iterableWith(2, "a", 1L))
                .with(iterableWith(1, "b", 1L)).with(iterableWith(2, "b", 1L))
                .with(iterableWith(1, "c", 1L)).with(iterableWith(2, "c", 1L))
                .with(iterableWith(1, "a", 2L)).with(iterableWith(2, "a", 2L))
                .with(iterableWith(1, "b", 2L)).with(iterableWith(2, "b", 2L))
                .with(iterableWith(1, "c", 2L)).with(iterableWith(2, "c", 2L))
                .with(iterableWith(1, "a", 3L)).with(iterableWith(2, "a", 3L))
                .with(iterableWith(1, "b", 3L)).with(iterableWith(2, "b", 3L))
                .with(iterableWith(1, "c", 3L)).with(iterableWith(2, "c", 3L))
                .build();

        // When
        Collection<Iterable<?>> actualCartesianProduct = materialize(Lazily.cartesianProduct(iterableWith(input1, input2, input3)));

        // Then
        assertThat(actualCartesianProduct, hasOnlyItemsInAnyOrder(expectedCartesianProduct));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfAnyIterablePassedToCartesianProductIsNull() throws Exception {
        // Given
        Iterable<Integer> input1 = iterableWith(1, 2);
        Iterable<String> input2 = iterableWith("a", "b", "c");
        Iterable<Long> input3 = null;

        // When
        Lazily.cartesianProduct(input1, input2, input3);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfIterableOfIterablesPassedToCartesianProductIsNull() throws Exception {
        // Given
        Iterable<Iterable<Integer>> iterables = null;

        // When
        Lazily.cartesianProduct(iterables);

        // Then a NullPointerException is thrown.
    }
}
