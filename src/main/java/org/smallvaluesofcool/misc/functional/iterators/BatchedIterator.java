package org.smallvaluesofcool.misc.functional.iterators;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.smallvaluesofcool.misc.IteratorUtils.toIterable;

public class BatchedIterator<T> implements Iterator<Iterable<T>> {
    private Map<Integer, T> cache = new HashMap<Integer, T>();
    private Iterator<? extends T> iterator;
    private int iteratorIndex = 0;
    private int batchOffset = 0;
    private int batchSize;

    public BatchedIterator(Iterator<? extends T> iterator, int batchSize) {
        this.iterator = iterator;
        this.batchSize = batchSize;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Iterable<T> next() {
        if (hasNext()) {
            setBatchOffset();
            prePopulateCacheForBatch();
            return nextBatchIterable();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private Iterable<T> nextBatchIterable() {
        return toIterable(new CacheBackedBatchIterator<T>(cache, batchOffset, batchSize));
    }

    private void setBatchOffset() {
        batchOffset = iteratorIndex;
    }

    private void prePopulateCacheForBatch() {
        for (int i = 0; i < batchSize; i++) {
            if (hasNext()) {
                cache.put(iteratorIndex++, iterator.next());
            }
        }
    }

    private class CacheBackedBatchIterator<S> implements Iterator<S> {
        private final Map<Integer, S> cache;
        private final int offset;
        private final int batchSize;
        private int cursor = 0;

        public CacheBackedBatchIterator(Map<Integer, S> cache, int offset, int batchSize) {
            this.cache = cache;
            this.offset = offset;
            this.batchSize = batchSize;
        }

        @Override
        public boolean hasNext() {
            return (cursor < batchSize) && cacheHasElementAt(nextElementIndex());
        }

        @Override
        public S next() {
            if (hasNext()) {
                return incrementCursorAndReturnElementAt(nextElementIndex());
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private S incrementCursorAndReturnElementAt(int elementIndex) {
            incrementCursor();
            return popFromCache(elementIndex);
        }

        private void incrementCursor() {
            cursor++;
        }

        private S popFromCache(int elementIndex) {
            return cache.remove(elementIndex);
        }

        private int nextElementIndex() {
            return offset + cursor;
        }

        private boolean cacheHasElementAt(int elementIndex) {
            return cache.containsKey(elementIndex);
        }
    }
}
