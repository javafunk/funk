package org.javafunk;

import org.javafunk.functional.functors.Predicate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.javafunk.IterableUtils.toSet;
import static org.javafunk.functional.Eager.*;
import static org.javafunk.functional.Lazy.filter;

public class SetUtils {
    public static <T> Set<T> union(Set<? extends T>... sets) {
        return union(asList(sets));
    }

    public static <T> Set<T> union(Iterable<? extends Set<? extends T>> sets) {
        Set<T> unionSet = new HashSet<T>();
        for(Set<? extends T> set : sets) {
            unionSet.addAll(set);
        }
        return unionSet;
    }

    public static <T> Set<T> intersection(Set<? extends T>... sets) {
        return intersection(asList(sets));
    }

    public static <T> Set<T> intersection(Iterable<? extends Set<? extends T>> sets) {
        final Set<? extends T> firstSet = first(sets);
        final Iterable<? extends Set<? extends T>> otherSets = rest(sets);

        return toSet(filter(firstSet, new Predicate<T>() {
            public boolean evaluate(T element) {
                return all(otherSets, contain(element));
            }
        }));
    }

    public static <T> Set<T> difference(Set<? extends T>... sets) {
        return difference(asList(sets));
    }

    public static <T> Set<T> difference(Iterable<? extends Set<? extends T>> sets) {
        final Set<? extends T> firstSet = first(sets);

        return toSet(filter(union(sets), new Predicate<T>() {
            public boolean evaluate(T element) {
                return !firstSet.contains(element);
            }
        }));
    }

    private static <T> Predicate<Collection<? extends T>> contain(final T element) {
        return new Predicate<Collection<? extends T>>() {
            public boolean evaluate(Collection<? extends T> collection) {
                return collection.contains(element);
            }
        };
    }
}
