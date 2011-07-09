package org.javafunk;

import java.util.*;

public class IterableUtils {
    public static <T> List<T> toList(Iterable<? extends T> iterable) {
        ArrayList<T> list = new ArrayList<T>();
        for (T item : iterable) {
            list.add(item);
        }
        return list;
    }

    // TODO: test this
    public static <T> Set<T> toSet(Iterable<? extends T> iterable) {
        return new HashSet<T>(toList(iterable));
    }

    public static <T> Collection<T> materialize(Iterable<? extends T> iterable) {
        return toList(iterable);
    }
}
