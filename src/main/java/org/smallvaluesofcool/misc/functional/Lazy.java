package org.smallvaluesofcool.misc.functional;

import org.apache.commons.lang.NotImplementedException;

import java.util.Iterator;

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
}
