package org.javafunk.functional.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CyclicIterator<T> implements Iterator<T> {
    private Iterator<? extends T> iterator;
    private List<T> elements = new ArrayList<T>();
    private int index = 0;

    public CyclicIterator(Iterator<? extends T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        if (iterator.hasNext()) {
            T next = iterator.next();
            elements.add(next);
            return next;
        } else {
            T next = elements.get(index);
            if (index == (elements.size() - 1)) {
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
}
