package org.smallvaluesofcool.misc.functional.iterators;

import org.smallvaluesofcool.misc.functional.functors.PredicateFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilteredIterator<T> implements Iterator<T> {
    private Iterator<? extends T> iterator;
    private PredicateFunction<T> predicate;
    private T match;
    private boolean hasMatch = false;
    private boolean canRemove = false;

    public FilteredIterator(Iterator<? extends T> iterator, PredicateFunction<T> predicate) {
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
                    removeAllowed(true);
                    return next;
                }
            }
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        if(canRemove()) {
            removeAllowed(false);
            iterator.remove();
        } else {
            throw new IllegalStateException();
        }
    }

    private void pushMatch(T match) {
        matchStored(true);
        removeAllowed(false);
        this.match = match;
    }

    private T popMatch() {
        matchStored(false);
        removeAllowed(true);
        T match = this.match;
        return match;
    }

    private boolean hasMatch() {
        return this.hasMatch;
    }

    private void matchStored(boolean hasMatch) {
        this.hasMatch = hasMatch;
    }

    private boolean canRemove() {
        return canRemove;
    }

    private void removeAllowed(boolean canRemove) {
        this.canRemove = canRemove;
    }
}
