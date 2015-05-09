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
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.javafunk.funk.BigDecimals.bigDecimal;
import static org.javafunk.funk.Iterables.asList;
import static org.javafunk.funk.Literals.collectionWith;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInAnyOrder;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertThat;

public class EagerlyMapCatTest {
    @Test
    public void shouldMapCat() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3);

        Mapper<Integer, Iterable<String>> mapFunction = new Mapper<Integer, Iterable<String>>() {
            public Iterable<String> map(Integer input) {
                return iterableWith(String.valueOf(input), "another");
            }
        };

        // When
        Collection<String> actual = Eagerly.mapCat(input, mapFunction);

        // Then
        assertThat(asList(actual), hasItems("1", "another", "2", "another", "3", "another"));
    }

    @Test
    public void shouldAllowMapperFunctionToBeForSuperTypeOfElementTypeAndSubTypeOfIterable() {
        // Given
        Iterable<BigDecimal> input = iterableWith(bigDecimal(1), bigDecimal(2), bigDecimal(3));

        Mapper<Object, Collection<String>> mapFunction = new Mapper<Object, Collection<String>>() {
            @Override public Collection<String> map(Object input) {
                return collectionWith(input.toString(), "thing");
            }
        };

        // When
        Collection<String> actual = Eagerly.mapCat(input, mapFunction);

        // Then
        assertThat(asList(actual), hasOnlyItemsInOrder("1", "thing", "2", "thing", "3", "thing"));
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
        Collection<String> actual = Eagerly.mapCat(input, mapFunction);

        // Then
        assertThat(asList(actual), hasOnlyItemsInOrder("1", "thing", null, null));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfUnaryFunctionSuppliedToMapCatIsNull() {
        // Given
        Iterable<Integer> inputs = iterableWith(1, 2, 3);
        UnaryFunction<Integer, String> unaryFunction = null;

        // When
        Eagerly.map(inputs, unaryFunction);

        // Then a NullPointerException is thrown
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowANullPointerExceptionIfIterablePassedToMapCatIsNull() throws Exception {
        // Given
        Iterable<Integer> input = null;
        UnaryFunction<Integer, String> function = new UnaryFunction<Integer, String>() {
            @Override public String call(Integer value) {
                return value.toString();
            }
        };

        // When
        Eagerly.map(input, function);

        // Then a NullPointerException is thrown
    }
}
