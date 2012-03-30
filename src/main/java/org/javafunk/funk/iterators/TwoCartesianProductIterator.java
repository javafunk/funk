package org.javafunk.funk.iterators;

import org.javafunk.funk.datastructures.tuples.Pair;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.javafunk.funk.Literals.tuple;

public class TwoCartesianProductIterator<S, T> implements Iterator<Pair<S, T>> {
    private final Iterator<S> firstIterator;
    private final Iterable<T> secondIterable;
    private Iterator<T> currentSecondIterator;
    private S currentFirstElement;

    public TwoCartesianProductIterator(Iterable<S> firstIterable, Iterable<T> secondIterable) {
        this.firstIterator = firstIterable.iterator();
        this.secondIterable = secondIterable;
        this.currentSecondIterator = secondIterable.iterator();
    }

    @Override public boolean hasNext() {
        return firstIterator.hasNext() || currentSecondIterator.hasNext();
    }

    @Override public Pair<S, T> next() {
        if (currentFirstElement == null) {
            if (firstIterator.hasNext()) {
                currentFirstElement = firstIterator.next();
            }
        }

        if (currentSecondIterator.hasNext()) {
            return tuple(currentFirstElement, currentSecondIterator.next());
        } else if(firstIterator.hasNext()) {
            currentFirstElement = firstIterator.next();
            currentSecondIterator = secondIterable.iterator();
            if (currentSecondIterator.hasNext()) {
                return tuple(currentFirstElement, currentSecondIterator.next());
            }
        }

        throw new NoSuchElementException();
    }

    @Override public void remove() {
        throw new UnsupportedOperationException();
    }
}
