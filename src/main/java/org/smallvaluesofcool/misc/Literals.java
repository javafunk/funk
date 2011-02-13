package org.smallvaluesofcool.misc;

import org.smallvaluesofcool.misc.collections.Bag;
import org.smallvaluesofcool.misc.collections.HashBag;
import org.smallvaluesofcool.misc.datastructures.FourTuple;
import org.smallvaluesofcool.misc.datastructures.ThreeTuple;
import org.smallvaluesofcool.misc.datastructures.TwoTuple;

import java.util.*;

import static java.util.Arrays.asList;

public class Literals {
    public static <T> ListBuilder<T> listWith(T... elements) {
        return new ListBuilder<T>().and(elements);
    }

    public static <T> SetBuilder<T> setWith(T... elements) {
        return new SetBuilder<T>().and(elements);
    }

    public static <T> BagBuilder<T> bagWith(T... elements) {
        return new BagBuilder<T>().and(elements);
    }

    public static <S, T> MapBuilder<S, T> mapWith(S key, T value) {
        return new MapBuilder<S, T>().and(key, value);
    }

    public static <S, T> TwoTuple<S, T> twoTuple(S first, T second) {
        return new TwoTuple<S, T>(first, second);
    }

    public static <S, T, U> ThreeTuple<S, T, U> threeTuple(S first, T second, U third) {
        return new ThreeTuple<S, T, U>(first, second, third);
    }

    public static <S, T, U, V> FourTuple<S, T, U, V> fourTuple(S first, T second, U third, V fourth) {
        return new FourTuple<S, T, U, V>(first, second, third, fourth);
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

    public static class SetBuilder<T> extends HashSet<T> {
        public SetBuilder<T> and(T... elements) {
            return and(asList(elements));
        }

        public SetBuilder<T> and(Collection<T> elements) {
            addAll(elements);
            return this;
        }

        public Set<T> build() {
            return this;
        }
    }

    public static class BagBuilder<T> extends HashBag<T> {
        public BagBuilder<T> and(T... elements) {
            return and(asList(elements));
        }

        public BagBuilder<T> and(Collection<T> elements) {
            addAll(elements);
            return this;
        }

        public Bag<T> build() {
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
