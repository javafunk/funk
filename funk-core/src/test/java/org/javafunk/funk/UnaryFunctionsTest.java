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
import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.BigDecimals.fromStringToBigDecimal;
import static org.javafunk.funk.BigIntegers.fromFloatToBigInteger;
import static org.javafunk.funk.BigIntegers.toStringValue;
import static org.javafunk.funk.Doubles.fromIntegerToDouble;
import static org.javafunk.funk.Floats.fromDoubleToFloat;
import static org.javafunk.funk.Integers.fromLongToInteger;
import static org.javafunk.funk.Longs.fromBigDecimalToLong;
import static org.javafunk.funk.UnaryFunctions.chain;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

public class UnaryFunctionsTest {
    @Test
    public void threadsTwoUnaryFunctionsTogether() {
        // Given
        UnaryFunction<String, BigDecimal> first = mapperUnaryFunction(fromStringToBigDecimal());
        UnaryFunction<BigDecimal, Long> second = mapperUnaryFunction(fromBigDecimalToLong());

        UnaryFunction<String, Long> chained = chain(first, second);

        // When
        Long result = chained.call("1234");

        // Then
        assertThat(result, is(1234L));
    }

    @Test
    public void threadsThreeUnaryFunctionsTogether() {
        // Given
        UnaryFunction<String, BigDecimal> first = mapperUnaryFunction(fromStringToBigDecimal());
        UnaryFunction<BigDecimal, Long> second = mapperUnaryFunction(fromBigDecimalToLong());
        UnaryFunction<Long, Integer> third = mapperUnaryFunction(fromLongToInteger());

        UnaryFunction<String, Integer> chained = UnaryFunctions.chain(first, second, third);

        // When
        Integer result = chained.call("1234");

        // Then
        assertThat(result, is(1234));
    }

    @Test
    public void threadsFourUnaryFunctionsTogether() {
        // Given
        UnaryFunction<String, BigDecimal> first = mapperUnaryFunction(fromStringToBigDecimal());
        UnaryFunction<BigDecimal, Long> second = mapperUnaryFunction(fromBigDecimalToLong());
        UnaryFunction<Long, Integer> third = mapperUnaryFunction(fromLongToInteger());
        UnaryFunction<Integer, Double> fourth = mapperUnaryFunction(fromIntegerToDouble());

        UnaryFunction<String, Double> chained = UnaryFunctions.chain(first, second, third, fourth);

        // When
        Double result = chained.call("1234");

        // Then
        assertThat(result, is(1234D));
    }

    @Test
    public void threadsFiveUnaryFunctionsTogether() {
        // Given
        UnaryFunction<String, BigDecimal> first = mapperUnaryFunction(fromStringToBigDecimal());
        UnaryFunction<BigDecimal, Long> second = mapperUnaryFunction(fromBigDecimalToLong());
        UnaryFunction<Long, Integer> third = mapperUnaryFunction(fromLongToInteger());
        UnaryFunction<Integer, Double> fourth = mapperUnaryFunction(fromIntegerToDouble());
        UnaryFunction<Double, Float> fifth = mapperUnaryFunction(fromDoubleToFloat());

        UnaryFunction<String, Float> chained = UnaryFunctions.chain(first, second, third, fourth, fifth);

        // When
        Float result = chained.call("1234");

        // Then
        assertThat(result, is(1234F));
    }

    @Test
    public void threadsSixUnaryFunctionsTogether() {
        // Given
        UnaryFunction<String, BigDecimal> first = mapperUnaryFunction(fromStringToBigDecimal());
        UnaryFunction<BigDecimal, Long> second = mapperUnaryFunction(fromBigDecimalToLong());
        UnaryFunction<Long, Integer> third = mapperUnaryFunction(fromLongToInteger());
        UnaryFunction<Integer, Double> fourth = mapperUnaryFunction(fromIntegerToDouble());
        UnaryFunction<Double, Float> fifth = mapperUnaryFunction(fromDoubleToFloat());
        UnaryFunction<Float, BigInteger> sixth = mapperUnaryFunction(fromFloatToBigInteger());

        UnaryFunction<String, BigInteger> chained = UnaryFunctions.chain(first, second, third, fourth, fifth, sixth);

        // When
        BigInteger result = chained.call("1234");

        // Then
        assertThat(result, is(new BigInteger("1234")));
    }

    @Test
    public void threadsSevenUnaryFunctionsTogether() {
        // Given
        UnaryFunction<String, BigDecimal> first = mapperUnaryFunction(fromStringToBigDecimal());
        UnaryFunction<BigDecimal, Long> second = mapperUnaryFunction(fromBigDecimalToLong());
        UnaryFunction<Long, Integer> third = mapperUnaryFunction(fromLongToInteger());
        UnaryFunction<Integer, Double> fourth = mapperUnaryFunction(fromIntegerToDouble());
        UnaryFunction<Double, Float> fifth = mapperUnaryFunction(fromDoubleToFloat());
        UnaryFunction<Float, BigInteger> sixth = mapperUnaryFunction(fromFloatToBigInteger());
        UnaryFunction<BigInteger, BigInteger> seventh = mapperUnaryFunction(toDoubledBigInteger());

        UnaryFunction<String, BigInteger> chained = UnaryFunctions.chain(
                first, second, third, fourth, fifth, sixth, seventh);

        // When
        BigInteger result = chained.call("1234");

        // Then
        assertThat(result, is(new BigInteger("2468")));
    }

    @Test
    public void threadsEightUnaryFunctionsTogether() {
        // Given
        UnaryFunction<String, BigDecimal> first = mapperUnaryFunction(fromStringToBigDecimal());
        UnaryFunction<BigDecimal, Long> second = mapperUnaryFunction(fromBigDecimalToLong());
        UnaryFunction<Long, Integer> third = mapperUnaryFunction(fromLongToInteger());
        UnaryFunction<Integer, Double> fourth = mapperUnaryFunction(fromIntegerToDouble());
        UnaryFunction<Double, Float> fifth = mapperUnaryFunction(fromDoubleToFloat());
        UnaryFunction<Float, BigInteger> sixth = mapperUnaryFunction(fromFloatToBigInteger());
        UnaryFunction<BigInteger, BigInteger> seventh = mapperUnaryFunction(toDoubledBigInteger());
        UnaryFunction<BigInteger, String> eighth = mapperUnaryFunction(toStringValue());

        UnaryFunction<String, String> chained = UnaryFunctions.chain(
                first, second, third, fourth, fifth, sixth, seventh, eighth);

        // When
        String result = chained.call("1234");

        // Then
        assertThat(result, is("2468"));
    }

    @Test
    public void threadsNinthUnaryFunctionsTogether() {
        // Given
        UnaryFunction<String, BigDecimal> first = mapperUnaryFunction(fromStringToBigDecimal());
        UnaryFunction<BigDecimal, Long> second = mapperUnaryFunction(fromBigDecimalToLong());
        UnaryFunction<Long, Integer> third = mapperUnaryFunction(fromLongToInteger());
        UnaryFunction<Integer, Double> fourth = mapperUnaryFunction(fromIntegerToDouble());
        UnaryFunction<Double, Float> fifth = mapperUnaryFunction(fromDoubleToFloat());
        UnaryFunction<Float, BigInteger> sixth = mapperUnaryFunction(fromFloatToBigInteger());
        UnaryFunction<BigInteger, BigInteger> seventh = mapperUnaryFunction(toDoubledBigInteger());
        UnaryFunction<BigInteger, String> eighth = mapperUnaryFunction(toStringValue());
        UnaryFunction<String, Integer> ninth = mapperUnaryFunction(toLength());

        UnaryFunction<String, Integer> chained = UnaryFunctions.chain(
                first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);

        // When
        Integer result = chained.call("1234");

        // Then
        assertThat(result, is(4));
    }

    private static Mapper<String, Integer> toLength() {
        return new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                return input.length();
            }
        };
    }

    private static Mapper<BigInteger, BigInteger> toDoubledBigInteger() {
        return new Mapper<BigInteger, BigInteger>() {
            @Override public BigInteger map(BigInteger input) {
                return input.multiply(new BigInteger("2"));
            }
        };
    }
}