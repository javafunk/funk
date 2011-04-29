package org.javafunk;

import java.util.Collection;

import static java.util.Arrays.asList;

public class StringUtils {
    public static <T> String join(Collection<T> collection, String separator) {
        return org.apache.commons.lang.StringUtils.join(collection, separator);
    }

    public static <T> String join(Collection<T> collection) {
        return join(collection, "");
    }

    public static <T> String join(T... objects) {
        return join(asList(objects));
    }
}
