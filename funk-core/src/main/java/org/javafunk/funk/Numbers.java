package org.javafunk.funk;

import org.javafunk.funk.functors.Reducer;
import org.javafunk.funk.monads.Option;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static java.lang.String.format;
import static org.javafunk.funk.Accumulators.*;
import static org.javafunk.funk.monads.Option.none;
import static org.javafunk.funk.monads.Option.option;

public class Numbers {
    private Numbers() {}

    public static <T extends Number> T sumOrThrow(Iterable<T> iterable, Class<T> numberClass) {
        return process(iterable, numberClass, sumAccumulatorMap, "sum");
    }

    public static <T extends Number> Option<T> sum(Iterable<T> iterable, Class<T> numberClass) {
        try {
            return option(sumOrThrow(iterable, numberClass));
        } catch(ArithmeticException exception) {
            return none();
        }
    }

    public static <T extends Number> T multiplyOrThrow(Iterable<T> iterable, Class<T> numberClass) {
        return process(iterable, numberClass, multiplyAccumulatorMap, "multiply");
    }

    public static <T extends Number> Option<T> multiply(Iterable<T> iterable, Class<T> numberClass) {
        try {
            return option(multiplyOrThrow(iterable, numberClass));
        } catch(ArithmeticException exception) {
            return none();
        }
    }

    private static final Map<Class<?>, Reducer<?, ?>> sumAccumulatorMap =
            Literals.<Class<?>, Reducer<?, ?>>mapBuilder()
                    .withKeyValuePair(Integer.class, integerAdditionAccumulator())
                    .withKeyValuePair(Long.class, longAdditionAccumulator())
                    .withKeyValuePair(BigInteger.class, bigIntegerAdditionAccumulator())
                    .withKeyValuePair(Float.class, floatAdditionAccumulator())
                    .withKeyValuePair(Double.class, doubleAdditionAccumulator())
                    .withKeyValuePair(BigDecimal.class, bigDecimalAdditionAccumulator())
                    .build();

    private static final Map<Class<?>, Reducer<?, ?>> multiplyAccumulatorMap =
            Literals.<Class<?>, Reducer<?, ?>>mapBuilder()
                    .withKeyValuePair(Integer.class, integerMultiplicationAccumulator())
                    .withKeyValuePair(Long.class, longMultiplicationAccumulator())
                    .withKeyValuePair(BigInteger.class, bigIntegerMultiplicationAccumulator())
                    .withKeyValuePair(Float.class, floatMultiplicationAccumulator())
                    .withKeyValuePair(Double.class, doubleMultiplicationAccumulator())
                    .withKeyValuePair(BigDecimal.class, bigDecimalMultiplicationAccumulator())
                    .build();

    @SuppressWarnings("unchecked")
    private static <T extends Number> T process(
            Iterable<T> iterable,
            Class<T> numberClass,
            Map<Class<?>, Reducer<?, ?>> reducerMap,
            String operation) {
        Checks.returnOrThrowIfNull(iterable,
                new NullPointerException(format("Cannot %s a null collection.", operation)));
        Checks.returnOrThrowIfNull(numberClass,
                new NullPointerException(format("Cannot %s when a null number class is provided.", operation)));
        Checks.returnOrThrowIfEmpty(iterable,
                new ArithmeticException(format("Cannot %s a collection containing no numbers.", operation)));

        if (reducerMap.containsKey(numberClass)) {
            return (T) Eagerly.reduce(iterable, (Reducer<Object, Object>) reducerMap.get(numberClass));
        } else {
            throw new UnsupportedOperationException(
                    format("Cannot %s numbers of type: %s", operation, numberClass.getSimpleName()));
        }
    }
}
