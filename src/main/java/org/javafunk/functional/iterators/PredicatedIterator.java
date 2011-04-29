package org.javafunk.functional.iterators;

import org.javafunk.functional.functors.PredicateFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PredicatedIterator<T> implements Iterator<T> {
    private Iterator<? extends T> iterator;
    private PredicateFunction<T> predicate;
    private T cachedNext;
    private boolean hasCachedNext = false;
    private boolean canRemove = false;

    public PredicatedIterator(Iterator<? extends T> iterator, PredicateFunction<T> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        if (hasCachedNext) {
            return true;
        } else {
            if (iterator.hasNext()) {
                cachedNext = iterator.next();
            } else {
                return false;
            }
            if (predicate.matches(cachedNext)) {
                hasCachedNext = true;
                return true;
            } else {
                canRemove = false;
                return false;
            }
        }
    }

    @Override
    public T next() {
        if (hasCachedNext) {
            hasCachedNext = false;
            canRemove = true;
            return cachedNext;
        } else {
            T next = iterator.next();
            if (predicate.matches(next)) {
                canRemove = true;
                return next;
            } else {
                canRemove = false;
                throw new NoSuchElementException();
            }
        }
    }

    @Override
    public void remove() {
        if (canRemove) {
            iterator.remove();
            canRemove = false;
        } else {
            throw new IllegalStateException();
        }
    }
}