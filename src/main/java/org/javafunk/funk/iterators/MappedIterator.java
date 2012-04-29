/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.javafunk.funk.functors.functions.UnaryFunction;

import java.util.Iterator;

public class MappedIterator<S, T> implements Iterator<T> {
    private Iterator<? extends S> iterator;
    private UnaryFunction<? super S, ? extends T> function;

    public MappedIterator(Iterator<? extends S> iterator, UnaryFunction<? super S, ? extends T> function) {
        this.iterator = iterator;
        this.function = function;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public T next() {
        return function.call(iterator.next());
    }

    public void remove() {
        iterator.remove();
    }
}
