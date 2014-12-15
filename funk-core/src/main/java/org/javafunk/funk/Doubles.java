package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Doubles {
    public static Mapper<String, Double> fromStringToDouble() {
        return new Mapper<String, Double>() {
            @Override public Double map(String input) {
                return Double.valueOf(input);
            }
        };
    }

    public static Mapper<Integer, Double> fromIntegerToDouble() {
        return new Mapper<Integer, Double>() {
            @Override public Double map(Integer input) {
                return input.doubleValue();
            }
        };
    }

    public static Mapper<Long, Double> fromLongToDouble() {
        return new Mapper<Long, Double>() {
            @Override public Double map(Long input) {
                return input.doubleValue();
            }
        };
    }

    public static Mapper<BigInteger, Double> fromBigIntegerToDouble() {
        return new Mapper<BigInteger, Double>() {
            @Override public Double map(BigInteger input) {
                return input.doubleValue();
            }
        };
    }

    public static Mapper<Float, Double> fromFloatToDouble() {
        return new Mapper<Float, Double>() {
            @Override public Double map(Float input) {
                return input.doubleValue();
            }
        };
    }

    public static Mapper<BigDecimal, Double> fromBigDecimalToDouble() {
        return new Mapper<BigDecimal, Double>() {
            @Override public Double map(BigDecimal input) {
                return input.doubleValue();
            }
        };
    }

    public static Mapper<Double, String> toStringValue() {
        return new Mapper<Double, String>() {
            @Override public String map(Double input) {
                return input.toString();
            }
        };
    }
}
