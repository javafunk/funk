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
import org.javafunk.funk.monads.Option;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.javafunk.funk.monads.Option.none;
import static org.javafunk.funk.monads.Option.option;

public class Longs {
    private Longs() {}

    public static Mapper<String, Long> fromStringToLong() {
        return new Mapper<String, Long>() {
            @Override public Long map(String input) {
                checkNotNull(input);
                return Long.valueOf(input);
            }
        };
    }

    public static Mapper<Integer, Long> fromIntegerToLong() {
        return new Mapper<Integer, Long>() {
            @Override public Long map(Integer input) {
                return input.longValue();
            }
        };
    }

    public static Mapper<BigInteger, Long> fromBigIntegerToLong() {
        return new Mapper<BigInteger, Long>() {
            @Override public Long map(BigInteger input) {
                return input.longValue();
            }
        };
    }

    public static Mapper<Float, Long> fromFloatToLong() {
        return new Mapper<Float, Long>() {
            @Override public Long map(Float input) {
                return input.longValue();
            }
        };
    }

    public static Mapper<Double, Long> fromDoubleToLong() {
        return new Mapper<Double, Long>() {
            @Override public Long map(Double input) {
                return input.longValue();
            }
        };
    }

    public static Mapper<BigDecimal, Long> fromBigDecimalToLong() {
        return new Mapper<BigDecimal, Long>() {
            @Override public Long map(BigDecimal input) {
                return input.longValue();
            }
        };
    }

    public static Long toLongOrNull(String value) {
        return option(value).flatMap(fromStringToPossibleLong()).getOrNull();
    }

    public static Long toLongOrNull(Integer value) {
        return option(value).map(fromIntegerToLong()).getOrNull();
    }

    public static Long toLongOrNull(BigInteger value) {
        return option(value).map(fromBigIntegerToLong()).getOrNull();
    }

    public static Long toLongOrNull(Float value) {
        return option(value).map(fromFloatToLong()).getOrNull();
    }

    public static Long toLongOrNull(Double value) {
        return option(value).map(fromDoubleToLong()).getOrNull();
    }

    public static Long toLongOrNull(BigDecimal value) {
        return option(value).map(fromBigDecimalToLong()).getOrNull();
    }

    public static Mapper<String, Option<Long>> fromStringToPossibleLong() {
        return new Mapper<String, Option<Long>>() {
            @Override public Option<Long> map(String input) {
                try {
                    return option(Long.valueOf(input));
                } catch(NumberFormatException e) {
                    return none();
                } catch(NullPointerException e) {
                    return none();
                }
            }
        };
    }
}
