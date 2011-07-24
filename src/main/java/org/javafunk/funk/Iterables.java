package org.javafunk.funk;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.javafunk.funk.functors.Reducer;

import java.util.*;

import static org.javafunk.funk.Eager.reduce;

public class Iterables {
    private Iterables() {}

    public static <T> List<T> asList(Iterable<? extends T> iterable) {
        return reduce(iterable, new ArrayList<T>(), new Reducer<T, List<T>>() {
            public List<T> accumulate(List<T> accumulator, T element) {
                accumulator.add(element);
                return accumulator;
            }
        });
    }

    public static <T> Set<T> asSet(Iterable<? extends T> iterable) {
        return reduce(iterable, new HashSet<T>(), new Reducer<T, Set<T>>() {
            public Set<T> accumulate(Set<T> accumulator, T element) {
                accumulator.add(element);
                return accumulator;
            }
        });
    }

    public static <T> Multiset<T> asMultiset(Iterable<? extends T> iterable) {
        return reduce(iterable, HashMultiset.<T>create(), new Reducer<T, HashMultiset<T>>() {
            public HashMultiset<T> accumulate(HashMultiset<T> accumulator, T element) {
                accumulator.add(element);
                return accumulator;
            }
        });
    }

    public static <T> Collection<T> materialize(Iterable<? extends T> iterable) {
        return asList(iterable);
    }
}
