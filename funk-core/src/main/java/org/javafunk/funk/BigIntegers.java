package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegers {
    public static Mapper<String, BigInteger> fromStringToBigInteger() {
        return new Mapper<String, BigInteger>() {
            @Override public BigInteger map(String input) {
                return new BigInteger(input);
            }
        };
    }

    public static Mapper<Integer, BigInteger> fromIntegerToBigInteger() {
        return new Mapper<Integer, BigInteger>() {
            @Override public BigInteger map(Integer input) {
                return new BigInteger(input.toString());
            }
        };
    }

    public static Mapper<Long, BigInteger> fromLongToBigInteger() {
        return new Mapper<Long, BigInteger>() {
            @Override public BigInteger map(Long input) {
                return new BigInteger(input.toString());
            }
        };
    }

    public static Mapper<Float, BigInteger> fromFloatToBigInteger() {
        return new Mapper<Float, BigInteger>() {
            @Override public BigInteger map(Float input) {
                return new BigInteger(((Integer) input.intValue()).toString());
            }
        };
    }

    public static Mapper<Double, BigInteger> fromDoubleToBigInteger() {
        return new Mapper<Double, BigInteger>() {
            @Override public BigInteger map(Double input) {
                return new BigInteger(((Long) input.longValue()).toString());
            }
        };
    }

    public static Mapper<BigDecimal, BigInteger> fromBigDecimalToBigInteger() {
        return new Mapper<BigDecimal, BigInteger>() {
            @Override public BigInteger map(BigDecimal input) {
                return input.toBigInteger();
            }
        };
    }

    public static Mapper<BigInteger, String> toStringValue() {
        return new Mapper<BigInteger, String>() {
            @Override public String map(BigInteger input) {
                return input.toString();
            }
        };
    }
}
