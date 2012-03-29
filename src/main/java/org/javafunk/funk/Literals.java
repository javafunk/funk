package org.javafunk.funk;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.javafunk.funk.datastructures.tuples.*;

import java.util.*;

import static java.util.Arrays.asList;

public class Literals {
    private Literals() {}

    public static <E> ListBuilder<E> list() {
        return new ListBuilder<E>();
    }

    public static <E> ListBuilder<E> listOf(Class<E> elementClass) {
        return new ListBuilder<E>();
    }

    public static <E> ListBuilder<E> listFrom(Iterable<? extends E> elements) {
        return new ListBuilder<E>().with(elements);
    }

    public static <E> ListBuilder<E> listFrom(E[] elementArray) {
        return new ListBuilder<E>().with(elementArray);
    }

    public static <E> SetBuilder<E> set() {
        return new SetBuilder<E>();
    }

    public static <E> SetBuilder<E> setOf(Class<E> elementClass) {
        return new SetBuilder<E>();
    }

    public static <E> SetBuilder<E> setFrom(Iterable<? extends E> elements) {
        return new SetBuilder<E>().with(elements);
    }

    public static <E> SetBuilder<E> setFrom(E[] elementArray) {
        return new SetBuilder<E>().with(elementArray);
    }

    public static <E> MultisetBuilder<E> multiset() {
        return new MultisetBuilder<E>();
    }

    public static <E> MultisetBuilder<E> multisetOf(Class<E> elementClass) {
        return new MultisetBuilder<E>();
    }

    public static <E> MultisetBuilder<E> multisetFrom(Iterable<? extends E> elements) {
        return new MultisetBuilder<E>().with(elements);
    }

    public static <E> MultisetBuilder<E> multisetFrom(E[] elementArray) {
        return new MultisetBuilder<E>().with(elementArray);
    }

    public static <K, V> MapBuilder<K, V> map() {
        return new MapBuilder<K, V>();
    }

    public static <K, V> MapBuilder<K, V> mapOf(Class<K> keyClass, Class<V> valueClass) {
        return new MapBuilder<K, V>();
    }

    public static <K, V> MapBuilder<K, V> mapWith(K key, V value) {
        return new MapBuilder<K, V>().with(key, value);
    }

    public static <S> Single<S> tuple(S first) {
        return new Single<S>(first);
    }

    public static <S, T> Pair<S, T> tuple(S first, T second) {
        return new Pair<S, T>(first, second);
    }

    public static <S, T, U> Triple<S, T, U> tuple(S first, T second, U third) {
        return new Triple<S, T, U>(first, second, third);
    }

    public static <S, T, U, V> Quadruple<S, T, U, V> tuple(S first, T second, U third, V fourth) {
        return new Quadruple<S, T, U, V>(first, second, third, fourth);
    }

    public static <S, T, U, V, W> Quintuple<S, T, U, V, W> tuple(S first, T second, U third, V fourth, W fifth) {
        return new Quintuple<S, T, U, V, W>(first, second, third, fourth, fifth);
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

    public static class MultisetBuilder<E> implements Multiset<E> {
        private HashMultiset<E> delegateMultiset;

        private MultisetBuilder() {
            delegateMultiset = HashMultiset.create();
        }

        public MultisetBuilder<E> with(E... elements) {
            return and(asList(elements));
        }

        public MultisetBuilder<E> with(Iterable<? extends E> elements) {
            return and(elements);
        }

        public MultisetBuilder<E> and(E... elements) {
            return and(asList(elements));
        }

        public MultisetBuilder<E> and(Iterable<? extends E> elements) {
            for (E element : elements) {
                add(element);
            }
            return this;
        }

        public Multiset<E> build() {
            return HashMultiset.create(this);
        }

        @Override
        public int count(Object element) {
            return delegateMultiset.count(element);
        }

        @Override
        public int add(E element, int occurrences) {
            return delegateMultiset.add(element, occurrences);
        }

        @Override
        public int remove(Object element, int occurrences) {
            return delegateMultiset.remove(element, occurrences);
        }

        @Override
        public int setCount(E element, int count) {
            return delegateMultiset.setCount(element, count);
        }

        @Override
        public boolean setCount(E element, int oldCount, int newCount) {
            return delegateMultiset.setCount(element, oldCount, newCount);
        }

        @Override
        public Set<E> elementSet() {
            return delegateMultiset.elementSet();
        }

        @Override
        public Set<Entry<E>> entrySet() {
            return delegateMultiset.entrySet();
        }

        @Override
        public Iterator<E> iterator() {
            return delegateMultiset.iterator();
        }

        @Override
        public Object[] toArray() {
            return delegateMultiset.toArray();
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            return delegateMultiset.toArray(ts);
        }

        @Override
        public int size() {
            return delegateMultiset.size();
        }

        @Override
        public boolean isEmpty() {
            return delegateMultiset.isEmpty();
        }

        @Override
        public boolean contains(Object element) {
            return delegateMultiset.contains(element);
        }

        @Override
        public boolean containsAll(Collection<?> elements) {
            return delegateMultiset.containsAll(elements);
        }

        @Override
        public boolean addAll(Collection<? extends E> es) {
            return delegateMultiset.addAll(es);
        }

        @Override
        public boolean add(E element) {
            return delegateMultiset.add(element);
        }

        @Override
        public boolean remove(Object element) {
            return delegateMultiset.remove(element);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return delegateMultiset.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return delegateMultiset.retainAll(c);
        }

        @Override
        public void clear() {
            delegateMultiset.clear();
        }

        @Override
        public boolean equals(Object o) {
            return delegateMultiset.equals(o);
        }

        @Override
        public int hashCode() {
            return delegateMultiset.hashCode();
        }

        @Override
        public String toString() {
            return delegateMultiset.toString();
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
    public static <E> MultisetBuilder<E> multisetWith(E e) {
        return multisetFrom(asList(e));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2) {
        return multisetFrom(asList(e1, e2));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3) {
        return multisetFrom(asList(e1, e2, e3));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4) {
        return multisetFrom(asList(e1, e2, e3, e4));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5) {
        return multisetFrom(asList(e1, e2, e3, e4, e5));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on));
    }

}
