package org.smallvaluesofcool.misc;

import java.util.*;

import static java.util.Arrays.asList;

public class Literals {
    public static <T> ListBuilder<T> listWith(T... elements) {
        return new ListBuilder<T>().and(elements);
    }

    public static <S, T> MapBuilder<S, T> mapWith(S key, T value) {
        return new MapBuilder<S, T>().and(key, value);
    }

    public static class ListBuilder<T> extends ArrayList<T> {
        public ListBuilder<T> and(T... elements) {
            return and(asList(elements));
        }

        public ListBuilder<T> and(Collection<T> elements) {
            addAll(elements);
            return this;
        }

        public List<T> build() {
            return this;
        }
    }

    public static class MapBuilder<S, T> extends LinkedHashMap<S, T> {
        public MapBuilder<S, T> and(S key, T value) {
            put(key, value);
            return this;
        }

        public Map<S, T> build() {
            return this;
        }
    }
}
