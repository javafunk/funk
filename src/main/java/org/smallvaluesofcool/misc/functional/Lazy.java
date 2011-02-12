package org.smallvaluesofcool.misc.functional;

import org.smallvaluesofcool.misc.datastructures.TwoTuple;

import java.util.Iterator;

import static org.smallvaluesofcool.misc.collections.IteratorUtils.toIterable;
import static org.smallvaluesofcool.misc.datastructures.TwoTuple.twoTuple;

public class Lazy {
    public static <S, T> Iterable<T> map(Iterable<? extends S> iterable, final MapFunction<S, T> function) {
        final Iterator<? extends S> iterator = iterable.iterator();
        return toIterable(
                new Iterator<T>() {
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    public T next() {
                        return function.map(iterator.next());
                    }

                    public void remove() {
                        iterator.remove();
                    }
                });
    }

    public static <S, T> Iterable<TwoTuple<S, T>> zip(Iterable<? extends S> iterable1, Iterable<? extends T> iterable2) {
        final Iterator<? extends S> iterator1 = iterable1.iterator();
        final Iterator<? extends T> iterator2 = iterable2.iterator();
        return toIterable(
                new Iterator<TwoTuple<S, T>>() {
                    @Override
                    public boolean hasNext() {
                        return iterator1.hasNext() && iterator2.hasNext();
                    }

                    @Override
                    public TwoTuple<S, T> next() {
                        return twoTuple(iterator1.next(), iterator2.next());
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                });
    }

    public static <T> Iterable<TwoTuple<Integer, T>> enumerate(Iterable<? extends T> iterable) {
        final Iterator<? extends T> iterator = iterable.iterator();
        return toIterable(
                new Iterator<TwoTuple<Integer, T>>() {
                    private Integer index = 0;

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public TwoTuple<Integer, T> next() {
                        return twoTuple(index++, iterator.next());
                    }

                    @Override
                    public void remove() {
                        iterator.remove();
                    }
                });
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
}
