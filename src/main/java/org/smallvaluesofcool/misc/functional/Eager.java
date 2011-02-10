package org.smallvaluesofcool.misc.functional;

import java.util.Iterator;

import static org.smallvaluesofcool.misc.collections.IteratorUtils.toIterable;

public class Eager {

    public static <T> T reduce(Iterable<T> iterable, ReduceFunction<T> function, T initialValue) {
        T accumulator = initialValue;
        for (T element : iterable) {
            accumulator = function.accumulate(accumulator, element);
        }
        return accumulator;
    }

    public static <T> T reduce(Iterable<T> iterable, ReduceFunction<T> function) {
        final Iterator<T> iterator = iterable.iterator();
        final T firstElement = iterator.next();
        final Iterable<T> restOfElements = toIterable(iterator);
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
}
