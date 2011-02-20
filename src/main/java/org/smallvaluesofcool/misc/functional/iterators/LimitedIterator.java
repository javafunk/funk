package org.smallvaluesofcool.misc.functional.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LimitedIterator<T> implements Iterator<T> {
    private int index = 0;
    private Iterator<? extends T> iterator;
    private int limit;

    public LimitedIterator(Iterator<? extends T> iterator, int limit) {
        this.iterator = iterator;
        this.limit = limit;
    }

    @Override
    public boolean hasNext() {
        return (index < limit) && iterator.hasNext();
    }

    @Override
    public T next() {
        if (hasNext()) {
            T next = iterator.next();
            index++;
            return next;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
