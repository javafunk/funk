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
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Literals.iterable;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.monads.Option.option;
import static org.junit.Assert.fail;

public class NumbersMultiplyTest {
    @Test
    public void shouldCalculateTheProductOfTheSuppliedIntegersOnMultiplyOrThrow() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);

        // When
        Integer result = Numbers.multiplyOrThrow(input, Integer.class);

        // Then
        assertThat(result, is(120));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedLongsOnMultiplyOrThrow() throws Exception {
        // Given
        Iterable<Long> input = iterableWith(1L, 2L, 3L, 4L, 5L);

        // When
        Long result = Numbers.multiplyOrThrow(input, Long.class);

        // Then
        assertThat(result, is(120L));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedBigIntegersOnMultiplyOrThrow() throws Exception {
        // Given
        Iterable<BigInteger> input = iterableWith(new BigInteger("123"), new BigInteger("456"), new BigInteger("789"));

        // When
        BigInteger result = Numbers.multiplyOrThrow(input, BigInteger.class);

        // Then
        assertThat(result, is(new BigInteger("44253432")));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedFloatsOnMultiplyOrThrow() throws Exception {
        // Given
        Iterable<Float> input = iterableWith(1.1F, 1.2F, 1.3F, 1.4F, 1.5F);

        // When
        Float result = Numbers.multiplyOrThrow(input, Float.class);

        // Then
        assertThat(result.doubleValue(), is(closeTo(3.6036F, 0.0001)));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedDoublesOnMultiplyOrThrow() throws Exception {
        // Given
        Iterable<Double> input = iterableWith(1.1D, 1.2D, 1.3D, 1.4D, 1.5D);

        // When
        Double result = Numbers.multiplyOrThrow(input, Double.class);

        // Then
        assertThat(result, is(closeTo(3.6036F, 0.0001)));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedBigDecimalsOnMultiplyOrThrow() throws Exception {
        // Given
        Iterable<BigDecimal> input = iterableWith(new BigDecimal("1.23"), new BigDecimal("4.56"), new BigDecimal("7.89"));

        // When
        BigDecimal result = Numbers.multiplyOrThrow(input, BigDecimal.class);

        // Then
        assertThat(result, is(new BigDecimal("44.253432")));
    }

    @Test
    public void shouldThrowArithmeticExceptionIfNoNumbersAreSuppliedOnMultiplyOrThrow() throws Exception {
        // Given
        Iterable<Integer> inputs = iterable();

        try {
            // When
            Numbers.multiplyOrThrow(inputs, Integer.class);
            fail("Expected an ArithmeticException to be thrown.");
        } catch (Throwable exception) {
            // Then
            assertThat(exception, instanceOf(ArithmeticException.class));
            assertThat(exception.getMessage(),
                    containsString("Cannot multiply a collection containing no numbers."));
        }
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSuppliedIterableIsNullOnMultiplyOrThrow() throws Exception {
        // Given
        Iterable<Integer> inputs = null;

        // When
        Numbers.multiplyOrThrow(inputs, Integer.class);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSuppliedClassIsNullOnMultiplyOrThrow() throws Exception {
        // Given
        Iterable<Number> inputs = null;
        Class<Number> numberClass = null;

        // When
        Numbers.multiplyOrThrow(inputs, numberClass);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedIntegersReturningAnOptionOnMultiply() throws Exception {
        // Given
        Iterable<Integer> input = iterableWith(1, 2, 3, 4, 5);

        // When
        Option<Integer> result = Numbers.multiply(input, Integer.class);

        // Then
        assertThat(result, is(option(120)));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedLongsReturningAnOptionOnMultiply() throws Exception {
        // Given
        Iterable<Long> input = iterableWith(1L, 2L, 3L, 4L, 5L);

        // When
        Option<Long> result = Numbers.multiply(input, Long.class);

        // Then
        assertThat(result, is(option(120L)));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedBigIntegersReturningAnOptionOnMultiply() throws Exception {
        // Given
        Iterable<BigInteger> input = iterableWith(new BigInteger("123"), new BigInteger("456"), new BigInteger("789"));

        // When
        Option<BigInteger> result = Numbers.multiply(input, BigInteger.class);

        // Then
        assertThat(result, is(option(new BigInteger("44253432"))));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedFloatsReturningAnOptionOnMultiply() throws Exception {
        // Given
        Iterable<Float> input = iterableWith(1.1F, 1.2F, 1.3F, 1.4F, 1.5F);

        // When
        Option<Float> result = Numbers.multiply(input, Float.class);

        // Then
        assertThat(result.get().doubleValue(), is(closeTo(3.6036F, 0.0001)));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedDoublesReturningAnOptionOnMultiply() throws Exception {
        // Given
        Iterable<Double> input = iterableWith(1.1D, 1.2D, 1.3D, 1.4D, 1.5D);

        // When
        Option<Double> result = Numbers.multiply(input, Double.class);

        // Then
        assertThat(result.get(), is(closeTo(3.6036F, 0.0001)));
    }

    @Test
    public void shouldCalculateTheProductOfTheSuppliedBigDecimalsReturningAnOptionOnMultiply() throws Exception {
        // Given
        Iterable<BigDecimal> input = iterableWith(new BigDecimal("1.23"), new BigDecimal("4.56"), new BigDecimal("7.89"));

        // When
        Option<BigDecimal> result = Numbers.multiply(input, BigDecimal.class);

        // Then
        assertThat(result, is(option(new BigDecimal("44.253432"))));
    }

    @Test
    public void shouldReturnNoneIfNoNumbersAreSuppliedToMultiply() throws Exception {
        // Given
        Iterable<Integer> inputs = iterable();

        // When
        Option<Integer> result = Numbers.multiply(inputs, Integer.class);

        // Then
        assertThat(result, is(Option.<Integer>none()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSuppliedIterableIsNullOnMultiply() throws Exception {
        // Given
        Iterable<Integer> inputs = null;

        // When
        Numbers.multiply(inputs, Integer.class);

        // Then a NullPointerException is thrown.
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfSuppliedClassIsNullOnMultiply() throws Exception {
        // Given
        Iterable<Number> inputs = null;
        Class<Number> numberClass = null;

        // When
        Numbers.multiply(inputs, numberClass);

        // Then a NullPointerException is thrown.
    }
}
