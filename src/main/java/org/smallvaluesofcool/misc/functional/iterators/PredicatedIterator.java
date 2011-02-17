package org.smallvaluesofcool.misc.functional.iterators;

import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PredicatedIterator<T> implements Iterator<T> {
    private Iterator<? extends T> iterator;
    private PredicateFunction<T> predicate;
    private T cachedNext;
    private boolean hasCachedNext = false;

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
                return false;
            }
        }
    }

    @Override
    public T next() {
        if (hasCachedNext) {
            hasCachedNext = false;
            return cachedNext;
        } else {
            T next = iterator.next();
            if (predicate.matches(next)) {
                return next;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}