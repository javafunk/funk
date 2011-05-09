package org.javafunk.functional.iterators;

import org.javafunk.functional.functors.Predicate;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PredicatedIterator<T> extends CachingIterator<T> {
    private Iterator<? extends T> iterator;
    private Predicate<? super T> predicate;

    public PredicatedIterator(Iterator<? extends T> iterator, Predicate<? super T> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
    }

    @Override
    protected T findNext() {
        if (iterator.hasNext()) {
            T next = iterator.next();
            if (predicate.evaluate(next)) {
                return next;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    protected void removeLast() {
        iterator.remove();
    }
}