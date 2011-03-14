package org.smallvaluesofcool.misc.datastructures;

import org.smallvaluesofcool.misc.IteratorUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntegerRange implements Range<Integer> {
    private final Integer start;
    private final Integer end;
    private final Integer step;

    public IntegerRange(Integer start, Integer end) {
        this(start, end, null);
    }

    public IntegerRange(Integer start, Integer end, Integer step) {
        this.step = (step == null) ? 1 : step;
        if (this.step == 0) {
            throw new IllegalArgumentException("Step size cannot be zero");
        }
        this.start = (start == null) ? 0 : start;
        this.end = (end == null) ? ((this.step > 0) ? Integer.MAX_VALUE : Integer.MIN_VALUE) : end;
    }

    @Override
    public boolean contains(Object other) {
        if (other instanceof Integer) {
            int valueToFind = (Integer) other;
            for (int valueInRange : this) {
                if (valueInRange == valueToFind) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Integer getStart() {
        return start;
    }

    @Override
    public Integer getEnd() {
        return end;
    }

    @Override
    public Integer getStep() {
        return step;
    }

    @Override
    public Iterator<Integer> iterator() {
        if (start < end && step < 0 || start > end && step > 0) {
            return IteratorUtils.emptyIterator();
        }

        return new IntegerRangeIterator();
    }

    private class IntegerRangeIterator implements Iterator<Integer> {
        private int currentValue = start;

        @Override
        public boolean hasNext() {
            return (step < 0) ? (currentValue > end) : (currentValue < end);
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                int next = currentValue;
                currentValue += step;
                return next;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
