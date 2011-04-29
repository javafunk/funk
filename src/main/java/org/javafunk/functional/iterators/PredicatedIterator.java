package org.javafunk.functional.iterators;

import org.javafunk.functional.functors.PredicateFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PredicatedIterator<T> implements Iterator<T> {
    private Iterator<? extends T> iterator;
    private PredicateFunction<T> predicate;
    private IteratorCache<T> matchCache = new IteratorCache<T>();
    private IteratorRemovalFlag removalFlag = new IteratorRemovalFlag();

    public PredicatedIterator(Iterator<? extends T> iterator, PredicateFunction<T> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        if (matchCache.isPopulated()) {
            return true;
        } else {
            if (iterator.hasNext()) {
                T next = iterator.next();
                if (predicate.matches(next)) {
                    matchCache.store(next);
                    removalFlag.disable();
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public T next() {
        if (matchCache.isPopulated()) {
            removalFlag.enable();
            return matchCache.fetch();
        } else {
            T next = iterator.next();
            if (predicate.matches(next)) {
                removalFlag.enable();
                return next;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    @Override
    public void remove() {
        if (removalFlag.isEnabled()) {
            removalFlag.disable();
            iterator.remove();
        } else {
            throw new IllegalStateException();
        }
    }
}