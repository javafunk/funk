package org.smallvaluesofcool.misc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static java.util.Arrays.asList;

public class Literals {

    /**
     * / Create {@Link List} with specified elements.
     */
    public static <T> List<T> listWith(T... elements) {
        return new ArrayList<T>(asList(elements));
    }

    /**
     * / Create {@Link Map} with specified entry.
     */
    public static <S, T> MapBuilder<S, T> mapWith(S key, T value) {
        return new MapBuilder<S, T>().and(key, value);
    }

    public static class MapBuilder<S, T> extends LinkedHashMap<S, T> {
        /**
         * / add specified entry to {@Link Map}.
         */
        public MapBuilder<S, T> and(S key, T value) {
            put(key, value);
            return this;
        }
    }
}
