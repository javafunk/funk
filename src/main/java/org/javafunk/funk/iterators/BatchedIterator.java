/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Collections.unmodifiableList;

public class BatchedIterator<T> implements Iterator<Iterable<T>> {
    private Iterator<? extends T> iterator;
    private int batchSize;

    public BatchedIterator(Iterator<? extends T> iterator, int batchSize) {
        this.iterator = iterator;
        if (batchSize <= 0) {
            throw new IllegalArgumentException("Batch size must be greater than zero.");
        }
        this.batchSize = batchSize;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Iterable<T> next() {
        if (hasNext()) {
            List<T> nextBatch = new ArrayList<T>();
            for(int i = 0; i < batchSize; i++) {
                if(iterator.hasNext()) {
                    nextBatch.add(iterator.next());
                }
            }
            return unmodifiableList(nextBatch);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
