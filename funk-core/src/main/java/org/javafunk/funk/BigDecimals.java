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

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimals {
    private BigDecimals() {}

    public static Mapper<Long, BigDecimal> fromLongToBigDecimal() {
        return new Mapper<Long, BigDecimal>() {
            @Override public BigDecimal map(Long input) {
                return new BigDecimal(input);
            }
        };
    }

    public static Mapper<Double, BigDecimal> fromDoubleToBigDecimal() {
        return new Mapper<Double, BigDecimal>() {
            @Override public BigDecimal map(Double input) {
                return new BigDecimal(input);
            }
        };
    }

    public static Mapper<Integer, BigDecimal> fromIntegerToBigDecimal() {
        return new Mapper<Integer, BigDecimal>() {
            @Override public BigDecimal map(Integer input) {
                return new BigDecimal(input);
            }
        };
    }

    public static Mapper<String, BigDecimal> fromStringToBigDecimal() {
        return new Mapper<String, BigDecimal>() {
            @Override public BigDecimal map(String input) {
                return new BigDecimal(input);
            }
        };
    }

    public static Mapper<BigDecimal, String> toPlainString() {
        return new Mapper<BigDecimal, String>() {
            @Override public String map(BigDecimal input) {
                return input.toPlainString();
            }
        };
    }

    public static Mapper<BigDecimal, BigDecimal> toScaled(final Integer scale, final RoundingMode roundingMode) {
        return new Mapper<BigDecimal, BigDecimal>() {
            @Override public BigDecimal map(BigDecimal input) {
                return input.setScale(scale, roundingMode);
            }
        };
    }

    public static BigDecimal bigDecimal(Integer value) {
        return new BigDecimal(value);
    }

    public static BigDecimal bigDecimal(Long value) {
        return new BigDecimal(value);
    }

    public static BigDecimal bigDecimal(Double value) {
        return new BigDecimal(value);
    }

    public static BigDecimal bigDecimal(String value) {
        return new BigDecimal(value);
    }
}
