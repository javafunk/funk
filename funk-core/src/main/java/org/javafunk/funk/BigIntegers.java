package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;

import java.math.BigInteger;

public class BigIntegers {
    public static Mapper<Float, BigInteger> fromFloatToBigInteger() {
        return new Mapper<Float, BigInteger>() {
            @Override public BigInteger map(Float input) {
                return new BigInteger(((Integer) input.intValue()).toString());
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
