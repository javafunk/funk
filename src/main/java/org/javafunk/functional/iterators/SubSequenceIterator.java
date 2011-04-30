package org.javafunk.functional.iterators;

import org.javafunk.functional.functors.DoFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.javafunk.functional.Eager.times;

public class SubSequenceIterator<T> extends CachingIterator<T> {
    private Iterator<? extends T> iterator;
    private Integer cursor = 0;
    private Integer start;
    private Integer stop;
    private Integer step;

    public SubSequenceIterator(Iterator<? extends T> iterator, Integer start, Integer stop, Integer step) {
        validateBounds(start, stop, step);

        this.iterator = iterator;
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

    private void validateBounds(Integer start, Integer stop, Integer step) {
        if (start != null && start < 0) {
            throw new IllegalArgumentException("Start must not be less than zero.");
        }
        if (stop != null && stop < 0) {
            throw new IllegalArgumentException("Stop must not be less than zero.");
        }
        if (start != null && stop!= null && stop < start) {
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
        times(numberOfElements, new DoFunction<Integer>() {
            public void actOn(Integer input) {
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
