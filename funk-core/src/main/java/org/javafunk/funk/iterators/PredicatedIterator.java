/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.javafunk.funk.functors.predicates.UnaryPredicate;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;

public class PredicatedIterator<T> extends CachingIterator<T> {
    private Iterator<? extends T> iterator;
    private UnaryPredicate<? super T> predicate;

    public PredicatedIterator(Iterator<? extends T> iterator, UnaryPredicate<? super T> predicate) {
        this.iterator = iterator;
        this.predicate = checkNotNull(predicate);
    }

    @Override
    protected T findNext() {
        if (iterator.hasNext()) {
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

    @Override public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("iterator", iterator)
                .append("predicate", predicate)
                .toString();
    }
}
