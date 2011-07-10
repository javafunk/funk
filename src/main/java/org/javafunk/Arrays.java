package org.javafunk;

import java.util.List;

import static java.util.Arrays.asList;

public class Arrays {
    private Arrays() {}

    public static <T> List<T> toList(T[] array) {
        return asList(array);
    }

    public static <T> Iterable<T> toIterable(T[] array) {
        return toList(array);
    }
}
