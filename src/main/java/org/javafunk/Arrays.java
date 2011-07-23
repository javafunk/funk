package org.javafunk;

import java.util.List;

public class Arrays {
    private Arrays() {}

    public static <T> List<T> asList(T[] array) {
        return java.util.Arrays.asList(array);
    }

    public static <T> Iterable<T> asIterable(T[] array) {
        return asList(array);
    }
}
