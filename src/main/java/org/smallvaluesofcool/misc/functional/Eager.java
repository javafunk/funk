package org.smallvaluesofcool.misc.functional;

import org.smallvaluesofcool.misc.collections.TwoTuple;

import java.util.Collection;
import java.util.Iterator;

import static org.smallvaluesofcool.misc.collections.IterableUtils.materialize;
import static org.smallvaluesofcool.misc.collections.IteratorUtils.toIterable;

public class Eager {
    public static <T> T reduce(Iterable<? extends T> iterable, ReduceFunction<T> function, T initialValue) {
        T accumulator = initialValue;
        for (T element : iterable) {
            accumulator = function.accumulate(accumulator, element);
        }
        return accumulator;
    }

    public static <T> T reduce(Iterable<? extends T> iterable, ReduceFunction<T> function) {
        final Iterator<? extends T> iterator = iterable.iterator();
        final T firstElement = iterator.next();
        final Iterable<? extends T> restOfElements = toIterable(iterator);
        return reduce(restOfElements, function, firstElement);
    }

    public static Integer sum(Iterable<Integer> iterable) {
        return reduce(iterable, integerAdditionAccumulator());
    }

    public static ReduceFunction<Integer> integerAdditionAccumulator() {
        return new ReduceFunction<Integer>() {
            public Integer accumulate(Integer accumulator, Integer element) {
                return accumulator + element;
            }
        };
    }

    public static ReduceFunction<Long> longAdditionAccumulator() {
        return new ReduceFunction<Long>() {
            public Long accumulate(Long accumulator, Long element) {
                return accumulator + element;
            }
        };
    }

    public static <T> Boolean any(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        for (T item : iterable) {
            if (predicate.matches(item)) {
                return true;
            }
        }
        return false;
    }

    public static <T> Boolean all(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        for (T item : iterable) {
            if (!predicate.matches(item)) {
                return false;
            }
        }
        return true;
    }

    public static <T> Boolean none(Iterable<? extends T> items, PredicateFunction<T> predicate) {
        return !any(items, predicate);
    }

    public static <T extends Comparable<T>> T max(Iterable<? extends T> iterable) {
        T max = null;
        for (T item : iterable) {
            if (max == null || (item != null && item.compareTo(max) > 0)) {
                max = item;
            }
        }
        return max;
    }

    public static <T extends Comparable<T>> T min(Iterable<? extends T> iterable) {
        T min = null;
        for (T item : iterable) {
            if (min == null || (item != null && item.compareTo(min) < 0)) {
                min = item;
            }
        }
        return min;
    }

    public static <S, T> Collection<T> map(Iterable<? extends S> iterable, MapFunction<S, T> function) {
        return materialize(Lazy.map(iterable, function));
    }

    public static <S, T> Collection<TwoTuple<S, T>> zip(Iterable<? extends S> iterable1, Iterable<? extends T> iterable2) {
        return materialize(Lazy.zip(iterable1, iterable2));
    }

    public static <T> Collection<TwoTuple<Integer, T>> enumerate(Iterable<? extends T> iterable) {
        return materialize(Lazy.enumerate(iterable));
    }

    public static <T> void each(Iterable<? extends T> targets, DoFunction<T> function) {
        materialize(Lazy.each(targets, function));
    }
}
