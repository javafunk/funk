/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterableWith;

public class EagerlySumTest {
    @Test
    public void shouldSumTheSuppliedIntegers() {
        // Given
        Iterable<Integer> inputs = iterableWith(1, 2, 3);

        // When
        Integer sum = Eagerly.sum(inputs);

        // Then
        assertThat(sum, is(6));
    }

    @Test
    public void shouldSumTheSuppliedLongs() {
        // Given
        Iterable<Long> inputs = iterableWith(1L, 2L, 3L);

        // When
        Long sum = Eagerly.sum(inputs);

        // Then
        assertThat(sum, is(6L));
    }

    @Test
    public void shouldSumTheSuppliedBigIntegers() {
        // Given
        Iterable<BigInteger> inputs = iterableWith(new BigInteger("123"), new BigInteger("234"), new BigInteger("345"));

        // When
        BigInteger sum = Eagerly.sum(inputs);

        // Then
        assertThat(sum, is(new BigInteger("702")));
    }

    @Test
    public void shouldSumTheSuppliedDoubles() {
        // Given
        Iterable<Double> inputs = iterableWith(1.6D, 2.2D, 3.5D);

        // When
        Double sum = Eagerly.sum(inputs);

        // Then
        assertThat(sum, is(closeTo(7.3D, 0.01D)));
    }

    @Test
    public void shouldSumTheSuppliedFloats() {
        // Given
        Iterable<Float> inputs = iterableWith(1.6F, 2.2F, 3.5F);

        // When
        Float sum = Eagerly.sum(inputs);

        // Then
        assertThat(sum.doubleValue(), is(closeTo(7.3D, 0.01)));
    }

    @Test
    public void shouldSumTheSuppliedBigDecimals() {
        // Given
        Iterable<BigDecimal> inputs = iterableWith(new BigDecimal("1.23"), new BigDecimal("2.34"), new BigDecimal("3.45"));

        // When
        BigDecimal sum = Eagerly.sum(inputs);

        // Then
        assertThat(sum, is(new BigDecimal("7.02")));
    }
}
