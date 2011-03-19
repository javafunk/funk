package org.smallvaluesofcool.misc.functional;

import org.smallvaluesofcool.misc.IterableUtils;
import org.smallvaluesofcool.misc.datastructures.TwoTuple;
import org.smallvaluesofcool.misc.functional.functors.DoFunction;
import org.smallvaluesofcool.misc.functional.functors.MapFunction;
import org.smallvaluesofcool.misc.functional.functors.NotPredicateFunction;
import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;
import org.smallvaluesofcool.misc.functional.iterators.*;

import java.util.Collections;
import java.util.Iterator;

import static org.smallvaluesofcool.misc.IteratorUtils.toIterable;
import static org.smallvaluesofcool.misc.Literals.listWith;
import static org.smallvaluesofcool.misc.Literals.twoTuple;
import static org.smallvaluesofcool.misc.functional.Eager.times;

public class Lazy {
    public static <T> Iterable<Iterable<T>> batch(Iterable<? extends T> iterable, int batchSize) {
        return toIterable(new BatchedIterator<T>(iterable.iterator(), batchSize));
    }

    public static <T> Iterable<T> cycle(Iterable<? extends T> iterable) {
        return toIterable(new CyclicIterator<T>(iterable.iterator()));
    }

    public static <T> Iterable<T> drop(Iterable<? extends T> iterable, int numberToTake) {
        return toIterable(new SubSequenceIterator<T>(iterable.iterator(), numberToTake, null));
    }

    @SuppressWarnings("unchecked")
    public static <T> Iterable<T> dropUntil(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        Iterator<? extends T> iterator = iterable.iterator();
        T next = null;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (predicate.matches(next)) {
                break;
            }
        }
        return toIterable(new ChainedIterator<T>(listWith(next).iterator(), (Iterator<T>) iterator));
    }

    @SuppressWarnings("unchecked")
    public static <T> Iterable<T> dropWhile(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        Iterator<? extends T> iterator = iterable.iterator();
        T next = null;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (!predicate.matches(next)) {
                break;
            }
        }
        return toIterable(new ChainedIterator<T>(listWith(next).iterator(), (Iterator<T>) iterator));
    }

    public static <T> Iterable<T> each(Iterable<? extends T> iterable, final DoFunction<T> doFunction) {
        final Iterator<? extends T> iterator = iterable.iterator();
        return toIterable(
                new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public T next() {
                        T next = iterator.next();
                        doFunction.actOn(next);
                        return next;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                });
    }

    public static <T> Iterable<TwoTuple<Integer, T>> enumerate(Iterable<? extends T> iterable) {
        return toIterable(new MappedIterator<T, TwoTuple<Integer, T>>(iterable.iterator(),
                new MapFunction<T, TwoTuple<Integer, T>>() {
                    private int index = 0;

                    public TwoTuple<Integer, T> map(T input) {
                        return twoTuple(index++, input);
                    }
                }));
    }

    public static <T> Iterable<T> filter(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return toIterable(new FilteredIterator<T>(iterable.iterator(), predicate));
    }

    public static <S, T> Iterable<T> map(Iterable<? extends S> iterable, final MapFunction<S, T> function) {
        return toIterable(new MappedIterator<S, T>(iterable.iterator(), function));
    }

    public static <T> TwoTuple<Iterable<T>,Iterable<T>> partition(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return twoTuple(filter(iterable, predicate), reject(iterable, predicate));
    }

    public static <T> Iterable<T> reject(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return toIterable(new FilteredIterator<T>(iterable.iterator(), new NotPredicateFunction<T>(predicate)));
    }

    public static <T> Iterable<T> slice(Iterable<? extends T> iterable, Integer start, Integer stop, Integer step) {
        return toIterable(new SubSequenceIterator<T>(iterable.iterator(), start, stop, step));
    }

    public static <T> Iterable<T> take(Iterable<? extends T> iterable, int numberToTake) {
        return toIterable(new SubSequenceIterator<T>(iterable.iterator(), null, numberToTake));
    }

    public static <T> Iterable<T> takeUntil(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return toIterable(new PredicatedIterator<T>(iterable.iterator(), new NotPredicateFunction<T>(predicate)));
    }

    public static <T> Iterable<T> takeWhile(Iterable<? extends T> iterable, PredicateFunction<T> predicate) {
        return toIterable(new PredicatedIterator<T>(iterable.iterator(), predicate));
    }

    public static <S, T> Iterable<TwoTuple<S, T>> zip(Iterable<? extends S> firstIterable, Iterable<? extends T> secondIterable) {
        return toIterable(new ZippedIterator<S, T>(firstIterable.iterator(), secondIterable.iterator()));
    }
}
