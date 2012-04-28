package org.javafunk.funk.generators;

import org.javafunk.funk.behaviours.Generator;

public abstract class AbstractGenerator<T> implements Generator<T> {
    @Override public boolean hasNext() {
        return true;
    }

    @Override public void remove() {
        throw new UnsupportedOperationException();
    }
}
