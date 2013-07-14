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
import org.javafunk.funk.functors.Action;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.javafunk.funk.Eagerly.times;

public class SubSequenceIterator<T> extends CachingIterator<T> {
    private Iterator<? extends T> iterator;
    private Integer cursor = 0;
    private Integer start;
    private Integer stop;
    private Integer step;

    public SubSequenceIterator(Iterator<? extends T> iterator, Integer start, Integer stop, Integer step) {
        validateBounds(start, stop, step);

        this.iterator = checkNotNull(iterator);
        this.start = start == null ? 0 : start;
        this.stop = stop == null ? Integer.MAX_VALUE : stop;
        this.step = step == null ? 1 : step;

        progressToStart();
    }

    public SubSequenceIterator(Iterator<? extends T> iterator, Integer start, Integer stop) {
        this(iterator, start, stop, 1);
    }

    @Override
    protected T findNext() {
        if (shouldStop()) {
            throw new NoSuchElementException();
        } else {
            progressToNext();
            incrementCursor();
            if (iterator.hasNext()) {
                return iterator.next();
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    @Override
    protected void removeLast() {
        iterator.remove();
    }

    @Override public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("iterator", iterator)
                .append("start", start)
                .append("stop", stop)
                .append("step", step)
                .append("cursor", cursor)
                .toString();
    }

    private void validateBounds(Integer start, Integer stop, Integer step) {
        if (start != null && start < 0) {
            throw new IllegalArgumentException("Start must not be less than zero.");
        }
        if (stop != null && stop < 0) {
            throw new IllegalArgumentException("Stop must not be less than zero.");
        }
        if (start != null && stop != null && stop < start) {
            throw new IllegalArgumentException("Stop must be greater than start.");
        }
        if (step != null && step < 1) {
            throw new IllegalArgumentException("Step must be greater than zero.");
        }
    }

    private void progressToStart() {
        progressBy(start);
    }

    private void progressToNext() {
        if (cursor > start) {
            progressBy(step - 1);
        }
    }

    private void progressBy(int numberOfElements) {
        if (numberOfElements <= 0) {
            return;
        }
        times(numberOfElements, new Action<Integer>() {
            public void on(Integer input) {
                incrementCursor();
                if (iterator.hasNext()) {
                    iterator.next();
                }
            }
        });
    }

    private void incrementCursor() {
        cursor += 1;
    }

    private boolean shouldStop() {
        return cursor + step > stop;
    }
}
