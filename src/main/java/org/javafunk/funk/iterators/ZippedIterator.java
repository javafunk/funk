package org.javafunk.funk.iterators;

import org.javafunk.funk.datastructures.TwoTuple;

import java.util.Iterator;

import static org.javafunk.funk.Literals.tuple;

public class ZippedIterator<S, T> implements Iterator<TwoTuple<S, T>> {
    private Iterator<? extends S> firstIterator;
    private Iterator<? extends T> secondIterator;

    public ZippedIterator(Iterator<? extends S> firstIterator, Iterator<? extends T> secondIterator) {
        this.firstIterator = firstIterator;
        this.secondIterator = secondIterator;
    }

    @Override
    public boolean hasNext() {
        return firstIterator.hasNext() && secondIterator.hasNext();
    }

    @Override
    public TwoTuple<S, T> next() {
        return tuple(firstIterator.next(), secondIterator.next());
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}