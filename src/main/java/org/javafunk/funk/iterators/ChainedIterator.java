package org.javafunk.funk.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static org.javafunk.funk.Literals.listWith;

public class ChainedIterator<T> implements Iterator<T> {
    private Iterator<? extends Iterator<? extends T>> iteratorsIterator;
    private Iterator<? extends T> currentIterator;

    public ChainedIterator(Iterable<? extends Iterator<? extends T>> iteratorCollection) {
        iteratorsIterator = iteratorCollection.iterator();
        if (iteratorsIterator.hasNext()) {
            currentIterator = iteratorsIterator.next();
        }
    }

    @Override
    public boolean hasNext() {
        if (currentIterator == null) {
            return false;
        }
        if (currentIterator.hasNext()) {
            return true;
        } else {
            while (iteratorsIterator.hasNext()) {
                currentIterator = iteratorsIterator.next();
                if (currentIterator.hasNext()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public T next() {
        if (hasNext()) {
            return currentIterator.next();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        currentIterator.remove();
    }

    @SuppressWarnings("unchecked")
    public ChainedIterator(Iterator<? extends T> i) {
        this(asList(i));
    }

    @SuppressWarnings("unchecked")
    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2) {
        this(asList(i1, i2));
    }

    @SuppressWarnings("unchecked")
    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2, Iterator<? extends T> i3) {
        this(asList(i1, i2, i3));
    }

    @SuppressWarnings("unchecked")
    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2, Iterator<? extends T> i3, Iterator<? extends T> i4) {
        this(asList(i1, i2, i3, i4));
    }

    @SuppressWarnings("unchecked")
    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2, Iterator<? extends T> i3, Iterator<? extends T> i4,
            Iterator<? extends T> i5) {
        this(asList(i1, i2, i3, i4, i5));
    }

    @SuppressWarnings("unchecked")
    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2, Iterator<? extends T> i3, Iterator<? extends T> i4,
            Iterator<? extends T> i5, Iterator<? extends T> i6) {
        this(asList(i1, i2, i3, i4, i5, i6));
    }

    @SuppressWarnings("unchecked")
    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2, Iterator<? extends T> i3, Iterator<? extends T> i4,
            Iterator<? extends T> i5, Iterator<? extends T> i6, Iterator<? extends T>... i7on) {
        this(listWith(i1, i2, i3, i4, i5, i6).and(i7on));
    }
}
