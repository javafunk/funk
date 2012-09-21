package org.javafunk.funk;

public class Checks {
    public static <T> T returnOrThrowIfNull(T value, RuntimeException exception) {
        if (value == null) {
            throw exception;
        }
        return value;
    }
}
