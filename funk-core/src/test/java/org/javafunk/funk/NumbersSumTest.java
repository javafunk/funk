/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.monads.Option;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.javafunk.funk.Literals.iterable;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.monads.Option.option;
import static org.junit.Assert.fail;

public class NumbersSumTest {
    @Test
    public void shouldSumTheSuppliedIntegersOnSumOrThrow() {
        // Given
        Iterable<Integer> inputs = iterableWith(1, 2, 3);

        // When
        Integer sum = Numbers.sumOrThrow(inputs, Integer.class);

        // Then
        assertThat(sum, is(6));
    }

    @Test
    public void shouldSumTheSuppliedLongsOnSumOrThrow() {
        // Given
        Iterable<Long> inputs = iterableWith(1L, 2L, 3L);

        // When
        Long sum = Numbers.sumOrThrow(inputs, Long.class);

        // Then
        assertThat(sum, is(6L));
    }

    @Test
    public void shouldSumTheSuppliedBigIntegersOnSumOrThrow() {
        // Given
        Iterable<BigInteger> inputs = iterableWith(new BigInteger("123"), new BigInteger("234"), new BigInteger("345"));

        // When
        BigInteger sum = Numbers.sumOrThrow(inputs, BigInteger.class);

        // Then
        assertThat(sum, is(new BigInteger("702")));
    }

    @Test
    public void shouldSumTheSuppliedDoublesOnSumOrThrow() {
        // Given
        Iterable<Double> inputs = iterableWith(1.6D, 2.2D, 3.5D);

        // When
        Double sum = Numbers.sumOrThrow(inputs, Double.class);

        // Then
        assertThat(sum, is(closeTo(7.3D, 0.01D)));
    }

    @Test
    public void shouldSumTheSuppliedFloatsOnSumOrThrow() {
        // Given
        Iterable<Float> inputs = iterableWith(1.6F, 2.2F, 3.5F);

        // When
        Float sum = Numbers.sumOrThrow(inputs, Float.class);

        // Then
        assertThat(sum.doubleValue(), is(closeTo(7.3D, 0.01)));
    }

    @Test
    public void shouldSumTheSuppliedBigDecimalsOnSumOrThrow() {
        // Given
        Iterable<BigDecimal> inputs = iterableWith(new BigDecimal("1.23"), new BigDecimal("2.34"), new BigDecimal("3.45"));

        // When
        BigDecimal sum = Numbers.sumOrThrow(inputs, BigDecimal.class);

        // Then
        assertThat(sum, is(new BigDecimal("7.02")));
    }

    @Test
    public void shouldThrowArithmeticExceptionIfNoNumbersAreSuppliedOnSumOrThrow() throws Exception {
        // Given
        Iterable<Integer> inputs = iterable();

        try {
            // When
            Numbers.sumOrThrow(inputs, Integer.class);
            fail("Expected an ArithmeticException to be thrown.");
        } catch (Throwable exception) {
            // Then
            assertThat(exception, instanceOf(ArithmeticException.class));
            assertThat(exception.getMessage(),
                    containsString("Cannot sum a collection containing no numbers."));
        }
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSuppliedIterableIsNullOnSumOrThrow() throws Exception {
        // Given
        Iterable<Integer> inputs = null;

        // When
        Numbers.sumOrThrow(inputs, Integer.class);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSuppliedClassIsNullOnSumOrThrow() throws Exception {
        // Given
        Iterable<Number> inputs = null;
        Class<Number> numberClass = null;

        // When
        Numbers.sumOrThrow(inputs, numberClass);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldSumTheSuppliedIntegersReturningAnOptionOnSum() {
        // Given
        Iterable<Integer> inputs = iterableWith(1, 2, 3);

        // When
        Option<Integer> sum = Numbers.sum(inputs, Integer.class);

        // Then
        assertThat(sum, is(option(6)));
    }

    @Test
    public void shouldSumTheSuppliedLongsReturningAnOptionOnSum() {
        // Given
        Iterable<Long> inputs = iterableWith(1L, 2L, 3L);

        // When
        Option<Long> sum = Numbers.sum(inputs, Long.class);

        // Then
        assertThat(sum, is(option(6L)));
    }

    @Test
    public void shouldSumTheSuppliedBigIntegersReturningAnOptionOnSum() {
        // Given
        Iterable<BigInteger> inputs = iterableWith(new BigInteger("123"), new BigInteger("234"), new BigInteger("345"));

        // When
        Option<BigInteger> sum = Numbers.sum(inputs, BigInteger.class);

        // Then
        assertThat(sum, is(option(new BigInteger("702"))));
    }

    @Test
    public void shouldSumTheSuppliedDoublesReturningAnOptionOnSum() {
        // Given
        Iterable<Double> inputs = iterableWith(1.6D, 2.2D, 3.5D);

        // When
        Option<Double> sum = Numbers.sum(inputs, Double.class);

        // Then
        assertThat(sum.get(), is(closeTo(7.3D, 0.01D)));
    }

    @Test
    public void shouldSumTheSuppliedFloatsReturningAnOptionOnSum() {
        // Given
        Iterable<Float> inputs = iterableWith(1.6F, 2.2F, 3.5F);

        // When
        Option<Float> sum = Numbers.sum(inputs, Float.class);

        // Then
        assertThat(sum.get().doubleValue(), is(closeTo(7.3D, 0.01)));
    }

    @Test
    public void shouldSumTheSuppliedBigDecimalsReturningAnOptionOnSum() {
        // Given
        Iterable<BigDecimal> inputs = iterableWith(new BigDecimal("1.23"), new BigDecimal("2.34"), new BigDecimal("3.45"));

        // When
        Option<BigDecimal> sum = Numbers.sum(inputs, BigDecimal.class);

        // Then
        assertThat(sum, is(option(new BigDecimal("7.02"))));
    }

    @Test
    public void shouldReturnNoneIfNoNumbersAreSuppliedToSum() throws Exception {
        // Given
        Iterable<Integer> inputs = iterable();

        // When
        Option<Integer> sum = Numbers.sum(inputs, Integer.class);

        // Then
        assertThat(sum, is(Option.<Integer>none()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSuppliedIterableIsNullOnSum() throws Exception {
        // Given
        Iterable<Integer> inputs = null;

        // When
        Numbers.sum(inputs, Integer.class);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSuppliedClassIsNullOnSum() throws Exception {
        // Given
        Iterable<Number> inputs = null;
        Class<Number> numberClass = null;

        // When
        Numbers.sum(inputs, numberClass);

        // Then a NullPointerException is thrown.
    }
}
