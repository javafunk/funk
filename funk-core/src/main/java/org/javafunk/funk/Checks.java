package org.javafunk.funk;

import org.javafunk.funk.functors.functions.NullaryFunction;

import static org.javafunk.funk.Eagerly.any;
import static org.javafunk.funk.Exceptions.nullPointerFactory;
import static org.javafunk.funk.Predicates.equalTo;

public class Checks {
    public static <T> T returnOrThrowIfNull(T value, NullaryFunction<? extends RuntimeException> exceptionFactory) {
        return orThrowIf(value, value == null, exceptionFactory);
    }

    public static <S, T extends Iterable<S>> T returnOrThrowIfEmpty(
            T value, NullaryFunction<? extends RuntimeException> exceptionFactory) {
        return orThrowIf(
                value,
                !value.iterator().hasNext(),
                exceptionFactory);
    }

    public static <S, T extends Iterable<S>> T returnOrThrowIfContainsNull(T iterable) {
        return orThrowIf(
                iterable,
                any(iterable, equalTo(null)),
                nullPointerFactory());
    }

    private static <T> T orThrowIf(T value, boolean condition, NullaryFunction<? extends RuntimeException> exceptionFactory) {
        throwIf(condition, exceptionFactory);
        return value;
    }

    private static void throwIf(boolean condition, NullaryFunction<? extends RuntimeException> exceptionFactory) {
        if (condition) throw exceptionFactory.call();
    }
}
