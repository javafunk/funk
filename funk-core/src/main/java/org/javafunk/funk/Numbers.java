package org.javafunk.funk;

import java.math.BigDecimal;
import java.math.BigInteger;

import static java.lang.String.format;
import static org.javafunk.funk.Accumulators.bigDecimalAdditionAccumulator;
import static org.javafunk.funk.Accumulators.bigDecimalMultiplicationAccumulator;
import static org.javafunk.funk.Accumulators.bigIntegerAdditionAccumulator;
import static org.javafunk.funk.Accumulators.bigIntegerMultiplicationAccumulator;
import static org.javafunk.funk.Accumulators.doubleAdditionAccumulator;
import static org.javafunk.funk.Accumulators.doubleMultiplicationAccumulator;
import static org.javafunk.funk.Accumulators.floatAdditionAccumulator;
import static org.javafunk.funk.Accumulators.floatMultiplicationAccumulator;
import static org.javafunk.funk.Accumulators.integerAdditionAccumulator;
import static org.javafunk.funk.Accumulators.integerMultiplicationAccumulator;
import static org.javafunk.funk.Accumulators.longAdditionAccumulator;
import static org.javafunk.funk.Accumulators.longMultiplicationAccumulator;

public class Numbers {
    @SuppressWarnings("unchecked")
    public static <T extends Number> T sum(Iterable<T> iterable, Class<T> numberClass) {
        if (numberClass == Integer.class)    return (T) Eagerly.reduce((Iterable<Integer>)    iterable, integerAdditionAccumulator());
        if (numberClass == Long.class)       return (T) Eagerly.reduce((Iterable<Long>)       iterable, longAdditionAccumulator());
        if (numberClass == BigInteger.class) return (T) Eagerly.reduce((Iterable<BigInteger>) iterable, bigIntegerAdditionAccumulator());
        if (numberClass == Float.class)      return (T) Eagerly.reduce((Iterable<Float>)      iterable, floatAdditionAccumulator());
        if (numberClass == Double.class)     return (T) Eagerly.reduce((Iterable<Double>)     iterable, doubleAdditionAccumulator());
        if (numberClass == BigDecimal.class) return (T) Eagerly.reduce((Iterable<BigDecimal>) iterable, bigDecimalAdditionAccumulator());
        throw new UnsupportedOperationException(format("Cannot sum numbers of type: %s", numberClass.getSimpleName()));
    }

    @SuppressWarnings("unchecked")
    public static <T extends Number> T multiply(Iterable<T> iterable, Class<T> numberClass) {
        if (numberClass == Integer.class)    return (T) Eagerly.reduce((Iterable<Integer>)    iterable, integerMultiplicationAccumulator());
        if (numberClass == Long.class)       return (T) Eagerly.reduce((Iterable<Long>)       iterable, longMultiplicationAccumulator());
        if (numberClass == BigInteger.class) return (T) Eagerly.reduce((Iterable<BigInteger>) iterable, bigIntegerMultiplicationAccumulator());
        if (numberClass == Float.class)      return (T) Eagerly.reduce((Iterable<Float>)      iterable, floatMultiplicationAccumulator());
        if (numberClass == Double.class)     return (T) Eagerly.reduce((Iterable<Double>)     iterable, doubleMultiplicationAccumulator());
        if (numberClass == BigDecimal.class) return (T) Eagerly.reduce((Iterable<BigDecimal>) iterable, bigDecimalMultiplicationAccumulator());
        throw new UnsupportedOperationException(format("Cannot multiply numbers of type: %s", numberClass.getSimpleName()));
    }
}
