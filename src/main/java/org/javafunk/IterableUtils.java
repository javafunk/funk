package org.javafunk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IterableUtils {
    public static <T> List<T> toList(Iterable<?extends T> iterable) {
        ArrayList<T> list = new ArrayList<T>();
        for (T item : iterable) {
            list.add(item);
        }
        return list;
    }

    public static <T> Collection<T> materialize(Iterable<? extends T> iterable) {
        return toList(iterable);
    }
}
