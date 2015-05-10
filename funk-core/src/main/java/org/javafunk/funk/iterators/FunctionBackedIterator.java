package org.javafunk.funk.iterators;

import org.javafunk.funk.functors.functions.NullaryFunction;

import java.util.Iterator;

public class FunctionBackedIterator<T> implements Iterator<T> {
    private final NullaryFunction<T> function;

    public FunctionBackedIterator(NullaryFunction<T> function) {
        this.function = function;
    }

    @Override public boolean hasNext() {
        return true;
    }

    @Override public T next() {
        return function.call();
    }

    @Override public void remove() {
        throw new UnsupportedOperationException();
    }
}
