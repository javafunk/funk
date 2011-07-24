package org.javafunk.funk;

import org.javafunk.funk.collections.Bag;
import org.javafunk.funk.collections.HashBag;
import org.javafunk.funk.datastructures.FourTuple;
import org.javafunk.funk.datastructures.ThreeTuple;
import org.javafunk.funk.datastructures.TwoTuple;

import java.util.*;

import static java.util.Arrays.asList;

public class Literals {
    private Literals() {}

    public static <E> ListBuilder<E> listFrom(Iterable<? extends E> elements) {
        return new ListBuilder<E>().with(elements);
    }

    public static <E> ListBuilder<E> listFrom(E[] elementArray) {
        return new ListBuilder<E>().with(elementArray);
    }

    public static <E> ListBuilder<E> listOf(Class<E> elementClass) {
        return new ListBuilder<E>();
    }

    public static <E> SetBuilder<E> setFrom(Iterable<? extends E> elements) {
        return new SetBuilder<E>().with(elements);
    }

    public static <E> SetBuilder<E> setFrom(E[] elementArray) {
        return new SetBuilder<E>().with(elementArray);
    }

    public static <E> SetBuilder<E> setOf(Class<E> elementClass) {
        return new SetBuilder<E>();
    }

    public static <E> BagBuilder<E> bagFrom(Iterable<? extends E> elements) {
        return new BagBuilder<E>().with(elements);
    }

    public static <E> BagBuilder<E> bagFrom(E[] elementArray) {
        return new BagBuilder<E>().with(elementArray);
    }

    public static <E> BagBuilder<E> bagOf(Class<E> elementClass) {
        return new BagBuilder<E>();
    }

    public static <K, V> MapBuilder<K, V> mapWith(K key, V value) {
        return new MapBuilder<K, V>().with(key, value);
    }

    public static <K, V> MapBuilder<K, V> mapOf(Class<K> keyClass, Class<V> valueClass) {
        return new MapBuilder<K, V>();
    }

    public static <S, T> TwoTuple<S, T> tuple(S first, T second) {
        return new TwoTuple<S, T>(first, second);
    }

    public static <S, T, U> ThreeTuple<S, T, U> tuple(S first, T second, U third) {
        return new ThreeTuple<S, T, U>(first, second, third);
    }

    public static <S, T, U, V> FourTuple<S, T, U, V> tuple(S first, T second, U third, V fourth) {
        return new FourTuple<S, T, U, V>(first, second, third, fourth);
    }

    public static class ListBuilder<E> extends ArrayList<E> {
        public ListBuilder<E> with(E... elements) {
            return and(asList(elements));
        }

        public ListBuilder<E> with(Iterable<? extends E> elements) {
            return and(elements);
        }

        public ListBuilder<E> and(E... elements) {
            return and(asList(elements));
        }

        public ListBuilder<E> and(Iterable<? extends E> elements) {
            for (E element : elements) {
                add(element);
            }
            return this;
        }

        public List<E> build() {
            return this;
        }
    }

    public static class SetBuilder<E> extends HashSet<E> {
        public SetBuilder<E> with(E... elements) {
            return and(asList(elements));
        }

        public SetBuilder<E> with(Iterable<? extends E> elements) {
            return and(elements);
        }

        public SetBuilder<E> and(E... elements) {
            return and(asList(elements));
        }

        public SetBuilder<E> and(Iterable<? extends E> elements) {
            for (E element : elements) {
                add(element);
            }
            return this;
        }

        public Set<E> build() {
            return this;
        }
    }

    public static class BagBuilder<E> extends HashBag<E> {
        public BagBuilder<E> with(E... elements) {
            return and(asList(elements));
        }

        public BagBuilder<E> with(Iterable<? extends E> elements) {
            return and(elements);
        }

        public BagBuilder<E> and(E... elements) {
            return and(asList(elements));
        }

        public BagBuilder<E> and(Iterable<? extends E> elements) {
            for (E element : elements) {
                add(element);
            }
            return this;
        }

        public Bag<E> build() {
            return this;
        }
    }
    
    public static class MapBuilder<K, V> extends LinkedHashMap<K, V> {
        public MapBuilder<K, V> with(K key, V value) {
            return and(key, value);
        }
        public MapBuilder<K, V> and(K key, V value) {
            put(key, value);
            return this;
        }

        public Map<K, V> build() {
            return this;
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e) {
        return listFrom(asList(e));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2) {
        return listFrom(asList(e1, e2));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3) {
        return listFrom(asList(e1, e2, e3));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4) {
        return listFrom(asList(e1, e2, e3, e4));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5) {
        return listFrom(asList(e1, e2, e3, e4, e5));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6, e7));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e) {
        return setFrom(asList(e));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2) {
        return setFrom(asList(e1, e2));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3) {
        return setFrom(asList(e1, e2, e3));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4) {
        return setFrom(asList(e1, e2, e3, e4));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5) {
        return setFrom(asList(e1, e2, e3, e4, e5));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6, e7));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11on) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on));
    }

    @SuppressWarnings("unchecked")
    public static <E> BagBuilder<E> bagWith(E e) {
        return bagFrom(asList(e));
    }

    @SuppressWarnings("unchecked")
    public static <E> BagBuilder<E> bagWith(E e1, E e2) {
        return bagFrom(asList(e1, e2));
    }

    @SuppressWarnings("unchecked")
    public static <E> BagBuilder<E> bagWith(E e1, E e2, E e3) {
        return bagFrom(asList(e1, e2, e3));
    }

    @SuppressWarnings("unchecked")
    public static <E> BagBuilder<E> bagWith(E e1, E e2, E e3, E e4) {
        return bagFrom(asList(e1, e2, e3, e4));
    }

    @SuppressWarnings("unchecked")
    public static <E> BagBuilder<E> bagWith(E e1, E e2, E e3, E e4, E e5) {
        return bagFrom(asList(e1, e2, e3, e4, e5));
    }

    @SuppressWarnings("unchecked")
    public static <E> BagBuilder<E> bagWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return bagFrom(asList(e1, e2, e3, e4, e5, e6));
    }

    @SuppressWarnings("unchecked")
    public static <E> BagBuilder<E> bagWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return bagFrom(asList(e1, e2, e3, e4, e5, e6, e7));
    }

    @SuppressWarnings("unchecked")
    public static <E> BagBuilder<E> bagWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return bagFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    @SuppressWarnings("unchecked")
    public static <E> BagBuilder<E> bagWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return bagFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    @SuppressWarnings("unchecked")
    public static <E> BagBuilder<E> bagWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return bagFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    @SuppressWarnings("unchecked")
    public static <E> BagBuilder<E> bagWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return bagFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on));
    }
}
