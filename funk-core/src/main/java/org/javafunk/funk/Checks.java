package org.javafunk.funk;

public class Checks {
    public static <T> T returnOrThrowIfNull(T value, RuntimeException exception) {
        if (value == null) {
            throw exception;
        }
        return value;
    }

    public static <S, T extends Iterable<S>> T returnOrThrowIfEmpty(
            T value, RuntimeException exception) {
        if (!value.iterator().hasNext()) {
            throw exception;
        }
        return value;
    }
}
