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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;

public class CyclicIterator<T> implements Iterator<T> {
    private Iterator<? extends T> iterator;
    private Integer numberOfTimesToRepeat;
    private List<T> elements = new ArrayList<T>();
    private int repeats = 0;
    private int index = 0;

    public CyclicIterator(Iterator<? extends T> iterator) {
        this.iterator = iterator;
    }

    public CyclicIterator(Iterator<? extends T> iterator, int numberOfTimesToRepeat) {
        if (numberOfTimesToRepeat < 0) {
            throw new IllegalArgumentException("Number of times to repeat cannot be negative");
        }
        this.iterator = checkNotNull(iterator);
        this.numberOfTimesToRepeat = numberOfTimesToRepeat;
        this.repeats = 1;
    }

    @Override
    public boolean hasNext() {
        if (shouldContinueCyclingInfinitely()) { return true; }
        if (shouldNotCycleAtAll()) { return false; }
        if (iteratorContainsMoreElements()) { return true; }
        if (!elementsAreCached()) { return false; }
        return hasRepeatsRemaining();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (iteratorContainsMoreElements()) {
            T next = iterator.next();
            elements.add(next);
            return next;
        } else {
            T next = elements.get(index);
            if (index == (elements.size() - 1)) {
                if (!shouldCycleInfinitely()) {
                    repeats++;
                }
                index = 0;
            } else {
                index++;
            }
            return next;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("iterator", iterator)
                .append("numberOfTimesToRepeat", numberOfTimesToRepeat == null ? "infinite" : numberOfTimesToRepeat)
                .append("numberOfRepeatsCompleted", repeats)
                .append("indexInCurrentRepeat", index)
                .toString();
    }

    private boolean shouldCycleInfinitely() {
        return numberOfTimesToRepeat == null;
    }

    private boolean shouldContinueCyclingInfinitely() {
        return shouldCycleInfinitely() && (iteratorContainsMoreElements() || elementsAreCached());
    }

    private boolean shouldNotCycleAtAll() {
        return numberOfTimesToRepeat != null && numberOfTimesToRepeat == 0;
    }

    private boolean iteratorContainsMoreElements() {
        return iterator.hasNext();
    }

    private boolean elementsAreCached() {
        return !elements.isEmpty();
    }

    private boolean hasRepeatsRemaining() {
        return repeats < numberOfTimesToRepeat;
    }
}
