/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.javafunk.funk.BigDecimals.bigDecimal;
import static org.javafunk.funk.Iterables.asList;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInAnyOrder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertThat;

public class LazilyMapCatTest {
    @Test
    public void shouldMapCatIterableUsingCustomMapFunction() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3);

        // When
        Iterable<String> actual = Lazily.mapCat(input, new Mapper<Integer, Iterable<String>>() {
            public Iterable<String> map(Integer input) {
                return iterableWith(String.valueOf(input), "other");
            }
        });

        // Then
        assertThat(asList(actual), hasOnlyItemsInOrder("1", "other", "2", "other", "3", "other"));
    }

    @Test
    public void shouldAllowMapperFunctionToReturnNullRetainingTheNullInTheResultingCollection() {
        // Given
        final AtomicInteger count = new AtomicInteger(0);
        final Iterable<BigDecimal> input = iterableWith(bigDecimal(1), bigDecimal(2), bigDecimal(3));

        final Mapper<Object, Collection<String>> mapFunction = new Mapper<Object, Collection<String>>() {
            @Override public Collection<String> map(Object input) {
                if (count.getAndIncrement() < 1) {
                    return collectionWith(input.toString(), "thing");
                }
                return null;
            }
        };

        // When
        Iterable<String> actual = Lazily.mapCat(input, mapFunction);

        // Then
        assertThat(asList(actual), hasOnlyItemsInAnyOrder("1", "thing", null, null));
    }

    @Test
    public void shouldAllowIteratorToBeCalledMultipleTimesReturningDifferentIterators() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3);

        // When
        Iterable<String> iterable = Lazily.mapCat(input, new Mapper<Object, Collection<String>>() {
            public Collection<String> map(Object input) {
                return collectionWith(String.valueOf(input), "other");
            }
        });

        Iterator<String> iterator1 = iterable.iterator();
        Iterator<String> iterator2 = iterable.iterator();

        // Then
        assertThat(iterator1.next(), is("1"));
        assertThat(iterator1.next(), is("other"));
        assertThat(iterator1.next(), is("2"));
        assertThat(iterator2.next(), is("1"));
        assertThat(iterator1.next(), is("other"));
        assertThat(iterator2.next(), is("other"));
        assertThat(iterator2.next(), is("2"));
        assertThat(iterator2.next(), is("other"));
        assertThat(iterator1.next(), is("3"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullUnaryFunctionSuppliedToMapCat() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3);
        UnaryFunction<? super Integer, ? extends Iterable<String>> mapper = null;

        // When
        Lazily.mapCat(input, mapper);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullIterableSuppliedToMapCat() throws Exception {
        // Given
        Iterable<Integer> input = null;
        Mapper<Integer, Iterable<String>> mapper = new Mapper<Integer, Iterable<String>>() {
            public Iterable<String> map(Integer input) {
                return iterableWith(String.valueOf(input), "other");
            }
        };

        // When
        Lazily.mapCat(input, mapper);

        // Then a NullPointerException is thrown.
    }
}
