package org.smallvaluesofcool.misc.functional.iterators;

import org.smallvaluesofcool.misc.functional.functors.DoFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.smallvaluesofcool.misc.functional.Eager.times;

public class SubSequenceIterator<T> implements Iterator<T> {
    private Iterator<? extends T> iterator;
    private T match;
    private int cursor = 0;
    private boolean hasMatch = false;
    private boolean canRemove = false;
    private Integer start;
    private Integer stop;
    private Integer step;

    public SubSequenceIterator(Iterator<? extends T> iterator, Integer start, Integer stop, Integer step) {
        validateBounds(start, stop, step);

        this.iterator = iterator;
        this.start = start == null ? 0 : start;
        this.stop = stop == null ? Integer.MAX_VALUE : stop;
        this.step = step;
        
        progressToStart();
    }

    public SubSequenceIterator(Iterator<? extends T> iterator, Integer start, Integer stop) {
        this(iterator, start, stop, 1);
    }

    @Override
    public boolean hasNext() {
        if (hasMatch()) {
            return true;
        } else if (shouldStop()) {
            return false;
        } else {
            progressToNext();
            incrementCursor();
            if (iterator.hasNext()) {
                pushMatch(iterator.next());
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public T next() {
        if (hasMatch()) {
            return popMatch();
        } else if (shouldStop()) {
            throw new NoSuchElementException();
        } else {
            progressToNext();
            incrementCursor();
            if (iterator.hasNext()) {
                removeAllowed(true);
                return iterator.next();
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    @Override
    public void remove() {
        if (canRemove()) {
            removeAllowed(false);
            iterator.remove();
        } else {
            throw new IllegalStateException();
        }
    }

    private void validateBounds(Integer start, Integer stop, Integer step) {
        if (start != null && start < 0) {
            throw new IllegalArgumentException("Start must not be negative.");
        }
        if (stop != null && stop < 1) {
            throw new IllegalArgumentException("Stop must be a positive integer.");
        }
        if (step < 1) {
            throw new IllegalArgumentException("Step must be a positive integer.");
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

    private void pushMatch(T match) {
        matchStored(true);
        removeAllowed(false);
        this.match = match;
    }

    private T popMatch() {
        matchStored(false);
        removeAllowed(true);
        T match = this.match;
        return match;
    }

    private boolean hasMatch() {
        return this.hasMatch;
    }

    private void matchStored(boolean hasMatch) {
        this.hasMatch = hasMatch;
    }

    private boolean canRemove() {
        return canRemove;
    }

    private void removeAllowed(boolean canRemove) {
        this.canRemove = canRemove;
    }
}
