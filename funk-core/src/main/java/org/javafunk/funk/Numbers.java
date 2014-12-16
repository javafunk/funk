/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Reducer;
import org.javafunk.funk.functors.functions.BinaryFunction;
import org.javafunk.funk.monads.Option;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static java.lang.String.format;
import static org.javafunk.funk.Accumulators.*;
import static org.javafunk.funk.Exceptions.arithmeticFactory;
import static org.javafunk.funk.Exceptions.nullPointerFactory;
import static org.javafunk.funk.monads.Option.none;
import static org.javafunk.funk.monads.Option.option;

public class Numbers {
    private Numbers() {}

    /**
     * Sums the supplied {@code Iterable} of {@code Number}s returning
     * an instance of the supplied concrete {@code Number} type.
     *
     * <p>Currently, all elements in the supplied {@code Iterable}
     * must be instances of the supplied concrete {@code Number} type
     * although this may change in a future version of Funk.</p>
     *
     * <p>The only subtypes of {@code Number} currently supported are
     * {@code Integer}, {@code Long}, {@code BigInteger}, {@code Float},
     * {@code Double} and {@code BigDecimal} although more may be supported
     * in a future version of Funk.</p>
     *
     * <p>In the case that the supplied iterable is empty, an
     * {@code ArithmeticException} is thrown. If either the supplied
     * {@code Iterable} or the supplied {@code Class} are {@code null},
     * a {@code NullPointerException} is thrown.</p>
     *
     * @param iterable    An {@code Iterable} of {@code Number}s to be summed.
     * @param numberClass A {@code Class} representing the concrete type of the
     *                    {@code Number}s to be summed.
     * @param <T>         The type of the {@code Number}s to be summed.
     * @return The sum of the supplied numbers.
     * @throws ArithmeticException  if the supplied {@code Iterable} is empty.
     * @throws NullPointerException if either the supplied {@code Iterable} or
     *                              the supplied {@code Class} are {@code null}.
     */
    public static <T extends Number> T sumOrThrow(Iterable<T> iterable, Class<T> numberClass) {
        return process(iterable, numberClass, sumAccumulatorMap, "sum");
    }

    /**
     * Sums the supplied {@code Iterable} of {@code Number}s returning
     * a {@code Some} over an instance of the supplied concrete
     * {@code Number} type. In the case that the supplied iterable is empty,
     * {@code None} is returned.
     *
     * <p>Currently, all elements in the supplied {@code Iterable}
     * must be instances of the supplied concrete {@code Number} type
     * although this may change in a future version of Funk.</p>
     *
     * <p>The only subtypes of {@code Number} currently supported are
     * {@code Integer}, {@code Long}, {@code BigInteger}, {@code Float},
     * {@code Double} and {@code BigDecimal} although more may be supported
     * in a future version of Funk.</p>
     *
     * <p>If either the supplied {@code Iterable} or the supplied
     * {@code Class} are {@code null}, a {@code NullPointerException} is
     * thrown.</p>
     *
     * @param iterable    An {@code Iterable} of {@code Number}s to be summed.
     * @param numberClass A {@code Class} representing the concrete type of the
     *                    {@code Number}s to be summed.
     * @param <T>         The type of the {@code Number}s to be summed.
     * @return An {@code Option} over the sum of the supplied numbers.
     * @throws NullPointerException if either the supplied {@code Iterable} or
     *                              the supplied {@code Class} are {@code null}.
     */
    public static <T extends Number> Option<T> sum(Iterable<T> iterable, Class<T> numberClass) {
        try {
            return option(sumOrThrow(iterable, numberClass));
        } catch (ArithmeticException exception) {
            return none();
        }
    }

    /**
     * Multiplies the supplied {@code Iterable} of {@code Number}s
     * returning an instance of the supplied concrete {@code Number} type.
     *
     * <p>Currently, all elements in the supplied {@code Iterable}
     * must be instances of the supplied concrete {@code Number} type
     * although this may change in a future version of Funk.</p>
     *
     * <p>The only subtypes of {@code Number} currently supported are
     * {@code Integer}, {@code Long}, {@code BigInteger}, {@code Float},
     * {@code Double} and {@code BigDecimal} although more may be supported
     * in a future version of Funk.</p>
     *
     * <p>In the case that the supplied iterable is empty, an
     * {@code ArithmeticException} is thrown. If either the supplied
     * {@code Iterable} or the supplied {@code Class} are {@code null},
     * a {@code NullPointerException} is thrown.</p>
     *
     * @param iterable    An {@code Iterable} of {@code Number}s to be multiplied.
     * @param numberClass A {@code Class} representing the concrete type of the
     *                    {@code Number}s to be multiplied.
     * @param <T>         The type of the {@code Number}s to be multiplied.
     * @return The product of the supplied numbers.
     * @throws ArithmeticException  if the supplied {@code Iterable} is empty.
     * @throws NullPointerException if either the supplied {@code Iterable} or
     *                              the supplied {@code Class} are {@code null}.
     */
    public static <T extends Number> T multiplyOrThrow(Iterable<T> iterable, Class<T> numberClass) {
        return process(iterable, numberClass, multiplyAccumulatorMap, "multiply");
    }

    /**
     * Multiplies the supplied {@code Iterable} of {@code Number}s
     * returning a {@code Some} over an instance of the supplied concrete
     * {@code Number} type. In the case that the supplied iterable is empty,
     * {@code None} is returned.
     *
     * <p>Currently, all elements in the supplied {@code Iterable}
     * must be instances of the supplied concrete {@code Number} type
     * although this may change in a future version of Funk.</p>
     *
     * <p>The only subtypes of {@code Number} currently supported are
     * {@code Integer}, {@code Long}, {@code BigInteger}, {@code Float},
     * {@code Double} and {@code BigDecimal} although more may be supported
     * in a future version of Funk.</p>
     *
     * <p>If either the supplied {@code Iterable} or the supplied
     * {@code Class} are {@code null}, a {@code NullPointerException} is
     * thrown.</p>
     *
     * @param iterable    An {@code Iterable} of {@code Number}s to be multiplied.
     * @param numberClass A {@code Class} representing the concrete type of the
     *                    {@code Number}s to be multiplied.
     * @param <T>         The type of the {@code Number}s to be multiplied.
     * @return An {@code Option} over the product of the supplied numbers.
     * @throws NullPointerException if either the supplied {@code Iterable} or
     *                              the supplied {@code Class} are {@code null}.
     */
    public static <T extends Number> Option<T> multiply(Iterable<T> iterable, Class<T> numberClass) {
        try {
            return option(multiplyOrThrow(iterable, numberClass));
        } catch (ArithmeticException exception) {
            return none();
        }
    }

    private static final Map<Class<?>, BinaryFunction<?, ?, ?>> sumAccumulatorMap =
            Literals.<Class<?>, BinaryFunction<?, ?, ?>>mapBuilder()
                    .withKeyValuePair(Integer.class, integerAdditionAccumulator())
                    .withKeyValuePair(Long.class, longAdditionAccumulator())
                    .withKeyValuePair(BigInteger.class, bigIntegerAdditionAccumulator())
                    .withKeyValuePair(Float.class, floatAdditionAccumulator())
                    .withKeyValuePair(Double.class, doubleAdditionAccumulator())
                    .withKeyValuePair(BigDecimal.class, bigDecimalAdditionAccumulator())
                    .build();

    private static final Map<Class<?>, BinaryFunction<?, ?, ?>> multiplyAccumulatorMap =
            Literals.<Class<?>, BinaryFunction<?, ?, ?>>mapBuilder()
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
            Map<Class<?>, BinaryFunction<?, ?, ?>> reducerMap,
            String operation) {
        Checks.returnOrThrowIfNull(iterable,
                nullPointerFactory(format("Cannot %s a null collection.", operation)));
        Checks.returnOrThrowIfNull(numberClass,
                nullPointerFactory(format("Cannot %s when a null number class is provided.", operation)));
        Checks.returnOrThrowIfEmpty(iterable,
                arithmeticFactory(format("Cannot %s a collection containing no numbers.", operation)));

        if (reducerMap.containsKey(numberClass)) {
            return (T) Eagerly.reduce(iterable, (Reducer<Object, Object>) reducerMap.get(numberClass));
        } else {
            throw new UnsupportedOperationException(
                    format("Cannot %s numbers of type: %s", operation, numberClass.getSimpleName()));
        }
    }
}
