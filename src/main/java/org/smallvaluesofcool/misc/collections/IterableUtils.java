package org.smallvaluesofcool.misc.collections;

import java.util.ArrayList;
import java.util.List;

public class IterableUtils {
    public static <T> List<T> toList(Iterable<T> iterable) {
        ArrayList<T> list = new ArrayList<T>();
        for (T item : iterable) {
            list.add(item);
        }
        return list;
    }
}
