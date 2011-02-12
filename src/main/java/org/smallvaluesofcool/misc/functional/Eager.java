package org.smallvaluesofcool.misc.functional;

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
        return reduce(iterable, integerAccumulator());
    }

    public static ReduceFunction<Integer> integerAccumulator() {
        return new ReduceFunction<Integer>() {
            public Integer accumulate(Integer accumulator, Integer element) {
                return accumulator + element;
            }
        };
    }

    public static ReduceFunction<Long> longAccumulator() {
        return new ReduceFunction<Long>() {
            public Long accumulate(Long accumulator, Long element) {
                return accumulator + element;
            }
        };
    }

    public static <T> Boolean any(Iterable<? extends T> iterable, Predicate<T> predicate) {
        for (T item : iterable) {
            if (predicate.matches(item)) {
                return true;
            }
        }
        return false;
    }

    public static <T> Boolean all(Iterable<? extends T> iterable, Predicate<T> predicate) {
        for (T item : iterable) {
            if (!predicate.matches(item)) {
                return false;
            }
        }
        return true;
    }

    public static <T> Boolean none(Iterable<? extends T> items, Predicate<T> predicate) {
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

    public static <T> void each(Iterable<? extends T> targets, DoFunction<T> doFunction) {
        materialize(Lazy.each(targets, doFunction));
    }
}
