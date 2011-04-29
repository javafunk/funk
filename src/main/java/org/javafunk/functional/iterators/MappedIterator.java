package org.javafunk.functional.iterators;

import org.javafunk.functional.functors.MapFunction;

import java.util.Iterator;

public class MappedIterator<S, T> implements Iterator<T> {
    private Iterator<? extends S> iterator;
    private MapFunction<S, T> function;

    public MappedIterator(Iterator<? extends S> iterator, MapFunction<S, T> function) {
        this.iterator = iterator;
        this.function = function;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public T next() {
        return function.map(iterator.next());
    }

    public void remove() {
        iterator.remove();
    }
}
