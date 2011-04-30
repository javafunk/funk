package org.javafunk.functional.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class CachingIterator<T> implements Iterator<T> {
    private IteratorCache<T> matchCache = new IteratorCache<T>();
    private IteratorRemovalFlag removalFlag = new IteratorRemovalFlag();

    @Override
    public boolean hasNext() {
        if (matchCache.isPopulated()) {
            return true;
        } else {
            try {
                matchCache.store(findNext());
                removalFlag.disable();
                return true;
            } catch (NoSuchElementException exception) {
                return false;
            }
        }
    }

    @Override
    public T next() {
        if (matchCache.isPopulated()) {
            removalFlag.enable();
            return matchCache.fetch();
        } else {
            T next = findNext();
            removalFlag.enable();
            return next;
        }
    }

    @Override
    public void remove() {
        if (removalFlag.isEnabled()) {
            removalFlag.disable();
            removeLast();
        } else {
            throw new IllegalStateException();
        }
    }

    protected abstract T findNext();

    protected abstract void removeLast();

    private static class IteratorCache<T> {
        private T match;
        private boolean hasMatch = false;

        void store(T match) {
            this.hasMatch = true;
            this.match = match;
        }

        T fetch() {
            this.hasMatch = false;
            return this.match;
        }

        boolean isPopulated() {
            return this.hasMatch;
        }

    }

    private static class IteratorRemovalFlag {
        private boolean canRemove = false;

        boolean isEnabled() {
            return canRemove;
        }

        void enable() {
            this.canRemove = true;
        }

        void disable() {
            this.canRemove = false;
        }
    }
}
