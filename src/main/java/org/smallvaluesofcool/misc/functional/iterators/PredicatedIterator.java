package org.smallvaluesofcool.misc.functional.iterators;

import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PredicatedIterator<T> implements Iterator<T> {
    private T match;
    private Iterator<? extends T> iterator;
    private PredicateFunction<T> predicate;

    public PredicatedIterator(Iterator<? extends T> iterator, PredicateFunction<T> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        if (hasMatch()) {
            return true;
        } else {
            while (iterator.hasNext()) {
                T next = iterator.next();
                if (predicate.matches(next)) {
                    pushMatch(next);
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public T next() {
        if (hasMatch()) {
            return popMatch();
        } else {
            while (iterator.hasNext()) {
                T next = iterator.next();
                if (predicate.matches(next)) {
                    return next;
                }
            }
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void pushMatch(T match) {
        this.match = match;
    }

    private T popMatch() {
        T match = this.match;
        this.match = null;
        return match;
    }

    private T readMatch() {
        return this.match;
    }

    private boolean hasMatch() {
        return readMatch() != null;
    }
}
