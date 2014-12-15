package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.google.common.base.Preconditions.checkNotNull;

public class Integers {
    public static Mapper<String, Integer> fromStringToInteger() {
        return new Mapper<String, Integer>() {
            @Override public Integer map(String input) {
                checkNotNull(input);
                return Integer.valueOf(input);
            }
        };
    }

    public static Mapper<Long, Integer> fromLongToInteger() {
        return new Mapper<Long, Integer>() {
            @Override public Integer map(Long input) {
                return input.intValue();
            }
        };
    }

    public static Mapper<BigInteger, Integer> fromBigIntegerToInteger() {
        return new Mapper<BigInteger, Integer>() {
            @Override public Integer map(BigInteger input) {
                return input.intValue();
            }
        };
    }

    public static Mapper<Float, Integer> fromFloatToInteger() {
        return new Mapper<Float, Integer>() {
            @Override public Integer map(Float input) {
                return input.intValue();
            }
        };
    }

    public static Mapper<Double, Integer> fromDoubleToInteger() {
        return new Mapper<Double, Integer>() {
            @Override public Integer map(Double input) {
                return input.intValue();
            }
        };
    }

    public static Mapper<BigDecimal, Integer> fromBigDecimalToInteger() {
        return new Mapper<BigDecimal, Integer>() {
            @Override public Integer map(BigDecimal input) {
                return input.intValue();
            }
        };
    }
}
