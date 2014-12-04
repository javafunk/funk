package org.javafunk.funk;

import static org.javafunk.funk.Eagerly.any;
import static org.javafunk.funk.Predicates.equalTo;

public class Checks {
    private static final NullPointerException NULL_POINTER_EXCEPTION = new NullPointerException();;

    public static <T> T returnOrThrowIfNull(T value, RuntimeException exception) {
        return orThrowIf(value, value == null, exception);
    }

    public static <S, T extends Iterable<S>> T returnOrThrowIfEmpty(
            T value, RuntimeException exception) {
        return orThrowIf(
                value,
                !value.iterator().hasNext(),
                exception);
    }

    public static <S, T extends Iterable<S>> T returnOrThrowIfContainsNull(T iterable) {
        return orThrowIf(
                iterable,
                any(iterable, equalTo(null)),
                NULL_POINTER_EXCEPTION);
    }

    private static <T> T orThrowIf(T value, boolean condition, RuntimeException exception) {
        throwIf(condition, exception);
        return value;
    }

    private static void throwIf(boolean condition, RuntimeException exception) {
        if (condition) throw exception;
    }
}
