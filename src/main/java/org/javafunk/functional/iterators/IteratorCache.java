package org.javafunk.functional.iterators;

class IteratorCache<T> {
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