package org.javafunk.iterators;

import org.javafunk.functors.Mapper;

import java.util.Iterator;

public class MappedIterator<S, T> implements Iterator<T> {
    private Iterator<? extends S> iterator;
    private Mapper<? super S, ? extends T> function;

    public MappedIterator(Iterator<? extends S> iterator, Mapper<? super S, ? extends T> function) {
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
