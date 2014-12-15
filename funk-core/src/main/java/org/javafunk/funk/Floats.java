package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Floats {
    public static Mapper<String, Float> fromStringToFloat() {
        return new Mapper<String, Float>() {
            @Override public Float map(String input) {
                return Float.valueOf(input);
            }
        };
    }

    public static Mapper<Integer, Float> fromIntegerToFloat() {
        return new Mapper<Integer, Float>() {
            @Override public Float map(Integer input) {
                return input.floatValue();
            }
        };
    }

    public static Mapper<Long, Float> fromLongToFloat() {
        return new Mapper<Long, Float>() {
            @Override public Float map(Long input) {
                return input.floatValue();
            }
        };
    }

    public static Mapper<BigInteger, Float> fromBigIntegerToFloat() {
        return new Mapper<BigInteger, Float>() {
            @Override public Float map(BigInteger input) {
                return input.floatValue();
            }
        };
    }

    public static Mapper<Double, Float> fromDoubleToFloat() {
        return new Mapper<Double, Float>() {
            @Override public Float map(Double input) {
                return input.floatValue();
            }
        };
    }

    public static Mapper<BigDecimal, Float> fromBigDecimalToFloat() {
        return new Mapper<BigDecimal, Float>() {
            @Override public Float map(BigDecimal input) {
                return input.floatValue();
            }
        };
    }

    public static Mapper<Float, String> toStringValue() {
        return new Mapper<Float, String>() {
            @Override public String map(Float input) {
                return input.toString();
            }
        };
    }
}
