/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.javafunk.funk.functors.predicates.UnaryPredicate;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilteredIterator<T> extends CachingIterator<T> {
    private Iterator<? extends T> iterator;
    private UnaryPredicate<? super T> predicate;

    public FilteredIterator(Iterator<? extends T> iterator, UnaryPredicate<? super T> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
    }

    @Override
    protected T findNext() {
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (predicate.evaluate(next)) {
                return next;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    protected void removeLast() {
        iterator.remove();
    }
}
