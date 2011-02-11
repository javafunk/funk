package org.smallvaluesofcool.misc.functional;

import org.smallvaluesofcool.misc.collections.TwoTuple;

import java.util.Iterator;

import static org.smallvaluesofcool.misc.collections.TwoTuple.twoTuple;

public class Lazy {
    public static <S, T> Iterable<T> map(Iterable<S> iterable, final MapFunction<S, T> function) {
        final Iterator<S> iterator = iterable.iterator();
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    public T next() {
                        return function.map(iterator.next());
                    }

                    public void remove() {
                        iterator.remove();
                    }
                };
            }
        };
    }

    public static <S, T> Iterable<TwoTuple<S, T>> zip(Iterable<S> iterable1, Iterable<T> iterable2) {
        final Iterator<S> iterator1 = iterable1.iterator();
        final Iterator<T> iterator2 = iterable2.iterator();
        return new Iterable<TwoTuple<S, T>>() {
            @Override
            public Iterator<TwoTuple<S, T>> iterator() {
                return new Iterator<TwoTuple<S, T>>() {
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
                };
            }
        };
    }

    public static <T> Iterable<TwoTuple<Integer, T>> enumerate(Iterable<T> iterable) {
        final Iterator<T> iterator = iterable.iterator();
        return new Iterable<TwoTuple<Integer, T>>() {
            @Override
            public Iterator<TwoTuple<Integer, T>> iterator() {
                return new Iterator<TwoTuple<Integer, T>>() {
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
                };
            }
        };
    }
}
