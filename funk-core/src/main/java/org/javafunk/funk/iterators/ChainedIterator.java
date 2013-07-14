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

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.javafunk.funk.Literals.iteratorBuilderWith;
import static org.javafunk.funk.Literals.iteratorWith;

public class ChainedIterator<T> implements Iterator<T> {
    private Iterator<? extends Iterator<? extends T>> iteratorsIterator;
    private Iterator<? extends T> currentIterator;

    public ChainedIterator(Iterator<? extends Iterator<? extends T>> iteratorsIterator) {
        this.iteratorsIterator = iteratorsIterator;
        if (this.iteratorsIterator.hasNext()) {
            currentIterator = this.iteratorsIterator.next();
        }
    }

    @Override
    public boolean hasNext() {
        if (currentIterator == null) {
            return false;
        }
        if (currentIterator.hasNext()) {
            return true;
        } else {
            while (iteratorsIterator.hasNext()) {
                currentIterator = iteratorsIterator.next();
                if (currentIterator.hasNext()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public T next() {
        if (hasNext()) {
            return currentIterator.next();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        currentIterator.remove();
    }

    @Override public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("currentIterator", currentIterator)
                .append("remainingIterators", iteratorsIterator)
                .toString();
    }

    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2) {
        this(iteratorWith(i1, i2));
    }

    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2, Iterator<? extends T> i3) {
        this(iteratorWith(i1, i2, i3));
    }

    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2, Iterator<? extends T> i3, Iterator<? extends T> i4) {
        this(iteratorWith(i1, i2, i3, i4));
    }

    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2, Iterator<? extends T> i3, Iterator<? extends T> i4,
            Iterator<? extends T> i5) {
        this(iteratorWith(i1, i2, i3, i4, i5));
    }

    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2, Iterator<? extends T> i3, Iterator<? extends T> i4,
            Iterator<? extends T> i5, Iterator<? extends T> i6) {
        this(iteratorWith(i1, i2, i3, i4, i5, i6));
    }

    public ChainedIterator(
            Iterator<? extends T> i1, Iterator<? extends T> i2, Iterator<? extends T> i3, Iterator<? extends T> i4,
            Iterator<? extends T> i5, Iterator<? extends T> i6, Iterator<? extends T>... i7on) {
        this(iteratorBuilderWith(i1, i2, i3, i4, i5, i6).and(i7on).build());
    }
}
